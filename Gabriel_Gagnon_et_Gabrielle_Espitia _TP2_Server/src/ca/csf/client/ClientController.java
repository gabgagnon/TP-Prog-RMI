package ca.csf.client;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.ArrayList;

import ca.csf.client.View;
import ca.csf.server.IServer;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

public class ClientController implements Serializable{

	static final long serialVersionUID =  42L;
	private CallHandler callHandler;
	static IServer myServiceCaller;
	private View view;
	
	
	public ClientController() {
		try {
			callHandler.registerGlobal(MyServerObserver.class, this);
		} catch (LipeRMIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		myServiceCaller.registerObserver(this);

	}
	
	
	public static void main(String... args) throws IOException{
		CallHandler callHandler = new CallHandler();
		Client client = new Client("127.0.0.1", 12345, callHandler);
		myServiceCaller = client.getGlobal(IServer.class);
		String toSend = "Frank";
		
		//Obtention du Proxy		
		myServiceCaller.sayHello(toSend);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client done");
		
		client.close();
	}
	public void columnClicked(int columnIndex) throws IOException 
	{

	}
	
	public void restartGame () 
	{

	}
	
	public String getPlayer() throws InvalidObjectException 
	{
		return null;
	
	}
	
	public String getAdversary() throws InvalidObjectException
	{
		return null;

	}
	
	public int getColMax ()
	{
		return 0;
	}
	
	public int getRowMax ()
	{
		return 0;
	}
	
}



