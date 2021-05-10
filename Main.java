import java.awt.*;
import java.util.*;
import javax.swing.*;




public class Main
{
    //setting width and height variables as public so we can acess them from other files to resize the screen based on screen width and height
    public static int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    public static int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    public static void main(String[] args) 
    {
        //creating frame + layout so we can switch between frames
        JFrame j = new JFrame(); 
        Container c  = j.getContentPane();
        c.setLayout(new CardLayout());

        //adding sort screen
        HomeScreen h = new HomeScreen();
        c.add(h, "HomeScreen");

        
        Sorts s = new Sorts();
        c.add(s, "SortScreen");
        //adding home screen to continer
       

       
        
        //making the frame be the correct size visible, exitable
        j.setSize(width, height);
        j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }


}
