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
        //should draw a black rectangle with 100 border 
		g.setColor(Color.CYAN);
		g.fillRect(100, 100, Main.width-200, Main.height-200);

		//displaying error message temporarily, if neccesary


		
		if(treeExists && dataToInsert.size()>0)
		{
			//displaying elements to add
			String display = "Elements to add: ";
			for(int e: dataToInsert)
				display += e+" ";
			g.drawString(display, 500-display.length()*3, 50);
		}

		
		
	}


	private void createTree()
	{
		//takes away the error message
		errorMessage = null;

		//make it deletable
		delete.setEnabled(true);

	
		//making it so we can draw the tree
		treeExists = true;
		
		//getting values to add to the tree
		String raw = dataInput.getText();
		//clearing data beacuse we don't need it
		dataInput.setText("");


		//processing data 
		String[] inputs = raw.split("[ ,]+");
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

			dataToInsert.add(Integer.parseInt(e));
		}
		repaint();

		//creating an intial BST so we can get height for spacing purposes





	}


	private void deleteTree()
	{
		//clearing everything
		treeExists = false;
		dataToInsert = null;

		//inactivating the button
		delete.setEnabled(false);

		//refreshing
		repaint();
	}


	private void drawTree(Graphics g)
	{

	}

	private void delay(int ms)
	{

	}
}





class BST
{

}