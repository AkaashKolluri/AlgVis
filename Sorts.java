import java.awt.*;
import java.util.*;
import javax.swing.*;


//sorts basically acts as a house for all of our differnt panels
//We add all the differnt types of sorts, adn then this will return all of it.
public class Sorts extends JTabbedPane{
    
    public Sorts()
	{
        super();
        BubbleSortMain b = new BubbleSortMain();
        InsertionSortMain i = new InsertionSortMain();
        SelectionSortMain s = new SelectionSortMain();
        MergeSortMain m = new MergeSortMain();
        QuickSortMain q = new QuickSortMain();
       
        this.addTab("Bubble Sort", null, b, "hey");
        this.addTab("Insertion Sort", null, i, "hey");
        this.addTab("Selection Sort", null, s, "hey");
        this.addTab("Merge Sort", null, m, "hey");
        this.addTab("Qucik Sort", null, q, "hey");
	}
}
