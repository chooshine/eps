package com.eps.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.eps.cons.CommonConstant;
import com.eps.domain.User;
import com.eps.utils.StringHelper;

public class LoginFilter implements Filter {

	private static final String FILTERED_REQUEST = "@@session_context_filtered_request";

	/**
	 * 无需验证的URL列表
	 */
	private String[] unNeedCheckUrl;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// 保证每个URL只被验证一次
		if (request != null && request.getAttribute(FILTERED_REQUEST) != null) {
			chain.doFilter(request, arg1);
		} else {
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) arg1;
			User user = (User) httpRequest.getSession().getAttribute(
					CommonConstant.USER_CONTEXT);

			if (user == null
					&& !isURILogin(httpRequest.getRequestURI(), httpRequest)) {
				String toUrl = httpRequest.getRequestURL().toString();
				if (!StringUtils.isBlank(httpRequest.getQueryString())) {
					toUrl += "?" + httpRequest.getQueryString();
				}
				// 保存请求的URL，登陆完成后跳转回来。
				httpRequest.getSession().setAttribute(
						CommonConstant.LOGIN_TO_URL, toUrl);
				// 跳转到登陆界面
//				request.getRequestDispatcher("/account/login.html").forward(request,
//						arg1);
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
				return;
			}
			chain.doFilter(request, arg1);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("unCheckURLsuffix");
		if (urls != null) {
			unNeedCheckUrl = StringHelper.replaceBlank(urls).split(",");
		}
	}

	/**
	 * 当前URI资源是否需要登录才能访问
	 * 
	 * @param requestURI
	 * @param request
	 * @return
	 */
	private boolean isURILogin(String requestURI, HttpServletRequest request) {
		if (request.getContextPath().equalsIgnoreCase(requestURI)
				|| (request.getContextPath() + "/")
						.equalsIgnoreCase(requestURI))
			return true;
		for (String uri : unNeedCheckUrl) {
			if (requestURI != null && requestURI.indexOf(uri) >= 0) {
				return true;
			}
		}
		return false;
	}
}
