package com.lovecws.mumu.storage.zimg.service;

import com.google.gson.Gson;
import com.lovecws.mumu.storage.zimg.bean.ZImgInfo;
import com.lovecws.mumu.storage.zimg.bean.ZImgRet;
import com.lovecws.mumu.storage.zimg.util.HttpClientUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zimg 上传下载服务
 * @author lovecws
 */
public class ZImgServiceImpl implements ZImgService{

	private static final String UPLOAD_ACTION="upload";
	private static final String DELETE_ACTION="admin";
	private static final String INFO_ACTION="info";
	
	private static final Logger log=Logger.getLogger(ZImgServiceImpl.class);
	
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@SuppressWarnings("unused")
	private ZImgServiceImpl() {
	}
	public ZImgServiceImpl(String url) {
		this.url = url;
	}
	
	/**
	 * 文件上传
	 * @param file 上传文件
	 * @return
	 */
	@Override
	public String upload(File file){
		String imgHtml = HttpClientUtil.upload(url+"/"+UPLOAD_ACTION, file,null,null);
		int beginIndex = imgHtml.indexOf("href");
		String imgURL=imgHtml.substring(beginIndex+7, beginIndex+39);
		return imgURL;
	}
	
	/**
	 * 文件上传
	 * @param file 上传文件
	 * @param conetentType 上传文件类型   'jpeg', 'jpg', 'png', 'gif', 'webp'
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public String upload(File file,String conetentType){
		Map<String, Object> headerMap=new HashMap<String, Object>();
		headerMap.put("Content-Type", conetentType);
		String json = HttpClientUtil.upload(url+"/"+UPLOAD_ACTION, file,null,headerMap);
		Gson gson=new Gson();
		Map map = gson.fromJson(json, Map.class);
		Object ret = map.get("ret");
		if("TRUE".equalsIgnoreCase(ret.toString())){
			Map infoMap = (Map) map.get("info");
			return infoMap.get("md5").toString();
		}
		log.info("upload error:"+((Map) map.get("error")).get("message"));
		return null;
	}
	
	/**
	 * 文件上传
	 * @param filePath 上传文件路径
	 * @return
	 */
	@Override
	public String uploadWithURL(String filePath){
		return url+"/"+upload( new File(filePath));
	}

	/**
	 * 文件上传
	 * @param filePath 上传文件路径
	 * @return
	 */
	@Override
	public String upload(String filePath){
		return upload( new File(filePath));
	}
	
	/**
	 * 文件上传
	 * @param filePath 上传文件路径
	 * @return
	 */
	@Override
	public String upload(String filePath,String conetentType){
		return upload( new File(filePath),conetentType);
	}
	
	/**
	 * 文件上传
	 * @param files 上传文件集合
	 * @return
	 */
	@Override
	public String[] uploads(List<String> files){
		if(files==null||files.size()==0){
			return null;
		}
		String[] images=new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			String upload = upload( files.get(i));
			images[i]=upload;
		}
		return images;
	}
	
	/**
	 * 删除zimg图片
	 * @param md5 图片名称
	 * @return
	 */
	@Override
	public String delete(String md5){
		if(md5==null||"".equals(md5)){
			return null;
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("md5", md5);
		params.put("t", md5);//命令类型 1 
		String string = HttpClientUtil.get(url+"/"+DELETE_ACTION,params);
		return string;
	}
	
	/**
	 * 查看图片详情
	 * @param md5 图片md5值
	 * @return
	 */
	@Override
	public ZImgRet info(String md5){
		if(md5==null||"".equals(md5)){
			return null;
		}
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("md5", md5);
		String json = HttpClientUtil.get(url+"/"+INFO_ACTION,params);
		Gson gson=new Gson();
		return gson.fromJson(json, ZImgRet.class);
	}
	
	/**
	 * 下载图片
	 * @param md5 md5 图片md5值
	 * @param param 参数值
	 * @return
	 */
	@Override
	public Reader download(String md5,ZImgInfo param){
		if(md5==null||"".equals(md5)){
			return null;
		}
		Map<String,Object> params=new HashMap<String,Object>();
		if(param!=null){
			params.put("f", param.getFormat());
			params.put("g", param.isGrey());
			params.put("h", param.getHeight());
			params.put("q", param.getQuality());
			params.put("r", param.getRotate());
			params.put("w", param.getWidth());
			params.put("x", param.getPositionX());
			params.put("y", param.getPositionY());
		}
		String json = HttpClientUtil.get(url+"/"+md5,params);
		return new StringReader(json);
	}
}
