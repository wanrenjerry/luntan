package controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import model.GetDate;
import model.Post;
import model.PostItem;

public class PostListControl {
	public static String select_title;
	public static boolean isFocus=false;                              //是否有选中
	@FXML
	private AnchorPane fx_postlist_pane;
	
	@FXML
	private Button fx_postlist_back_btn;
	
	@FXML
	private ListView<PostItem> fx_postlist_post_list;
	
	@FXML
	private TextField fx_postlist_posttitle_text;
	
	@FXML
	private TextArea fx_postlist_postcontent_text;
	
	@FXML
	private Button fx_postlist_writepost_btn;
	
	@FXML
	private void onMouseClick(){                              //查看帖子具体内容函数
		if(isFocus){
			if(isHasPost(select_title)){
			    PostControl.select_title=select_title;                                           //设置当前选中的
			
		        Stage postStage=new Stage();
		
		        FXMLLoader loader=new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("/view/PostLayout.fxml"));
		        try {
			        AnchorPane postPane=(AnchorPane)loader.load();
			
			        Scene scene=new Scene(postPane);
			        postStage.setScene(scene);
			        postStage.setScene(scene);
			        postStage.show();
		        } catch (IOException e) {
			    // TODO Auto-generated catch block
			        e.printStackTrace();
		        }
		        fx_postlist_post_list.getSelectionModel().clearSelection();                      //清空选中的
		    }
		}
		else{
			initListView();
		}
		isFocus=false;                                                                       //每次响应之后就置为没有聚焦，即没有选中item
	}
	
	@FXML
	private void onBtnWrite(){                                                               //发表帖子函数
		String title=fx_postlist_posttitle_text.getText();
		String content=fx_postlist_postcontent_text.getText();
		String author=LoginControl.userName;                                                 //作者的名字
		int forumid=ForumTabControl.forumid;
		
		Post post=new Post();
		post.setAuthor(author);
		post.setContent(content);
		post.setTitle(title);
		post.setForumid(forumid);
		post.setDate(GetDate.getDate());
		
		
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("write_post");                                                      //告诉服务器，我要写数据
			out.flush();
			
			ObjectOutputStream ois=new ObjectOutputStream(MainApp.ss.getOutputStream());     //将写好的帖子发给服务器端
			ois.writeObject(post);
			ois.flush();
			
			int flag=in.readInt();
			if(flag==1){
				Dialogs.create()
		        .title("发表结果")
		        .masthead(null)
		        .message("发表成功!")
		        .showInformation();
				
				fx_postlist_post_list.getItems().add(0,new PostItem(title,GetDate.getDate(),author));      //新发表的条目加入到listview中
			}
			else{
				Dialogs.create()
		        .title("发表结果")
		        .masthead(null)
		        .message("发表失败!")
		        .showInformation();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@FXML
	private void onBtnBack(){
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/MainLayout.fxml"));
		try {
			AnchorPane myroot=(AnchorPane)loader.load();
			
			Scene scene=new Scene(myroot);
			MainApp.primaryStage.setScene(scene);
			MainApp.primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void initialize(){                                //初始化函数
		initListView();
		fx_postlist_post_list.setCellFactory(e -> new ListCell<PostItem>() {
            @Override
            public void updateItem(PostItem item, boolean empty) {
                if (!empty && item != null) {
                    BorderPane cell = new BorderPane();
                    cell.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));//.grayRgb(54)
                    
                    Text name = new Text(item.getTitle());
                    name.setFont(Font.font(14));
                    name.setWrappingWidth(420);
                    name.setFill(Color.BLACK);
                    cell.setTop(name);
                    
                    Text date = new Text(item.getDate());
                    date.setFont(Font.font(10));
                    date.setFill(Color.BLACK);
                    cell.setLeft(date);
                    
                    Text status = new Text(item.getAuthor());
                    status.setFont(Font.font(10));
                    status.setFill(Color.BLACK);
                    cell.setRight(status);
                    
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
		fx_postlist_post_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//fx_postlist_post_list.getSelectionModel().selectFirst();
		fx_postlist_post_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Listener
			try{
			    select_title=newValue.getTitle();
			}
			catch(Exception e){
				
			}
			isFocus=true;                                              //选中条目
        });
	}
	
	public void initListView(){
		ObservableList<PostItem> postitems=getItems();
		if(postitems!=null){                                  //不为空则初始化
		    fx_postlist_post_list.setItems(getItems());
		}
	}
	public ObservableList<PostItem> getItems(){
		List<PostItem> datalist=new ArrayList<PostItem>();
		PostItem temp;
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("forum_content");                                         //请求加载论坛中的帖子
			out.flush();
			out.writeInt(ForumTabControl.forumid);                                 //通过论坛号
			out.flush();                                                           //获得帖子
			
			int nums=in.readInt();                                                 //读出有多少个论坛
			
			ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(MainApp.ss.getInputStream()));
			int i=0;
			for(i=0;i<nums;i++){                                                   //一次读出论坛对象
				temp=(PostItem)ois.readObject();
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
	
	public boolean isHasPost(String title){                                             //判断这个数据是否存在
		try {
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("ishaspost");
			out.flush();
			
			out.writeUTF(title);
			out.flush();
			
			int flag=in.readInt();
			
			if(flag==1){
				return true;
			}
			else if(flag==2){
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
}
