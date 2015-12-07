package ca.csf.server;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import ca.csf.client.ClientController;
import ca.csf.client.MyServerObserver;
import ca.csf.client.View;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;

public class ServerController extends Server implements IServer {

	private ca.csf.server.Model model;
	private ArrayList<MyServerObserver> observers = new ArrayList<MyServerObserver>();


	public ServerController() {
		
		Model model;
		ca.csf.client.View view;
		JFrame window;

		
		CallHandler callHandler = new CallHandler();
		try {
			callHandler.registerGlobal(IServer.class, this);
			this.bind(12345, callHandler);
//			ServListener serv = new ServListener();
//			this.addServerListener(new ServListener());

			this.model = new Model();
			
			while(true){
				try {
					Thread.sleep(500);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}				
		} catch (LipeRMIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	@Override
	public void sayHello(String helloFromWho) {
		System.out.println(helloFromWho);
	}
	
	public static void main(String... arg) 
	{
		new ServerController();
	}

	@Override
	public void addServListener(ServListener clientProxy) {
		this.addServerListener(clientProxy);
	}


	@Override
	public void addModelObserver(MyServerObserver view) {
		this.model.addObserver(view);
	}



}
