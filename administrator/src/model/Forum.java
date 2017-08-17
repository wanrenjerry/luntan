package model;

public class Forum {
	public String topic;
	public String date;
	public int see;
	public int top;
	public String head;
	
	public Forum(){
		
	}
	
	public Forum(String topic,String date,int see,int top,String head){
		this.topic=topic;
		this.date=date;
		this.see=see;
		this.top=top;
		this.head=head;
	}
	
	public String getTopic(){
		return this.topic;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public int getSee(){
		return this.see;
	}
	
	public int getTop(){
		return this.top;
	}
	
	public String getHead(){
		return this.head;
	}

}
