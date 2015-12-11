package ca.csf.client;

import java.io.IOException;
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
	
	private int nbRows;
	private int nbColumns;
	
	public ClientController() 
	{
		callHandler = new CallHandler();
		Client client;
		this.view = new View(this);
		
		try {			
			client = new Client("127.0.0.1", 12345, callHandler);
			myServiceCaller = client.getGlobal(IServer.class);
			
			id = myServiceCaller.addPlayerObserver(view);
			
			nbRows = myServiceCaller.getNbRows();
			nbColumns = myServiceCaller.getNbColumns();
			
			view.initBoard(nbRows,nbColumns);
			
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

	
	
	public void restartGame () 
	{

	}
	
	public int getColMax ()
	{
		return nbColumns;
	}
	
	public int getRowMax ()
	{
		return nbRows;
	}


	public void columnClicked(int columnIndex) {
		myServiceCaller.columnClicked(columnIndex);
	}



	
}



