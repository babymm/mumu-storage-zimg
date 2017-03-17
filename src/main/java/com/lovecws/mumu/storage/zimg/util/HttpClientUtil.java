package com.lovecws.mumu.storage.zimg.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * httpclient工具類
 * @author lovercws
 */
public class HttpClientUtil {
	private static final Logger log = Logger.getLogger(HttpClientUtil.class);

	/**
	 * httpClient get 获取资源
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		return get(url, null);
	}
	
	/**
	 * httpClient get 获取资源
	 * @param url
	 * @param 参数
	 * @return
	 */
	public static String get(String url,Map<String, Object> params) {
		if(params!=null&&params.size()>0){
			StringBuilder paramBuilder=new StringBuilder("?");
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Object> entry = iterator.next();
				paramBuilder.append(entry.getKey()+"="+entry.getValue());
				if(iterator.hasNext()){
					paramBuilder.append("&");
				}
			}
			url=url+paramBuilder.toString();
		}
		log.info("GET:"+url);
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet http = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(http);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * httpClient post 获取资源
	 * @param uri
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, Object> params) {
		log.info("POST:"+url);
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(url);
			if (params != null && params.size() > 0) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				Set<String> keySet = params.keySet();
				for (String key : keySet) {
					Object object = params.get(key);
					nvps.add(new BasicNameValuePair(key, object==null?null:object.toString()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * httpClient delete 删除资源
	 * @param url
	 * @return
	 */
	public static String delete(String url) {
		log.info("DELETE:"+url);
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpDelete httpDelete = new HttpDelete(url);
			CloseableHttpResponse response = httpClient.execute(httpDelete);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * httpClient 代理 delete 删除资源
	 * @param url
	 * @return
	 */
	public static String proxyDelete(String url, Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("_method", "delete");
		return post(url, params);
	}

	/**
	 * httpClient 代理 put 添加资源
	 * @param url
	 * @return
	 */
	public static String proxyPut(String url, Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("_method", "put");
		return post(url, params);
	}

	
	/**
	 * 文件上传
	 * @param url 上传路径
	 * @param file 上传文件
	 * @param paramMap 参数map
	 * @param headerMap 请求头map
	 * @return
	 */
	public static String upload(String url, File file,Map<String, Object> paramMap,Map<String, Object> headerMap) {
		List<File> files=new ArrayList<File>();
		files.add(file);
		return uploads(url, files, paramMap,headerMap);
	}
	
	/**
	 * 文件上传
	 * @param url 上传路径
	 * @param files 文件集合
	 * @param paramMap 参数map
	 * @param headerMap 请求头map
	 * @return
	 */
	public static String uploads(String url, List<File> files, Map<String, Object> paramMap,Map<String, Object> headerMap) {
		log.info("UPLOAD:"+url);
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(url);
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 封装文件
			if (files != null && files.size() > 0) {
				for (File file : files) {
					builder.addBinaryBody(UUID.randomUUID().toString(), file, ContentType.MULTIPART_FORM_DATA, file.getName());
				}
			}
			
			// 封装参数
			if (paramMap != null && paramMap.size() > 0) {
				for (Entry<String, Object> entry : paramMap.entrySet()) {
					Object value = entry.getValue();
					builder.addTextBody(entry.getKey(), value==null?null:value.toString(),ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
				}
			}
			builder.setCharset(Charset.forName("UTF-8"));
			builder.setContentType(ContentType.MULTIPART_FORM_DATA);
			
			//封装请求头
			if(headerMap!=null&&headerMap.size()>0){
				for (Entry<String, Object> entry : headerMap.entrySet()) {
					Object value = entry.getValue();
					httpPost.setHeader(entry.getKey(), value==null?null:value.toString());
				}
			}
			httpPost.setEntity(builder.build());
			CloseableHttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			log.error(e);
		} catch (ParseException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return null;
	}
	
}
