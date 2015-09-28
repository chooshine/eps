package com.eps.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;







import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.io.*;
import java.util.*;

/**
 * 
 * HTTP 帮助类
 *
 */
public class HttpHelper
{
	/** URL 地址分隔符 */
	public static final String URL_PATH_SEPARATOR	= "/";
	/** HTTP URL 标识 */
	public static final String HTTP_SCHEMA			= "http";
	/** HTTPS URL 标识 */
	public static final String HTTPS_SCHEMA			= "https";
	/** HTTP 默认端口 */
	public static final int HTTP_DEFAULT_PORT		= 80;
	/** HTTPS 默认端口 */
	public static final int HTTPS_DEFAULT_PORT		= 443;
	/** 默认缓冲区大小 */
	private static final int DEFAULT_BUFFER_SIZE	= 4096;
	
	private static final String METHOD_GET = "GET";
	private static final String METHOD_POST = "POST";
	private static ServletContext servletContext;
	
	/** 获取 {@link ServletContext} */
	public static ServletContext getServletContext()
	{
		return servletContext;
	}

	private static final String defaultContentEncoding = "UTF-8";

	/** 向页面输出文本内容 */
	public final static void writeString(HttpURLConnection conn, String content, String charsetName) throws IOException
	{
		writeString(conn.getOutputStream(), content, charsetName);
	}

	/** 向页面输出文本内容 */
	public final static void writeString(HttpServletResponse res, String content, String charsetName) throws IOException
	{
		writeString(res.getOutputStream(), content, charsetName);
	}

