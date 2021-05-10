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
        //creating frame
        JFrame j = new JFrame(); 
        //adding home screen and setting size
        HomeScreen h = new HomeScreen();
        j.setSize(width, height);
        j.add(h);
        //making the frame be seen and exitable
        j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }


}
