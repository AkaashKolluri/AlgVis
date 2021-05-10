import java.awt.*;
import java.util.*;
import javax.swing.*;



public class HomeScreen extends JPanel{
    
    public HomeScreen()
	{
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);
        JButton b = new JButton("Sorts");
        b.setBounds(50,50,200,50);
        add(b);

        //add(new JButton("Trees"));
        // add(new JButton("Paths"));
	}
	
	public void paintComponent(Graphics g)
	{
        //should draw a black rectangle with 100 border 
		g.setColor(Color.BLACK);
		g.fillRect(0,0,Main.width, Main.height);
		
	}
}
