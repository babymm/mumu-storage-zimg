package com.lovecws.mumu.storage.zimg.bean;

import java.io.Serializable;

/**
 * zimg
 * @author lovecws
 */
public class ZImgRet implements Serializable{

	private static final long serialVersionUID = 6958139328905106313L;
	
	private boolean ret;
	private ZImgInfo info;

	public boolean isRet() {
		return ret;
	}

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	public ZImgInfo getInfo() {
		return info;
	}

	public void setInfo(ZImgInfo info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ZImgRet [ret=" + ret + ", info=" + info + "]";
	}
}
