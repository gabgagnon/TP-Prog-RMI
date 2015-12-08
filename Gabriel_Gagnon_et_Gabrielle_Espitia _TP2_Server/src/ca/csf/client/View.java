package ca.csf.client;

import ca.csf.client.MyServerObserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InvalidObjectException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class View extends JFrame implements MyServerObserver
{
	private static final long serialVersionUID = 1L;

	private JButton[] controlButtons;

	private MyImageContainer[][] placeHolders;

	private final JTextField message = new JTextField(20);
	private final JPanel centerPane = new JPanel();

	private ClientController controller;
	
	private Color colorPlayerOne = new Color(255,0,0);
	private Color colorPlayerTwo = new Color(0,0,255);
	
	
	private final String TURN_PREFIX = "It's the turn of ";
	
	public View(ClientController controller)
	{
		this.controller = controller;
		
		this.setTitle("Connect4");

		this.configureWindow();

		this.setLayout(new BorderLayout());
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new FlowLayout());
		panelNorth.add(this.message);
		this.message.setEditable(false);
		this.message.setText("Welcome!");
		this.add(panelNorth, BorderLayout.NORTH);
		this.createMenu();
		this.setVisible(true);
	}
	
	private void createMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenuItem resignMenuItem = new JMenuItem("Resign");
		resignMenuItem.addActionListener(new ResignActionHandler());
		gameMenu.add(resignMenuItem);
		menuBar.add(gameMenu);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new AboutActionHandler());
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);
	}

	public void initBoard(int nbRows, int nbColumns) 
	{
		if (nbRows <= 0 || nbColumns <= 0) throw new IndexOutOfBoundsException("The board can't be empty.");
		if (nbRows >= 25 || nbColumns >= 25) throw new IndexOutOfBoundsException("The maximum size for a board is 25 by 25");

		this.centerPane.removeAll();
		this.placeHolders = new MyImageContainer[nbRows][nbColumns];
		this.controlButtons = new JButton[nbColumns];

		centerPane.setLayout(new GridLayout(nbRows + 1, nbColumns));

		for (int i = 0; i < nbColumns; i++)
		{
			JButton button = new JButton("T");
			this.controlButtons[i] = button;
			button.addActionListener(new ButtonHandler(i));
			centerPane.add(button);
		}

		for (int row = nbRows - 1; row >= 0; row--)
		{
			for (int column = 0; column < nbColumns; column++)
			{
				MyImageContainer button = new MyImageContainer();
				button.setOpaque(true);
				placeHolders[row][column] = button;
				centerPane.add(button);
			}
		}
		this.add(centerPane, BorderLayout.CENTER);
		this.revalidate();
	}

	private void configureWindow()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(((screenSize.width * 3) / 6), ((screenSize.height * 4) / 7));
		setLocation(((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2));
	}

	private class ButtonHandler implements ActionListener
	{
		private final int columnIndex;

		private ButtonHandler(int columnIndex)
		{
			this.columnIndex = columnIndex;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			System.out.println("Action on button: " + columnIndex);
			try 
			{
				controller.columnClicked(columnIndex);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class ResignActionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			System.out.println("Action on menu");
			try 
			{
				showScreenIsFullDialog(controller.getPlayer() + " resigned. " + controller.getAdversary()+ " has won!");
			}
			catch (InvalidObjectException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	private class AboutActionHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			JOptionPane.showMessageDialog(View.this, "GUI for Connect4\n420-520-SF TP1\n\nAuthor: François Gagnon", "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void coinAdded(int column, int row, boolean player) throws InvalidObjectException 
	{
		if (column < 0 || row < 0) throw new IndexOutOfBoundsException("The added coin can't be on a column or row' that doesn't exist");
		if (column >= controller.getColMax() ||  row >= controller.getRowMax()) throw new IndexOutOfBoundsException("The added coin can't be on a column or row' that doesn't exist");
		
		if (player) 
		{
			placeHolders[row][column].setBackground(colorPlayerTwo);
		}
		else if (!player) 
		{
			placeHolders[row][column].setBackground(colorPlayerOne);
		}
		this.message.setText(TURN_PREFIX + controller.getPlayer());
	}
	
	@Override 
	public void disableControlButtons () throws InvalidObjectException 
	{
	    for (JButton t : controlButtons)
	    {
	        t.setEnabled(false);
	    }
	    showScreenIsFullDialog(controller.getAdversary() + " won !");
	}
	
	@Override 
	public void disableControlButton (int column) 
	{
		if (column >= controller.getColMax() || column < 0) throw new IndexOutOfBoundsException("The button to disable is out of bounds.");

		controlButtons[column].setEnabled(false);
	}
	
	public void showScreenIsFullDialog(String message)
	{
		JOptionPane.showMessageDialog(View.this, message);
		controller.restartGame();
	}

}

