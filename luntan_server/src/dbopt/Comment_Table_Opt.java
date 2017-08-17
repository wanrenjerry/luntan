package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Comment;

public class Comment_Table_Opt {
	public Statement stm;
	
	public Comment_Table_Opt(Statement stm){
		this.stm=stm;
	}
	
	public boolean addComment(Comment com){
		String sql="insert into comments(id,fromuser,touser,comment) values("+com.getPostId()+","
				                                                         +"'"+com.getFromUser()+"',"
				                                                         +"'"+com.getToUser()+"',"
				                                                         +"'"+com.getComment()+"');";
		System.out.println(sql);
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
	
	public ArrayList<Comment> getComments(int postid){                                                       //将找到的评论返回
		ArrayList<Comment> list=new ArrayList<Comment>();
		String sql="select fromuser,touser,comment from comments where id="+postid+";";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				list.add(new Comment(postid,res.getString("fromuser"),res.getString("touser"),res.getString("comment")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
