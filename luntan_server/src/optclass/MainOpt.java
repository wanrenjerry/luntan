package optclass;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import dbopt.DBService;
import model.Comment;
import model.Forum;
import model.Person;
import model.Post;
import model.PostItem;

public class MainOpt {
	public Socket ss;
	public static DBService db;
	
	public MainOpt(Socket ss){
		this.ss=ss;
		db=new DBService();
	}
	
	public void service(){                                                           //主服务处理函数
		try {
			//写入
			DataInputStream in=new DataInputStream(ss.getInputStream());
			//读出
			DataOutputStream out=new DataOutputStream(ss.getOutputStream());
			
			//循环查询，看是否有请求
			while(true){
			    String recv=in.readUTF();
			    if(recv.equals("zhuce")){
				    ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(ss.getInputStream()));
				    Object obj=ois.readObject();
				
				    Person pp=(Person)obj;
				    System.out.println(pp.getName());
				    if(db.addUser(pp)){                                            //如果添加用户成功
					    out.write(1);
					    out.flush();
				    }
				    else{                                                          //如果失败
					    out.write(2);
					    out.flush();
				    }
			    }
			    else if(recv.equals("denglu")){
				    String msg=in.readUTF();                                       //读出信息
				
				    String[] items=msg.split("\\+");
				
				    String name=items[0];
				    String password=items[1];
				
				    if(db.findUser(name, password)){
					    out.write(1);                                              //登陆成功，用户存在
					    out.flush();
					
					    while(true){                                               //登陆成功之后，不停监视用户请求
						    recv=in.readUTF();
						    mainService(recv);
					    }
					
				    }
				    else{
				  	    out.write(2);                                              //登陆失败，密码错误或用户不存在
					    out.flush();
				    }
			    }
			}	
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mainService(String server_type){
		try {
			//写入
			DataInputStream in=new DataInputStream(ss.getInputStream());
			//读出
			DataOutputStream out=new DataOutputStream(ss.getOutputStream());
			
			if(server_type.equals("forum_list")){                                 //获取论坛主题列表
				ArrayList<Forum> getF=db.findForum();
				
				int size=getF.size();
				
				out.writeInt(size);                                               //将条目的数量写过去
				out.flush();
				
				ObjectOutputStream oos=new ObjectOutputStream(ss.getOutputStream());               //将论坛按对象写过去
				
				for(int i=size-1;i>=0;i--){
					oos.writeObject(getF.get(i));
					oos.flush();
				}
			}
			else if(server_type.equals("ishasforum")){
				String topic=in.readUTF();
				
				int flag=db.getForumid(topic);
				if(flag==-1){
					out.writeInt(2);
					out.flush();
				}
				else{
					out.writeInt(1);
					out.flush();
				}
			}
			else if(server_type.equals("get_forumid")){                                          //通过论坛主题获得论坛的id
				String topic=in.readUTF();
				
				int flag=db.getForumid(topic);
				
				out.writeInt(flag);
				out.flush();
			}
			else if(server_type.equals("forum_content")){                                        //获取论坛中的帖子
				int forumid=in.readInt();                                                        //获取论坛的id
				
				ArrayList<PostItem> list=db.getPostsTitle(forumid);
				
				int size=list.size();
				
				out.writeInt(size);                                                              //把帖子数量写过去
				out.flush();
				
				ObjectOutputStream oos=new ObjectOutputStream(ss.getOutputStream());               //将帖子标题等信息写过去
				for(int i=size-1;i>=0;i--){
					oos.writeObject(list.get(i));
					oos.flush();
				}
				
			}
			else if(server_type.equals("ishaspost")){
				String title=in.readUTF();
				
				int flag=db.getPostId(title);
				if(flag==-1){                                                                     //帖子已不存在
					out.writeInt(2);
					out.flush();
				}
				else{
					out.writeInt(1);
					out.flush();
				}
			}
			else if(server_type.equals("get_postid")){
				String title=in.readUTF();
				
				int postid=db.getPostId(title);                                                  //获得post的id
				
				out.writeInt(postid);
				out.flush();                                                                     //将postid发送过去
			}
			else if(server_type.equals("post_comments")){                                        //请求帖子的评论
				int postid=in.readInt();
				
				ArrayList<Comment> list=db.getComments(postid);
				int size=list.size();
				
				out.writeInt(size);                                                              //先把评论的数量写过去
				out.flush();
				
				ObjectOutputStream oos=new ObjectOutputStream(ss.getOutputStream());
				for(int i=0;i<size;i++){
					oos.writeObject(list.get(i));                                                //将评论一个一个发过去
				}
			}
			else if(server_type.equals("post_content")){                                         //获取帖子主要内容
				String title=in.readUTF();                                                       //读出帖子的标题
			    
				Post post=db.getPost(title);
				
				ObjectOutputStream oos=new ObjectOutputStream(ss.getOutputStream());
				oos.writeObject(post);                                                           //将帖子发过去
				oos.flush();
			}
			else if(server_type.equals("write_post")){                                           //写帖子
				ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(ss.getInputStream()));
				try {
					Post post=(Post)ois.readObject();                                            //读出帖子，并添加到数据库
					
					if(db.addPost(post)){
						out.writeInt(1);
						out.flush();
					}
					else{
						out.writeInt(2);
						out.flush();
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(server_type.equals("write_comment")){                                        //写评论
				ObjectInputStream  ois=new ObjectInputStream(ss.getInputStream());               //读到客户写的评论
				try {
					Comment com=(Comment)ois.readObject();
					
					db.addComment(com);                                                          //添加评论
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			else if(server_type.equals("my_posts")){                                             //查看我发过的帖子
				String name=in.readUTF();                                                        //作者的名字
				
				ArrayList<PostItem> list=db.getPostTitle(name);
				
				int size=list.size();                                                            //先告诉大小
				out.writeInt(size);
				
				ObjectOutputStream oos=new ObjectOutputStream(ss.getOutputStream());
				for(int i=size-1;i>=0;i--){                                                         //一个一个的写过去
					oos.writeObject(list.get(i));
					oos.flush();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
