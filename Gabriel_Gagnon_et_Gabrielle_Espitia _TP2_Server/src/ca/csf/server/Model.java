package ca.csf.server;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;


import ca.csf.client.MyServerObserver;

public class Model
{
	private ArrayList<MyServerObserver> observers = new ArrayList<MyServerObserver>();
	
	private int howManyInLineToWin;
	private int counterToWin;
	
	private Case [] [] gameArray; //FG: ce n'est pas un tableau 2D normal, il devrait être wrappé dans une classe pour définir correctement son comportement (lors de l'ajout)
	
	private int rowMax;
	private int colMax;
	
	private boolean player = true;
	
	final String ARRAY_FULL_MSG = "Le tableau de jeu est complètement rempli. Partie nulle.";
		
	public Model(int column, int row, int width)
	{
		howManyInLineToWin = width;
		rowMax = row;
		colMax = column;
				
		gameArray = new Case [colMax][rowMax];
		
		initArray();
	}
	
	public int getRowMax() 
	{
		return rowMax;
	}

	public int getColMax() 
	{
		return colMax;
	}	
	
	private void initArray() 
	{
		for (int x = 0; x < colMax; ++x)
		{
			for (int y = 0; y < rowMax; ++y)
			{
				gameArray[x][y] = new Case ();
			}
		}
	}
	
	public boolean getPlayer()
	{
		return player;
	}
		
	public void addObserver(MyServerObserver observer)
	{
		this.observers.add(observer);
	}
	
	public int countObservers()
	{
		return this.observers.size();
	}
	
	public void columnClicked(int column) throws Exception 
	{
		if (column < 0 || column > colMax) throw new Exception("The value of the column exceeds the number of columns");
		
		for (int row = 0; row < rowMax; ++row)
		{//FG: peu efficace, on devrait conserver cette information au lieu de la calculer à chaque fois.
			if (gameArray[column][row].empty)
			{
				player ^= true;		
				gameArray[column][row].player = player;
				notifyObservers(column, row, player);
				isWon(column, row);
				gameArray[column][row].empty = false;
				break;
			}
		}
		
		checkIfSomeColumnsAreFull();
		
		if (IsArrayFull())
		{
			gameOver(ARRAY_FULL_MSG);
		}		
	}
	
	public boolean IsArrayFull() 
	{
		for (int x = 0; x < colMax; ++x)
		{
			if (gameArray[x][rowMax - 1].empty) {
				return false;
			}			
		}
		
		return true;
	}

	private void checkIfSomeColumnsAreFull()
	{
		for (int x = 0; x < colMax; ++x)
		{
			if (!gameArray[x][rowMax - 1].empty) {
				disableControlButton(x);
			}
		}		
	}

	//FG: algo très bien, mais il serait intéressant d'abstraire les directions au lieu d'utiilser 1, 0 et -1 dans les boucles...
	private void isWon (int column, int row) throws InvalidObjectException
	{
		boolean hasWon = false;
		for (int x = -1; x < 2; ++x)
		{
			for (int y = -1; y < 2; ++y)
			{
				if (!(x == 0 && y == 0))
				{
					counterToWin = 1;
				    checkInSameDirection(x, y, column, row);
				    checkInSameDirection(x * (-1), y* (-1), column, row);
				    if (counterToWin >= howManyInLineToWin)
				    {
				    	hasWon = true;
				    }
				}   
			}
		}
		if(hasWon)
		{
			disableControlButtons();
		}
	 }
	
	private void checkInSameDirection(int x, int y, int currentColumn, int currentRow) throws InvalidObjectException 
	{
		currentColumn += x;
		currentRow += y;
		
		if (currentColumn >= 0 && currentRow >= 0 && currentColumn < colMax && currentRow < rowMax)
		{
			Case currentCase = gameArray[currentColumn][currentRow];
			if (currentCase.player == player && !currentCase.empty)
			{
				++counterToWin;
				checkInSameDirection(x, y, currentColumn, currentRow);
				
			}
		}		
	}
	
	private void notifyObservers(int column, int row, boolean player) throws IOException
	{
		for(MyServerObserver obs : this.observers)
		{
			obs.coinAdded(column, row, player);
		}
	}

	private void disableControlButtons() throws InvalidObjectException
	{
		for(MyServerObserver obs : this.observers)
		{
			obs.disableControlButtons();
		}
	}
	
	private void disableControlButton(int column)
	{
		for(MyServerObserver obs : this.observers)
		{
			obs.disableControlButton(column);
		}
	}
	private void gameOver(String message) throws InvalidObjectException
	{
		for(MyServerObserver obs : this.observers)
		{
			obs.gameOver(message);
		}
	}
	
}
