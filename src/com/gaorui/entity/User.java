package com.gaorui.entity;

public class User {
	private Long user_id;
	private Long user_tel;
	private String user_password;
	private double user_longitude;
	private double user_latitude;
	private String user_name;
	private String user_content;
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(Long user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public double getUser_longitude() {
		return user_longitude;
	}
	public void setUser_longitude(double user_longitude) {
		this.user_longitude = user_longitude;
	}
	public double getUser_latitude() {
		return user_latitude;
	}
	public void setUser_latitude(double user_latitude) {
		this.user_latitude = user_latitude;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_content() {
		return user_content;
	}
	public void setUser_content(String user_content) {
		this.user_content = user_content;
	}
}
