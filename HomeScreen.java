import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomeScreen extends JPanel
{
    
    public HomeScreen()
	{
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);

        //Creating a button, then making it switch screens when pressed, then adding to the frame
        //Button for sorts
        JButton s = new JButton("Sorts");
        s.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) 
            {
                Main.switchScreen("SortScreen");
            }  
        });
        add(s);

        //Button for trees
        JButton t = new JButton("Trees");
        t.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) 
            {
                Main.switchScreen("TreeScreen");
            }
        });
        add(t);

        //button for paths
        JButton p = new JButton("Paths");
        p.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                Main.switchScreen("PathScreen");
            }
          
          });


        add(p);

	}
	
	public void paintComponent(Graphics g)
	{
        //should draw a black rectangle with 100 border 
		g.setColor(Color.BLACK);
		g.fillRect(0,0,Main.width, Main.height);
		
	}
}


