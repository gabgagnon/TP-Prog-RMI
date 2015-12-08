package ca.csf.client;

import java.io.IOException;
import java.io.InvalidObjectException;

public interface MyServerObserver 
{
	public void coinAdded(int row, int column, boolean player) throws IOException;
	public void gameOver(String message);
	public void disableControlButtons() throws InvalidObjectException;
	public void disableControlButton(int column);
}
