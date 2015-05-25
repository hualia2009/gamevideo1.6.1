package com.leslie.gamevideo.entity;

public class VideoPath {
	
	private int order;
	private int seconds;
	private String youUrl;
	private String size;
	private String key;
	private String title;
	
	public VideoPath(){
		
	}
	
	public VideoPath(int order, int seconds, String youUrl, String size,
			String key, String title) {
		super();
		this.order = order;
		this.seconds = seconds;
		this.youUrl = youUrl;
		this.size = size;
		this.key = key;
		this.title = title;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public String getYouUrl() {
		return youUrl;
	}
	public void setYouUrl(String youUrl) {
		this.youUrl = youUrl;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
