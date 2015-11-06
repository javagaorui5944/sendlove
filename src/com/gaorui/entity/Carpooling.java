package com.gaorui.entity;

import java.util.Date;

public class Carpooling {
	private Long Carpooling_id;
	private Long user_id;
	private String Carpooling_origin;
	private String Carpooling_destination;
	private Date Carpooling_Date;
	private int Carpooling_distance;
	private String Carpooling_way;
	public Long getCarpooling_id() {
		return Carpooling_id;
	}
	public void setCarpooling_id(Long carpooling_id) {
		Carpooling_id = carpooling_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getCarpooling_origin() {
		return Carpooling_origin;
	}
	public void setCarpooling_origin(String carpooling_origin) {
		Carpooling_origin = carpooling_origin;
	}
	public String getCarpooling_destination() {
		return Carpooling_destination;
	}
	public void setCarpooling_destination(String carpooling_destination) {
		Carpooling_destination = carpooling_destination;
	}
	public Date getCarpooling_Date() {
		return Carpooling_Date;
	}
	public void setCarpooling_Date(Date carpooling_Date) {
		Carpooling_Date = carpooling_Date;
	}
	public int getCarpooling_distance() {
		return Carpooling_distance;
	}
	public void setCarpooling_distance(int carpooling_distance) {
		Carpooling_distance = carpooling_distance;
	}
	public String getCarpooling_way() {
		return Carpooling_way;
	}
	public void setCarpooling_way(String carpooling_way) {
		Carpooling_way = carpooling_way;
	}
	public int getCarpooling_count() {
		return Carpooling_count;
	}
	public void setCarpooling_count(int carpooling_count) {
		Carpooling_count = carpooling_count;
	}
	public Long getCarpooling_nearme() {
		return Carpooling_nearme;
	}
	public void setCarpooling_nearme(Long carpooling_nearme) {
		Carpooling_nearme = carpooling_nearme;
	}
	public String getCarpooling_content() {
		return Carpooling_content;
	}
	public void setCarpooling_content(String carpooling_content) {
		Carpooling_content = carpooling_content;
	}
	public int getCarpooling_status() {
		return Carpooling_status;
	}
	public void setCarpooling_status(int carpooling_status) {
		Carpooling_status = carpooling_status;
	}
	public double getCarpooling_longitude() {
		return Carpooling_longitude;
	}
	public void setCarpooling_longitude(double carpooling_longitude) {
		Carpooling_longitude = carpooling_longitude;
	}
	public double getCarpooling_latitude() {
		return Carpooling_latitude;
	}
	public void setCarpooling_latitude(double carpooling_latitude) {
		Carpooling_latitude = carpooling_latitude;
	}
	private int Carpooling_count;
	private Long Carpooling_nearme;
	private String Carpooling_content;
	private int Carpooling_status;
	private double Carpooling_longitude;
	private double Carpooling_latitude;
}
