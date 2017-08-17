package dbopt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Forum;
import model.Post;
import model.User;

public class DBService {
	public Connection con;
	public Statement stm;
	public static Person_Table_Opt pto;
	public static Forum_Table_Opt fto;
	public static Post_Table_Opt postto;
	public static PostId_Table_Opt postidto;
	public static Comment_Table_Opt cto;
	
	public DBService(){
        String  url="jdbc:mysql://localhost:3306/luntan?&characterEncoding=gbk";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();       //加载mysql驱动
			System.out.println("成功加载驱动!");
			
			con=DriverManager.getConnection(url, "root", "201392260");
			stm=con.createStatement();
			
			pto=new Person_Table_Opt(stm);
			fto=new Forum_Table_Opt(stm);
			postto=new Post_Table_Opt(stm);
			postidto=new PostId_Table_Opt(stm);
			cto=new Comment_Table_Opt(stm);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean deleteForum(String topic){                              //删除论坛
		return fto.deleteForum(topic);
	}
	public boolean setForumTop(String topic,int top){                      //将论坛置顶
		return fto.setTop(topic, top);
	}
	public boolean setForumMsg(String topic,int see,int top,String head){                      //设置论坛的可见性
		return fto.setMsg(topic, see, top, head);
	}
	public boolean setSectHead(String topic,String name){                  //为论坛设置坛主
		return fto.setSectHead(topic, name);
	}
	public ArrayList<String> getTopic(){
		return fto.getTopic();
	}
	public boolean addForum(String topic){                                 //插入论坛
		return fto.addForum(topic);
	}
	public Forum getForum(String topic){
		return fto.getForum(topic);
	}
	public String getTopic(int id){
		return fto.getTopic(id);
	}
	
	
	
	public boolean deleteUser(String name){                                //删除用户
		return pto.deleteUser(name);
	}
	public boolean setJuese(String name,int juese){                        //设置用户角色
		return pto.setJuese(name, juese);
	}
    public ArrayList<String> getUser(){
		return pto.getUser();
	}
    public User getUserMsg(String name){
    	return pto.getUserMsg(name);
    }
	
	
	public boolean deletePost(String title){                               //删除帖子
		return postto.deletePostTitle(title);
	}
	public boolean setPostTop(String title,int top){                       //将帖子置顶
		return postto.setTop(title, top);
	}
	public boolean setPostMsg(String title,int see,int top){                       //设置帖子可见性
		return postto.setPostMsg(title, see, top);
	}
	public ArrayList<String> getTitles(){                                  //获得帖子标题
		return postto.getTitle();
	}
	public Post getPost(String title){
		return postto.getPost(title);
	}
	
	
	public ArrayList<String> getComments(String title){
		return cto.getComments(title);
	}

}
