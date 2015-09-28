package com.eps.web.esextends;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eps.dao.Page;
import com.eps.domain.FGoodModel;
import com.eps.domain.FTrade;
import com.eps.domain.User;
import com.eps.service.esextends.ExamOrderService;
import com.eps.service.esextends.TeacherAuthenService;
import com.eps.service.examsystem.ExamAfterService;
import com.eps.utils.LStrMap;
import com.eps.utils.SequenceFactory;
import com.eps.utils.UStrMap;
import com.eps.web.BaseController;

@Controller
public class ExamOrderSystem extends BaseController{
	
	@Autowired
	private ExamOrderService eos;
	
	@Autowired
	private ExamAfterService eas;
	
	@Autowired
	private TeacherAuthenService tas;
	/**
	 * 进入试卷相关首页
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/order_index.html") 
	public String enterOrderIndex(ModelMap mm){
		mm.put("title", "考试首页");
		return "examsystem/order_index";
	}
	
	/**
	 * 进入我的订阅的试卷页面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/exam_orderExam.html")
	public String enterOrderExam(ModelMap mm,HttpServletRequest request,String sortNo,String commitFlag,int pageNo, int pageSize){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		List<List<LStrMap<Object>>> list=eos.getOrderLeftInfoService(userId);
		mm.put("ORDEXAM", list.get(0));
		mm.put("ORDTEACHER",list.get(1));
		mm.put("RELSTATUS", list.get(2));
		mm.put("RECTEACHER",list.get(3));
		mm.put("COMMQU", eos.getCommitQuService(userId));
		Page page=eos.getNowExamInfoService(userId,sortNo,commitFlag,pageNo,pageSize);
		mm.put("EXAMINFO", page.getData());
		mm.put("page", page);
		mm.put("TESTREC", eos.getTestRecordService(userId));
		mm.put("USER", user);
		mm.put("PHOTO", user.getPhoto());
		mm.put("CONDITION1", sortNo);
		mm.put("CONDITION2", commitFlag);
		return "examsystem/exam_orderExam";
	}
	
	/**
	 * 进入我的订阅的老师页面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/exam_orderTeacher.html")
	public String enterOrderTeacher(HttpServletRequest request,ModelMap mm,String isLose,String subjectNo,int pageNo, int pageSize){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		List<List<LStrMap<Object>>> list=eos.getOrderLeftInfoService(userId);
		mm.put("ORDEXAM", list.get(0));
		mm.put("ORDTEACHER", list.get(1));
		mm.put("RELSTATUS", list.get(2));
		mm.put("RECTEACHER", list.get(3));
		mm.put("ISLOSE", eos.getTeacherLoseService(userId));
		Page page=eos.getTeacherInfoService(userId, isLose, subjectNo, pageNo, pageSize);
		mm.put("TINFO", page.getData());
		
		mm.put("page", page);
		mm.put("CONDITION1", subjectNo);
		mm.put("CONDITION2", isLose);
		mm.put("PHOTO", user.getPhoto());
		return "examsystem/exam_orderTeacher";
	}
	
	/**
	 * 进入我的发布页面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/exam_orderRelease.html")
	public String enterOrderRelease(HttpServletRequest request,ModelMap mm,int release,int pageSize,int pageNo){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		if(request.getParameter("saleType")!=null){
			int salType=Integer.parseInt(request.getParameter("saleType").toString());
			if(salType!=-1){
				eos.updateForsaleService(userId,salType);
			}
		}
		//删除试卷
		if(request.getParameter("examId")!=null){
			eos.deleteExamService(userId,Integer.parseInt(request.getParameter("examId").toString()));
		}
		List<List<LStrMap<Object>>> list=eos.getOrderLeftInfoService(userId);
		mm.put("ORDEXAM", list.get(0));
		mm.put("ORDTEACHER",list.get(1));
		mm.put("RELSTATUS", list.get(2));
		mm.put("RECTEACHER", list.get(3));
		mm.put("RELSALE", eos.getcfInforService(userId));
		
		Page page=eos.getExamReleaseService(userId, pageNo, pageSize, release);
		mm.put("RELEXAMINFO", page.getData());
		mm.put("page", page);
		mm.put("PHOTO", user.getPhoto());
		mm.put("CONDITION1", release);
		
		mm.put("AUTHENLIST", tas.getAuthenStatusService(userId));
		
		return "examsystem/exam_orderRelease";
	}
	
	/**
	 * 增加评论信息界面
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/exam_orderAssess.html")
	public String initAssess(HttpServletRequest request,ModelMap mm,int teacherId){
		mm.put("ONETEACHER", eos.getOneTeacherInfoService(teacherId));
		if(request.getParameter("payType")!=null){
			mm.put("payType",request.getParameter("payType"));
		}
		return "examsystem/exam_orderAssess";
	}
	
	/**
	 * 增加评论信息
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/saveAssess/exam_orderComDetail.html")
	public String saveAssessInfo(HttpServletRequest request,ModelMap mm,int pageNo, int pageSize,int assType){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		String goodsId=request.getParameter("goodsId").toString();
		String assessType=request.getParameter("assessType").toString();
		String assessInfo=request.getParameter("assessInfo").toString();
		if(request.getParameter("payType")!=null){
			mm.put("payType",request.getParameter("payType"));
		}
		UStrMap<Object> map=UStrMap.newInstance();
		map.put("GOODS_ID", goodsId);
		map.put("USER_NO", userId);
		map.put("ASS_TYPE",assessType);
		map.put("ASS_CONTENT",assessInfo);
		eos.saveGoodsAssessService(map);
		int teacherId=Integer.parseInt(request.getParameter("teacherId").toString());
		return "redirect:/exam_orderComDetail.html?teacherId="+teacherId+"&goodsId="+goodsId+"&pageNo="+pageNo+"&pageSize="+pageSize+"&assType="+assType;
		
	}
	
	/**
	 * 评论界面
	 * @param request
	 * @param mm
	 * @param pageNo
	 * @param pageSize
	 * @param assType
	 * @return
	 */
	@RequestMapping(value="/exam_orderComDetail.html")
	public String initAssessInfo(HttpServletRequest request,ModelMap mm,int pageNo, int pageSize,int assType){
		int teacherId=Integer.parseInt(request.getParameter("teacherId").toString());
		mm.put("TEAINFO", eos.getTeacherPerInfoService(teacherId));
		String goodsId=request.getParameter("goodsId").toString();
		if(request.getParameter("payType")!=null){
			mm.put("payType",request.getParameter("payType"));
		}
		Page page=eos.getTeacherAssessService(goodsId,pageNo,pageSize,assType);
		mm.put("TEACHERASS",page.getData());
		mm.put("ASSNUM", eos.getAssessQuentityService(goodsId));
		mm.put("page", page);
		mm.put("TEACHERID", teacherId);
		mm.put("GOODSID", goodsId);
		mm.put("ASSTYPE", assType);
		mm.put("TEACHERID", teacherId);
		return "examsystem/exam_orderComDetail";
	}
	
