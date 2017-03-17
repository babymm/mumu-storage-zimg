package com.lovecws.mumu.storage.zimg.bean;

import java.io.Serializable;

/**
 * zimg info
 * 
 * @author lovecws
 */
public class ZImgInfo implements Serializable {

	private static final long serialVersionUID = 5887741972760354399L;

	private int width;// 宽度

	private int height;// 高度

	private int size;// 大小

	private int positionX;// position x

	private int positionY;// position y

	private int rotate;// rotate旋转角度

	private int quality;// 图片质量

	private String format;// format格式 jpeg, png, gif, webp

	private boolean grey;// 去除颜色 1 ;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getRotate() {
		return rotate;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isGrey() {
		return grey;
	}

	public void setGrey(boolean grey) {
		this.grey = grey;
	}

	@Override
	public String toString() {
		return "ZImgInfo [width=" + width + ", height=" + height + ", size=" + size + ", positionX=" + positionX
				+ ", positionY=" + positionY + ", rotate=" + rotate + ", quality=" + quality + ", format=" + format
				+ ", grey=" + grey + "]";
	}
}
