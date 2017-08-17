package server;

import java.net.Socket;

import optclass.MainOpt;

public class OptThread extends Thread{
	public Socket ss;
	OptThread(Socket ss){                          //传递通信套接字
		this.ss=ss;
	}
	public void run(){
		MainOpt opt=new MainOpt(ss);
		opt.service();
	}

}
