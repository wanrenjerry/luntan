package controller;

import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Post;

public class PostOptControl {
	public static Post post;
	private String title;
	@FXML
	private AnchorPane fx_postopt_pane;
	
	@FXML
	private Label fx_post_postname_label;
	
	@FXML
	private Label fx_post_postauthor_label;
	
	@FXML
	private Label fx_post_date_label;
	
	@FXML
	private Label fx_post_forum_label;
	
	@FXML
	private Label fx_post_istop_label;
	
	@FXML
	private Label fx_post_issee_label;
	
	@FXML
	private CheckBox fx_post_top_checkbox;
	
	@FXML
	private CheckBox fx_post_see_checkbox;
	
	@FXML
	private Button fx_post_del_btn;
	
	@FXML
	private Button fx_post_update_btn;
	
	
	@FXML
	private void onBtnContent(){
		Stage stage=new Stage();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/view/PostContent.fxml"));
		
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
	
	@SuppressWarnings("deprecation")
	@FXML
	private void onBtnUpdate(){
		int see=0;
		if(fx_post_see_checkbox.isSelected()){                //0是可见,1是不可见
			see=1;
		}
		int top=0;
		if(fx_post_top_checkbox.isSelected()){
			top=1;
		}
		
		if(MainApp.db.setPostMsg(title, see, top)){
			Dialogs.create()
	        .title("Confirm Dialog")
	        .message("设置成功!")
	        .showConfirm();
			PostsControl.stage.close();
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
		initLabel();
	}
	
	private void initLabel(){
		post=MainApp.db.getPost(PostsControl.select_title);
		
		fx_post_postname_label.setText(post.getTitle());
		title=post.getTitle();
		fx_post_postauthor_label.setText(post.getAuthor());
		fx_post_date_label.setText(post.getDate());
		if(post.getSee()==0){
		    fx_post_issee_label.setText("是");
		}
		else{
			fx_post_issee_label.setText("否");
		}
		if(post.getTop()==1){
			fx_post_istop_label.setText("是");
		}
		else{
			fx_post_istop_label.setText("否");
		}
		
		String topic=MainApp.db.getTopic(post.getForumId());
		fx_post_forum_label.setText(topic);
	}
	

}
