package com.eps.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eps.domain.ques.BigQues;
import com.eps.domain.ques.Content;
import com.eps.domain.ques.Exam;
import com.eps.domain.ques.Image;
import com.eps.domain.ques.Option;
import com.eps.domain.ques.Question;
import com.eps.utils.LStrMap;
import com.eps.web.corpus.CorpusController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/applicationContext.xml"})
public class ExportWordTest {
	
	private Exam exam;
	private static Random random = new Random(47);
	private static final String[] indexNo = {"A","B","C","D","E"};
//	@Before
	public void init(){
		exam = new Exam();
		exam.setId(1);
		exam.setName("XXX高中一年级物理测试一");
		exam.setExamType(1);
		exam.setbCodes(new String[]{"一","二","三"});
		exam.seteStatus(1);
		exam.setExamArea(1);
		exam.setmCodes(new String[]{"1","2","3"});
		exam.setoRandom(1);
		exam.setPassScore(60);
		exam.setqRandom(1);
		exam.setQuestionNum(10);
		exam.setSubjectNo(1);
		exam.setTotalScore(100);
		exam.setYear(2013);
		exam.setBigQuesNum(2);
		BigQues bq1 = new BigQues();
		bq1.setId(1);
		bq1.setDefaultScore(30);
		bq1.setDetail("选择题");
		bq1.setIndexNo("一");
		bq1.setName("选择题");
		bq1.setOrderNum(1);
		bq1.setQuesType(1);
		BigQues bq2 = new BigQues();
		bq2.setId(2);
		bq2.setDefaultScore(30);
		bq2.setDetail("阅读理解");
		bq2.setIndexNo("二");
		bq2.setName("阅读理解");
		bq2.setOrderNum(2);
		bq2.setQuesType(6);
		bq1.addQuestion(createChildQues()).addQuestion(createChildQues()).addQuestion(createChildQues());
		bq2.addQuestion(createQuestion()).addQuestion(createQuestion()).addQuestion(createChildQues());
		exam.addBigQues(bq1).addBigQues(bq2);
		exam.addImage(createImage());
	}
	private Question createQuestion(){
		Question q = new Question();
		q.setQuestionId(random.nextInt(100000));
		q.setScore(random.nextInt(20));
		q.setDifficulty(random.nextInt(5));
		q.setIndexNo(String.valueOf(random.nextInt(10)));
		q.setContents(createContent());
		q.setAnswers(createContent());
		q.setParses(createContent());
		for(int i=0;i<3;i++){
			q.addChildQuestion(createChildQues());
		}
		return q;
	}
	private Question createChildQues(){
		Question q = new Question();
		q.setQuestionId(random.nextInt(100000));
		q.setScore(random.nextInt(20));
		q.setDifficulty(random.nextInt(5));
		q.setIndexNo(String.valueOf(random.nextInt(10)));
		q.setContents(createContent());
		q.setAnswers(createContent());
		q.setParses(createContent());
		q.setOptions(createOption());
		return q;
	}
	private List<Content> createContent(){
		List<Content> list = new ArrayList<Content>();
		for (int i = 0; i < 5; i++) {
			if(random.nextInt(5) == 3){
				list.add(createImg());
			}else{
				list.add(createText());
			}
		}
		return list;
	}
	private List<Option> createOption(){
		List<Option>  options = new ArrayList<Option>();
		for (int i = 0; i < 4; i++) {
			Option o = new Option();
			o.setContents(createContent());
			o.setOptionId(random.nextInt(5));
			o.setOptionNo(indexNo[i]);
			options.add(o);
		}
		return options;
	}
	private Content.Img createImg(){
		return new Content.Img("111111");
	}
	private Content.Text createText(){
		return new Content.Text("小物块沿斜面向上滑行的过程中，受重力、支持力、摩擦力");
	}
	private Image createImage(){
		Image image = new Image();
		image.setKey("111111");
		image.setPath("");
		image.setBase64("R0lGODlhxQAYAHcAMSH+GlNvZnR3YXJlOiBNaWNyb3NvZnQgT2ZmaWNlACH5BAEAAAAALAIABgDAAA4AgAAAAAAAAAL/hI+pC8EbojNS0viy3ryjelSY5ZXmWU5Uk0yOisbyA78tC8/67uVrg2H4eMTM8BUsKpcsxVCoqTFRx2kniK2BQthVEgrpGT/Ai5lLuuS+wGqYlz2bXbh6+fdDtvFONj86FuJV56IihUeX1+dmJOJHaNc0Ipl4+JfYFCgY5egnZXOHmTnKJwq2CUepmFeIiMq1abixlkJDFoope7ub+fhKOhMneas7mua0CozMO2sbi6rlI838RFr90bnciwi6bUqcfG1tUl2YJvsN/Zx8wxysDjpSnLSFbW5xrDZdC0FyDquv35poLfIpizHP3B1AAK0oEXdqocOJRGhRTEWl3cWNDcHqcZQBkYbHjyQNFAAAOw==");
		image.setSuffix("jpg");
		image.setWidth(112);
		image.setHeight(14);
		return image;
	}
//	@Test
	public void case1() {
	//	String json = "{\"examId\":2701,\"examName\":\"高中语文期中测试题\",\"testId\":1131,\"year\":2013,\"semester\":2,\"subjectNo\":1, \"examType\":1,\"b_topic_num\":10,\"m_topic_num\":50, \"total\":120, \"creator\":\"admin\", \"qRandom\":1, \"oRandom\":1, \"bCodeType\":[\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"七\",\"八\",\"九\",\"十\",\"十一\"], \"mCodeType\":[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"9\",\"10\",\"11\",\"12\"], \"passScore\":75, \"eStatus\":\"\", \"examArea\":10, \"gradeLevel\":5, \"bigQues\":[{ \"typeId\":1802, \"typeName\":\"阅读理解\", \"typeDetail\":\"阅读理解\", \"orderNum\":3, \"bTopic\":\"三\", \"quesType\":6, \"quesNum\":2, \"defaultScore\":20, \"quesList\":[{ \"quesId\":1, \"quesType\":6, \"score\":0, \"quesRec\":\"\", \"defaultScore\":20, \"parse\":\"落地有声 鞯 革\", \"answer\":\"芏砝码霏霏地在 震 砝码\", \"knowledge_point\" : [{ \"kpname\" : \"字音\", \"kppath\" : \"基础知识 - 字音\", \"kpid\" : \"8\" }], \"difficulty\":3.0, \"ques_content\" : \"阿萨德法师打发士大夫\", \"m_topic\" : 1, \"shareFlag\":\"\", \"options\":[], \"childQuesList\":[{ \"quesId\":1, \"quesType\":1, \"score\":2, \"defaultScore\":20, \"parse\":\"\", \"quesRec\":\"\", \"answer\":\"\", \"knowledge_point\" : [{ \"kpname\" : \"字音\", \"kppath\" : \"基础知识 - 字音\", \"kpid\" : \"8\" }], \"difficulty\":2.5, \"ques_content\":\"阿萨德法师打发士大夫\", \"m_topic\" : 1, \"shareFlag\":\"\", \"options\":[{ \"optionId\":100, \"optionNo\":\"A\", \"isRight\":0, \"optionContent\":\"aaaaaaa\" },{ \"optionId\":101, \"optionNo\":\"B\", \"isRight\":0, \"optionContent\":\"bbbbbbb\" },{ \"optionId\":102, \"optionNo\":\"C\", \"isRight\":1, \"optionContent\":\"ccccccc\" },{ \"optionId\":103, \"optionNo\":\"D\", \"isRight\":0, \"optionContent\":\"dddddd\" }], \"childQuesList\":[] },{ \"quesId\":2, \"quesType\":1, \"score\":2, \"quesRec\":\"\", \"parse\":\"\", \"answer\":\"\", \"knowledge_point\" : [{ \"kpname\" : \"字音\", \"kppath\" : \"基础知识 - 字音\", \"kpid\" : \"8\" }], \"difficulty\" : 2.5, \"ques_content\" : \"阿萨德法师打发士大夫\", \"m_topic\" : 2, \"shareFlag\":\"\", \"options\":[{ \"optionId\":200, \"optionNo\":\"A\", \"isRight\":0, \"optionContent\":\"aaaaaaa\" },{ \"optionId\":201, \"optionNo\":\"B\", \"isRight\":0, \"optionContent\":\"bbbbbbb\" },{ \"optionId\":202, \"optionNo\":\"C\", \"isRight\":1, \"optionContent\":\"ccccccc\" },{ \"optionId\":303, \"optionNo\":\"D\", \"isRight\":0, \"optionContent\":\"dddddd\" }] }] }]  },{ \"typeId\":1803, \"typeName\":\"选择题\", \"typeDetail\":\"选择题\", \"orderNum\":1, \"bTopic\":\"一\", \"quesType\":1, \"quesNum\":2, \"defaultScore\":30, \"quesList\":[{ \"quesId\":55, \"quesType\":1, \"score\":0, \"quesRec\":\"\", \"parse\":\"\", \"answer\":\"\", \"knowledge_point\" : [{ \"kpname\" : \"字音\", \"kppath\" : \"基础知识 - 字音\", \"kpid\" : \"8\" }], \"difficulty\" : 3.0, \"ques_content\" : \"阿萨德法师打发士大夫\", \"m_topic\" : 1, \"shareFlag\":\"\", \"options\":[{ \"optionId\":200, \"optionNo\":\"A\", \"isRight\":0, \"optionContent\":\"aaaaaaa\" },{ \"optionId\":201, \"optionNo\":\"B\", \"isRight\":0, \"optionContent\":\"bbbbbbb\" },{ \"optionId\":202, \"optionNo\":\"C\", \"isRight\":1, \"optionContent\":\"ccccccc\" },{ \"optionId\":303, \"optionNo\":\"D\", \"isRight\":0, \"optionContent\":\"dddddd\"}], \"childQuesList\":[]}]}]}";
		Gson gson = new GsonBuilder().create();
		//Map exam = gson.fromJson(json, Map.class);
		System.out.println(gson.toJson(exam));
		LStrMap<Object> data = LStrMap.newInstance();
		data.put("exam", exam);
		Configuration config = new Configuration();
		config.setClassForTemplateLoading(CorpusController.class, "/template");
		config.setDefaultEncoding("UTF-8");
		try {
			Template tpl = config.getTemplate("试卷导出模板A3.xml");
			tpl.process(data,new FileWriterWithEncoding(new File("C:/Users/adminPC/Documents/export.doc"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
