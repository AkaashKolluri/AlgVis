import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomeScreen extends JPanel implements ActionListener
{
    
	int rCoords[][][]; //x,y,two coords (top left)
	boolean color[][]; //x,y true for white false for black
	int columns=15;
	int rows=10;
	HashSet<ArrayList<Integer>> whites0;
	HashSet<ArrayList<Integer>> blacks0;
	HashSet<ArrayList<Integer>> whites1;
	HashSet<ArrayList<Integer>> blacks1;
	
	int shift=0;
	int move=0;
	//Timer
	public Timer time;
	private boolean timer = false;
	    	
    public HomeScreen()
	{
    	
    	time = new Timer(2, this);
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);

        //Creating a button, then making it switch screens when pressed, then adding to the frame
        //Button for sorts
        // JButton s = new JButton("Sorts");
        // s.addActionListener(new ActionListener() 
        // {
        //     public void actionPerformed(ActionEvent event) 
        //     {
        //         Main.switchScreen("SortScreen");
        //     }  
        // });
        // add(s);

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
        
        //Animation
        boolean white=true;
		rCoords=new int[columns][rows][2];
        color=new boolean[columns][rows];
        whites0=new HashSet<ArrayList<Integer>>();
        blacks0=new HashSet<ArrayList<Integer>>();
        whites1=new HashSet<ArrayList<Integer>>();
        blacks1=new HashSet<ArrayList<Integer>>();
        
        for(int i=0; i<columns; i++) {
			for(int j=0; j<rows; j++) {
				rCoords[i][j][0]=102*i-102;
				rCoords[i][j][1]=102*j-102;
				color[i][j]=white;
				
				ArrayList<Integer> yes=new ArrayList<Integer>();
				yes.add(i); yes.add(j);
				
				if(white) {
					if(i%2==0) {
						whites0.add(yes);
					}
					else {
						whites1.add(yes);
					}
				}
				else {
					if(i%2==0) {
						blacks0.add(yes);
					}
					else {
						blacks1.add(yes);
					}
				}
				
				white=!white;
			}
			if(i%2==0) {
				white=false;
			}
			else {
				white=true;
			}
		}
        
        timer=true;
        time.start();
	}
	
	public void paintComponent(Graphics g)
	{
        //should draw a black rectangle with 100 border 
		g.setColor(Color.BLACK);
		g.fillRect(0,0,Main.width, Main.height);
		
		for(int i=0; i<columns; i++) {
			for(int j=0; j<rows; j++) {
				if(color[i][j]) {
					g.setColor(Color.WHITE);
				}
				else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(rCoords[i][j][0],rCoords[i][j][1],100,100);
			}
		}
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(timer)
		{
			if(move==132) {
				move=0;
				shift++;
				shift%=4;
			}
			
			if(102<=move) {
				
			}
			else if(shift==0) {
				for(ArrayList<Integer> k: whites0) {
					rCoords[k.get(0)][k.get(1)][0]+=1;
				}
				/*
				for(ArrayList<Integer> k: blacks0) {
					rCoords[k.get(0)][k.get(1)][0]+=1;
				}
				*/
				
				for(ArrayList<Integer> k: whites1) {
					rCoords[k.get(0)][k.get(1)][0]-=1;
				}
				
				/*
				for(ArrayList<Integer> k: blacks1) {
					rCoords[k.get(0)][k.get(1)][0]-=1;
				}
				*/
			}
			else if(shift==1)
			{
				for(ArrayList<Integer> k: whites0) {
					rCoords[k.get(0)][k.get(1)][1]-=1;
				}
				/*
				for(ArrayList<Integer> k: blacks1) {
					rCoords[k.get(0)][k.get(1)][1]-=1;
				}
				*/
				
				for(ArrayList<Integer> k: whites1) {
					rCoords[k.get(0)][k.get(1)][1]+=1;
				}
				/*
				for(ArrayList<Integer> k: blacks0) {
					rCoords[k.get(0)][k.get(1)][1]+=1;
				}
				*/
			}
			else if(shift==2)
			{
				for(ArrayList<Integer> k: whites0) {
					rCoords[k.get(0)][k.get(1)][0]-=1;
				}
				/*
				for(ArrayList<Integer> k: blacks0) {
					rCoords[k.get(0)][k.get(1)][0]-=1;
				}
				*/
				
				for(ArrayList<Integer> k: whites1) {
					rCoords[k.get(0)][k.get(1)][0]+=1;
				}
				/*
				for(ArrayList<Integer> k: blacks1) {
					rCoords[k.get(0)][k.get(1)][0]+=1;
				}
				*/
			}
			else {
				for(ArrayList<Integer> k: whites0) {
					rCoords[k.get(0)][k.get(1)][1]+=1;
				}
				/*
				for(ArrayList<Integer> k: blacks1) {
					rCoords[k.get(0)][k.get(1)][1]+=1;
				}
				*/
				
				for(ArrayList<Integer> k: whites1) {
					rCoords[k.get(0)][k.get(1)][1]-=1;
				}
				/*
				for(ArrayList<Integer> k: blacks0) {
					rCoords[k.get(0)][k.get(1)][1]-=1;
				}
				*/
			}
			move++;
			repaint();
		}
		
		
	}
}