	/** 向页面输出文本内容 */
	public final static void writeString(OutputStream os, String content, String charsetName) throws IOException
	{
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, charsetName));
		
		pw.write(content);
		pw.flush();
		pw.close();
	}

	/** 向页面输出字节内容 */
	public final static void writeBytes(HttpURLConnection conn, byte[] content) throws IOException
	{
		writeBytes(conn.getOutputStream(), content);
	}

	/** 向页面输出字节内容 */
	public final static void writeBytes(HttpServletResponse res, byte[] content) throws IOException
	{
		writeBytes(res.getOutputStream(), content);
	}

	/** 向页面输出字节内容 */
	public final static void writeBytes(OutputStream os, byte[] content) throws IOException
	{
		BufferedOutputStream bos = new BufferedOutputStream(os);
		
		bos.write(content);
		bos.flush();
		bos.close();
	}

	/** 读取页面请求的文本内容 */
	public final static String readString(HttpURLConnection conn, boolean escapeReturnChar, String charsetName) throws IOException
	{
		return readString(conn.getInputStream(), escapeReturnChar, charsetName);
	}

	/** 读取页面请求的字节内容 */
	public final static String readString(HttpServletRequest request, boolean escapeReturnChar, String charsetName) throws IOException
	{
		return readString(request.getInputStream(), escapeReturnChar, charsetName);
	}

	/** 读取页面请求的文本内容 */
	public final static String readString(InputStream is, boolean escapeReturnChar, String charsetName) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, charsetName));
		
		try
		{
    		if(escapeReturnChar)
    		{
        		for(String line = null; (line = rd.readLine()) != null;)
        			sb.append(line);
     		}
    		else
    		{
    			int count		= 0;
    			char[] array	= new char[DEFAULT_BUFFER_SIZE];
    			
    			while((count = rd.read(array)) != -1)
    				sb.append(array, 0, count);
    		}
		}
		finally
		{
			rd.close();
		}
		
		return sb.toString();
	}
	
	/** 读取页面请求的字节内容 */
	public final static byte[] readBytes(HttpURLConnection conn) throws IOException
	{
		return readBytes(conn.getInputStream(), conn.getContentLength());
	}

	/** 读取页面请求的字节内容 */
	public final static byte[] readBytes(HttpServletRequest request) throws IOException
	{
		return readBytes(request.getInputStream(), request.getContentLength());
	}
	
	/** 读取页面请求的字节内容 */
	public final static byte[] readBytes(InputStream is) throws IOException
	{
		return readBytes(is, 0);
	}

	/** 读取页面请求的字节内容 */
	public final static byte[] readBytes(InputStream is, int length) throws IOException
	{
		byte[] array = null;
		
		if(length > 0)
		{
			array = new byte[length];
		
			int read	= 0;
			int total	= 0;
			
			while((read = is.read(array, total, array.length - total)) != -1)
				total += read;
		}
		else
		{
			List<byte[]> list	= new LinkedList<byte[]>();
			byte[] buffer		= new byte[DEFAULT_BUFFER_SIZE];

			int read	= 0;
			int total	= 0;
			
			for(; (read = is.read(buffer)) != -1; total += read)
			{
				byte[] e = new byte[read];
				System.arraycopy(buffer, 0, e, 0, read);
				list.add(e);
			}
			
			array = new byte[total];
			
			int write = 0;
			for(byte[] e : list)
			{
				System.arraycopy(e, 0, array, write, e.length);
				write += e.length;
			}
		}
		
		return array;
	}
	

	/** 置换常见的 XML 特殊字符 */
	public final static String regulateXMLStr(String src)
	{
		String result = src;
		result = result.replaceAll("&", "&amp;");
		result = result.replaceAll("\"", "&quot;");
		result = result.replaceAll("'", "&apos;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		
		return result;
	}

	/** 置换常见的 HTML 特殊字符 */
	public final static String regulateHtmlStr(String src)
	{
		String result = src;
		result = result.replaceAll("&", "&amp;");
		result = result.replaceAll("\"", "&quot;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("\r\n", "<br/>");
		result = result.replaceAll(" ", "&nbsp;");
		
		return result;
	}
	
	/** 确保 URL 路径的前后存在 URL 路径分隔符 */
	public static final String ensurePath(String path, String defPath)
	{
		if(StringUtils.isEmpty(path))
			path = defPath;
		if(!path.startsWith(URL_PATH_SEPARATOR))
			path = URL_PATH_SEPARATOR + path;
		if(!path.endsWith(URL_PATH_SEPARATOR))
			path = path + URL_PATH_SEPARATOR;
		
		return path;
	}
	
	/** 获取 {@link HttpServletRequest} 的指定属性值 */
	@SuppressWarnings("unchecked")
	public final static <T> T getRequestAttribute(HttpServletRequest request, String name)
	{
		return (T)request.getAttribute(name);
	}

	/** 设置 {@link HttpServletRequest} 的指定属性值 */
	public final static <T> void setRequestAttribute(HttpServletRequest request, String name, T value)
	{
		request.setAttribute(name, value);
	}
	
	/** 删除 {@link HttpServletRequest} 的指定属性值 */
	public final static void removeRequestAttribute(HttpServletRequest request, String name)
	{
		request.removeAttribute(name);
	}

	/** 获取 {@link HttpSession} 的指定属性值 */
	@SuppressWarnings("unchecked")
	public final static <T> T getSessionAttribute(HttpSession session, String name)
	{
		return (T)session.getAttribute(name);
	}

	/** 设置 {@link HttpSession} 的指定属性值 */
	public final static <T> void setSessionAttribute(HttpSession session, String name, T value)
	{
		session.setAttribute(name, value);
	}
	
	/** 删除 {@link HttpSession} 的指定属性值 */
	public final static void removeSessionAttribute(HttpSession session, String name)
	{
		session.removeAttribute(name);
	}
	
	/** 销毁 {@link HttpSession} */
	public final static void invalidateSession(HttpSession session)
	{
		session.invalidate();
	}

	/** 获取 {@link ServletContext} 的指定属性值 */
	@SuppressWarnings("unchecked")
	public final static <T> T getApplicationAttribute(String name)
	{
		return (T)getApplicationAttribute(servletContext, name);
	}

	/** 获取 {@link ServletContext} 的指定属性值 */
	@SuppressWarnings("unchecked")
	public final static <T> T getApplicationAttribute(ServletContext servletContext, String name)
	{
		return (T)servletContext.getAttribute(name);
	}

	/** 设置 {@link ServletContext} 的指定属性值 */
	public final static <T> void setApplicationAttribute(String name, T value)
	{
		setApplicationAttribute(servletContext, name, value);
	}

	/** 设置 {@link ServletContext} 的指定属性值 */
	public final static <T> void setApplicationAttribute(ServletContext servletContext, String name, T value)
	{
		servletContext.setAttribute(name, value);
	}
	
	/** 删除 {@link ServletContext} 的指定属性值 */
	public final static void removeApplicationAttribute(String name)
	{
		removeApplicationAttribute(servletContext, name);
	}
	
	/** 删除 {@link ServletContext} 的指定属性值 */
	public final static void removeApplicationAttribute(ServletContext servletContext, String name)
	{
		servletContext.removeAttribute(name);
	}

	/** 获取 {@link HttpServletRequest} 的指定请求参数值，并去除前后空格 */
	public final static String getParam(HttpServletRequest request, String name)
	{
		String param = getParamNoTrim(request, name);
		if(param != null) return param = param.trim();
		
		return param;
	}

	/** 获取 {@link HttpServletRequest} 的指定请求参数值 */
	public final static String getParamNoTrim(HttpServletRequest request, String name)
	{
		return request.getParameter(name);
	}

	/** 获取 {@link HttpServletRequest} 的参数名称集合 */
	public final static List<String> getParamNames(HttpServletRequest request)
	{
		List<String> names		= new ArrayList<String>();
		Enumeration<String> en	= request.getParameterNames();
		
		while(en.hasMoreElements())
			names.add(en.nextElement());
		
		return names;
	}

	/** 获取 {@link HttpServletRequest} 的指定请求参数值集合 */
	public final static List<String> getParamValues(HttpServletRequest request, String name)
	{
		String[] values = request.getParameterValues(name);
		return values != null ? Arrays.asList(values) : null;
	}
	
	/** 获取 {@link HttpServletRequest} 的所有参数名称和值 */
	public final static Map<String, String[]> getParamMap(HttpServletRequest request)
	{
		return request.getParameterMap();
	}

	

	/** 获取 {@link HttpSession} 对象，如果没有则进行创建。 */
	public final static HttpSession getSession(HttpServletRequest request)
	{
		return getSession(request, true);
	}

	/** 获取 {@link HttpSession} 对象，如果没有则根据参数决定是否创建。 */
	public final static HttpSession getSession(HttpServletRequest request, boolean create)
	{
		return request.getSession(create);
	}

	/** 创建 {@link HttpSession} 对象，如果已存在则返回原对象。 */
	public final static HttpSession createSession(HttpServletRequest request)
	{
		return getSession(request);
	}

	/** 获取所有 {@link Cookie} */
	public final static Cookie[] getCookies(HttpServletRequest request)
	{
		return request.getCookies();
	}
	
	/** 获取指定名称的 {@link Cookie} */
	public final static Cookie getCookie(HttpServletRequest request, String name)
	{
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null)
		{
			for(Cookie c : cookies)
			{
				if(c.getName().equals(name))
				{
					cookie = c;
					break;
				}
			}
		}
		
		return cookie;
	}

	/** 获取指定名称的 {@link Cookie} 值，失败返回 null */
	public final static String getCookieValue(HttpServletRequest request, String name)
	{
		String value = null;
		Cookie cookie = getCookie(request, name);
		
		if(cookie != null)
			value = cookie.getValue();
			if (value != null) {
				try {
					value = URLDecoder.decode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		return value;
	}

	/** 添加 {@link Cookie} */
	public final static void addCookie(HttpServletResponse response, Cookie cookie)
	{
		response.addCookie(cookie);
	}

	/** 添加 {@link Cookie} */
	public final static void addCookie(HttpServletResponse response, String name, String value)
	{
		addCookie(response, new Cookie(name, value));
	}
	
	/** 获取 URL 的  BASE 路径 */
	public final static String getRequestBasePath(HttpServletRequest request)
	{
		String scheme		= request.getScheme();
		int serverPort		= request.getServerPort();
		StringBuilder sb	= new StringBuilder(scheme).append("://").append(request.getServerName());
		
		if	(!(
				(scheme.equals(HTTP_SCHEMA) && serverPort == HTTP_DEFAULT_PORT) ||
				(scheme.equals(HTTPS_SCHEMA) && serverPort == HTTPS_DEFAULT_PORT) 
			))
				sb.append(":").append(request.getServerPort());
		
			sb.append(request.getContextPath()).append("/");

		return sb.toString();
	}
	
	/** 获取 URL 地址在文件系统的绝对路径,
	 * 
	 * Servlet 2.4 以上通过 request.getServletContext().getRealPath() 获取,
	 * Servlet 2.4 以下通过 request.getRealPath() 获取。
	 *  
	 */
	@SuppressWarnings("deprecation")
	public final static String getRequestRealPath(HttpServletRequest request, String path)
	{
		if(servletContext != null)
			return servletContext.getRealPath(path);
		else
		{
			try
			{
				Method m = request.getClass().getMethod("getServletContext");
				ServletContext sc = (ServletContext)m.invoke(request);
				return sc.getRealPath(path);
			}
			catch(Exception e)
			{
				return request.getRealPath(path);
			}
		}
	}
	
	/** 获取发送请求的客户端浏览器所在的操作系统平台 */
	public final static String getRequestUserAgentPlatform(HttpServletRequest request)
	{
		int index		= 1;
		String platform	= null;
		String agent	= request.getHeader("user-agent");
		
		if(StringUtils.isNotEmpty(agent))
		{
			int i				= 0;
			StringTokenizer st	= new StringTokenizer(agent, ";");
			
			while(st.hasMoreTokens())
			{
				String token = st.nextToken();
				
				if(i == 0)
				{
					if(token.toLowerCase().indexOf("compatible") != -1)
						index = 2;
				}
				else if(i == index)
				{
					int sep = token.indexOf(")");
					
					if(sep != -1)
						token = token.substring(0, sep);
					
					platform = StringUtils.trimToEmpty(token);
					
					break;
				}
				
				++i;
			}
		}

		return platform;
	}
	
	/** 设置 HTTP 的 'Content-Type' 响应头 */
	public final static void setContentType(HttpServletResponse response, String contentType, String encoding)
	{
		StringBuilder sb = new StringBuilder(contentType);
				
		if(encoding != null)
			sb.append(";charset=").append(encoding);
		
		response.setContentType(sb.toString());
	}
	
	/** 禁止浏览器缓存当前页面 */
	public final static void setNoCacheHeader(HttpServletResponse response)
	{
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}
	
	/** 检查请求是否来自非 Windows 系统的浏览器 */
	public final static boolean isRequestNotComeFromWidnows(HttpServletRequest request)
	{
		String agent = request.getHeader("user-agent");
		
		if(StringUtils.isNotEmpty(agent))
			return agent.toLowerCase().indexOf("windows") == -1;
		
		return false;
	}
	/**
	 * 发送GET请求
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public final static HttpRespons sendGet(String url) throws IOException{
		return send(url,METHOD_GET,null,null);
	}
	public final static HttpRespons sendGet(String url, Map<String,String> params) throws IOException{
		return send(url,METHOD_GET,params,null);
	}
	public final static HttpRespons sendGet(String url, Map<String,String> params,Map<String,String> properties) throws IOException{
		return send(url,METHOD_GET,params,properties);
	}
	public final static HttpRespons sendPost(String url) throws IOException{
		return send(url,METHOD_POST,null,null);
	}
	public final static HttpRespons sendPost(String url,Map<String,String> params) throws IOException{
		return send(url,METHOD_POST,params,null);
	}
	public final static HttpRespons sendPost(String url, Map<String,String> params,Map<String,String> properties) throws IOException{
		return send(url,METHOD_POST,params,properties);
	}
	/**
	 * 发送POST请求
	 * @param urlStr 请求地址
	 * @param send   发送数据包
	 * @return
	 * @throws IOException
	 */
	public final static HttpRespons sendPost(String urlStr,String send) throws IOException{
		HttpURLConnection conn = null;
		URL url = new URL(urlStr);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_POST);
        conn.setDoOutput(true);  
        conn.setDoInput(true);  
        conn.setUseCaches(false);
        conn.getOutputStream().write(send.getBytes());
        conn.getOutputStream().flush();
        conn.getOutputStream().close();
		return makeRespons(urlStr,conn);
	}
	private final static HttpRespons send(String urlStr,String method,Map<String, String> params, Map<String, String> propertys)  
			throws IOException{
		HttpURLConnection conn = null;
		if(METHOD_GET.equals(method) && params !=null){
			StringBuffer param = new StringBuffer();  
			int i = 0;  
			for (String key : params.keySet()) {  
				if (i == 0)  
					param.append("?");  
				else  
					param.append("&");  
				param.append(key).append("=").append(params.get(key));
				i++;  
			}  
			urlStr += param; 
		}
		URL url = new URL(urlStr);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
        conn.setDoOutput(true);  
        conn.setDoInput(true);  
        conn.setUseCaches(false);
        if(propertys != null){
        	for (String key : propertys.keySet()) {
                conn.addRequestProperty(key, propertys.get(key));
            }
        }
        if(METHOD_POST.equals(method) && params != null){
        	StringBuffer param = new StringBuffer();
            for (String key : params.keySet()) {  
                param.append("&");  
                param.append(key).append("=").append(params.get(key));
            }  
            conn.getOutputStream().write(param.toString().getBytes());
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
        }
		return makeRespons(urlStr,conn);
	}
	private final static HttpRespons makeRespons(String urlString,HttpURLConnection urlConnection)throws IOException{
		HttpRespons httpResponser = new HttpRespons();  
        try {  
            InputStream in = urlConnection.getInputStream();  
            BufferedReader bufferedReader = new BufferedReader(  
                    new InputStreamReader(in));  
            httpResponser.contentCollection = new Vector<String>();  
            StringBuffer temp = new StringBuffer();  
            String line = bufferedReader.readLine();
            while (line != null) {  
                httpResponser.contentCollection.add(line);  
                temp.append(line).append("\r\n");  
                line = bufferedReader.readLine();  
            }  
            bufferedReader.close();  
   
            String ecod = urlConnection.getContentEncoding();  
            if (ecod == null)  
                ecod = defaultContentEncoding;  
   
            httpResponser.urlString = urlString;  
   
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();  
            httpResponser.file = urlConnection.getURL().getFile();  
            httpResponser.host = urlConnection.getURL().getHost();  
            httpResponser.path = urlConnection.getURL().getPath();  
            httpResponser.port = urlConnection.getURL().getPort();  
            httpResponser.protocol = urlConnection.getURL().getProtocol();  
            httpResponser.query = urlConnection.getURL().getQuery();  
            httpResponser.ref = urlConnection.getURL().getRef();  
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();  
   
            httpResponser.content = new String(temp.toString().getBytes(), ecod);  
            httpResponser.contentEncoding = ecod;  
            httpResponser.code = urlConnection.getResponseCode();  
            httpResponser.message = urlConnection.getResponseMessage();  
            httpResponser.contentType = urlConnection.getContentType();  
            httpResponser.method = urlConnection.getRequestMethod();  
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();  
            httpResponser.readTimeout = urlConnection.getReadTimeout();
            
            return httpResponser;  
        } catch (IOException e) {  
            throw e;  
        } finally {  
            if (urlConnection != null)  
                urlConnection.disconnect();  
        }  
	}
	public final static String createSubmitForm(String action,String method,Map<String,String> params){
		List<String> keys = new ArrayList<String>(params.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form id=\"submitForm\" name=\"submitForm\" action=\"" + action
                      + "\" method=\"" + method
                      + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) params.get(name);
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"提交\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['submitForm'].submit();</script>");
        return sbHtml.toString();
	}
}
