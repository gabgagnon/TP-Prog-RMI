package ca.csf.server;

import java.net.Socket;

import net.sf.lipermi.net.IServerListener;

public class ServListener implements IServerListener 
{
	public void clientConnected(Socket socket) 
	{
		System.out.println("Client connected: " + socket.getInetAddress());
	}

	public void clientDisconnected(Socket socket)
{
		System.out.println("Client disconnected: " + socket.getInetAddress());
	}

	public void coinAdded(int row, int column, boolean player) 
	{

	}

	public void disableControlButtons() 
	{

	}

	public void disableControlButton(int column)
	{

	}

}
