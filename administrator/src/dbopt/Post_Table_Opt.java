package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Post;

public class Post_Table_Opt {
	public Statement stm;
	public PostId_Table_Opt postidto;
	
	public Post_Table_Opt(Statement stm){
		this.stm=stm;
		postidto=new PostId_Table_Opt(stm);
	}
	
	public boolean deletePost(String author){                                    //得先删除与这个帖子有关的评论
		String sql="select title from posts where anthor="+"'"+author+"';";
		ArrayList<String> titles=new ArrayList<String>();
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				titles.add(res.getString("title"));
			}
			
			sql="delete from posts where anthor="+"'"+author+"';";
			
			if(stm.executeUpdate(sql)>0){
				for(int i=0;i<titles.size();i++){                                //一个一个删除
					postidto.deletePostId(titles.get(i));
				}
				return true;
			}
			else{
				return false;
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePostTitle(String title){                                 //根据标题删除帖子
		postidto.deletePostId(title);                                             //删除相关评论
		
		String sql="delete from posts where title="+"'"+title+"';";
		try {
			if(stm.executeUpdate(sql)>0){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePost(int forumid){                                       //根据论坛号删除
		String sql="select title from posts where forumid="+forumid+";";
		ArrayList<String> titles=new ArrayList<String>();
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				titles.add(res.getString("title"));
			}
			
			sql="delete from posts where forumid="+forumid+";";
			
			if(stm.executeUpdate(sql)>0){                                         //相关号也得删除
				for(int i=0;i<titles.size();i++){
					postidto.deletePostId(titles.get(i));
				}
				return true;
			}
			else{
				return false;
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean setTop(String title,int top){                                           //设置置顶
		String sql="update posts set isTop="+top+" where title="+"'"+title+"';";
		try {
			if(stm.executeUpdate(sql)>0){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setPostMsg(String title,int see,int top){                                            //设置是否可见
		String sql="update posts set issee="+see+",isTop="+top+" where title="+"'"+title+"';";
		try {
			if(stm.executeUpdate(sql)>0){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public ArrayList<String> getTitle(){                                           //获得帖子标题
		ArrayList<String> list=new ArrayList<String>();
		
		String sql="select title from posts;";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			
			while(res.next()){
				list.add(res.getString("title"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	public Post getPost(String title){                                            //返回帖子信息
		String sql="select anthor,postdate,isTop,issee,forumid,content from posts where title="+"'"+title+"';";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			
			while(res.next()){
				return new Post(title,res.getString("anthor"),res.getString("postdate"),res.getInt("istop"),res.getInt("issee"),res.getInt("forumid"),res.getString("content"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
