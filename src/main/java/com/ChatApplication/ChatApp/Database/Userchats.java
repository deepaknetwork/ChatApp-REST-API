package com.ChatApplication.ChatApp.Database;

import java.util.Objects;

public class Userchats{
	public long phone;
	public String name;
	public Boolean read;
	public Userchats(long phone, String name, Boolean read) {
		super();
		this.phone = phone;
		this.name = name;
		this.read = read;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	@Override
	public String toString() {
		return "Userchats [phone=" + phone + ", name=" + name + ", read=" + read + "]";
	}

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Userchats userchats = (Userchats) o;
	        return phone == userchats.phone &&
	                Objects.equals(name, userchats.name) &&
	                Objects.equals(read, userchats.read);
	    }

	    // Override hashCode method (generated using Objects.hash) to maintain consistency
	    @Override
	    public int hashCode() {
	        return Objects.hash(phone, name, read);
	    }
	
}
