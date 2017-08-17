package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PostContentControl {
	@FXML
	private TextField fx_post_content_title;
	
	@FXML
	private TextArea fx_post_content;
	
	@FXML
	private ListView<String> fx_post_comments;
	
	@FXML
	private void initialize(){
		System.out.println("进来了");
		initText();
		initList();
	}
	
	private void initText(){
		System.out.println(PostOptControl.post.getTitle()+"+++++");
		fx_post_content_title.setText(PostOptControl.post.getTitle());
		fx_post_content.setWrapText(true);
		fx_post_content.setText(PostOptControl.post.getContent());
	}
	
	private void initList(){
		ArrayList<String> commentlist=MainApp.db.getComments(PostOptControl.post.getTitle());
		
		ObservableList<String> list=FXCollections.observableArrayList(commentlist);
		if(list!=null){
		    fx_post_comments.setItems(list);
		}
	}

}
