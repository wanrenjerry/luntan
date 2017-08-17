package model;

public class Person  implements java.io.Serializable{                   //可序列化的实体类
	private static final long serialVersionUID = 1L;  
	
	public String name;
	public String password;
	public String school;
	public String mail;
	public String sex;
	
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return this.password;
	}
	
	public void setSchool(String school){
		this.school=school;
	}
	public String getSchool(){
		return this.school;
	}
	
	public void setMail(String mail){
		this.mail=mail;
	}
	public String getMail(){
		return this.mail;
	}
	
	public void setSex(String sex){
		this.sex=sex;
		
	}
	public String getSex(){
		return this.sex;
	}

}
