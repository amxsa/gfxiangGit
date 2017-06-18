package com.gf.ims.file.bean;

import java.util.ArrayList;
import java.util.List;

public class ResponseVo {
	// 结果
	private String result = "success";
	// 错误信息
	private String error = "";
	/**
	 * 图片的路径信息列表
	 */
	private List<ImagePathVo> images=new ArrayList<ImagePathVo>();

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<ImagePathVo> getImages() {
		return images;
	}

	public void setImages(List<ImagePathVo> images) {
		this.images = images;
	}

	
}
