package controller;

import java.io.IOException;
import java.util.List;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ForumsControl {
	public static String select_topic;
	public static Stage stage;
	@FXML
	AnchorPane fx_forum_pane;
	@FXML
	private ListView<String> fx_forum_listview;
	
	@FXML
	private Button fx_forum_del_btn;
	
	@FXML
	private Button fx_forum_update_btn;
	
	@FXML
	private TextField fx_forum_topic_text;
	
	@FXML
	private Button fx_forum_add_btn;
	
	@FXML
	private void onBtnDelete(){                                                             //删除论坛
		@SuppressWarnings("deprecation")
		Action response = Dialogs.create()
		        .title("Confirm Dialog")
		        .message("确定要删除吗?")
		        .showConfirm();

		if (response == Dialog.ACTION_YES) {
			select_topic=fx_forum_listview.getSelectionModel().getSelectedItem();
			MainApp.db.deleteForum(select_topic);
			int selectedIndex = fx_forum_listview.getSelectionModel().getSelectedIndex();
			fx_forum_listview.getItems().remove(selectedIndex);
		} else {
		    // ... user chose NO, CANCEL, or closed the dialog
		}
	}
	
	@FXML
	private void onBtnUpdate(){
		select_topic=fx_forum_listview.getSelectionModel().getSelectedItem();
		stage=new Stage();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/ForumOptLayout.fxml"));
	    try {
		     AnchorPane myroot=(AnchorPane)loader.load();
			
			 Scene scene=new Scene(myroot);
			 stage.setScene(scene);
			
			 stage.show();
		} catch (IOException e) {
			 // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onBtnAddForum(){
		String topic=fx_forum_topic_text.getText();                                        //得到主题
		if(MainApp.db.addForum(topic)){
			fx_forum_listview.getItems().add(0, topic);
			Dialogs.create()
	        .title("Confirm Dialog")
	        .message("添加成功!")
	        .showConfirm();
		}
		else{
			Dialogs.create()
	        .title("Confirm Dialog")
	        .message("添加失败,可能论坛已存在!")
	        .showError();
		}
	}
	
	@FXML
	private void initialize(){
		ObservableList<String> postitems=getItems();
		if(postitems!=null){                                  //不为空则初始化
			fx_forum_listview.setItems(postitems);
		}
		fx_forum_listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		fx_forum_listview.getSelectionModel().selectFirst();
		fx_forum_listview.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	public ObservableList<String> getItems(){
		List<String> datalist=MainApp.db.getTopic();
		
		return FXCollections.observableArrayList(datalist);
	}
}
