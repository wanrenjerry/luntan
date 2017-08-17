package controller;

import java.io.IOException;
import java.net.Socket;

import org.controlsfx.dialog.Dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ConnectControl {
	@FXML
	private AnchorPane fx_con_pane;
	@FXML
	private TextField fx_ip_text;                             //杈撳叆ip鐨則extfield鎺т欢
	
	@FXML
	private Button fx_con_btn;
	
	@FXML
	private void onBtnCon(ActionEvent event){                 //按钮事件，用来连接服务器
		try {
			String ip=fx_ip_text.getText();                   //ip输入框
			MainApp.ss=new Socket(ip,6000);                   //根据ip和端口连接服务器
			if(MainApp.ss.isConnected()){                     //如何连接成功
				
			    FXMLLoader loader=new FXMLLoader();
			    loader.setLocation(MainApp.class.getResource("/view/LoginLayout.fxml"));
			    AnchorPane mypane=(AnchorPane)loader.load();
			
			    Scene scene=new Scene(mypane);
			    MainApp.primaryStage.setScene(scene);
			    MainApp.primaryStage.show();
			}
			else{
				Dialogs.create()
		        .title("连接结果")
		        .masthead(null)
		        .message("连接失败!")
		        .showInformation();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Dialogs.create()
	        .title("连接结果")
	        .masthead(null)
	        .message("连接失败!服务器未开启或ip及端口号错误!!")
	        .showInformation();
		}
	}
}
