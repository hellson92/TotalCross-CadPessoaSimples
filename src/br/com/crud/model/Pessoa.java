package br.com.crud.model;

import totalcross.util.Date;

public class Pessoa {
	private int	id;
	private String name;
	private Date born;
	private String number;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBorn() {
		return born;
	}
	public void setBorn(Date date) {
		this.born = date;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
