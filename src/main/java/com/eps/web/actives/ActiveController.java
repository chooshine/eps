package com.eps.web.actives;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eps.dao.Page;
import com.eps.domain.User;
import com.eps.service.actives.ActiveServiece;
import com.eps.service.esextends.TeacherAuthenService;
import com.eps.service.user.UserService;
import com.eps.utils.HttpHelper;
import com.eps.utils.LStrMap;
import com.eps.web.BaseController;

@Controller
public class ActiveController extends BaseController {

	@Autowired
	private ActiveServiece activeServiece;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeacherAuthenService tas;
	
	private final static String PHONECODE = "phonecode";

	/**
	 * 点击"查看详情"，跳转到活动页面
	 * 
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/actives/active.html")
	public String toActive(HttpServletRequest request, ModelMap mm) {
		return "actives/active";
	}

	/**
	 * 点击"立即参与"，跳转到不同的页面
	 * 
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/actives/activeTo.html")
	public String toActiveTo(HttpServletRequest request, ModelMap mm) {
		List<LStrMap<Object>> list = tas.getAuthenInfo(this.getSessionUser(request).getUserId());
		if (!list.isEmpty()) {
			return "redirect:/person/center.html";
		}
		return "redirect:/inputAuthenInfo.html";
	}

	/* 点击"账户金额"，跳转到"账户余额"页面 */
	@RequestMapping(value = "/person/accountAndBalance.html")
	public String toAccontAndBalance(String tradePageNo, String recordPageNo, String tradeOrRecord, HttpServletRequest request, ModelMap mm) {
		if(tradePageNo==null){
			tradePageNo="1";
		}
		if (recordPageNo==null) {
			recordPageNo="1";
		}
		if (tradeOrRecord==null) {
			tradeOrRecord="trade";
		}
		mm.put("title", "个人中心");
		User user = this.getSessionUser(request);
		long user_id = user.getUserId();

		// 得到用户账户余额
		List<LStrMap<Object>> accountBalance = activeServiece
				.getBalance(user_id);

		int tradePageNum = Integer.parseInt(tradePageNo);
		int recordPageNum = Integer.parseInt(recordPageNo);
		// 得到用户的提现记录
		Page allDrawRecord = activeServiece.getAllDrawRecord(user_id,recordPageNum);
		
		Page allTrade = activeServiece.getAllTradeByUserId(tradePageNum, user_id);

		mm.put("allTrade", allTrade);
		mm.put("accountBalance", accountBalance.get(0));
		mm.put("allDrawRecord", allDrawRecord);
		mm.put("tradeOrRecord", tradeOrRecord);
		mm.put("tradePageNo", tradePageNum);
		mm.put("recordPageNo", recordPageNum);
		return "personalCenter/accountAndBalance";
	}

	@RequestMapping(value="/drawMoney/getCheckCode.json")
	public String getCheckCode(HttpServletRequest request){
		User user = this.getSessionUser(request);
		String code = userService.sendPhoneCode(user, user.getPhone());
		request.getSession().setAttribute(PHONECODE, code);
		return "jsonView";
	}
	
	@RequestMapping(value="/drawMoney/checkCheckCode.json")
	public String checkCheckCode(HttpServletRequest request, ModelMap mm, String checkcode){
		String realCode = (String)request.getSession().getAttribute(PHONECODE);
		if (checkcode.equals(realCode)) {
			mm.put("success", 1);
		} else {
			mm.put("success", 0);
		}
		return "jsonView";
	}
	
	/* 点击"提现"按钮，跳转到提现第一步页面 */
	@RequestMapping(value = "/actives/drawMoneyStep1.html")
	public String toStep1(HttpServletRequest request, ModelMap mm, HttpServletResponse response) {
		User user = this.getSessionUser(request);
		if (user != null) {
			long userId = user.getUserId();
			//得到正在审核中的提现总额度
			double freezeMoney = activeServiece.getFreezeMoneyAmount(userId);
			//得到用户账户余额
			List<LStrMap<Object>> balance = activeServiece.getBalance(userId);
			double cash = ((BigDecimal)balance.get(0).get("cash")).doubleValue();
			//得到未冻结的账户额度
			double unfreezeMoney = cash-freezeMoney;
			//得到最大可提取的100的整数倍的金额
			int permitMoney = ((int)(unfreezeMoney/100))*100;
			if (cash >= 100) {
				mm.put("title", "余额提现");
				mm.put("cash", cash);
				mm.put("unfreezeMoney", unfreezeMoney);
				mm.put("permitMoney", permitMoney);
				return "actives/drawMoneyStep1";
			} else {
				mm.put("title", "个人中心");
				return "redirect:/person/center.html";
			}
		} else {
			mm.put("title", "登陆");
			return "redirect:/login.html";
		}
	}

