package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post implements java.io.Serializable{                                        //序列化，方便传输信息
	private static final long serialVersionUID = 1L;  
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");      //日期格式
	
	public String title;                                                                  //论坛的主题
	public Date date;
	public String author;                                                                 //坛主姓名
	public String content;
	public int forumid;                                                                   //这个帖子属于哪个论坛
	
	
	public Post(){
		
	}
	
	public Post(String title,String content,String date,String author,int forumid){
		this.title=title;
		this.content=content;
		try {
            this.date = DATE_FORMAT.parse(date);
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
		this.author=author;
		this.forumid=forumid;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return this.title;
	}
	
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return this.content;
	}
	
	public void setDate(String date){
		try {
			this.date=DATE_FORMAT.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getDate(){
		return DATE_FORMAT.format(date);
	}
	
	public void setAuthor(String author){
		this.author=author;
	}
	public String getAuthor(){
		return this.author;
	}
	
	public void setForumid(int id){
		this.forumid=id;
	}
	public int getForumid(){
		return this.forumid;
	}
}

