package com.eps.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.eps.service.crawl.Crawler;
import com.eps.service.crawl.ZhujuanCrawler;
import com.eps.service.crawl.ZhujuanPool;
import com.eps.service.crawl.bean.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class ZhujuanServiceTest {
	
	@Autowired
	private ZhujuanCrawler service;
	@Autowired
	private Crawler crawler;
//	@Test
	public void case1(){
		String[] ids = {"1","59","96","196","292","835","957","1074","1243","1316","1372","1570","589","1669","1767","1852","1962","2145","","","","","44426","38454","48076"};
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			if("".equals(id))continue;
			service.insertZsd(id,String.valueOf(i+1));
			try {
				TimeUnit.MILLISECONDS.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Assert.isTrue(true);
	}
	
//	@Test
	public void case2(){
		String html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中地理人教版\" textbookid=\"25427\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"高中地理鲁教版\" textbookid=\"25635\">鲁教版</a><a href=\"javascript:void(0)\" title=\"高中地理湘教版\" textbookid=\"25819\">湘教版</a><a href=\"javascript:void(0)\" title=\"高中地理中图版\" textbookid=\"26009\">中图版</a><a href=\"javascript:void(0)\" title=\"高中地理人教版（旧）\" textbookid=\"2903\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中地理鲁教版（旧）\" textbookid=\"2738\">鲁教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中地理湘教版（旧）\" textbookid=\"3111\">湘教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中地理中图版（旧）\" textbookid=\"3249\">中图版（旧）</a></div>";
		String bankId = "18";
		Map<String,String> map = new HashMap<String,String>();
		map.put(bankId, html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中语文人教版\" textbookid=\"28107\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"初中语文北师大版\" textbookid=\"27105\">北师大版</a><a href=\"javascript:void(0)\" title=\"初中语文苏教版\" textbookid=\"29445\">苏教版</a><a href=\"javascript:void(0)\" title=\"初中语文鲁教版\" textbookid=\"27889\">鲁教版</a><a href=\"javascript:void(0)\" title=\"初中语文北京课改版\" textbookid=\"26781\">北京课改版</a><a href=\"javascript:void(0)\" title=\"初中语文长春版\" textbookid=\"27221\">长春版</a><a href=\"javascript:void(0)\" title=\"初中语文鄂教版\" textbookid=\"27345\">鄂教版</a><a href=\"javascript:void(0)\" title=\"初中语文沪教版\" textbookid=\"27545\">沪教版</a><a href=\"javascript:void(0)\" title=\"初中语文冀教版\" textbookid=\"27675\">冀教版</a><a href=\"javascript:void(0)\" title=\"初中语文语文版\" textbookid=\"29872\">语文版</a><a href=\"javascript:void(0)\" title=\"初中语文浙教版\" textbookid=\"30101\">浙教版</a><a href=\"javascript:void(0)\" title=\"初中语文上海教育出版社\" textbookid=\"29063\">上海教育出版社</a><a href=\"javascript:void(0)\" title=\"初中语文上海版（五四学制）\" textbookid=\"28319\">上海版（五四学制）</a><a href=\"javascript:void(0)\" title=\"初中语文人教版（旧）\" textbookid=\"11474\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中语文鲁教版（旧）\" textbookid=\"11242\">鲁教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中语文苏教版（旧）\" textbookid=\"11754\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中语文北师大版（旧）\" textbookid=\"10836\">北师大版（旧）</a></div>";
		map.put("1", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中数学人教版\" textbookid=\"31906\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"初中数学北师大版\" textbookid=\"32031\">北师大版</a><a href=\"javascript:void(0)\" title=\"初中数学浙教版\" textbookid=\"32239\">浙教版</a><a href=\"javascript:void(0)\" title=\"初中数学华师大版\" textbookid=\"32442\">华师大版</a><a href=\"javascript:void(0)\" title=\"初中数学苏科版\" textbookid=\"32591\">苏科版</a><a href=\"javascript:void(0)\" title=\"初中数学湘教版\" textbookid=\"32782\">湘教版</a><a href=\"javascript:void(0)\" title=\"初中数学沪科版\" textbookid=\"32930\">沪科版</a><a href=\"javascript:void(0)\" title=\"初中数学冀教版\" textbookid=\"33064\">冀教版</a><a href=\"javascript:void(0)\" title=\"初中数学北京课改版\" textbookid=\"33272\">北京课改版</a><a href=\"javascript:void(0)\" title=\"初中数学青岛版\" textbookid=\"33465\">青岛版</a><a href=\"javascript:void(0)\" title=\"初中数学鲁教版（五四学制）\" textbookid=\"51528\">鲁教版（五四学制）</a><a href=\"javascript:void(0)\" title=\"初中数学沪教版（五四学制）\" textbookid=\"51529\">沪教版（五四学制）</a><a href=\"javascript:void(0)\" title=\"初中数学人教版（旧）\" textbookid=\"9485\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中数学苏教版（旧）\" textbookid=\"9623\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中数学沪科版（旧）\" textbookid=\"9347\">沪科版（旧）</a><a href=\"javascript:void(0)\" title=\"初中数学浙教版（旧）\" textbookid=\"9813\">浙教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中数学北师大版（旧）\" textbookid=\"9103\">北师大版（旧）</a></div>";
		map.put("2", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中英语人教新目标版\" textbookid=\"30741\" class=\"current\">人教新目标版</a><a href=\"javascript:void(0)\" title=\"初中英语外研版\" textbookid=\"30903\">外研版</a><a href=\"javascript:void(0)\" title=\"初中英语仁爱版\" textbookid=\"30808\">仁爱版</a><a href=\"javascript:void(0)\" title=\"初中英语鲁教版\" textbookid=\"30434\">鲁教版</a><a href=\"javascript:void(0)\" title=\"初中英语冀教版\" textbookid=\"30383\">冀教版</a><a href=\"javascript:void(0)\" title=\"初中英语北师大版\" textbookid=\"30345\">北师大版</a><a href=\"javascript:void(0)\" title=\"初中英语北京课改版\" textbookid=\"30269\">北京课改版</a><a href=\"javascript:void(0)\" title=\"初中英语牛津译林版\" textbookid=\"30690\">牛津译林版</a><a href=\"javascript:void(0)\" title=\"初中英语牛津上海版\" textbookid=\"30488\">牛津上海版</a><a href=\"javascript:void(0)\" title=\"初中英语牛津深圳版\" textbookid=\"30556\">牛津深圳版</a><a href=\"javascript:void(0)\" title=\"初中英语牛津沈阳版\" textbookid=\"30600\">牛津沈阳版</a><a href=\"javascript:void(0)\" title=\"初中英语人教版（旧）\" textbookid=\"8556\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中英语仁爱版（旧）\" textbookid=\"8625\">仁爱版（旧）</a><a href=\"javascript:void(0)\" title=\"初中英语外研版（旧）\" textbookid=\"8900\">外研版（旧）</a></div>";
		map.put("3", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中物理人教版\" textbookid=\"31133\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"初中物理苏科版\" textbookid=\"31134\">苏科版</a><a href=\"javascript:void(0)\" title=\"初中物理北师大版\" textbookid=\"31135\">北师大版</a><a href=\"javascript:void(0)\" title=\"初中物理沪科版\" textbookid=\"31451\">沪科版</a><a href=\"javascript:void(0)\" title=\"初中物理教科版\" textbookid=\"31452\">教科版</a><a href=\"javascript:void(0)\" title=\"初中物理沪粤版\" textbookid=\"31453\">沪粤版</a><a href=\"javascript:void(0)\" title=\"初中物理北京课改版\" textbookid=\"31777\">北京课改版</a><a href=\"javascript:void(0)\" title=\"初中物理鲁教版\" textbookid=\"31778\">鲁教版</a><a href=\"javascript:void(0)\" title=\"初中物理人教版（旧）\" textbookid=\"6540\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中物理苏教版（旧）\" textbookid=\"6645\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中物理沪科版（旧）\" textbookid=\"6442\">沪科版（旧）</a><a href=\"javascript:void(0)\" title=\"初中物理粤教版（旧）\" textbookid=\"6743\">粤教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中物理北京课改版（旧）\" textbookid=\"6246\">北京课改版（旧）</a><a href=\"javascript:void(0)\" title=\"初中物理北师大版（旧）\" textbookid=\"6342\">北师大版（旧）</a></div>";
		map.put("4", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中化学人教版\" textbookid=\"33662\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"初中化学鲁教版\" textbookid=\"33772\">鲁教版</a><a href=\"javascript:void(0)\" title=\"初中化学沪教版\" textbookid=\"33827\">沪教版</a><a href=\"javascript:void(0)\" title=\"初中化学湘教版\" textbookid=\"33872\">湘教版</a><a href=\"javascript:void(0)\" title=\"初中化学粤教版\" textbookid=\"33969\">粤教版</a><a href=\"javascript:void(0)\" title=\"初中化学北京课改版\" textbookid=\"33720\">北京课改版</a><a href=\"javascript:void(0)\" title=\"初中化学仁爱湘教版\" textbookid=\"52217\">仁爱湘教版</a><a href=\"javascript:void(0)\" title=\"初中化学人教版（旧）\" textbookid=\"5721\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中化学沪科版（旧）\" textbookid=\"5639\">沪科版（旧）</a><a href=\"javascript:void(0)\" title=\"初中化学鲁教版（旧）\" textbookid=\"5680\">鲁教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中化学北京课改版（旧）\" textbookid=\"5587\">北京课改版（旧）</a><a href=\"javascript:void(0)\" title=\"初中化学粤教版（旧）\" textbookid=\"33915\">粤教版（旧）</a></div>";
		map.put("5", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中生物人教版\" textbookid=\"34018\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"初中生物苏教版\" textbookid=\"34019\">苏教版</a><a href=\"javascript:void(0)\" title=\"初中生物北师大版\" textbookid=\"34122\">北师大版</a><a href=\"javascript:void(0)\" title=\"初中生物苏科版\" textbookid=\"34123\">苏科版</a><a href=\"javascript:void(0)\" title=\"初中生物北京课改版\" textbookid=\"34124\">北京课改版</a><a href=\"javascript:void(0)\" title=\"初中生物河北少儿版\" textbookid=\"34125\">河北少儿版</a><a href=\"javascript:void(0)\" title=\"初中生物鲁科版\" textbookid=\"34126\">鲁科版</a><a href=\"javascript:void(0)\" title=\"初中生物济南版\" textbookid=\"34127\">济南版</a><a href=\"javascript:void(0)\" title=\"初中生物人教版（旧）\" textbookid=\"4893\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中生物苏科版（旧）\" textbookid=\"5015\">苏科版（旧）</a><a href=\"javascript:void(0)\" title=\"初中生物北京课改版（旧）\" textbookid=\"4823\">北京课改版（旧）</a></div>";
		map.put("6", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中政治人教版\" textbookid=\"35212\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"初中政治北师大版\" textbookid=\"34948\">北师大版</a><a href=\"javascript:void(0)\" title=\"初中政治教科版\" textbookid=\"35031\">教科版</a><a href=\"javascript:void(0)\" title=\"初中政治鲁教版\" textbookid=\"35139\">鲁教版</a><a href=\"javascript:void(0)\" title=\"初中政治人民版\" textbookid=\"35285\">人民版</a><a href=\"javascript:void(0)\" title=\"初中政治陕教版\" textbookid=\"35358\">陕教版</a><a href=\"javascript:void(0)\" title=\"初中政治苏教版\" textbookid=\"35419\">苏教版</a><a href=\"javascript:void(0)\" title=\"初中政治湘教版\" textbookid=\"35500\">湘教版</a><a href=\"javascript:void(0)\" title=\"初中政治粤教版\" textbookid=\"35606\">粤教版</a><a href=\"javascript:void(0)\" title=\"初中政治人教版（旧）\" textbookid=\"4110\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中政治苏教版（旧）\" textbookid=\"4266\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中政治湘教版（旧）\" textbookid=\"12887\">湘教版（旧）</a></div>";
		map.put("7", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中历史人教版\" textbookid=\"36703\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"初中历史北师大版\" textbookid=\"35693\">北师大版</a><a href=\"javascript:void(0)\" title=\"初中历史川教版\" textbookid=\"35870\">川教版</a><a href=\"javascript:void(0)\" title=\"初中历史华师大版\" textbookid=\"36008\">华师大版</a><a href=\"javascript:void(0)\" title=\"初中历史冀教版\" textbookid=\"36196\">冀教版</a><a href=\"javascript:void(0)\" title=\"初中历史沪教版\" textbookid=\"36365\">沪教版</a><a href=\"javascript:void(0)\" title=\"初中历史鲁教版\" textbookid=\"36516\">鲁教版</a><a href=\"javascript:void(0)\" title=\"初中历史岳麓版\" textbookid=\"36985\">岳麓版</a><a href=\"javascript:void(0)\" title=\"初中历史中华书局版\" textbookid=\"37138\">中华书局版</a><a href=\"javascript:void(0)\" title=\"初中历史人教版《历史与社会》\" textbookid=\"36876\">人教版《历史与社会》</a><a href=\"javascript:void(0)\" title=\"初中历史人教版（旧）\" textbookid=\"3591\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中历史北师大版（旧）\" textbookid=\"3414\">北师大版（旧）</a></div>";
		map.put("8", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"初中地理人教新课标版\" textbookid=\"37318\" class=\"current\">人教新课标版</a><a href=\"javascript:void(0)\" title=\"初中地理上海教育版\" textbookid=\"37320\">上海教育版</a><a href=\"javascript:void(0)\" title=\"初中地理湘教版\" textbookid=\"37321\">湘教版</a><a href=\"javascript:void(0)\" title=\"初中地理中图版\" textbookid=\"37695\">中图版</a><a href=\"javascript:void(0)\" title=\"初中地理粤教版\" textbookid=\"37619\">粤教版</a><a href=\"javascript:void(0)\" title=\"初中地理商务星球版\" textbookid=\"37319\">商务星球版</a><a href=\"javascript:void(0)\" title=\"初中地理晋教版\" textbookid=\"52342\">晋教版</a><a href=\"javascript:void(0)\" title=\"初中地理人教版（旧）\" textbookid=\"2667\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"初中地理粤教版（旧）\" textbookid=\"2590\">粤教版（旧）</a></div>";
		map.put("9", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中语文人教新课标\" textbookid=\"17858\" class=\"current\">人教新课标</a><a href=\"javascript:void(0)\" title=\"高中语文粤教版\" textbookid=\"18979\">粤教版</a><a href=\"javascript:void(0)\" title=\"高中语文鲁教版\" textbookid=\"19320\">鲁教版</a><a href=\"javascript:void(0)\" title=\"高中语文北师大版\" textbookid=\"19728\">北师大版</a><a href=\"javascript:void(0)\" title=\"高中语文苏教版\" textbookid=\"22881\">苏教版</a><a href=\"javascript:void(0)\" title=\"高中语文语文版（07版）\" textbookid=\"50570\">语文版（07版）</a><a href=\"javascript:void(0)\" title=\"高中语文北京版（08版）\" textbookid=\"50571\">北京版（08版）</a><a href=\"javascript:void(0)\" title=\"高中语文鲁人版（08版）\" textbookid=\"50572\">鲁人版（08版）</a><a href=\"javascript:void(0)\" title=\"高中语文人教版（旧）\" textbookid=\"12151\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中语文苏教版（旧）\" textbookid=\"12267\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中语文鲁教版（旧）\" textbookid=\"12078\">鲁教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中语文语文版（旧）\" textbookid=\"12545\">语文版（旧）</a><a href=\"javascript:void(0)\" title=\"高中语文粤教版（旧）\" textbookid=\"12687\">粤教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中语文新课标版（旧）\" textbookid=\"12406\">新课标版（旧）</a></div>";
		map.put("10", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中数学文科库\" textbookid=\"2345\" class=\"current\">文科库</a><a href=\"javascript:void(0)\" title=\"高中数学新课标人教A版\" textbookid=\"16621\">新课标人教A版</a><a href=\"javascript:void(0)\" title=\"高中数学新课标人教B版\" textbookid=\"16967\">新课标人教B版</a><a href=\"javascript:void(0)\" title=\"高中数学北师大版\" textbookid=\"17194\">北师大版</a><a href=\"javascript:void(0)\" title=\"高中数学苏教版\" textbookid=\"17515\">苏教版</a><a href=\"javascript:void(0)\" title=\"高中数学湘教版\" textbookid=\"17677\">湘教版</a><a href=\"javascript:void(0)\" title=\"高中数学苏教版（旧）\" textbookid=\"10651\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中数学上海版（旧）\" textbookid=\"10522\">上海版（旧）</a><a href=\"javascript:void(0)\" title=\"高中数学人教A版（旧）\" textbookid=\"10129\">人教A版（旧）</a><a href=\"javascript:void(0)\" title=\"高中数学人教B版（旧）\" textbookid=\"10293\">人教B版（旧）</a><a href=\"javascript:void(0)\" title=\"高中数学北师大版（旧）\" textbookid=\"10015\">北师大版（旧）</a></div>";
		map.put("11", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中英语人教新课标版\" textbookid=\"13813\">人教新课标版</a><a href=\"javascript:void(0)\" title=\"高中英语译林牛津版\" textbookid=\"13880\">译林牛津版</a><a href=\"javascript:void(0)\" title=\"高中英语外研版\" textbookid=\"14038\">外研版</a><a href=\"javascript:void(0)\" title=\"高中英语北师大版\" textbookid=\"14127\">北师大版</a><a href=\"javascript:void(0)\" title=\"高中英语冀教版\" textbookid=\"14172\">冀教版</a><a href=\"javascript:void(0)\" title=\"高中英语重庆大学版\" textbookid=\"51048\">重庆大学版</a><a href=\"javascript:void(0)\" title=\"高中英语人教版（旧）\" textbookid=\"9035\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中英语北师大版（旧）\" textbookid=\"8990\" class=\"current\">北师大版（旧）</a></div>";
		map.put("12", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中物理新课标人教版\" textbookid=\"14896\" class=\"current\">新课标人教版</a><a href=\"javascript:void(0)\" title=\"高中物理沪科版\" textbookid=\"15226\">沪科版</a><a href=\"javascript:void(0)\" title=\"高中物理教科版\" textbookid=\"15228\">教科版</a><a href=\"javascript:void(0)\" title=\"高中物理鲁科版\" textbookid=\"15229\">鲁科版</a><a href=\"javascript:void(0)\" title=\"高中物理粤教版\" textbookid=\"16222\">粤教版</a><a href=\"javascript:void(0)\" title=\"高中物理江苏版\" textbookid=\"15227\">江苏版</a><a href=\"javascript:void(0)\" title=\"高中物理人教版（旧）\" textbookid=\"7677\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中物理沪科版（旧）\" textbookid=\"7146\">沪科版（旧）</a><a href=\"javascript:void(0)\" title=\"高中物理粤教版（旧）\" textbookid=\"8338\">粤教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中物理鲁科版（旧）\" textbookid=\"7456\">鲁科版（旧）</a></div>";
		map.put("13", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中化学人教版\" textbookid=\"13091\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"高中化学鲁科版\" textbookid=\"13230\">鲁科版</a><a href=\"javascript:void(0)\" title=\"高中化学苏教版\" textbookid=\"13377\">苏教版</a><a href=\"javascript:void(0)\" title=\"高中化学人教版（旧）\" textbookid=\"5969\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中化学苏教版（旧）\" textbookid=\"6106\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中化学沪科版（旧）\" textbookid=\"5770\">沪科版（旧）</a><a href=\"javascript:void(0)\" title=\"高中化学鲁科版（旧）\" textbookid=\"5822\">鲁科版（旧）</a></div>";
		map.put("14", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中生物人教版\" textbookid=\"14264\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"高中生物苏教版\" textbookid=\"14416\">苏教版</a><a href=\"javascript:void(0)\" title=\"高中生物浙科版\" textbookid=\"14417\">浙科版</a><a href=\"javascript:void(0)\" title=\"高中生物中图版\" textbookid=\"14627\">中图版</a><a href=\"javascript:void(0)\" title=\"高中生物北师大版\" textbookid=\"14628\">北师大版</a><a href=\"javascript:void(0)\" title=\"高中生物沪科版\" textbookid=\"14629\">沪科版</a><a href=\"javascript:void(0)\" title=\"高中生物人教版（旧）\" textbookid=\"5118\">人教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中生物苏教版（旧）\" textbookid=\"5270\">苏教版（旧）</a><a href=\"javascript:void(0)\" title=\"高中生物浙科版（旧）\" textbookid=\"5373\">浙科版（旧）</a><a href=\"javascript:void(0)\" title=\"高中生物中图版（旧）\" textbookid=\"5508\">中图版（旧）</a></div>";
		map.put("15", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中政治人教版\" textbookid=\"23530\">人教版</a><a href=\"javascript:void(0)\" title=\"高中政治沪教版\" textbookid=\"23840\">沪教版</a><a href=\"javascript:void(0)\" title=\"高中政治人教版（旧）\" textbookid=\"4495\" class=\"current\">人教版（旧）</a></div>";
		map.put("16", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"高中历史人教新课标版\" textbookid=\"23908\" class=\"current\">人教新课标版</a><a href=\"javascript:void(0)\" title=\"高中历史标准实验版\" textbookid=\"24336\">标准实验版</a><a href=\"javascript:void(0)\" title=\"高中历史北师大版\" textbookid=\"24447\">北师大版</a><a href=\"javascript:void(0)\" title=\"高中历史人民版\" textbookid=\"24748\">人民版</a><a href=\"javascript:void(0)\" title=\"高中历史大象版\" textbookid=\"25033\">大象版</a><a href=\"javascript:void(0)\" title=\"高中历史新岳麓版\" textbookid=\"25078\">新岳麓版</a><a href=\"javascript:void(0)\" title=\"高中历史岳麓版\" textbookid=\"25158\">岳麓版</a><a href=\"javascript:void(0)\" title=\"高中历史人教版（旧）\" textbookid=\"3792\">人教版（旧）</a></div>";
		map.put("17", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"小学数学人教版\" textbookid=\"44434\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"小学数学北京版\" textbookid=\"44428\">北京版</a><a href=\"javascript:void(0)\" title=\"小学数学北师大版\" textbookid=\"44429\">北师大版</a><a href=\"javascript:void(0)\" title=\"小学数学沪教版\" textbookid=\"44430\">沪教版</a><a href=\"javascript:void(0)\" title=\"小学数学冀教版\" textbookid=\"44431\">冀教版</a><a href=\"javascript:void(0)\" title=\"小学数学苏教版\" textbookid=\"44436\">苏教版</a><a href=\"javascript:void(0)\" title=\"小学数学西南师大版\" textbookid=\"44437\">西南师大版</a><a href=\"javascript:void(0)\" title=\"小学数学西师大版\" textbookid=\"44438\">西师大版</a><a href=\"javascript:void(0)\" title=\"小学数学浙教版\" textbookid=\"44439\">浙教版</a><a href=\"javascript:void(0)\" title=\"小学数学青岛版（六年制）\" textbookid=\"44432\">青岛版（六年制）</a><a href=\"javascript:void(0)\" title=\"小学数学青岛版（五年制）\" textbookid=\"44433\">青岛版（五年制）</a></div>";
		map.put("23", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"小学语文人教版\" textbookid=\"38552\" class=\"current\">人教版</a><a href=\"javascript:void(0)\" title=\"小学语文北京版\" textbookid=\"38546\">北京版</a><a href=\"javascript:void(0)\" title=\"小学语文北师大版\" textbookid=\"38547\">北师大版</a><a href=\"javascript:void(0)\" title=\"小学语文沪教版\" textbookid=\"38548\">沪教版</a><a href=\"javascript:void(0)\" title=\"小学语文冀教版\" textbookid=\"38549\">冀教版</a><a href=\"javascript:void(0)\" title=\"小学语文教科版\" textbookid=\"38550\">教科版</a><a href=\"javascript:void(0)\" title=\"小学语文鲁教版\" textbookid=\"38551\">鲁教版</a><a href=\"javascript:void(0)\" title=\"小学语文苏教版\" textbookid=\"38553\">苏教版</a><a href=\"javascript:void(0)\" title=\"小学语文西师大版\" textbookid=\"38554\">西师大版</a><a href=\"javascript:void(0)\" title=\"小学语文语文A版\" textbookid=\"38555\">语文A版</a><a href=\"javascript:void(0)\" title=\"小学语文语文S版\" textbookid=\"38556\">语文S版</a><a href=\"javascript:void(0)\" title=\"小学语文长春版\" textbookid=\"38557\">长春版</a><a href=\"javascript:void(0)\" title=\"小学语文鄂教版\" textbookid=\"52528\">鄂教版</a></div>";
		map.put("24", html);
		html = "<div class=\"line_rt w_850\"><a href=\"javascript:void(0)\" title=\"小学英语北师大版（一起）\" textbookid=\"48078\" class=\"current\">北师大版（一起）</a><a href=\"javascript:void(0)\" title=\"小学英语冀教版\" textbookid=\"48079\">冀教版</a><a href=\"javascript:void(0)\" title=\"小学英语辽师大版快乐英语（三起）\" textbookid=\"48080\">辽师大版快乐英语（三起）</a><a href=\"javascript:void(0)\" title=\"小学英语牛津上海版\" textbookid=\"48082\">牛津上海版</a><a href=\"javascript:void(0)\" title=\"小学英语牛津译林版\" textbookid=\"48083\">牛津译林版</a><a href=\"javascript:void(0)\" title=\"小学英语人教PEP\" textbookid=\"48084\">人教PEP</a><a href=\"javascript:void(0)\" title=\"小学英语人教新版\" textbookid=\"48085\">人教新版</a><a href=\"javascript:void(0)\" title=\"小学英语陕旅版（三起）\" textbookid=\"48086\">陕旅版（三起）</a><a href=\"javascript:void(0)\" title=\"小学英语上海牛津版（一起）\" textbookid=\"48087\">上海牛津版（一起）</a><a href=\"javascript:void(0)\" title=\"小学英语外研版（三起）\" textbookid=\"48088\">外研版（三起）</a><a href=\"javascript:void(0)\" title=\"小学英语外研版（一起）\" textbookid=\"48089\">外研版（一起）</a><a href=\"javascript:void(0)\" title=\"小学英语湘少版\" textbookid=\"48090\">湘少版</a><a href=\"javascript:void(0)\" title=\"小学英语新起点\" textbookid=\"48091\">新起点</a></div>";
		map.put("25", html);
		for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
			String id =  iterator.next();
			String str = map.get(id);
			service.insertZj(str, id);
		}
		//		service.insertZj(html, bankId);
	}
	
//	@Test
	public void case3(){
		service.zhujuanLogin();
		//service.initClient();
		List<Task> tasks = service.getTask();
		for (Task task : tasks) {
			service.searchQues(task);
		}
		Assert.isTrue(true);
	}
//	@Test
	public void case4(){
		service.download();
	}
//	@Test
	public void case5(){
		service.zhujuanLogin();
		service.downloadAnswerAndParseImg();
		
//		try {
//			TimeUnit.SECONDS.sleep(10l);
//			ZhujuanPool.stop();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	@Test
	public void case6(){
		crawler.crawl();
	}
}
