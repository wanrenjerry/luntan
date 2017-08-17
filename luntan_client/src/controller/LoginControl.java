package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.controlsfx.dialog.Dialogs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginControl {
	public static String userName;
	@FXML
	private AnchorPane fx_login_pane;
	@FXML
	private TextField fx_name_text;
	@FXML
	private PasswordField fx_password_text;
	@FXML
	private Button fx_login_btn;
	@FXML
	private Button fx_regist_btn;
	
	public static Stage mystage;
	
	@FXML
	private void onBtnRegist(){
		try {
			mystage=new Stage();
			
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/RegistLayout.fxml"));
			AnchorPane myroot=(AnchorPane)loader.load();
			
			Scene scene=new Scene(myroot);
			mystage.setScene(scene);
			mystage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void onBtnLogin(){
		String name=fx_name_text.getText();                                 //得到用户名和密码
		String password=fx_password_text.getText();
		
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("denglu");                                        //向服务器说是登陆
			out.flush();
			
			String msg=name+"+"+password;                                  //用户信息
			out.writeUTF(msg);
			out.flush();
			
			int flag=in.read();
			if(flag==1){
				userName=name;
				Dialogs.create()
		        .title("登陆结果")
		        .masthead(null)
		        .message("登陆成功!")
		        .showInformation();
			}
			else{
				Dialogs.create()
		        .title("登陆结果")
		        .masthead(null)
		        .message("登录失败，用户名密码错误或者用户不存在!")
		        .showInformation();
			}
			
			
			//登陆成功之后要进入主界面
			
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/MainLayout.fxml"));
			AnchorPane myroot=(AnchorPane)loader.load();
			
			Scene scene=new Scene(myroot);
			MainApp.primaryStage.setScene(scene);
			MainApp.primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
