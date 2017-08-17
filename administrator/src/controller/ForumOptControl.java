package controller;

import java.util.ArrayList;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Forum;

public class ForumOptControl {
	private String topic;
	private String secthead;
	@FXML
	private AnchorPane fx_forumopt_pane;
	
	@FXML
	private Label fx_forum_forumname;
	
	@FXML
	private Label fx_forum_secthead;
	
	@FXML
	private Label fx_forum_date;
	
	@FXML
	private Label fx_forum_istop;
	
	@FXML
	private Label fx_forum_issee;
	
	@FXML
	private ComboBox<String> fx_forum_combox;
	
	@FXML
	private CheckBox fx_forum_top_checkbox;
	
	@FXML
	private CheckBox fx_forum_nosee_checkbox;
	
	
	@FXML
	private Button fx_forum_update_btn;
	
	
	@SuppressWarnings("deprecation")
	@FXML
	private void onBtnUpdate(){
		int see=0;
		if(fx_forum_nosee_checkbox.isSelected()){
			see=1;                                         //0是可见
		}
		
		int top=0;                                         //默认不置顶
		
		if(fx_forum_top_checkbox.isSelected()){
			top=1;                                         //置顶
		}
		
		if(MainApp.db.setForumMsg(topic, see, top, secthead)){
			Dialogs.create()
	        .title("Confirm Dialog")
	        .message("设置成功!")
	        .showConfirm();
			ForumsControl.stage.close();
		}
		else{
			Dialogs.create()
	        .title("Confirm Dialog")
	        .message("设置失败!!")
	        .showError();
		}
	}
	
	@FXML
	private void initialize(){
		initLabels();
		initCombox();
	}
	
	
	public void initLabels(){
		topic=ForumsControl.select_topic;
		
		Forum forum=MainApp.db.getForum(topic);
		
		fx_forum_forumname.setText(forum.getTopic());
		fx_forum_date.setText(forum.getDate());
		if(forum.getHead()==null){
			fx_forum_secthead.setText("无");
		}
		else{
		    fx_forum_secthead.setText(forum.getHead());
		}
		if(forum.getSee()==0){
			fx_forum_issee.setText("是");
		}
		else{
			fx_forum_issee.setText("否");
		}
		
		if(forum.getTop()==1){
			fx_forum_istop.setText("是");
		}
		else{
			fx_forum_istop.setText("否");
		}
	}
	
	public void initCombox(){
		ArrayList<String> userlist=MainApp.db.getUser();
		
		ObservableList<String> list=FXCollections.observableArrayList(userlist);
		fx_forum_combox.setItems(list);
		
		fx_forum_combox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Listener
			try{
			    secthead=newValue;
			}
			catch(Exception e){
				
			}
        });	
	}
}
