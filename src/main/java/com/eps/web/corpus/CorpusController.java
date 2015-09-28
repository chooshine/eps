package com.eps.web.corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.dao.Page;
import com.eps.domain.Article;
import com.eps.domain.Card;
import com.eps.domain.KeyWord;
import com.eps.domain.LSubscribe;
import com.eps.domain.ServiceConstant;
import com.eps.domain.User;
import com.eps.service.corpus.CorpusService;
import com.eps.service.corpus.KeyWordService;
import com.eps.service.corpus.SubscribeService;
import com.eps.service.system.SCodeService;
import com.eps.service.system.SYSParameterService;
import com.eps.utils.DateHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.web.BaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
public class CorpusController extends BaseController {
	
	@Autowired
	private CorpusService service;
	
	@Autowired 
	private SubscribeService subService;
	
	@Autowired
	private KeyWordService kwservice;
	@Autowired
	private SYSParameterService sysService;
	
	@Autowired
	private SCodeService codeService;
	
	@RequestMapping(value="/corpus/index.html")
	public String toIndex(HttpServletRequest request,ModelMap mm){
//		if(!subService.hasSubscribe(getSessionUser(request).getUserId()))
//			return "redirect:/corpus/subscribe.html";
		mm.put("updateDate", sysService.getSystemParameter("INDEXUPDATETIME"));
		mm.put("totalCount", sysService.getSystemParameter("INDEXALLTOTAL"));
		return "corpus/index";
	}
	@RequestMapping(value="/corpus/search.html")
	public String search(final HttpServletRequest request,final SearchParamComm comm,String key, int pageNo, int pageSize, ModelMap mm){
		//System.out.println(key);
		final User user = getSessionUser(request);
		if(!subService.hasSubscribe(user.getUserId()))
			return "redirect:/corpus/subscribe.html";
		LSubscribe subscribe = subService.getSubscribeByUser(user.getUserId());
		mm.put("endDate", DateHelper.formatYMDHMS(subscribe.getEndDate()));
		comm.setKey(key);
		if(comm.getKeys().length<1) return "redirect:/corpus/advanced.html";
		//System.out.println(comm.getAuthors());
		mm.put("title", "语料搜索");
		final String sessionId = request.getSession().getId();
		final KeyWord kw = new KeyWord();
		String fileName = comm.getKeys()[0]+"_"+System.currentTimeMillis()+".xml";
		kw.setFileName(fileName);
		kw.setKeyWordName(comm.getKeys()[0]);
		kw.setLanguage(comm.getLanguage());
		kw.setRepType(comm.getRepositoryType());
		kwservice.saveKeyWord(request,kw);
		new Thread(new Runnable() {
			public void run() {
				service.writeBuffFile(kw,comm,sessionId);
				service.writeSearchLog(kw.getKeyWordName(), user.getUserId(), comm.getKeys().length==2?comm.getKeys()[1]:"",comm.getRepositoryType());
			}
		}).start();
//		try{
//			KeyWord kw = kwservice.getKeyWord(request);
//			if(comm.getIsPageQuery()== 0 || kw == null){
//				kw = new KeyWord();
//				String fileName = comm.getKeys()[0]+"_"+System.currentTimeMillis()+".xml";
//				kw.setFileName(fileName);
//				kw.setKeyWordName(comm.getKeys()[0]);
//				kw.setLanguage(comm.getLanguage());
//				kw.setRepType(comm.getRepositoryType());
//				kwservice.saveKeyWord(request,kw);
//				service.writeBuffFile(kw,comm,request);
//				service.writeSearchLog(kw.getKeyWordName(), user.getUserId(), comm.getKeys().length==2?comm.getKeys()[1]:"",comm.getRepositoryType());
//			}
//			Page page = service.search(comm, kw, pageNo, pageSize);
//			mm.put("page", page);
//		}catch(Exception e){
//			mm.put(ERROR_MSG_KEY, "输入关键词含非法字符，请重新输入");
//			mm.put("page", new Page());
//		}
		mm.put("key", key);
		mm.put("pageSize", pageSize);
		mm.put("comm", comm);
		mm.put("repType", codeService.getCodes("reptype"));
//		mm.put("searchLimit", codeService.getCodes("SEARCH_LIMIT"));
		return "corpus/result";
	}
	@RequestMapping(value="/corpus/export.html")
	public String exportExcel(HttpServletRequest request,HttpServletResponse response,SearchParamComm comm) throws IOException{
		KeyWord kw = kwservice.getKeyWord(request);
		if(kw == null) return "redirect:/corpus/index.html";
		//HSSFWorkbook book = service.exportExcel(comm, kw);
		List<UStrMap<String>> data = service.getExcelData(comm, kw);
		String name = kw.getKeyWordName() + "_"  + data.size() + "_" + DateHelper.formatDateTime(new Date(), "yyMMddHHmm") + ".xls";
		//name = new String(name.getBytes("utf-8"),"ISO-8859-1");
		name = URLEncoder.encode(name, "UTF-8");
		try {
			response.reset();// 清空输出流   
			response.setHeader("Content-Type","application/msexcel");
		    response.setHeader("Content-disposition", "attachment; filename=\""+name+"\"");// 设定输出文件头   
		    response.setContentType("application/octet-stream;charset=UTF-8;");// 定义输出类型 
		    Template template = getExcelTemplate();
		    Map<String,Object> rootMap = new HashMap<String,Object>();
		    rootMap.put("dataList", data);
		    rootMap.put("size", data.size());
		   // rootMap.put("title", getExcelColumn());
		    template.setEncoding("UTF-8");
		    template.process(rootMap, new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),"UTF-8")));
			//book.write(response.getOutputStream());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}
	private UStrMap<String> getExcelColumn(){
		UStrMap<String> columns = UStrMap.newInstance();
		columns.put("number", "番号");
		columns.put("left", "前文脈");
		columns.put("key", "キーワード");
		columns.put("right", "後文脈");
		columns.put("AUTHOR", "作者");
		columns.put("ARTICLENAME", "出所");
		columns.put("DDATE", "発表時間");
		return columns;
	}
	private Template getExcelTemplate() throws IOException{
		Configuration config = new Configuration();
		config.setClassForTemplateLoading(CorpusController.class, "/template");
		config.setDefaultEncoding("UTF-8");
		return config.getTemplate("excel.ftl");
	}
	@RequestMapping(value="/corpus/progress.json")
	public String progress(HttpServletRequest request,ModelMap mm){
		mm.put("iscomplete", CorpusService.completeMap.get(request.getSession().getId()));
		mm.put("value", CorpusService.progressValueMap.get(request.getSession().getId()));
		return "jsonView";
	}
	
	@RequestMapping(value="/corpus/article/list.html")
	public String getArticleList(HttpServletRequest request,SearchParamComm comm,String key, int pageNo, int pageSize, ModelMap mm){
		Page page = service.search(comm,kwservice.getKeyWord(request) , pageNo, pageSize);
		mm.put("page", page);
		mm.put("comm", comm);
		mm.put("searchLimit", codeService.getCodes("SEARCH_LIMIT"));
		return "corpus/articleList";
	}
	@RequestMapping(value="/corpus/advanced.html")
	public String advance(HttpServletRequest request,ModelMap mm){
		if(!subService.hasSubscribe(getSessionUser(request).getUserId()))
			return "redirect:/corpus/subscribe.html";
		LSubscribe subscribe = subService.getSubscribeByUser(getSessionUser(request).getUserId());
		mm.put("endDate", DateHelper.formatYMDHMS(subscribe.getEndDate()));
		mm.put("title", "高级搜索");
		List<UStrMap<Object>> authors = service.getAuthorByReport("l_article");
		mm.put("repType", codeService.getCodes("reptype"));
		mm.put("codes", authors);
		mm.put("searchLimit", codeService.getCodes("SEARCH_LIMIT"));
		return "corpus/advancedSearch";
	}
	@RequestMapping(value="/corpus/loadAuthor.html")
	public String loadAuthorByReport(String reportName,ModelMap mm){
		List<UStrMap<Object>> authors = service.getAuthorByReport(reportName);
		mm.put("codes", authors);
		return "corpus/authorList";
	}
	@RequestMapping(value="/corpus/distribute.html")
	public String getCorpusDistribute(ModelMap mm){
		List<UStrMap<Object>>  authors = service.getAllAuthorAndArticleNum();
		mm.put("codes", authors);
		List<LStrMap<Object>> topauthors = service.getTopAuthor();
		List<LStrMap<Object>> dis = service.getArticleDistribute();
		Gson gson = new GsonBuilder().create();
		mm.put("pieData", gson.toJson(dis));
		mm.put("barData", gson.toJson(topauthors));
		mm.put("title", "语料分布");
		return "corpus/distribute";
	}
	
	@RequestMapping(value="/corpus/getArticleByAuthor.html")
	public String getArticleByAuthor(int authorId,ModelMap mm){
		List<Article> articles = service.getArticleByAuthor(authorId);
		mm.put("articles", articles);
		return "corpus/articleTable";
	}
	
	@RequestMapping(value="/corpus/getChartData.json")
	public String getChartData(ModelMap mm){
		List<LStrMap<Object>> authors = service.getTopAuthor();
		List<LStrMap<Object>> dis = service.getArticleDistribute();
		mm.put("pieData", dis);
		mm.put("barData", authors);
		return "jsonView";
	}
	
	@RequestMapping(value="/corpus/instructions.html")
	public String toHelp(ModelMap mm){
		mm.put("title", "语料帮助");
		return "corpus/instructions";
	}
	
	@RequestMapping(value="/corpus/searchAll.html")
	public String searchAll(HttpServletRequest request,SearchParamComm comm,String key, int pageNo, int pageSize, ModelMap mm){
		if(!subService.hasSubscribe(getSessionUser(request).getUserId()))
			return "redirect:/corpus/subscribe.html";
		LSubscribe subscribe = subService.getSubscribeByUser(getSessionUser(request).getUserId());
		mm.put("endDate", DateHelper.formatYMDHMS(subscribe.getEndDate()));
		mm.put("title", "语料搜索");
		KeyWord kw = kwservice.getKeyWord(comm.getKeys()[0], comm.getLanguage(), comm.getRepositoryType());
		service.complementArticleByKeyWord(kw,comm);
		Page page = service.search(comm, kw, pageNo, pageSize);
		mm.put("page", page);
		mm.put("key", key);
		mm.put("pageSize", pageSize);
		mm.put("comm", comm);
		
		//查看全部也要显示下拉框的数据
		mm.put("repType", codeService.getCodes("reptype"));
		return "corpus/result";
	}
	
	@RequestMapping(value="/corpus/membershipcard.html")
	public String toMembershipCard(ModelMap mm){
		mm.put("title", "VIP通道");
		return "corpus/membershipcard";
	}
	
	@RequestMapping(value="/check_search_service.json")
	public String analyMembership(HttpServletRequest request, ModelMap mm,String card_id,String card_password){
		long userId = this.getSessionUser(request).getUserId();
		String serviceType = ServiceConstant.CORPUSSEARCH;
		
		Card card = service.getCard(card_id,card_password);
		LStrMap<Object> map = service.analyCard(userId, card, serviceType);
		int flag = (Integer)map.get("flag");
		if (flag == 0) {//flag为0，开通语料服务
			
			LStrMap<Object> serviceMap = (LStrMap<Object>) map.get("service");
			mm.put("remark", serviceMap.get("remark"));
			//开启语料服务
			service.openSearchService(userId, card, String.valueOf(serviceMap.get("service_content")));
			//设置该卡对应的语料服务使用状态为已使用
			service.updateServiceStatus((Integer)serviceMap.get("cardservice_id"), ServiceConstant.OPENED);
			//将会员卡状态设为已使用,并且设置使用者为当前用户
			service.updateCardStatusUser(card.getCardId(), userId);
		} 
		mm.put("flag", flag);
		return "jsonView";
	}
	
	@RequestMapping(value="/corpus/open_searchservice_success.html")
	public String toOpenSearchService(ModelMap mm, String remark){
		mm.put("title", "VIP通道");
		mm.put("remark", remark);
		return "corpus/open_searchservice_success";
	}
}
