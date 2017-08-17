package controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Comment;
import model.Post;
import model.PostItem;

public class PostControl {
	public static String select_title;
	public static int postid;
	public boolean isFocus=false;
	
	public String author;
	public String toUser;
	@FXML
	private AnchorPane fx_post_pane;
	
	@FXML
	private TextField fx_post_date_text;
	
	@FXML
	private TextField fx_post_author_text;
	
	@FXML
	private TextField fx_post_title_text;
	
	@FXML
	private TextArea fx_post_content_text;
	
	@FXML
	private ListView<Comment> fx_post_comments_list;
	
	@FXML
	private TextArea fx_post_mycomment_text;
	
	@FXML
	private Button fx_post_publish_btn;
	
	@FXML
	private void initialize(){                                                    //初始化函数
		initText();
		getPost();
		getPostId();
		initListView();
		fx_post_mycomment_text.setText("回复 "+author+":");
	}
	
	@FXML
	private void onBtnPublish(){                                                  //发表评论的按钮
		String mycomment=fx_post_mycomment_text.getText();                        //先得到写的评论
		Comment com=new Comment(postid,LoginControl.userName,toUser,mycomment);                  //将这条评论的信息打包发过去
		
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("write_comment");                                        //告诉服务器，我要写数据
			out.flush();
			
			ObjectOutputStream oos=new ObjectOutputStream(MainApp.ss.getOutputStream());
			oos.writeObject(com);                                                 //将评论发过去
			oos.flush();
			
			fx_post_comments_list.getItems().add(com);                            //将最新的评论显示在评论栏
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void onMouseClick(){                                                  //点击事件主要是用来选中回复的人
		if(isFocus){                                                              //如果有选中的
			System.out.println("选中时:"+"++"+toUser);
			fx_post_comments_list.getSelectionModel().clearSelection();
		}
		else{
			toUser=author;       
			System.out.println("未选中条目时:"+"++"+toUser);
		}
		
		fx_post_mycomment_text.setText("回复 "+toUser+":");
		
		isFocus=false;
	}
	
 	private void getPost(){
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("post_content");                                          //请求内容
			out.flush();
			
			out.writeUTF(select_title);                            //将需要请求的帖子的标题发过去
			out.flush();
			
			ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(MainApp.ss.getInputStream()));      //读取帖子对象用的
			
			Post recv_post=(Post)ois.readObject();
			
			fx_post_date_text.setText(recv_post.getDate());                       //利用传过来的变量值对控件进行赋值
			fx_post_author_text.setText(recv_post.getAuthor());
			author=recv_post.getAuthor();
			fx_post_title_text.setText(recv_post.getTitle());
			fx_post_content_text.setText(recv_post.getContent());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initText(){
		fx_post_content_text.setWrapText(true);
		fx_post_mycomment_text.setWrapText(true);
		fx_post_content_text.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		fx_post_date_text.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		fx_post_author_text.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		fx_post_title_text.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	
	private void initListView(){
		ObservableList<Comment> postitems=getItems();
		if(postitems!=null){                                  //不为空则初始化
			fx_post_comments_list.setItems(getItems());
		}
		fx_post_comments_list.setCellFactory(e -> new ListCell<Comment>() {
            @Override
            public void updateItem(Comment item, boolean empty) {
                if (!empty && item != null) {
                    BorderPane cell = new BorderPane();
                    cell.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));//.grayRgb(54)
                    
                    String content=item.getFromUser()+" "+item.getComment();              //评论的模式
                    
                    Text name = new Text(content);
                    name.setFont(Font.font(10));
                    name.setWrappingWidth(420);
                    name.setFill(Color.BLACK);
                    cell.setTop(name);
                    
                    /*Text date = new Text(item.getDate());
                    date.setFont(Font.font(10));
                    date.setFill(Color.BLACK);
                    cell.setLeft(date);
                    
                    Text status = new Text(item.getAuthor());
                    status.setFont(Font.font(10));
                    status.setFill(Color.BLACK);
                    cell.setRight(status);*/
                    
                    setGraphic(cell);
                }
                super.updateItem(item, empty);
            }
            
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                ((BorderPane) getGraphic()).setBackground(new Background(new BackgroundFill(selected ? Color.YELLOW : Color.WHITE, null, null)));
            }
        });
		fx_post_comments_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//fx_postlist_post_list.getSelectionModel().selectFirst();
		fx_post_comments_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Listener
			try{
			      toUser=newValue.getFromUser();
			}
			catch(Exception e){
				
			}
			isFocus=true;                                              //选中条目
        });
	}
	
	public ObservableList<Comment> getItems(){
		List<Comment> datalist=new ArrayList<Comment>();
		Comment temp;
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("post_comments");                                         //请求加载论坛中的帖子评论
			out.flush();
			out.writeInt(postid);                                                  //通过帖子编号
			out.flush();                                                           //获得帖子评论
			
			int nums=in.readInt();                                                 //读出有多少个论坛
			
			ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(MainApp.ss.getInputStream()));
			int i=0;
			for(i=0;i<nums;i++){                                                   //一次读出论坛对象
				temp=(Comment)ois.readObject();
				datalist.add(temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FXCollections.observableArrayList(datalist);
	}
	
	public void getPostId(){
		try{
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("get_postid");                                          //发出请求postid
			out.flush();
			
			out.writeUTF(select_title);                                          //将帖子的标题发送过去
			out.flush();
			
			postid=in.readInt();                                                 //读出帖子的id
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
