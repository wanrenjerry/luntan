package controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

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
import model.PostItem;

public class MyPostTabControl {
	public static String select_title;
	public static boolean isFocus=false; 
	@FXML
	private AnchorPane fx_mypost_tab_pane;
	
	@FXML
	private ListView<PostItem> fx_mypost_tab_listview;
	
	
	@FXML
	private void onMouseClick(){
		if(isFocus){
			
			if(isHasPost(select_title)){                                       //如何这个帖子未被删除
			    PostControl.select_title=select_title;
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
		        fx_mypost_tab_listview.getSelectionModel().clearSelection();                      //清空选中的
			}
			else{
				initListView();
			}
		}
		isFocus=false;
		
	}
	
	@FXML
	private void initialize(){
		initListView();
		fx_mypost_tab_listview.setCellFactory(e -> new ListCell<PostItem>() {
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
		fx_mypost_tab_listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//fx_postlist_post_list.getSelectionModel().selectFirst();
		fx_mypost_tab_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
		if(postitems!=null){                                                                       //不为空则初始化
		    fx_mypost_tab_listview.setItems(getItems());
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
			
			out.writeUTF("my_posts");                                              //请求自己写的帖子
			out.flush();
			out.writeUTF(LoginControl.userName);                                   //通过作者的名字获得
			out.flush();                                                           //获得帖子
			
			int nums=in.readInt();                                                 //读出有多少个帖子
			
			ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(MainApp.ss.getInputStream()));
			int i=0;
			for(i=0;i<nums;i++){                                                 //一次读出论坛对象,有序读出
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
	
	public boolean isHasPost(String title){
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
