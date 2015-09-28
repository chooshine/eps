package com.eps.service.corpus;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eps.dao.Page;
import com.eps.dao.corpus.ArticleDao;
import com.eps.dao.corpus.AuthorDao;
import com.eps.dao.corpus.CardDao;
import com.eps.dao.corpus.KeyWordDao;
import com.eps.dao.corpus.SearchLogDao;
import com.eps.dao.corpus.SubscribeDao;
import com.eps.domain.Article;
import com.eps.domain.Author;
import com.eps.domain.Card;
import com.eps.domain.KeyWord;
import com.eps.domain.LSubscribe;
import com.eps.utils.DateHelper;
import com.eps.utils.LStrMap;
import com.eps.utils.UStrMap;
import com.eps.web.corpus.SearchParamComm;


@Service
public class CorpusService {
//	public final static String ISCOMPLETE = "iscomplete";
//	public final static String PROGERSSVALUE="progressValue";
	Logger log = LoggerFactory.getLogger(CorpusService.class);
	public static Map<String, String> completeMap = new HashMap<String, String>();
	public static Map<String,Integer> progressValueMap = new HashMap<String, Integer>();
	@Autowired
	private ArticleDao adao;
	
	@Autowired
	private KeyWordDao kwdao;
	
	@Autowired
	private XmlArticleStore store;
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private SearchLogDao logDao;
	
	@Autowired
	private CardDao cardDao;
	
	@Autowired
	private SubscribeDao sdao;
	
	@Value("${search.limit}")
	private int MaxLimit;
	
	@Value("${search.result.count}")
	private int resultCount;
	
	@Value("${search.max.count}")
	private int searchCount;
	
	
	public Page search(SearchParamComm comm,KeyWord kw, int pageNo,int pageSize){
		Page page = new Page();
		try {
			page = store.readBuffFile(comm, kw.getFileName(), pageNo, pageSize);
		} catch (IOException e) {
			log.error("语料搜索异常",e);
		}
		return page;
	}
	public List<UStrMap<String>> getExcelData(SearchParamComm comm,KeyWord kw){
		HSSFWorkbook book = new HSSFWorkbook();
		try {
			return store.getExcelData(comm, kw.getFileName(), book);
		} catch (IOException e) {
			log.error("导出异常",e);
		}
		return null;
	}
	/**
	 * 记录查询日志
	 * @param keywordId
	 * @param userId
	 * @param otherKey
	 */
	public void writeSearchLog(String keyword,long userId, String otherKey,String table){
		logDao.writeLog(keyword, userId, otherKey,table);
	}
	public void writeBuffFile(KeyWord kw,SearchParamComm comm, String sessionId){
//		request.getSession().setAttribute(ISCOMPLETE, false);
		completeMap.put(sessionId, "false");
		progressValueMap.put(sessionId,0);
		List<Article> articles = searticeArticle(comm, 0, MaxLimit);
		if(articles.size()<resultCount){
			for(int i=0;i<searchCount-1;i++){
				//System.out.println(Math.round(articles.size()/(resultCount+0d)*100));
				progressValueMap.put(sessionId, Integer.parseInt(String.valueOf(Math.round(articles.size()/(resultCount+0d)*100))));
//				request.getSession().setAttribute(PROGERSSVALUE, Math.round(articles.size()/resultCount)*100);
				List<Article> temp = searticeArticle(comm, 0, MaxLimit);
				for (Article article : temp) {
					if(!articles.contains(article)){
						articles.add(article);
					}
				}
				if(articles.size()>=resultCount)break;
			}
		}
//		List<Article> articles = searticeArticle(comm, 0, MaxLimit);
		store.writeHeader(kw.getFileName());
		store.writeBuffFile(articles, kw.getFileName());
		store.writeBottom(kw.getFileName());
		completeMap.put(sessionId, "true");
//		request.getSession().setAttribute(ISCOMPLETE, true);
	}
	/**
	 * 根据关键字查询文章
	 * @param key
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Article> searticeArticle(SearchParamComm comm,int start,int limit){
		return adao.findArticleByKey(comm, start, limit);
	}
	
	public void complementAllArticle(){
		List<KeyWord> words = kwdao.findKeyWord();
		for (KeyWord keyWord : words) {
			SearchParamComm comm = new SearchParamComm();
			comm.setRepositoryType(keyWord.getRepType());
			comm.setKeys(new String[]{keyWord.getKeyWordName()});
			complementArticleByKeyWord(keyWord,comm);
		}
	}
	public void complementArticleByKeyWord(KeyWord keyWord, SearchParamComm comm){
		String key = keyWord.getKeyWordName();
		String fileName = keyWord.getFileName();
		String repType = keyWord.getRepType();
		store.writeHeader(fileName);
		List<Article> articles;
		for(int i=0;true;i+=MaxLimit){
			int start = i;
			int limit = MaxLimit;
			articles = searticeArticle(comm, start, limit);
			if(articles.size()==0) break;
			store.writeBuffFile(articles, fileName);
		}
		store.writeBottom(fileName);
		keyWord.setStatus(KeyWord.COMPLIT);
		kwdao.updateKeyWord(keyWord);
	}
	/**
	 * 获取所有作者信息并按编码进行分类(不包括作品数量)
	 * @return
	 */
	public List<UStrMap<Object>> getAllAuthor(){
		List<Author> authors = authorDao.getAllAuthor();
		List<LStrMap<Object>> codes = authorDao.getAllNameCode();
		return feachAuthor(codes,authors);
	}
	public List<UStrMap<Object>> getAuthorByReport(String reportName){
		List<Author> authors = authorDao.getAuthorByReport(reportName);
		List<LStrMap<Object>> codes = authorDao.getAllNameCode();
		return feachAuthor(codes,authors);
	}
	/**
	 * 获取所有作者信息并按编码进行分类(包括作品数量)
	 * @return
	 */
	public List<UStrMap<Object>> getAllAuthorAndArticleNum(){
		List<Author> authors = authorDao.getAllAuthorArticleNum();
		List<LStrMap<Object>> codes = authorDao.getAllNameCode();
		return feachAuthor(codes,authors);
	}
	
