import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;



public class BubbleSortMain extends JPanel implements ActionListener
{
    private Timer time;
    public BubbleSortMain()
	{
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);
		time = new Timer(250, this); //action preformed gets called every 250ms
		time.start();
	}
	
	public void paintComponent(Graphics g)
	{
        //should draw a black rectangle with 100 border 
		g.setColor(Color.BLACK);
		g.fillRect(100, 100, Main.width-200, Main.height-200);
		
	}

	public void actionPerformed(ActionEvent e)
	{
		
		
	}
}