	/**
	 * 所有教师显示
	 * @param request
	 * @param mm
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/exam_allTeacher.html")
	public String initAllTeacher(HttpServletRequest request,ModelMap mm,int pageNo,int pageSize){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		int sortParentId=Integer.parseInt(request.getParameter("sortParentId").toString());
		String parentSubjectno=request.getParameter("parentSubjectno").toString();
		String sortParentName=request.getParameter("sortParentName").toString();
		String examarea=request.getParameter("examarea").toString();
		String sortName=request.getParameter("sortName").toString();
		String sortType=request.getParameter("sortType").toString();
		
		mm.put("SUBLIST", eos.getSubjectnoQuenService(userId, sortParentId));
		mm.put("AREALIST", eos.getExamAreaQuenService(userId, sortParentId));
		Page page= eos.getAllOrderTeacherService(userId, parentSubjectno, examarea, pageNo, pageSize,sortName,sortType,sortParentId);
		mm.put("TEACHERLIST", page.getData());
		mm.put("page", page);
		
		mm.put("CONDITION1", parentSubjectno);
		mm.put("CONDITION2", examarea);
		mm.put("SORTNAME", sortName);
		mm.put("SORTTYPE", sortType);
		mm.put("SORTPARENTNAME", sortParentName);
		mm.put("SORTPARENTID", sortParentId);
		
		mm.put("sortList",eas.getAllSortNoAndNameService());
		
		return "examsystem/exam_allTeacher";
	}
	
	/**
	 * 定购界面
	 * @param request
	 * @param mm
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/exam_orderView.html")
	public String initOrderView(HttpServletRequest request,ModelMap mm,int pageNo,int pageSize,HttpServletResponse response){
		int teacherId=Integer.parseInt(request.getParameter("teacherId").toString());
		String whatType=request.getParameter("whatType").toString();
		String payType=request.getParameter("payType").toString();
		mm.put("TEAINFO", eos.getTeacherPerInfoService(teacherId));
		Page traPage=eos.getTradeByTeacheridService(teacherId, pageNo, pageSize);
		mm.put("TRADELIST", traPage.getData());
		Page relPage=eos.getReleaseByTeacheridService(teacherId, pageNo, pageSize);
		mm.put("RELLIST", relPage.getData());

		mm.put("page1", relPage);

		mm.put("page2", traPage);
		
		mm.put("RELCOUNT",relPage.getTotalCount());
		mm.put("TRACOUNT",traPage.getTotalCount());
		mm.put("WHATTYPE", whatType);
		mm.put("TEACHER_ID", teacherId);
		mm.put("payType",payType);
		
		response.setCharacterEncoding("UFT-8");
		
		mm.put("MODELLIST", eos.getModelByTeacheridService(teacherId));
		
		return "examsystem/exam_orderView";
	}
	
	/**
	 * 支付方式选择界面
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/order_choosePay.html")
	public String initChoosePay(HttpServletRequest request,ModelMap mm){
		Float price=Float.parseFloat(request.getParameter("price").toString());
		int orderMonth=Integer.parseInt(request.getParameter("orderMonth").toString());
		String authorName=request.getParameter("authorName").toString();
		String goodsId=request.getParameter("goodsId").toString();
		int modelId=Integer.parseInt(request.getParameter("modelId").toString());
		mm.put("PRICE", price);
		mm.put("ORDERMONTH", orderMonth);
		mm.put("AUTHORNAME",authorName);
		mm.put("GOODS_ID", goodsId);
		mm.put("MODEL_ID", modelId);
		System.out.println(price+":"+orderMonth+":"+authorName+":"+goodsId+":"+modelId);
		return "examsystem/order_choosePay";
	}
	
	/**
	 * 生成订单界面
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/order_createOrder.html")
	public String initCreateOrder(HttpServletRequest request,ModelMap mm){
		Float price=Float.parseFloat(request.getParameter("price").toString());
		String goodsId=request.getParameter("goodsId").toString();
		int modelId=Integer.parseInt(request.getParameter("modelId").toString());
		String modelName=request.getParameter("modelName");
		mm.put("PRICE", price);
		mm.put("ORDERNUM", SequenceFactory.getTradeSequence());
		mm.put("GOODS_ID", goodsId);
		mm.put("MODEL_ID", modelId);
		mm.put("ORDER_MONTH", modelName);
		return "examsystem/order_createOrder";
	}
	
	/**
	 * 生成订单界面(发送订单)
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/in/order_createOrder.html")
	public String initCreateOrder(HttpServletRequest request,ModelMap mm,FTrade fTrade){
		User user=getSessionUser(request);
		Float total_fee=Float.parseFloat(request.getParameter("total_fee").toString());
		String out_trade_no=request.getParameter("out_trade_no").toString();
		String subject=request.getParameter("subject").toString();
		fTrade.setMoney(total_fee);
		fTrade.setTradeId(out_trade_no);
		fTrade.setUserId(user.getUserId());
		fTrade.setTradeType(1);
		fTrade.setTradeVariety(7);
		fTrade.setTradeStatus(1);
		eos.saveTradeRecordService(fTrade);
		mm.put("total_fee", total_fee);
		mm.put("out_trade_no", out_trade_no);
		mm.put("subject", subject);
		mm.put("redirect_success", "/order_paySuccess.html");
		mm.put("redirect_error", "/order_payFail.html");
		return "redirect:/alipay.html";
	}
	
	/**
	 * 支付成功　同步
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/order_paySuccess.html")
	public String initPaySuccess(HttpServletRequest request,ModelMap mm){
		User user=getSessionUser(request);
		Float total_fee=Float.parseFloat(request.getParameter("total_fee").toString());
		String out_trade_no=request.getParameter("out_trade_no").toString();
		eos.payMoneyService(total_fee, out_trade_no,user.getUserId());
		return "examsystem/order_paySuccess";
	}

	/**
	 * 支付成功　异步
	 * @param request
	 * @param mm
	 */
	@RequestMapping(value="/async/order_paySuccess.html")
	public void initAsyncPaySuccess(HttpServletRequest request,ModelMap mm){
		User user=getSessionUser(request);
		Float total_fee=Float.parseFloat(request.getParameter("total_fee").toString());
		String out_trade_no=request.getParameter("out_trade_no").toString();
		eos.payMoneyService(total_fee, out_trade_no,user.getUserId());
	}
	
