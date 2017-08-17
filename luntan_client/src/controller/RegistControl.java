package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.controlsfx.dialog.Dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Person;

public class RegistControl {
	@FXML
	private AnchorPane fx_regist_pane;
	@FXML
	private TextField fx_regist_name_text;
	@FXML
	private PasswordField fx_regist_password_text;
	@FXML
	private TextField fx_regist_school_text;
	@FXML
	private TextField fx_regist_mail_text;
	@FXML
	private RadioButton fx_regist_nan_btn;
	@FXML
	private RadioButton fx_regist_nv_btn;
	@FXML
	private Button fx_regist_sureregist_btn;
	@FXML
	private Button fx_regist_cancelregist_btn;
	
	@FXML
	private void onBtnSelectNan(){
		fx_regist_nan_btn.setSelected(true);                        //点中这个男，男就为真
		fx_regist_nv_btn.setSelected(false);
	}
	
	@FXML
	private void onBtnSelectNv(){
		fx_regist_nan_btn.setSelected(false);                        //点中这个女，女就为真
		fx_regist_nv_btn.setSelected(true);
	}
	
	@FXML
	private void onBtnSureRegist(){                                  //确定注册按钮
		Person pp=new Person();
		
		pp.setName(fx_regist_name_text.getText());
		pp.setPassword(fx_regist_password_text.getText());
		pp.setSchool(fx_regist_school_text.getText());
		pp.setMail(fx_regist_mail_text.getText());
		if(fx_regist_nan_btn.isSelected()){
			pp.setSex("男");
		}
		else if(fx_regist_nv_btn.isSelected()){
			pp.setSex("女");
		}
		
		try {
			//写数据
			DataOutputStream out=new DataOutputStream(MainApp.ss.getOutputStream());
			//读数据
			DataInputStream in=new DataInputStream(MainApp.ss.getInputStream());
			
			out.writeUTF("zhuce");                                //给服务器端发送注册
			out.flush();
			
			ObjectOutputStream oos=new ObjectOutputStream(MainApp.ss.getOutputStream());
			oos.writeObject(pp);                                  //将用户信息发过去
			oos.flush();
			
			int flag=in.read();
			if(flag==1){                                          //注册成功
				Dialogs.create()
		        .title("注册结果")
		        .masthead(null)
		        .message("注册成功!")
		        .showInformation();
			}
			else{                                                 //注册失败
				Dialogs.create()
		        .title("注册结果")
		        .masthead(null)
		        .message("注册失败,网络异常或该用户名已存在!")
		        .showInformation();
			}
			LoginControl.mystage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onBtnCancelRegist(){                             //取消注册按钮
		LoginControl.mystage.close();
	}
	
	@FXML
	private void onBtnExit(){
		
	}
	
	
	
	
	
	
	

}
