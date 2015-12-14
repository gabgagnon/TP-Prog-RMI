package ca.csf.server;

import java.io.IOException;

import javax.swing.JFrame;

import ca.csf.client.MyServerObserver;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;

public class ServerController extends Server implements IServer
{
	private ca.csf.server.Model model;
	private JFrame newGameDialog;

	public ServerController(int column, int row, int width, JFrame newGameDialog)
	{
		if (row <= 0 || column <= 0) throw new IndexOutOfBoundsException("The board can't be empty.");
		if (row >= 25 || column >= 25) throw new IndexOutOfBoundsException("The maximum size for a board is 25 by 25");
		if (width > 12 || width < 4) throw new IndexOutOfBoundsException("The connect requirement has to be at least of 4, and lesser than 12");

		this.model = new Model( column,  row,  width);
		this.newGameDialog = newGameDialog;

		
		CallHandler callHandler = new CallHandler();
		try {
			callHandler.registerGlobal(IServer.class, this);
			this.bind(12345, callHandler);
			ServListener serv = new ServListener();
			this.addServerListener(new ServListener());
			while(true){
				try {
					Thread.sleep(500);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}				
		} catch (LipeRMIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sayHello(String helloFromWho) {
		System.out.println(helloFromWho);
	}
	
	@Override
	public void addServListener(ServListener clientProxy) {
		this.addServerListener(clientProxy);
	}

	@Override
	public int addPlayerObserver(MyServerObserver view)
	{
		this.model.addObserver(view);
		return this.model.countObservers();		 
	}
	
	@Override
	public int getNbRows() {
		return this.model.getRowMax();
	}

	@Override
	public int getNbColumns() {
		return this.model.getColMax();
	}

	@Override
	public void columnClicked(int columnIndex)
	{		
		try {
			model.columnClicked(columnIndex);
			if(model.IsArrayFull());
			{
				close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void gameClosed() 
	{
		newGameDialog.setVisible(true);
	}
}