	public List<Article> getArticleByAuthor(int authorId){
		return adao.getArticleByAuthor(authorId);
	}
	
	public List<LStrMap<Object>> getTopAuthor(){
		return authorDao.getTopAuthor();
	}
	
	public List<LStrMap<Object>> getArticleDistribute(){
		return adao.getArticleDistribute();
	}
	/*
	 * 按编码对作者姓名进行分类
	 */
	private List<UStrMap<Object>> feachAuthor(List<LStrMap<Object>> codes,List<Author> authors){
		UStrMap<List<Author>> result = UStrMap.newInstance();
		for(Author author:authors){
			for(LStrMap<Object> map:codes){
				String remark = String.valueOf(map.get("REMARK"));
				String[] nameCode = remark.split(",");
				if(ArrayUtils.contains(nameCode, author.getNameCode())){
					List<Author> temp = result.get(map.get("NAME"));
					if(temp == null){
						temp = new ArrayList<Author>();
					}
					temp.add(author);
					result.put((String)map.get("NAME"), temp);
					break;
				}
			}
		}
		List<UStrMap<Object>> list = new ArrayList<UStrMap<Object>>();
		UStrMap<Object> all = UStrMap.newInstance();
		all.put("NAMECODE", "全部");
		all.put("AUTHORS", authors);
		list.add(all);
		for(LStrMap<Object> code :codes){
			List<Author> temp = result.get(code.get("NAME"));
			if(temp != null){
				UStrMap<Object> map = UStrMap.newInstance();
				map.put("NAMECODE", code.get("NAME"));
				map.put("AUTHORS", temp);
				list.add(map);
			}
		}
		result = null;
		return list;
	}
	
	public Card getCard(String card_id,String card_password){
		UStrMap<Object> map = UStrMap.newInstance();
		map.put("card_no", card_id);
		map.put("card_password", card_password);
		Card card = cardDao.getCardByCardId(map); 
		return card;
	}
	
	//开启语料搜索服务
	public void openSearchService(long userId,Card card,String service_content){
		LSubscribe subscribe = sdao.getSucscribeByUser(userId);
		Date now = new Date();
		if(subscribe == null){ //如以前未购买过
			subscribe = new LSubscribe();
			subscribe.setUserId(userId);
			subscribe.setStartDate(now);
			
			subscribe.setEndDate(DateHelper.add(now, Calendar.MONTH, Integer.parseInt(service_content)));
			subscribe.setRemark(card.getCardName()+"会员免费使用"+service_content+"个月");
			sdao.saveSubscribe(subscribe);
		}else{
			Date endDate = subscribe.getEndDate();
			if(endDate.compareTo(now)>0){
				endDate = DateHelper.add(endDate, Calendar.MONTH, Integer.parseInt(service_content));
			}else{
				endDate = DateHelper.add(now, Calendar.MONTH,  Integer.parseInt(service_content));
			}
			subscribe.setStartDate(now);
			subscribe.setEndDate(endDate);
			subscribe.setRemark(card.getCardName()+"会员免费使用"+service_content+"个月");
			sdao.updateSubscribe(subscribe);
		}
	}
	
	/**
	 * 判断输入的信息是否能开通对应的服务，并返回相应的结果，
	 * 0代表可以开通服务，1代表卡号或密码不正确，2代表当前用户不具有使用该卡的权限，3代表该卡已被锁定，4代表该卡当前没有语料搜索的服务
	 * @param card
	 * @param serviceType
	 * @return
	 */
	public LStrMap<Object> analyCard(Long userId, Card card, String serviceType){
		LStrMap<Object> map = LStrMap.newInstance();
		if(card != null) {//卡号和密码都正确
			if (userId==card.getCardUser() || card.getCardUser()==0 || Long.valueOf(card.getCardUser())==null) {//第一次使用该卡或者使用者为同一个人，则具有使用该卡的权限
				if (card.getLockStatus() == 0) {//未被锁定
					//得到卡是否拥有开通该服务的权限
					List<LStrMap<Object>> serviceList = cardDao.getServiceByCardIdServiceType(card.getCardId(), serviceType);
					if (!serviceList.isEmpty()) {//可以开通该服务
						map.put("flag", 0);
						map.put("service", serviceList.get(0));
					} else {
						map.put("flag", 4);
					}
				} else {//已被锁定
					map.put("flag", 3);
				}
			} else {//使用者不是同一人
				map.put("flag", 2);
			}
		} else {//卡号或密码不正确
			map.put("flag", 1);
		}
		
		return map;
	}
	
	public void updateServiceStatus(int cardservice_id, int use_status){
		cardDao.updateServiceStatus(cardservice_id, use_status);
	}
	
	public void updateCardStatusUser(long cardId, long cardUser){
		cardDao.updateCardStatusUser(cardId, cardUser);
	}
	
}
