package controller;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.User;

@SuppressWarnings("deprecation")
public class UserOptControl {
	private String role=null;
	private String userName;
	private String juese;
	
    private	UsersControl uc;
	
	@FXML
	private AnchorPane fx_useropt_pane;
	
	@FXML
	private Label fx_user_name_label;
	
	@FXML
	private Label fx_user_password_label;
	
	@FXML
	private Label fx_user_juese_label;
	
	@FXML
	private ComboBox<String> fx_user_combox;
	
	@FXML
	private Button fx_user_del_btn;
	
	@FXML
	private Button fx_user_update_btn;
	
	
	@SuppressWarnings("deprecation")
	
	@FXML
	private void onBtnUpdate(){
		if(role==null){
			Dialogs.create()
	        .title("Confirm Dialog")
	        .message("未选择角色!!!")
	        .showError();
		}
		else if(role.equals(juese)){
			return;
		}
		else{
			int flag=0;
	        if(role.equals("普通会员")){
	        	flag=1;
		    }
		    else if(role.equals("高级会员")){
			    flag=2;
		    }
		    else if(role.equals("超级会员")){
			    flag=3;
		    }
	        MainApp.db.setJuese(userName, flag);
	        Dialogs.create()
	        .title("Confirm Dialog")
	        .message("修改成功!")
	        .showConfirm();
	        
	        UsersControl.stage.close();
		}
	}
	
	@FXML
	private void initialize(){
		User user=MainApp.db.getUserMsg(UsersControl.select_name);
		fx_user_name_label.setText(user.getName());
		userName=user.getName();
		fx_user_password_label.setText(user.getPassword());
		
		int flag=user.getRole();
		if(flag==1){
			fx_user_juese_label.setText("普通会员");
			juese="普通会员";
		}
		else if(flag==2){
			fx_user_juese_label.setText("高级会员");
			juese="高级会员";
		}
		else if(flag==3){
			fx_user_juese_label.setText("超级会员");
			juese="超级会员";
		}
		
		ObservableList<String> list=FXCollections.observableArrayList("普通会员","高级会员","超级会员");
		fx_user_combox.setItems(list);
		fx_user_combox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Listener
			try{
			    role=newValue;
			}
			catch(Exception e){
				
			}
        });
		uc=new UsersControl();
	}
}
