package model;

public class Post {
	public String title;
	public String author;
	public String date;
	public int top;
	public int see;
	public int forumid;
	public String content;
	
	public Post(){
		
	}
	
	public Post(String title,String author,String date,int top,int see,int forumid,String content){
		this.title=title;
		this.author=author;
		this.date=date;
		this.top=top;
		this.see=see;
		this.forumid=forumid;
		this.content=content;
	}
	
	public String getTitle(){
		return this.title;
	}
	public String getAuthor(){
		return this.author;
	}
	public String getDate(){
		return this.date;
	}
	public int getTop(){
		return this.top;
	}
	public int getSee(){
		return this.see;
	}
	public int getForumId(){
		return this.forumid;
	}
	public String getContent(){
		return this.content;
	}

}
