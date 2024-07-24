package com.ChatApplication.ChatApp.Database;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserDetails {

@Id
public long phone;
public String name;
public String password;
public LocalDate dob;
public String gender;
public String bio;
public ArrayList<Userchats> chat=new ArrayList<>();
public UserDetails(int id, String name, long phone ,String password) {
	super();
	this.name = name;
	this.phone = phone;
	this.password = password;
}
public UserDetails() {
	
}
	
}
