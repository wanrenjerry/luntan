package controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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
import javafx.stage.Stage;

public class UsersControl {
	public static boolean needUpdate=false;
	public static String select_name;
	public static Stage stage;
	@FXML
	private AnchorPane fx_user_pane;
	
	@FXML
	public ListView<String> fx_user_listview;
	
	@FXML
	private Button fx_user_flush_btn;
	
	@FXML
	private Button fx_user_update_btn;

	
	@FXML
	private void onBtnDelete(){                                                               //删除按钮
		@SuppressWarnings("deprecation")
		Action response = Dialogs.create()
		        .title("Confirm Dialog")
		        .message("确定要删除吗?")
		        .showConfirm();

		if (response == Dialog.ACTION_YES) {
			select_name=fx_user_listview.getSelectionModel().getSelectedItem();
			MainApp.db.deleteUser(select_name);
			int selectedIndex = fx_user_listview.getSelectionModel().getSelectedIndex();
			fx_user_listview.getItems().remove(selectedIndex);
		} else {
		    // ... user chose NO, CANCEL, or closed the dialog
		}
		
	}
	
	@FXML
	private void onBtnUpdate(){                                                               //查看按钮
		select_name=fx_user_listview.getSelectionModel().getSelectedItem();
		stage=new Stage();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/UserOptLayout.fxml"));
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
	private void initialize(){                                                                //初始化按钮
		ObservableList<String> postitems=getItems();
		if(postitems!=null){                                  //不为空则初始化
			fx_user_listview.setItems(postitems);
		}
		fx_user_listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		fx_user_listview.getSelectionModel().selectFirst();
		fx_user_listview.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	public ObservableList<String> getItems(){
		List<String> datalist=MainApp.db.getUser();
		
		return FXCollections.observableArrayList(datalist);
	}

}
