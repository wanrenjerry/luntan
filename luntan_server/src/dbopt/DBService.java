package dbopt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Comment;
import model.Forum;
import model.ForumData;
import model.Person;
import model.Post;
import model.PostData;
import model.PostItem;

public class DBService {
	public Connection con;
	public Statement stm;
	public static Person_Table_Opt pto;
	public static Forum_Table_Opt fto;
	public static Post_Table_Opt postto;
	public static PostId_Table_Opt postidto;
	public static Comment_Table_Opt comto;
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
			comto=new Comment_Table_Opt(stm);
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
	
	
	public boolean addUser(Person pp){                       //添加用户
		if(pto.addUser(pp)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean findUser(String name,String password){    //查找用户
		if(pto.findUser(name, password)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public ArrayList<Forum> findForum(){                     //查找论坛
		return fto.findForum();
	}
	
	public boolean addForum(Forum fm){                       //添加论坛
		return fto.addForum(fm);
	}
	
	public boolean updateForum(ForumData fd){                //修改论坛属性
		return fto.updateForum(fd);
	}
	public int getForumid(String topic){                     //通过论坛主题获取论坛id
		
		return fto.findId(topic);
	}
	
	
	public ArrayList<PostItem> getPostsTitle(int id){         //获得帖子列表
		return postto.getPostsTitle(id);
	}
	public ArrayList<PostItem> getPostTitle(String name){     //通过作者获得帖子列表
		return postto.getPostsTitle(name);
	}
	public boolean addPost(Post post){                        //添加帖子
		return postto.addPost(post);
	}
	public boolean updatePost(PostData pd){                   //更新帖子，对帖子进行一些设置
		return postto.updatePost(pd);
	}
	public Post getPost(String title){                        //通过帖子的标题来得到帖子
		return postto.getPost(title);
	}
	
	
	public int getPostId(String title){                       //根据标题获得id
		return postidto.getIdfromTitle(title);
	}
	
	
	public ArrayList<Comment> getComments(int postid){        //根据id号来查找评论
		return comto.getComments(postid);
	}
	public boolean addComment(Comment com){                   //添加评论
		return comto.addComment(com);
	}

}
