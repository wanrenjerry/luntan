package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Person;

public class Person_Table_Opt {
	public Statement stm;
	Person_Table_Opt(Statement stm){                 //用来执行MySQL语句的
		this.stm=stm;
	}
	
	public boolean addUser(Person pp){               //添加用户
		int role=1;                                  //默认情况下用户是1级，也就是普通会员
		int nums=0;                                  //用户的发的帖子数
		
		String sql="insert into users values("+"'"+pp.getName()+"'"+","
		                                      + "'"+pp.getPassword()+"'"+","
		                                      +"'"+pp.getSchool()+"'"+","
		                                      +"'"+pp.getMail()+"'"+","
		                                      +"'"+pp.getSex()+"'"+","
		                                      +"'"+role+"'"+","
		                                      +"'"+nums+"'"
		                                      +");";
		
		try {
			if(stm.executeUpdate(sql)>0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;		                                                                                
	}
	
	public boolean findUser(String name,String password){                     //查找用户是否存在
		String sql="select name from users where name="+"'"+name+"'"+
				                               " and password="+"'"+password+"'"+
	                                              ";";
		try {
			System.out.println(sql);
			ResultSet rs = stm.executeQuery(sql);
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
