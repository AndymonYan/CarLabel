package Dao;



import java.io.Serializable;
import java.util.Date;

//package com.demo.SpringJdbc.entity;  

public class User implements Serializable{
	
	private int Id;

	private String name;

	private String password;

	//private int EmailUrl;

	private String Company;

	private String EmailUrl;

	public int getUserId() {
		return Id;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getEmailUrl() {
		return EmailUrl;
	}

	public void setEmailUrl(String emailUrl) {
		EmailUrl = emailUrl;
	}

	public void setUserId(int userId) {
		this.Id = userId;
	}

	public String getUserName() {
		return name;
	}

	public void setUserName(String userName) {
		this.name = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}