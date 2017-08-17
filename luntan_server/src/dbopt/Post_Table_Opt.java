package dbopt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Post;
import model.PostData;
import model.PostItem;

public class Post_Table_Opt {
	public Statement stm;
	public PostId_Table_Opt postidto;
	public Post_Table_Opt(Statement stm){
		this.stm=stm;
		postidto=new PostId_Table_Opt(stm);
	}
	
	public ArrayList<PostItem> getPostsTitle(int id){                                 //根据论坛ID获得论坛中的帖子
		ArrayList<PostItem> list=new ArrayList<PostItem>();
		ResultSet res;
		//先找到置顶的可见的属于该论坛的帖子
		String sql="select title,postdate,anthor from posts where isTop=0 and issee=0 and forumid="+id+";";
		try {
			res=stm.executeQuery(sql);
			while(res.next()){
				list.add(new PostItem(res.getString("title"),res.getString("postdate"),res.getString("anthor")));
			}
			
			sql="select title,postdate,anthor from posts where isTop=1 and issee=0 and forumid="+id+";";        //然后是没置顶的
			res=stm.executeQuery(sql);
			while(res.next()){
				list.add(new PostItem(res.getString("title"),res.getString("postdate"),res.getString("anthor")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public boolean addPost(Post post){                                                                          //插入帖子
		int istop=0;
		int issee=0;
		String sql="insert into posts values("+"'"+post.getTitle()+"',"
				                              +"'"+post.getAuthor()+"',"
				                              +"'"+post.getDate()+"',"
				                              +istop+","
				                              +issee+","
				                              +post.getForumid()+","
				                              +"'"+post.getContent()+"');";
		System.out.println(sql);
		try {
			if(stm.executeUpdate(sql)>0){                                                                       //如果帖子能加进去，那么这个也一定能
				postidto.addPostId(post.getTitle());
				return true;
			}
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean updatePost(PostData pd){                                                                  //管理员修改帖子的信息
		String sql="update posts set isTop="+pd.getTop()+","
				                   +"issee="+pd.getSee()
				                   +" where title="+"'"+pd.getTitle()+",;";
		
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
	
	
	public Post getPost(String title){
		String sql="select title,anthor,postdate,forumid,content from posts where title="
				                                              +"'"+title+"';";
		
		System.out.println(sql);
		Post post=new Post();
		try {
			ResultSet res=stm.executeQuery(sql);
			
			while(res.next()){
				post.setTitle(res.getString("title"));
				post.setAuthor(res.getString("anthor"));
				post.setDate(res.getString("postdate"));
				post.setForumid(res.getInt("forumid"));
				post.setContent(res.getString("content"));
			}
			return post;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<PostItem> getPostsTitle(String name){
		ArrayList<PostItem> list=new ArrayList<PostItem>();
		ResultSet res;
		String sql="select title,postdate,anthor from posts where isTop=0 and issee=0 and anthor="+"'"+name+"';";
		try {
			res=stm.executeQuery(sql);
			
			while(res.next()){
				list.add(new PostItem(res.getString("title"),res.getString("postdate"),res.getString("anthor")));     //先添加没有置顶的，可见的
			}
			
			sql="select title,postdate,anthor from posts where isTop=1 and issee=0 and anthor="+"'"+name+"';";
			
			res=stm.executeQuery(sql);
			while(res.next()){
				list.add(new PostItem(res.getString("title"),res.getString("postdate"),res.getString("anthor")));     //再添加置顶的，可见的
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
