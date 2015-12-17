package ca.csf.client;

import java.io.InvalidObjectException;

public interface MyServerObserver 
{
	public void coinAdded(int row, int column, boolean player);
	public void showInConsole(String message);
	public void disableControlButtons() throws InvalidObjectException;
	public void disableControlButton(int column);
}
