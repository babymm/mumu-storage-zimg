package com.lovecws.mumu.storage.zimg.service;

import com.lovecws.mumu.storage.zimg.bean.ZImgInfo;
import com.lovecws.mumu.storage.zimg.bean.ZImgRet;

import java.io.File;
import java.io.Reader;
import java.util.List;

/**
 * 使用zimg上传文件
 * @author lovecws
 */
public interface ZImgService {

	/**
	 * 上传文件
	 * @param file File实体
	 * @return
	 */
	String upload(File file);

	/**
	 * 上传文件
	 * @param file File实体
	 * @param conetentType 图片类型 'jpeg', 'jpg', 'png', 'gif', 'webp'
	 * @return
	 */
	String upload(File file, String conetentType);
	
	/**
	 * 上传文件
	 * @param filePath 文件本地路径
	 * @return
	 */
	String upload(String filePath);

	String uploadWithURL(String filePath);

	/**
	 * 上传文件
	 * @param filePath 文件本地路径
	 * @param conetentType 图片类型 'jpeg', 'jpg', 'png', 'gif', 'webp'
	 * @return
	 */
	String upload(String filePath, String conetentType);
	
	/**
	 * 上传多个文件
	 * @param files 文件路径集合
	 * @return
	 */
	String[] uploads(List<String> files);

	/**
	 * 删除图片
	 * @param md5 图片md5值
	 * @return
	 */
	String delete(String md5);
	
	/**
	 * 获取图片详情
	 * @param md5 图片md5值
	 * @return
	 */
	ZImgRet info(String md5);

	/**
	 * 下载图片
	 * @param md5 图片md5值
	 * @param param zimg参数
	 * @return
	 */
	Reader download(String md5, ZImgInfo param);

}
