import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TreeScreen extends JPanel
{
    //declaring so we can acess in all methods
	private JTextField dataInput;
	private JButton ct;
	private JButton delete;
	private boolean treeExists;
	private Queue<Integer> dataToInsert;
	private String errorMessage;
	private BST intial;
	private BST display;
	//making Graphics accesible from any method in this class
	Graphics g;

    public TreeScreen()
	{
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
            }
          });
        add(ct);

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
	}
	
	public void paintComponent(Graphics g)
	{
		this.g = g;
        //should draw a black rectangle with 100 border 
		g.setColor(Color.CYAN);
		g.fillRect(100, 100, Main.width/20, Main.width/20);

		//displaying error message temporarily, if neccesary


		
		if(treeExists && dataToInsert.size()>0)
		{
			//displaying elements to add
			g.setColor(Color.BLACK);
			Font f = new Font ("Courier New", 1, 24);
			g.setFont(f);
			String display = "Elements to add: ";
			for(int e: dataToInsert)
				display += e+" ";
			g.drawString(display, Main.width/2-display.length()*6, 50);

			
		}

		
		
	}


	private void createTree()
	{
		//takes away the error message
		errorMessage = null;

		//make it deletable and prevent new trees
		delete.setEnabled(true);
		ct.setEnabled(false);

	
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
		repaint();

		//get height for spacing purposes
		int h = Math.max(3,intial.height());
		double boxSize = 0;
		if(h==3)
			boxSize = 1/15;

		if(h==4)
			boxSize = 1/20;
		
		if(h==5)
			boxSize = 1/25;

		if(h==6)
			boxSize = 1/40;


		int starth = 60;
		int startw = Main.width/2;
		g.fillRect(10,10,200,200);
		//drawTree(startw, starth, boxSize, h, display.getRoot());
		







	}


	private void drawTree(Graphics g)
	{

	}

	private void deleteTree()
	{
		//clearing everything
		treeExists = false;
		dataToInsert = null;
		intial = null;
		display = null;

		//inactivating the button
		delete.setEnabled(false);
		ct.setEnabled(true);

		//refreshing
		repaint();
	}


	

	private void delay(int ms)
	{

	}
}





class BST
{
	private TreeNode root;

	public BST()
	{

	}

	public TreeNode getRoot()
	{
		return root;
	}

	public void add(int v)
	{
		TreeNode temp = root;
		TreeNode temp1 = root;
		while(temp1!=null)
		{
			temp = temp1;
			if(v<temp.getValue())
				temp1 = temp1.getLeft();
			else
				temp1 = temp1.getRight();
		}

		if(temp == null)
			root = new TreeNode(v, null, null);
		else if(v<temp.getValue())
			temp.setLeft(new TreeNode(v, null, null));
		else
			temp.setRight(new TreeNode(v, null, null));
	}

	//method for public heigh access
	public int height()
	{
		//start recrusive method
		return heightHelper(root);
	}

	//recurivse method
	private int heightHelper(TreeNode t)
	{
		if(t == null)
			return 0;
		return 1+Math.max(heightHelper(t.getRight()), heightHelper(t.getLeft()));
	}
}



class TreeNode
{
	private int value;
	private TreeNode right;
	private TreeNode left;

	public TreeNode(int v, TreeNode r, TreeNode l)
	{
		value = v;
		right = r;
		left = l;
	}

	public TreeNode getRight()
	{
		return right;
	}

	public TreeNode getLeft()
	{
		return left;
	}

	public int getValue()
	{
		return value;
	}

	public void setRight(TreeNode r)
	{
		right = r;
	}

	public void setLeft(TreeNode l)
	{
		left = l;
	}

	public void setValue(int v)
	{
		value = v;
	}
}