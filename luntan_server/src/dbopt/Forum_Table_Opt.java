package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Forum;
import model.ForumData;

public class Forum_Table_Opt {
	public Statement stm;                                   //用来操作数据库的
	
	Forum_Table_Opt(Statement stm){
		this.stm=stm;
	}
	
	public ArrayList<Forum> findForum(){                    //找到需要显示的论坛
		ArrayList<Forum> list=new ArrayList<Forum>();
	    
		String sql="Select topic,builddate,secthead from forums where issee=0 and isTop=0";            //先查看是否有置顶的，优先放前面
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				list.add(new Forum(res.getString("topic"),res.getString("builddate"),res.getString("secthead")));
			}
			
			sql="Select topic,builddate,secthead from forums where issee=0 and isTop=1";             //0代表可见，没有隐藏   
			res=stm.executeQuery(sql);
			while(res.next()){
				list.add(new Forum(res.getString("topic"),res.getString("builddate"),res.getString("secthead")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return list;
	}
	
	public boolean addForum(Forum fum){
		int top=0;                                                                                     //默认没有置顶
		int see=0;                                                                                     //默认可见
		//String name=null;                                                                              //版主默认为空
		
		String sql="insert into forums(topic,builddate,issee,istop,secthead) values("
				                   +"'"+fum.getTopic()+"',"
				                   +"'"+fum.getDate()+"',"
				                   +"'"+see+"',"
				                   +"'"+top+"',"
				                   +"'"+fum.getSectHead()+"',);";
		
		try {
			if(stm.executeUpdate(sql)>0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				                   
		return false;
	}
	
	
	public boolean updateForum(ForumData fd){                                        //管理员修改论坛参数
		String sql="Update forums set issee="+fd.see+
				                      ",isTop="+fd.top+
				                      ",secthead="+"'"+fd.getName()+"'"+
				                      " where topic="+"'"+fd.topic+"';";
		
		try {
			if(stm.executeUpdate(sql)>0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int findId(String topic){
		String sql="select id from forums where topic="+"'"+topic+"';";
		System.out.println(sql);
		
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				return res.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}

}
