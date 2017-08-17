package model;

public class User {
	public String name;
	public String password;
	public int role;
	
	public User(){
		
	}
	
	public User(String name,String password,int role){
		this.name=name;
		this.password=password;
		this.role=role;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public int getRole(){
		return this.role;
	}

}
