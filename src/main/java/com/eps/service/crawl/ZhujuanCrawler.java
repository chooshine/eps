package com.eps.service.crawl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.crawl.ZhujuanDao;
import com.eps.mybatis.auto.ZhujuanImagesMapper;
import com.eps.mybatis.auto.ZhujuanQuesMapper;
import com.eps.mybatis.auto.entity.ZhujuanImages;
import com.eps.mybatis.auto.entity.ZhujuanImagesExample;
import com.eps.mybatis.auto.entity.ZhujuanQues;
import com.eps.mybatis.auto.entity.ZhujuanQuesExample;
import com.eps.mybatis.auto.entity.ZhujuanQuesWithBLOBs;
import com.eps.service.crawl.ZhujuanPool.Worker;
import com.eps.service.crawl.bean.Task;
import com.eps.service.crawl.impl.ZhujuanParse;
import com.eps.utils.HttpHelper;
import com.eps.utils.HttpRespons;
import com.eps.utils.LStrMap;
import com.eps.utils.MapUtils;
import com.eps.utils.UStrMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class ZhujuanCrawler {

	private Logger log = LoggerFactory.getLogger(ZhujuanCrawler.class);

	@Autowired
	private ZhujuanDao dao;
	@Autowired
	private ZhujuanQuesMapper quesDao;
	@Autowired
	private ZhujuanImagesMapper imageDao;
	public static final String ZHUJUANHOST = "http://www.zujuan.com";
	public static final String ZHUJUANDOMAIN = "www.zujuan.com";
	public static final String ZHUJUANDOWNLOADHOST = "static.zujuan.com";
	public static final int ZHUJUANPOST = 80;
	public static final String ZHUJUANHTTP = "http://";
	public static final String ZHUJUANLOGINNAME = "zjhz4z";
	public static final String ZHUJUANLOGINPWD = "22896n";
	private static final int DEFAULT_PAGE_SIZE = 50;
	private static final String ANSWERPATH = "http://image.zujuan.com/Answer";
	private static final String PARSEPATH = "http://image.zujuan.com/Parse";

	public CloseableHttpClient client;
	public CookieStore store;
	@Value("${zhujuan.image.save.path}")
	private String imageSavePath;
	public static ZhujuanParse parser = new ZhujuanParse();

	public void initClient() {
		client = HttpClientBuilder.create().build();

		// client.getHostConfiguration().setHost(ZHUJUANHOST, ZHUJUANPOST,
		// ZHUJUANHTTP);
	}

	public void insertZsd(String parentId, String bankId) {
		try {
			List<UStrMap<Object>> list = new ArrayList<UStrMap<Object>>();
			UStrMap<Object> item = UStrMap.newInstance();
			item.put("id", parentId);
			item.put("name", "");
			item.put("parentid", 0);
			item.put("type", 10);
			item.put("bankId", bankId);
			list.add(item);
			crawlCategory(parentId, list, "10", bankId);
			if (list.size() > 0)
				dao.batchInsert(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertZj(String html, String bankId) {
		ZhujuanParse parser = new ZhujuanParse();
		List<UStrMap<Object>> list = parser.parseRoot(html, "20", bankId);
		if (list.size() > 0)
			dao.batchInsert(list);
		for (UStrMap<Object> item : list) {
			List<UStrMap<Object>> result = new ArrayList<UStrMap<Object>>();
			String id = (String) item.get("id");
			try {
				crawlCategory(id, result, "20", bankId);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (result.size() > 0) {
				dao.batchInsert(result);
			}
		}

	}

	public void insertQues() {

	}

	private HttpUriRequest getLoginMethod() {
		HttpPost post = new HttpPost(ZHUJUANHOST);
		NameValuePair action = new BasicNameValuePair("action", "login");
		NameValuePair name = new BasicNameValuePair("username",
				ZHUJUANLOGINNAME);
		NameValuePair password = new BasicNameValuePair("password",
				ZHUJUANLOGINPWD);
		NameValuePair check = new BasicNameValuePair("checkverifycode", "false");
		post.setEntity(EntityBuilder.create()
				.setParameters(action, name, password, check).build());
		return post;
	}

	private HttpUriRequest getSearchMethod(int categoryId, int bankId,
			int pageNo, int pageSize) {
		HttpPost method = new HttpPost(ZHUJUANHOST
				+ "/Web/Handler1.ashx?action=queslistquery");
		NameValuePair bankid = new BasicNameValuePair("bankid",
				String.valueOf(bankId));
		NameValuePair categroryId = new BasicNameValuePair("categoryid",
				categoryId + "");
		NameValuePair questype = new BasicNameValuePair("questype", "0");
		NameValuePair quesdiff = new BasicNameValuePair("quesdiff", "0");
		NameValuePair curpage = new BasicNameValuePair("curpage", pageNo + "");
		NameValuePair pagesize = new BasicNameValuePair("pagesize", pageSize
				+ "");
		NameValuePair quesyear = new BasicNameValuePair("quesyear", "0");
		HttpEntity entity = EntityBuilder
				.create()
				.setParameters(bankid, categroryId, questype, quesdiff,
						curpage, pagesize, quesyear)
				.setContentType(ContentType.APPLICATION_FORM_URLENCODED)
				.build();
		method.setEntity(entity);
		return method;
	}

	public void zhujuanLogin() {
		initClient();
		HttpUriRequest post = getLoginMethod();
		HttpClientContext context = HttpClientContext.create();
		try {
			CloseableHttpResponse response = client.execute(post, context);
			try {
				HttpEntity entity = response.getEntity();
				Gson gson = new GsonBuilder().create();
				ContentType contentType = ContentType.getOrDefault(entity);
				Charset charset = contentType.getCharset();
				Reader reader = new InputStreamReader(entity.getContent(),
						charset);
				Map result = gson.fromJson(reader, HashMap.class);
				store = context.getCookieStore();
				double d = (Double) result.get("userid");
				int u = (int) d;
				BasicClientCookie userId = new BasicClientCookie("userId",
						String.valueOf(u));
				userId.setDomain(ZHUJUANDOMAIN);
				userId.setPath("/");
				userId.setVersion(0);
				store.addCookie(userId);
				BasicClientCookie userName = new BasicClientCookie("userName",
						ZHUJUANLOGINNAME);
				userName.setDomain(ZHUJUANDOMAIN);
				userName.setPath("/");
				userName.setVersion(0);
				store.addCookie(userName);
				log.info("登陆返回:{}", result);
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int searchPage(int categoryId, String type, int bankId, int pageNo) {
		HttpUriRequest post;
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(store);
		post = getSearchMethod(categoryId, bankId, pageNo, DEFAULT_PAGE_SIZE);
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post, context);
			Map<String, Object> result = parseResponse(response);
			if(result == null || result.isEmpty()) return 0;
			ZhujuanPool.execute(new Worker((List) result.get("data"), parser,
					dao), false);
			int totalCount = Integer.parseInt(MapUtils.getValue(result,
					"totalCount"));
			return totalCount % DEFAULT_PAGE_SIZE == 0 ? totalCount
					/ DEFAULT_PAGE_SIZE : totalCount / DEFAULT_PAGE_SIZE + 1;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return 0;
	}
	public void downloadAnswerAndParseImg(){
		ZhujuanQuesExample example = new ZhujuanQuesExample();
		ZhujuanQuesExample.Criteria c = example.createCriteria();
		c.andFlagEqualTo(0);
		example.setLimitStart(0);
		example.setLimitSize(1000);
		LinkedList<ZhujuanQues> list = new LinkedList<ZhujuanQues>(quesDao.selectByExample(example));
		List<ZhujuanProcesser> processers = new ArrayList<ZhujuanProcesser>();
		for (int i = 0; i < 5; i++) {
			ZhujuanProcesser processer = new ZhujuanPool.AnswerAndParseWorker(this, list);
			ZhujuanPool.execute(processer, true);
			processers.add(processer);
		}
		while(true){
			try {
				TimeUnit.SECONDS.sleep(30l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (list) {
				log.info("待下载队例数量:[{}]", list.size());
				if (list.isEmpty()) {
					list.addAll(quesDao.selectByExample(example));
					if (list.isEmpty()) {
						//ZhujuanPool.cancle = false;
						for (ZhujuanProcesser zhujuanProcesser : processers) {
							zhujuanProcesser.stop();
						}
						break;
					}
					list.notifyAll();
				}
			}
		}
	}
	public void updatQues(ZhujuanQuesWithBLOBs ques){
		quesDao.updateByPrimaryKeySelective(ques);
	}
	private Map<String, Object> parseResponse(CloseableHttpResponse response)
			throws ParseException, IOException {
		HttpEntity entity = response.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		String content = EntityUtils.toString(entity, charset);
		String[] str = content.split("###");
		Gson gson = new GsonBuilder().create();
		Map<String, Object> result = new HashMap<String, Object>();
		if (str.length == 2) {
			Map<String, Object> map = gson.fromJson(str[0], HashMap.class);
			result.put("totalCount", MapUtils.getValue(map, "quescount"));
			List data = gson.fromJson(str[1], ArrayList.class);
			result.put("data", data);
			// log.debug("返回结果:{}", result);
			return result;
		}
		return null;
	}

	public List<Task> getTask() {
		return dao.selectTask();
	}

	public void searchQues(Task task) {
		task = dao.getTaskByLog(task);
		int totalPage = searchPage(task.getCategoryId(), task.getType(),
				task.getBankId(), task.getPageNo());

		for (int i = task.getPageNo() + 1; i <= totalPage; i++) {
			dao.saveLog(task.getId(), i);
			searchPage(task.getCategoryId(), task.getType(), task.getBankId(),
					i);
			// try {
			// TimeUnit.MICROSECONDS.sleep(5000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
		}
		dao.updateTask(task.getId());
	}

	private void crawlCategory(String parentId, List<UStrMap<Object>> result,
			String type, String bankId) throws IOException {
		String url = "http://www.zujuan.com/Web/Handler1.ashx";
		LStrMap<String> params = LStrMap.newInstance();
		params.put("action", "categorytree");
		params.put("parentid", parentId);
		HttpRespons response = HttpHelper.sendGet(url, params);
		ZhujuanParse parser = new ZhujuanParse();
		List<UStrMap<Object>> list = parser.parseCategory(
				response.getContent(), parentId, type, bankId);
		if (list.size() > 0) {
			result.addAll(list);
			for (UStrMap<Object> uStrMap : list) {
				try {
					TimeUnit.MILLISECONDS.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				crawlCategory((String) uStrMap.get("id"), result, type, bankId);
			}
		}
	}
	
	public void parseQues() {

	}
//	List<ZhujuanProcesser> processers = new ArrayList<ZhujuanProcesser>();
	public void download() {
		if (client == null)
			initClient();
//		LinkedList<String> urls = new LinkedList<String>(
//				dao.selectImageUrl(1000));
		ZhujuanImagesExample example = new ZhujuanImagesExample();
		example.setLimitStart(0);
		example.setLimitSize(1000);
		LinkedList<ZhujuanImages> images = new LinkedList<ZhujuanImages>(imageDao.selectByExample(example));
		//log.debug("待下载队例数量:[{}]", images.size());
		List<ZhujuanProcesser> processers = new ArrayList<ZhujuanProcesser>();
		for (int i = 0; i < 5; i++) {
			ZhujuanProcesser processer = new ZhujuanPool.downloader(images,this);
			ZhujuanPool.execute(processer, true);
			processers.add(processer);
		}
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(10l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (images) {
				log.debug("待下载队例数量:[{}]", images.size());
				if (images.isEmpty()) {
					images.addAll(imageDao.selectByExample(example));
					if (images.isEmpty()) {
						//ZhujuanPool.cancle = false;
						for (ZhujuanProcesser zhujuanProcesser : processers) {
							zhujuanProcesser.stop();
						}
						break;
					}
					images.notifyAll();
				}
			}
		}
	//	ZhujuanPool.cancle = false;
	}
//	public void stop(){
//		for (ZhujuanProcesser zhujuanProcesser : processers) {
//			zhujuanProcesser.stop();
//		}
//	}
	public String downloadAnswer(ZhujuanQues q) {
		String path = new StringBuffer().append(ANSWERPATH)
				.append("/")
				.append(q.getId())
				.append("/")
				.append(q.getBankId())
				.append("/726/12").toString();
		String savePath = new StringBuffer().append("/answer/").append(q.getId())
				.append(".gif").toString();
		return downloadImg(path, savePath)?savePath:"";
	}

	public String downloadParse(ZhujuanQues q) {
		String path = new StringBuffer().append(PARSEPATH)
				.append("/")
				.append(q.getId())
				.append("/")
				.append(q.getBankId())
				.append("/726/12").toString();
		String savePath = new StringBuffer().append("/parse/").append(q.getId())
				.append(".gif").toString();
		return downloadImg(path, savePath)?savePath:"";
	}

	public boolean downloadImg(String path, String savePath) {
		log.debug("下载图片:[{}]",path);
		HttpGet get = new HttpGet(path);
		String filePath = imageSavePath + (savePath.startsWith("/") ? "":"/") + savePath;
		String rec = filePath.substring(0, filePath.lastIndexOf("/"));
		InputStream is = null;
		FileOutputStream fs = null;
		try {
			CloseableHttpResponse response = client.execute(get);
			
			HttpEntity entity = response.getEntity();
//			log.info("ContentType:[{}]",entity.getContentType());
//			log.info("ContentEncoding:[{}]",entity.getContentEncoding());
//			log.info("ContentLength:[{}]",entity.getContentLength());
			is = entity.getContent();
			File file = new File(rec);
			if (!file.exists())
				file.mkdirs();
			fs = new FileOutputStream(new File(filePath));
			int temp;
			while ((temp = is.read()) != -1) {
				fs.write(temp);
			}
		} catch (ClientProtocolException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (is != null)
					is.close();
				if (fs != null)
					fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return true;
		// dao.deleteImage(path);
	}

	public void deleteImage(ZhujuanImages images) {
		//dao.deleteImage(path);
		imageDao.deleteByPrimaryKey(images.getId());
	}

	public static void main(String[] args) {
//		ZhujuanCrawler service = new ZhujuanCrawler();
//		service.initClient();
		// service.downloadImg("http://attach.etiantian.com/ett20/study/service/testonline/package/163982/image001.jpg");

		//service.downloadImg("Upload/2014-03/19/018d4318-bee9-4e68-bff7-579f7d4009d9/paper.files/image001.jpg","");
		// URI uri;
		// try {
		// uri = new
		// URI("http://attach.etiantian.com/ett20/study/service/testonline/package/163982/image001.jpg");
		// System.out.println("host:"+uri.getHost());
		// System.out.println("query"+uri.getQuery());
		// System.out.println("path:"+uri.getPath());
		// } catch (URISyntaxException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
