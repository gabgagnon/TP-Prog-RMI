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
            		colonne = Integer.parseInt(JOptionPane.showInputDialog(newGameView,
                            "How many columns would you like to have on the board? There's a 25 maximum.", 6));
                	rangee = Integer.parseInt(JOptionPane.showInputDialog(newGameView,
                            "How many rows would you like to have on the board? There's a 25 maximum.", 7));
                	longueurDeChaine = Integer.parseInt(JOptionPane.showInputDialog(newGameView,
                            "How many coins in line does it take to win? There's a 12 maximum.", 4));
                	newGameView.setVisible(false);
                	
                	try
                	{
    					new ServerController(colonne, rangee, longueurDeChaine, newGameView);
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
