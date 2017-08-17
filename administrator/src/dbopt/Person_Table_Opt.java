package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.User;

public class Person_Table_Opt {
	public Statement stm;
	public Post_Table_Opt pto;
	
	public Person_Table_Opt(Statement stm){
		this.stm=stm;
		pto=new Post_Table_Opt(stm);
	}
	
	public boolean setJuese(String name,int juese){
		String sql="update users set role="+juese+" where name="+"'"+name+"';";
		
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
	
	public boolean deleteUser(String name){                                          //删除会员还得删除与他相连的帖子
		
		String sql="delete from users where name="+"'"+name+"';";
		
		try {
			pto.deletePost(name);                                                    //先删除与这个人有关的帖子
			
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
	
	public ArrayList<String> getUser(){                                                 //获得用户列表
		ArrayList<String> list=new ArrayList<String>();
		String sql="select name from users;";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			
			while(res.next()){
				list.add(res.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public User getUserMsg(String name){
		String sql="select password,role from users where name="+"'"+name+"';";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			
			while(res.next()){
				return new User(name,res.getString("password"),res.getInt("role"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
  
}
