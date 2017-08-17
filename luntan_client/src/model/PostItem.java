package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostItem implements java.io.Serializable{                   //�����л���ʵ����
	private static final long serialVersionUID = 1L;  
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");      //日期格式
	
	public String title;               //论坛的主题
	public Date date;
	public String author;            //坛主姓名
	
	public PostItem(){
		
	}
	
	public PostItem(String title,String date,String author){
		this.title=title;
		try {
            this.date = DATE_FORMAT.parse(date);
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
		this.author=author;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return this.title;
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
}
