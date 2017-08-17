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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PostsControl {
	public static String select_title;
	public static Stage stage;
	@FXML
	private AnchorPane fx_post_pane;
	
	@FXML
	private ListView<String> fx_post_listview;
	
	@FXML
	private Button fx_post_del_btn;
	
	@FXML
	private Button fx_post_update_btn;
	
	@FXML
	private void onBtnDelete(){
		@SuppressWarnings("deprecation")
		Action response = Dialogs.create()
		        .title("Confirm Dialog")
		        .message("确定要删除吗?")
		        .showConfirm();

		if (response == Dialog.ACTION_YES) {
			select_title=fx_post_listview.getSelectionModel().getSelectedItem();
			MainApp.db.deletePost(select_title);
			int selectedIndex = fx_post_listview.getSelectionModel().getSelectedIndex();
			fx_post_listview.getItems().remove(selectedIndex);
		} else {
		    // ... user chose NO, CANCEL, or closed the dialog
		}
	}
	
	@FXML
	private void onBtnUpdate(){
		select_title=fx_post_listview.getSelectionModel().getSelectedItem();
		stage=new Stage();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/PostOptLayout.fxml"));
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
	private void initialize(){
		ObservableList<String> postitems=getItems();
		if(postitems!=null){                                  //不为空则初始化
			fx_post_listview.setItems(postitems);
		}
		fx_post_listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		fx_post_listview.getSelectionModel().selectFirst();
		fx_post_listview.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	public ObservableList<String> getItems(){
		List<String> datalist=MainApp.db.getTitles();
		
		return FXCollections.observableArrayList(datalist);
	}

}
