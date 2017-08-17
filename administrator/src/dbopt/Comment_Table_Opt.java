package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Comment_Table_Opt {
	public Statement stm;
	public Comment_Table_Opt(Statement stm){
		this.stm=stm;
	}
	
	public boolean deleteComment(int id){                                             //删除与这个id有关的评论
		String sql="delete from comments where id="+id+";";
		
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
	
	public ArrayList<String> getComments(String title){                               //通过帖子名字来获得评论
		ArrayList<String> list=new ArrayList<String>();
		
		int id=getPostId(title);
		
		String sql="select fromuser,touser,comment from comments where id="+id+";";
		
		ResultSet res;
		try {
			res = stm.executeQuery(sql);
			while(res.next()){
				list.add(res.getString("fromuser")+" 回复  "+res.getString("touser")+":"+res.getString("comment"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int getPostId(String title){
		String sql="select id from postid where title="+"'"+title+"';";
		
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
