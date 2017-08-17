package model;

public class PostData {                                              //管理员用来管理帖子的参数
	public String title;                                             //帖子的标题用来表示帖子
	public int top;                                                  //帖子是否置顶
	public int see;                                                  //帖子是否可见
	public PostData(){
		
	}
	public PostData(String title,int top,int see){
		this.title=title;
		this.top=top;
		this.see=see;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return this.title;
	}
	
	public void setTop(int top){
		this.top=top;
	}
	public int getTop(){
		return this.top;
	}
	
	public void setSee(int see){
		this.see=see;
	}
	public int getSee(){
		return this.see;
	}

}