	/**
	 * 支付失败
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/order_payFail.html")
	public String initPayFail(HttpServletRequest request,ModelMap mm){
		
		return "examsystem/order_payFail";
	}
	
	/**
	 * 老师上架
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/order_orderPrice.html")
	public String saleTeacher(HttpServletRequest request,ModelMap mm){
		User user=getSessionUser(request);
		long userId=user.getUserId();
		List<LStrMap<Object>> list=eos.getGoodsIdService(user.getUserId());
		if(list.get(0).get("GOODS_ID")==null){
			eos.saveGoodsService(userId, Integer.parseInt(list.get(0).get("TEACHER_ID").toString()));
		}else{
			eos.updateForsaleService(userId,1);
		}
		mm.put("TEACHER_NAME", list.get(0).get("TEACHER_NAME"));
		mm.put("EXAMMODEL", eos.getGoodModelService(userId));
		return "examsystem/order_orderPrice";
	}
	
	/**
	 * 设置商品价格 教师
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value="/order_setSuccess.html")
	public String setSuccess(HttpServletRequest request,ModelMap mm){
		User user=getSessionUser(request);
		List<LStrMap<Object>> list=eos.getGoodsIdService(user.getUserId());
		String goodsId=list.get(0).get("GOODS_ID").toString();
		mm.put("TEACHER_NAME", list.get(0).get("TEACHER_NAME"));
		FGoodModel fGoodModel=new FGoodModel();
		fGoodModel.setGoodsId(goodsId);
		fGoodModel.setStock(-1);
		fGoodModel.setRemark("");
		for (int i = 1; i <= Integer.parseInt(request.getParameter("countMonth")); i++) {
			if(request.getParameter("month"+i)!=null){
				String modelName=request.getParameter("month"+i).toString();
				float unitPrice=Float.parseFloat(request.getParameter("price"+i).toString().toString());
				fGoodModel.setUnitPrice(unitPrice);
				fGoodModel.setModelName(modelName);
				if(request.getParameter("modelId"+i)!=null){
					fGoodModel.setModelId(Integer.parseInt(request.getParameter("modelId"+i)));
					eos.updateGoodModelService(fGoodModel);
				}else{
					eos.saveGoodModelService(fGoodModel);
				}
			}else{
				if(request.getParameter("modelId"+i)!=null ){
					fGoodModel.setModelId(Integer.parseInt(request.getParameter("modelId"+i)));
					eos.deleteGoodModelService(fGoodModel);
				}
			}
		}
		
		mm.put("pageSize", 10);
		mm.put("pageNo", 1);
		mm.put("release", -1);
		mm.put("saleType", -1);
		mm.put("examId", 0);
		return "redirect:/exam_orderRelease.html";
	}
}
