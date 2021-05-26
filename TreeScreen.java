import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;

import javax.swing.event.*;



public class TreeScreen extends JPanel implements ActionListener, ChangeListener
{
    //declaring so we can acess in all methods
	private JTextField dataInput;
	
	private JButton ct;
	private JButton ex;
	private JButton delete;
	private JButton addNode;
	private JButton inOrder;
	private JButton postOrder;
	private JButton preOrder;
	private JButton rOrder;
	private JSlider speed;
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


	private boolean isIn = false;
	private boolean isPre = false;
	private boolean isPost = false;
	private boolean isRev = false;
	private Queue<MyNode> traversalToShow;
	private String traversalOrder = "";

    public TreeScreen()
	{
		
		time = new Timer(400, this);
        //setting size to whatever the window size is, and making it visible
		setSize(Main.width, Main.height);
		setVisible(true);
		
		//tree isn't made yet
		treeExists = false;

		ex = new JButton("Exit");
		ex.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
				
				Main.switchScreen("HomeScreen");
				
            }
          });
        add(ex);

		//adding input field
		dataInput = new JTextField(20);
		add(dataInput);
		
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
				//Reseting the traverse values in case a traverse occured
				display.turnOff();
				repaint();
				traversalToShow = new LinkedList<MyNode>();
				traversalOrder = "";
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
                 isPre = true;
				 startTraversal();
            }
          });
        add(preOrder);


		postOrder = new JButton("postOrder Traversal");
		postOrder.setEnabled(false);
        postOrder.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
				isPost = true;
				startTraversal();
            }
          });
        add(postOrder);


		inOrder = new JButton("inOrder Traversal");
		inOrder.setEnabled(false);
        inOrder.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
				isIn = true;
				startTraversal();
            }
          });
        add(inOrder);


		rOrder = new JButton("reverseOrder Traversal");
		rOrder.setEnabled(false);
        rOrder.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent event) 
			{
                isRev = true;
				startTraversal();
            }
          });
	
        add(rOrder);

		speed = new JSlider(0,1000,250);
		
		speed.setMajorTickSpacing(200);
        speed.setMinorTickSpacing(200);
		speed.setPaintTrack(true);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);
 
        // setChangeListener
        speed.addChangeListener(this);
		
	
		add(speed);
	}
	
	public void stateChanged(ChangeEvent e)
    {
		time.stop();
		time = new Timer(speed.getValue(), this);
		time.start();
    }

	public void paintComponent(Graphics g)
	{
		this.g = g;
        //should draw a black rectangle with 100 border 
		Color c = new Color(181, 203, 235);
		g.setColor(c);
		g.fillRect(0,0,Main.width, Main.height);
		g.setColor(Color.BLACK);
		Font f = new Font ("Helvetica", 1, 24);
		g.setFont(f);
		g.drawString(traversalOrder, Main.width/2-(int)(traversalOrder.length()*5.5), Main.height-50);

		//displaying error message temporarily, if neccesary


		
		if(treeExists && dataToInsert.size()>0)
		{
			//displaying elements to add
			g.setColor(Color.BLACK);
			
			g.setFont(f);
			String display = "Elements to add: ";
			if(dataToInsert.size()<50)
			{ 
				for(int e: dataToInsert)
					display += e+" ";
			}
			g.drawString(display, Main.width/2-(int)(display.length()*5.5), 120);
			
		}

		if(startPaint)
		{
			g.setColor(Color.BLACK);
			if(this.intial.getRoot() == null)
			{
				g.drawString("Empty Tree", Main.width/2-55,120);
				
			}
			treePainter(g, startw, starth, 1, this.display.getRoot());
		}

		
		
	}

	private void treePainter(Graphics g, int sw, int sh, int level, MyNode r)
	{
		if(r == null)
			return;
		
		//g.drawOval(sw, sh, (int)(boxSize*Main.width), (int)(boxSize*Main.width));
		Font f = new Font ("Helvetica", 1, 24);
		g.setFont(f);
		if(r.isLit())
		{
			Color c2 = new Color(255, 242, 84);
			g.setColor(c2);
		}
		g.drawString(r.getValue()+"", sw, sh);
		g.setColor(Color.BLACK);

		if(r.getRight() != null)
		{
			g.drawLine(sw, sh, (int)( sw+boxSize*(Main.width-100)*Math.pow(2, h-level)), (int)(sh+(Main.height-200)/(h+1)));
			//g.fillOval(sw, sh, (int)(boxSize*Main.width), (int)(boxSize*Main.width));
			treePainter( g, (int)( sw+boxSize*(Main.width-100)*Math.pow(2, h-level)), (int)(sh+(Main.height-200)/(h+1)), level+1, r.getRight());
		}

		//recrusive call for the right child

		if(r.getLeft() != null)
		{
			g.drawLine(sw, sh, (int)( sw-boxSize*(Main.width-100)*Math.pow(2, h-level)), (int)(sh+(Main.height-200)/(h+1)));
			//g.fillOval(sw, sh, (int)(boxSize*Main.width), (int)(boxSize*Main.width));
			treePainter(g,  (int)( sw-boxSize*(Main.width-100)*Math.pow(2, h- level)), (int)(sh+(Main.height-200)/(h+1)), level+1, r.getLeft());
		}

	}

	private void createTree()
	{
		//takes away the error message
		errorMessage = null;
		display = new BST();

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
			if(!e.matches("-?\\d*"))
			{
				errorMessage = "Please enter only numbers";

				//deleting any partial tree we have
			

				//breaking the method
			
				
			}
			else if(e.matches("-?\\d+")){
			//adding to a queue for the display tree
				dataToInsert.add(Integer.parseInt(e));
			//adding to our intial bst so we can get the height
				intial.add(Integer.parseInt(e));}
		}

		
	


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

		
		this.starth = 160;
		this.startw = Main.width/2;
		timer= true;
		repaint();
		time.start();
		
		







	}


	public void addNode()
	{
		treeExists = true;
		String raw = dataInput.getText();
		dataInput.setText("");
		String[] inputs = raw.split("[ ,]+");
		
		for(String e: inputs)
		{
			//sends error message if the data is entered incorrectly
			if(!e.matches("-?\\d*"))
			{
				errorMessage = "Please enter only numbers";
			}
			else
			{
			//adding to a queue for the display tree
			dataToInsert.add(Integer.parseInt(e));
			//adding to our intial bst so we can get the height
			intial.add(Integer.parseInt(e));
			}
		}
		



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

		this.starth = 160;
		this.startw = Main.width/2;

		
		startPaint = true;
		timer= true;
		time.start();
		
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


		if(isPre || isIn || isPost || isRev)
		{
			if(traversalToShow.size()>0)
			{
				MyNode r = traversalToShow.remove();
				r.makeLit();
				traversalOrder+=" " + r.getValue();

			}
			else
			{
				time.stop();
				endTraversal();
				repaint();
			}
			repaint();
		}
		
		
	}


	public void endTraversal()
	{
		isPre = false;
		isIn = false;
		isPost = false;
		isRev = false;

		rOrder.setEnabled(true);
		inOrder.setEnabled(true);
		postOrder.setEnabled(true);
		preOrder.setEnabled(true);

		addNode.setEnabled(true);
		delete.setEnabled(true);

		
	}


	public void startTraversal()
	{
		display.turnOff();
		repaint();
		traversalToShow = new LinkedList<MyNode>();
		traversalOrder = "";
		rOrder.setEnabled(false);
		inOrder.setEnabled(false);
		postOrder.setEnabled(false);
		preOrder.setEnabled(false);
		addNode.setEnabled(false);
		delete.setEnabled(false);

		if(isPre)
		{
			preOrderTraversal(display.getRoot());
			traversalOrder = "PreOrder traversal:";
		}
		else if(isIn)
		{
			inOrderTraversal(display.getRoot());
			traversalOrder = "InOrder traversal:";
		}
		else if(isPost)
		{
			postOrderTraversal(display.getRoot());
			traversalOrder = "PostOrder traversal:";
		}
		else if(isRev)
		{
			rOrderTraversal(display.getRoot());
			traversalOrder = "ReverseOrder traversal:";
		}

		time.start();
	}

	public void inOrderTraversal(MyNode r)
	{
		if(r==null)
			return;
		inOrderTraversal(r.getLeft());
		traversalToShow.add(r);
		inOrderTraversal(r.getRight());

	}

	public void postOrderTraversal(MyNode r)
	{
		if(r==null)
			return;
		postOrderTraversal(r.getLeft());
		postOrderTraversal(r.getRight());
		traversalToShow.add(r);
	}

	public void preOrderTraversal(MyNode r)
	{
		if(r==null)
			return;
		traversalToShow.add(r);
		preOrderTraversal(r.getLeft());
		preOrderTraversal(r.getRight());
	}

	public void rOrderTraversal(MyNode r)
	{
		if(r==null)
			return;
			rOrderTraversal(r.getRight());
			traversalToShow.add(r);
			rOrderTraversal(r.getLeft());
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
		traversalOrder = "";

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

