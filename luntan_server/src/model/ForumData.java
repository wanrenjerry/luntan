package model;

public class ForumData {                                                         //修改forum时穿进去的参数
	public int see;
	public int top;
	public String name;
	public String topic;
	
	public ForumData(){
		
	}
	
	public ForumData(String topic,int see,int top,String name){
		this.topic=topic;
		this.see=see;
		this.top=top;
		this.name=name;
	}
	
	public int getSee(){
		return this.see;
	}
	public void setSee(int see){
		this.see=see;
	}
	
	public int Gettop(){
		return this.top;
	}
	public void setTop(int top){
		this.top=top;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}

}
