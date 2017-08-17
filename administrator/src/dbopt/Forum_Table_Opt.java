package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Forum;
import model.GetDate;

public class Forum_Table_Opt {
	public Statement stm;
	public Post_Table_Opt pto;
	
	public Forum_Table_Opt(Statement stm){
		this.stm=stm;
		pto=new Post_Table_Opt(stm);
	}
	
	public boolean deleteForum(String topic){                                     //删除论坛
		String sql="select id from forums where topic="+"'"+topic+"';";
		ArrayList<Integer> ids=new ArrayList<Integer>();
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				ids.add(res.getInt("id"));
			}
			
			sql="delete from forums where topic="+"'"+topic+"';";
			if(stm.executeUpdate(sql)>0){
				for(int i=0;i<ids.size();i++){
					pto.deletePost(ids.get(i));
				}
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
	
	public boolean setTop(String topic,int top){                                                  //设置置顶
		String sql="update forums set isTop="+top+" where topic="+"'"+topic+"';";
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
	
	public boolean setMsg(String topic,int see,int top,String head){                                                   //设置是否隐藏
		String sql="update forums set issee="+see+
				",isTop="+top+",secthead="+"'"+head+"'"
				+" where topic="+"'"+topic+"';";
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
	
	public boolean setSectHead(String topic,String name){                                           //设置版主名字
		String sql="update forums set secthead="+"'"+name+"'"+" where topic="+"'"+topic+"';";
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
	
	public ArrayList<String> getTopic(){                                //获得论坛主题
		ArrayList<String> list=new ArrayList<String>();
		
		String sql="select topic from forums;";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			
			while(res.next()){
				list.add(res.getString("topic"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean addForum(String topic){
		int top=0;                                                      //默认可见，不置顶
		int see=0;
		String date=GetDate.getDate();
		
		String sql="insert into forums(topic,builddate,issee,istop) values(+"+"'"+topic+"',"
				                                                             +"'"+date+"',"
				                                                             +see+","
				                                                             +top+");";
		try {
			if(stm.executeUpdate(sql)>0){                               //插入
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
	
	
	public Forum getForum(String topic){                                        //获得论坛信息
		String sql="select topic,builddate,issee,isTop,secthead from forums where topic="+"'"+topic+"';";
		
		ResultSet res;
		try {
			res = stm.executeQuery(sql);
			
			while(res.next()){
				return new Forum(topic,res.getString("builddate"),res.getInt("issee"),res.getInt("isTop"),res.getString("secthead"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String getTopic(int id){                                           //通过论坛的id号查找论坛
		String sql="select topic from forums where id="+id+";";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				return res.getString("topic");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
