package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostId_Table_Opt {
	private Statement stm;
	public PostId_Table_Opt(Statement stm){
		this.stm=stm;
	}
	
	public boolean addPostId(String title){
		String sql="insert into postid(title) values("+"'"+title+"');";
		
		try {
			if(stm.executeUpdate(sql)>0){                                            //表示插入成功
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
	
	public int getIdfromTitle(String title){                                         //根据帖子的标题找到相应的id
		String sql="select id from postid where title="+"'"+title+"';";
		
		try {
			ResultSet res=stm.executeQuery(sql);
			
			while(res.next()){
				return res.getInt("id");                                             //返回找到的id
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;                                                                  //表示帖子已被删除
	}

}
