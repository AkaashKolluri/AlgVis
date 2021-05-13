import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;


public class TreeScreen extends JPanel implements ActionListener
{
    //declaring so we can acess in all methods
	private JTextField dataInput;
	
	private JButton ct;
	private JButton delete;
	private JButton addNode;
	private JButton inOrder;
	private JButton postOrder;
	private JButton preOrder;
	private JButton rOrder;
	private boolean treeExists;
	private Queue<Integer> dataToInsert;
	private String errorMessage;
	private BST intial;
	private BST display;
	private Graphics g;

	private double boxSize;
	private int starth;
	private int startw;
	private int h;
	private boolean startPaint;

	private Timer time;
	private boolean timer = false;

    public TreeScreen()
	{
		time = new Timer(250, this);
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);
		
		//tree isn't made yet
		treeExists = false;


		//adding input field
		dataInput = new JTextField(20);
		add(dataInput, BorderLayout.SOUTH);
		
		//adding a button that starts the code sequence
		ct = new JButton("Create Tree");
        ct.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
				
                createTree();  
				time.start();
				
            }
          });
        add(ct);


		addNode = new JButton("Add nodes");
		addNode.setEnabled(false);
        addNode.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
				
                addNode();
				time.start();
				
            }
          });
        add(addNode);

		delete = new JButton("Delete Tree");
		delete.setEnabled(false);
        delete.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
                deleteTree();  
            }
          });
        add(delete);

		preOrder = new JButton("preOrder Traversal");
		preOrder.setEnabled(false);
        preOrder.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
                 
            }
          });
        add(preOrder);


		postOrder = new JButton("postOrder Traversal");
		postOrder.setEnabled(false);
        postOrder.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
              
            }
          });
        add(postOrder);


		inOrder = new JButton("inOrder Traversal");
		inOrder.setEnabled(false);
        inOrder.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
            
            }
          });
        add(inOrder);


		rOrder = new JButton("reverseOrder Traversal");
		rOrder.setEnabled(false);
        rOrder.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
                
            }
          });
        add(rOrder);
	}
	
	public void paintComponent(Graphics g)
	{
		this.g = g;
        //should draw a black rectangle with 100 border 
		g.setColor(Color.CYAN);
		g.fillRect(0,0,Main.width, Main.height);

		//displaying error message temporarily, if neccesary


		
		if(treeExists && dataToInsert.size()>0)
		{
			//displaying elements to add
			g.setColor(Color.BLACK);
			Font f = new Font ("Courier New", 1, 24);
			g.setFont(f);
			String display = "Elements to add: ";
			if(dataToInsert.size()<50)
			{ 
				for(int e: dataToInsert)
					display += e+" ";
			}
			g.drawString(display, Main.width/2-display.length()*6, 50);
			
		}

		if(startPaint)
		{
			g.setColor(Color.BLACK);
			
			treePainter(g, startw, starth, 1, this.display.getRoot());
		}

		
		
	}

	private void treePainter(Graphics g, int sw, int sh, int level, MyNode r)
	{
		if(r == null)
			return;
		
		//g.drawOval(sw, sh, (int)(boxSize*Main.width), (int)(boxSize*Main.width));
		Font f = new Font ("Courier New", 1, 24);
		g.setFont(f);
		g.drawString(r.getValue()+"", sw, sh);

		if(r.getRight() != null)
		{
			g.drawLine(sw, sh, (int)( sw+boxSize*(Main.width-100)*Math.pow(2, h-level)), (int)(sh+(Main.height-100)/(h+1)));
			//g.fillOval(sw, sh, (int)(boxSize*Main.width), (int)(boxSize*Main.width));
			treePainter( g, (int)( sw+boxSize*(Main.width-100)*Math.pow(2, h-level)), (int)(sh+(Main.height-100)/(h+1)), level+1, r.getRight());
		}

		//recrusive call for the right child

		if(r.getLeft() != null)
		{
			g.drawLine(sw, sh, (int)( sw-boxSize*(Main.width-100)*Math.pow(2, h-level)), (int)(sh+(Main.height-100)/(h+1)));
			//g.fillOval(sw, sh, (int)(boxSize*Main.width), (int)(boxSize*Main.width));
			treePainter(g,  (int)( sw-boxSize*(Main.width-100)*Math.pow(2, h- level)), (int)(sh+(Main.height-100)/(h+1)), level+1, r.getLeft());
		}

	}

	private void createTree()
	{
		//takes away the error message
		errorMessage = null;

		//make it deletable and prevent new trees
		delete.setEnabled(true);
		ct.setEnabled(false);
		addNode.setEnabled(true);

	
		//making it so we can draw the tree
		treeExists = true;
		
		//getting values to add to the tree
		String raw = dataInput.getText();
		//clearing data beacuse we don't need it
		dataInput.setText("");


		//processing data 
		String[] inputs = raw.split("[ ,]+");
		intial = new BST();
		dataToInsert = new LinkedList<Integer>();
		for(String e: inputs)
		{
			//sends error message if the data is entered incorrectly
			if(!e.matches("\\d*"))
			{
				errorMessage = "Please enter only numbers";

				//deleting any partial tree we have
				deleteTree();

				//breaking the method
				return;
				
			}

			//adding to a queue for the display tree
			dataToInsert.add(Integer.parseInt(e));
			//adding to our intial bst so we can get the height
			intial.add(Integer.parseInt(e));
		}
		
		// int e = Integer.parseInt(inputs[0]);
		// for(int i =e; i>0; i--)
		// {
		// 	for(int j =0; j<i; j++)
		// 	{
		// 		intial.add(i);
		// 		dataToInsert.add(i);
		// 	}
		// }


		//get height for spacing purposes
		this.h = Math.max(3,intial.height());
		boxSize = 0;
		if(h<=3)
			boxSize = 1.0/16;

		else if(h==4)
			boxSize = 1.0/32;
		
		else if(h==5)
			boxSize = 1.0/64;

		else if(h==6)
			boxSize = 1.0/128;
		else
			boxSize = 1.0/Math.pow(2,h+1);

		display = new BST();
		this.starth = 90;
		this.startw = Main.width/2;
		timer= true;
		repaint();
		time.start();
		
		







	}


	public void addNode()
	{
		String raw = dataInput.getText();
		dataInput.setText("");
		String[] inputs = raw.split("[ ,]+");
		timer = false;
		time.stop();
		for(String e: inputs)
		{
			//sends error message if the data is entered incorrectly
			if(!e.matches("\\d*"))
			{
				errorMessage = "Please enter only numbers";
				return;
				
			}

			//adding to a queue for the display tree
			dataToInsert.add(Integer.parseInt(e));
			//adding to our intial bst so we can get the height
			intial.add(Integer.parseInt(e));
		}
		
		// int e = Integer.parseInt(inputs[0]);
		// for(int i =e; i>0; i--)
		// {
		// 	for(int j =0; j<i; j++)
		// 	{
		// 		intial.add(i);
		// 		dataToInsert.add(i);
		// 	}
		// }


		//get height for spacing purposes
	
		boolean resize = false;
		if(intial.height()>h)
		{
		    resize = true;
		}
		this.h = intial.height();
		boxSize = 0;
		if(h<=3)
			boxSize = 1.0/16;

		else if(h==4)
			boxSize = 1.0/32;
		
		else if(h==5)
			boxSize = 1.0/64;

		else if(h==6)
			boxSize = 1.0/128;
		else
			boxSize = 1.0/Math.pow(2,h+1);

		this.starth = 90;
		this.startw = Main.width/2;

		
		startPaint = true;
		timer= true;
		
	}

	private int delaye(int t)
	{
		int x = 0;
		for(double i = 0.001; i<t; i+=0.000001)
			x++;
		return x;
	}

	public void actionPerformed(ActionEvent e)
	{
		
		if(timer)
		{
			rOrder.setEnabled(false);
			inOrder.setEnabled(false);
			preOrder.setEnabled(false);
			postOrder.setEnabled(false);
			if(dataToInsert.size()>0)
				display.add(dataToInsert.remove());
			else
			{
				rOrder.setEnabled(true);
				inOrder.setEnabled(true);
				preOrder.setEnabled(true);
				postOrder.setEnabled(true);

				timer = false;
				time.stop();
			}
			startPaint  = true;
			repaint();
		}
		
		
	}

	

	private void deleteTree()
	{
		//clearing everything
		treeExists = false;
		dataToInsert = null;
		intial = null;
		display = null;
		startPaint = false;
		boxSize = 0;
		h = 0;
		starth = 0;
		startw = 0;
		timer = false;
		time.stop();

		//inactivating the button
		delete.setEnabled(false);
		ct.setEnabled(true);
		addNode.setEnabled(false);
		rOrder.setEnabled(false);
		inOrder.setEnabled(false);
		preOrder.setEnabled(false);
		postOrder.setEnabled(false);

		//refreshing
		repaint();
	}


	

}

