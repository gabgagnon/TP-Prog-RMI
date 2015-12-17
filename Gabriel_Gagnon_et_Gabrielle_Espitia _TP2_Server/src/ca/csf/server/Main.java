package ca.csf.server;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main 
{	
	public static int colonne;
	public static int rangee;
	public static int longueurDeChaine;
	
	public static void main(final String[] args)
	{
        final JFrame newGameView = new JFrame();
        JButton button = new JButton();

        newGameView.setLocationRelativeTo(null);
        
        button.setText("Commencer une nouvelle partie?");
        newGameView.add(button);
        newGameView.pack();
        newGameView.setVisible(true);

        button.addActionListener(new java.awt.event.ActionListener() 
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
            	try 
            	{
                	try
                	{
    					new ServerController(7, 6, 4, newGameView);
    					button.setText("Partie en cours");
    				} catch (IndexOutOfBoundsException e) {
    					System.out.println("The game parameters were invalid. Please restart the application.");
    				}
            	}
            	catch (NumberFormatException e)
            	{
					System.out.println("Cancel button shouldn't be clicked.");
            	}
            }
        });
        
	}

}
