package model;

public class Comment implements java.io.Serializable{                   //序列化的评论类
	private static final long serialVersionUID = 1L;
	
	public int postid;                                                  //属于哪个帖子
	public String fromUSer;                                             //给出评论的人
	public String toUser;                                              //接受评论的人
	public String mycomment;                                            //评论
	
	public Comment(){
		
	}
	
	public Comment(int postid,String fromUser,String toUser,String mycomment){
		this.postid=postid;
		this.fromUSer=fromUser;
		this.toUser=toUser;
		this.mycomment=mycomment;
	}
	
	public void setPostId(int id){
		this.postid=id;
	}
	public int getPostId(){
		return this.postid;
	}
	
	public void setFromUser(String fromUser){
		this.fromUSer=fromUser;
	}
	public String getFromUser(){
		return this.fromUSer;
	}
	
	public void setToUser(String toUser){
		this.toUser=toUser;
	}
	public String getToUser(){
		return this.toUser;
	}
	
	public void setComment(String mycomment){
		this.mycomment=mycomment;
	}
	public String getComment(){
		return this.mycomment;
	}
	
}
