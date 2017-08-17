package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Forum implements java.io.Serializable{                   //�����л���ʵ����
	private static final long serialVersionUID = 1L;  
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");      //日期格式
	
	public String topic;               //论坛的主题
	public Date date;
	public String secthead;            //坛主姓名
	
	public Forum(){
		
	}
	
	public Forum(String topic,String date,String secthead){
		this.topic=topic;
		try {
            this.date = DATE_FORMAT.parse(date);
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
		this.secthead=secthead;
	}
	
	public void setTopic(String topic){
		this.topic=topic;
	}
	public String getTopic(){
		return this.topic;
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
	
	public void setSectHead(String name){
		this.secthead=name;
	}
	public String getSectHead(){
		return this.secthead;
	}

}
