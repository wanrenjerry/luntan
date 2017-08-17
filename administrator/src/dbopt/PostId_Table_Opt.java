package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PostId_Table_Opt {
	public Statement stm;
	public Comment_Table_Opt cto;
	
	public PostId_Table_Opt(Statement stm){
		this.stm=stm;
		cto=new Comment_Table_Opt(stm);
	}
	
	public boolean deletePostId(String title){                                           //必须得先删除与这个帖子id有关的评论
		String sql="select id from postid where title="+"'"+title+"';";
		ArrayList<Integer> ids=new ArrayList<Integer>();
		try {
			ResultSet res=stm.executeQuery(sql);
			while(res.next()){
				ids.add(res.getInt("id"));
			}
			
			sql="delete from postid where title="+"'"+title+"';";
			if(stm.executeUpdate(sql)>0){
				for(int i=0;i<ids.size();i++){
					cto.deleteComment(ids.get(i));
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
	
	

}
