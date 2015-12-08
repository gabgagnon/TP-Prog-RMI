package ca.csf.client;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;

import ca.csf.client.View;
import ca.csf.server.IServer;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

public class ClientController implements Serializable{

	static final long serialVersionUID =  42L;
	private CallHandler callHandler;
	private IServer myServiceCaller;
	private View view;
	private int id = 0;
	
	public ClientController() 
	{
		callHandler = new CallHandler();
		Client client;
		this.view = new View(this);
		
		try {			
			client = new Client("127.0.0.1", 12345, callHandler);
			myServiceCaller = client.getGlobal(IServer.class);
			
			id = myServiceCaller.addPlayerObserver(view);
			int a = myServiceCaller.getNbRows();
			view.initBoard(myServiceCaller.getNbRows(), myServiceCaller.getNbColumns());
			
			callHandler.registerGlobal(MyServerObserver.class, view);

		} catch (IOException | LipeRMIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
	}
	
	
	public static void main(String... args) throws IOException
	{
		new ClientController();
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



