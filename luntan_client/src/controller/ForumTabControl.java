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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Forum;

public class ForumTabControl {
	public static String select_topic;
	public static boolean isFocus=false;
	public static int forumid;
	@FXML
	private AnchorPane fx_forum_tab_pane;
	
	@FXML
	private ListView<Forum> fx_forum_tab_listview;
	
	@FXML
	private void onMouseClick(){                         //点击选中帖子按钮,先获得论坛id,再进行跳转进入帖子界面
		if(isFocus){
			if(isHasForum(select_topic)){                //如果论坛未被删除
		        try {
			        DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			        DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			        out.writeUTF("get_forumid");
			        out.flush();
			        out.writeUTF(select_topic);                  //说这里是空
			        out.flush();
			
			        forumid=in.readInt();                        //得到了点中的论坛的ID
			
			        FXMLLoader loader=new FXMLLoader();
			        loader.setLocation(MainApp.class.getResource("/view/PostListLayout.fxml"));
			
			        AnchorPane myroot = (AnchorPane)loader.load();
			        Scene scene=new Scene(myroot);
			        MainApp.primaryStage.setScene(scene);
			        MainApp.primaryStage.show();
		        } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
		        }
		        fx_forum_tab_listview.getSelectionModel().clearSelection();
		    }
		}
		else{
			initListView();
		}
		isFocus=false;                                        //每次点击结束设为不聚焦
	}
	
	@FXML
	private void initialize(){                                //很重要的初始化函数
		initListView();
		fx_forum_tab_listview.setCellFactory(e -> new ListCell<Forum>() {
            @Override
            public void updateItem(Forum item, boolean empty) {
                if (!empty && item != null) {
                    BorderPane cell = new BorderPane();
                    cell.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));//.grayRgb(54)
                    
                    Text topic = new Text(item.getTopic());
                    topic.setFont(Font.font(14));
                    topic.setWrappingWidth(420);
                    topic.setFill(Color.BLACK);
                    cell.setTop(topic);
                    
                    Text date = new Text(item.getDate());
                    date.setFont(Font.font(10));
                    date.setFill(Color.BLACK);
                    cell.setLeft(date);
                    
                    Text name = new Text(item.getSectHead());
                    name.setFont(Font.font(10));
                    name.setFill(Color.BLACK);
                    cell.setRight(name);
                    
                    setGraphic(cell);
                }
                super.updateItem(item, empty);
            }
            
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                ((BorderPane) getGraphic()).setBackground(new Background(new BackgroundFill(selected ? Color.PALEGREEN : Color.AZURE, null, null)));
            }
        });
		fx_forum_tab_listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//fx_forum_tab_listview.getSelectionModel().selectFirst();
		fx_forum_tab_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Listener
			isFocus=true;
			try{
                select_topic=newValue.getTopic();                             //选中的String值
			}
			catch(Exception e){
				
			}
        });
		
	}
	
	public void initListView(){
		ObservableList<Forum> forumlist=getItems();
		if(forumlist!=null){                                  //如果不为空，则加载
		    fx_forum_tab_listview.setItems(getItems());
		}
	}
	
	public ObservableList<Forum> getItems(){
		List<Forum> datalist=new ArrayList<Forum>();
		Forum temp;
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("forum_list");                                            //发出求数据请求
			out.flush();
			
			int nums=in.readInt();                                                 //读出有多少个论坛
			
			ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(MainApp.ss.getInputStream()));
			int i=0;
			for(i=0;i<nums;i++){                                                   //一次读出论坛对象
				temp=(Forum)ois.readObject();
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
	
	
	public boolean isHasForum(String topic){
		try {
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("ishasforum");
			out.flush();
			
			out.writeUTF(topic);
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
