import java.awt.*;
import java.util.*;
import javax.swing.*;



public class TreeScreen extends JPanel{
    
    public TreeScreen()
	{
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);
	}
	
	public void paintComponent(Graphics g)
	{
        //should draw a black rectangle with 100 border 
		g.setColor(Color.CYAN);
		g.fillRect(100, 100, Main.width-200, Main.height-200);
		
	}
}