	@RequestMapping(value="/drawMoney/analyBalance.json")
	public String analyBalance(HttpServletRequest request, ModelMap mm, String amount){
		LStrMap<Object> map = activeServiece.getSeveralValue(this.getSessionUser(request).getUserId());
		double cash = (Double)map.get("cash");
		double unfreezemoney = (Double)map.get("unfreezemoney");
		
		if (cash < 100) {
			mm.put("result", 0);
			mm.put("cash", cash);
			mm.put("unfreezemoney", unfreezemoney);
		} else if (Double.parseDouble(amount) > unfreezemoney) {
			mm.put("result", 1);
			mm.put("cash", cash);
			mm.put("unfreezemoney", unfreezemoney);
		} else {
			mm.put("result", 2);
		}
		
		return "jsonView";
	}
	/* 点击"确定提现"和查看详情,跳转到提现第二步页面 */
	@RequestMapping(value = "/actives/drawMoneyStep2.html")
	public String toStep2(HttpServletRequest request, ModelMap mm, HttpServletResponse response) throws UnsupportedEncodingException {
		User user = this.getSessionUser(request);
		mm.put("title", "余额提现");
		// 用一个map存放提现时填写的信息
		LStrMap<Object> writedInfo = LStrMap.newInstance();

		// 判断用户是否已登陆
		if (user != null) {
			String referUrl = request.getHeader("referer");
			if(referUrl==null || referUrl.equals("") || !referUrl.contains(request.getServerName())) {
				mm.put("title", "个人中心");
				return "redirect:/person/center.html";
			} else {
				// 判断请求来自于个人中心还是第一步提现页面
				List<LStrMap<Object>> list = null;
				LStrMap<Object> oneRecord = null;
				if (request.getParameter("drawmoney_id") != null) {// 有提现id，说明是从个人中心跳转过来的
					list = activeServiece.getOneDrawRecord(Integer.parseInt(request.getParameter("drawmoney_id")));
					oneRecord = list.get(0);
					writedInfo.put("drawmoney_time",oneRecord.get("drawmoney_time"));
					writedInfo.put("bankname", oneRecord.get("name"));
					writedInfo.put("account", oneRecord.get("account"));
					writedInfo.put("amount", oneRecord.get("amount"));
					writedInfo.put("receiver", oneRecord.get("receiver"));
					writedInfo.put("state", oneRecord.get("state"));
					writedInfo.put("unpassreason", oneRecord.get("remark"));
				} else {// 提现申请
					
					long userId = user.getUserId();
					float amount = Float.parseFloat(request.getParameter("amount"));
					String bankName = request.getParameter("bankname");
					String account = request.getParameter("account");
					String receiver = request.getParameter("receiver");
					
					writedInfo.put("bankname", activeServiece.getBankName(bankName).get("name"));
					writedInfo.put("account", account);
					writedInfo.put("amount", amount);
					writedInfo.put("receiver", receiver);
					writedInfo.put("state", 0);
					// 新建一个提现记录
					activeServiece.createDrawRecord(userId, bankName, receiver, account, amount);
				}
			}
		} else {
			mm.put("title", "登陆");
			return "redirect:/login.html";
		}

		mm.put("writedInfo", writedInfo);
		return "actives/drawMoneyStep2";
	}

	// 后台部分================================
	/**
	 * 管理员查看提现记录
	 * 
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/actives/activeBack.html")
	public String toActiveBack(HttpServletRequest request, ModelMap mm) {
		User user = this.getSessionUser(request);
		if (user != null) {
			if (user.getUserType() == 1) {// 用户类型为1，说明是管理员
				mm.put("title", "提现记录");
				String state = request.getParameter("state");
				if(state == null) {
					state = "-1";
				}
				List<LStrMap<Object>> list = activeServiece.getRecordsByState(state);
				mm.put("allDrawMoneyRecord", list);
				return "actives/activeBack";
			} else {// 类型不为1，说明是普通用户，跳转到个人中心
				mm.put("title", "个人中心");
				return "redirect:/person/center.html";
			}
		} else {
			mm.put("title", "登陆");
			return "redirect:/login.html";
		}
	}

	/**
	 * 查看提现记录详情
	 * 
	 * @param request
	 * @param mm
	 * @return
	 */
	@RequestMapping(value = "/actives/recordDetail.json")
	public String toRecordDetail(HttpServletRequest request, ModelMap mm) {
		long recordId = Long.parseLong(request.getParameter("drawmoney_id"));
		List<LStrMap<Object>> list = activeServiece.getOneDrawRecord(recordId);
		LStrMap<Object> map = list.get(0);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (map.get("transfer_time") != null) {
			String transfer_time = df.format((Date) map.get("transfer_time"));
			mm.put("transfer_time", transfer_time);
		}

		mm.put("drawmoney_id", map.get("drawmoney_id"));
		mm.put("bankname", map.get("name"));
		mm.put("account", map.get("account"));
		mm.put("receiver", map.get("receiver"));
		mm.put("transfer_user_no", map.get("transfer_user_no"));
		mm.put("transfer_evidence", map.get("transfer_evidence"));
		return "jsonView";
	}
	
	
	/**
	 * 上传汇款证明
	 * 
	 * @param mm
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/actives/upEvidence.html", method = RequestMethod.POST)
	public void upCertificate(ModelMap mm, HttpServletRequest request, HttpServletResponse response) {
		User user = this.getSessionUser(request);
		long user_id = user.getUserId();
		Map<String, String> map = activeServiece.upEvidence(user_id, request, response);
		try {
			HttpHelper.writeString(response, "<script>parent.setCerTt(\""
					+ URLEncoder.encode(map.get("errorInfo"), "UTF-8")
					+ "\",\"" + map.get("path") + "\");</script>", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/backstage/backstage.html")
	public String toBack(HttpServletRequest request, ModelMap mm) {
		User user = this.getSessionUser(request);
		if (user != null) {
			if (user.getUserType() == 1) {// 用户类型为1，说明是管理员
				mm.put("title", "后台管理");
				return "backstage/backstage";
			} else {// 类型不为1，说明是普通用户，跳转到个人中心
				mm.put("title", "个人中心");
				return "redirect:/person/center.html";
			}
		} else {
			mm.put("title", "登陆");
			return "redirect:/login.html";
		}
	}
	
	@RequestMapping(value="/drawmoney/unpass.json")
	public void drawMoneyUnpass(HttpServletRequest request, String drawmoney_id, String unpassReason){
		List<LStrMap<Object>> list = activeServiece.getOneDrawRecord(Long.parseLong(drawmoney_id));
		activeServiece.checkUnpassed(Long.parseLong(drawmoney_id), unpassReason, list, this.getSessionUser(request).getUserId());
	}
	
}
