import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jack Farrelly
 *
 * GUI Class and centre of the application
 * 
 */
public class MainGUI extends JFrame
{
  public static MainGUI window;
  public Container contents;
	
  public MainGUI()
  {
    contents = getContentPane();
		
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
	
  /**
   * Method that displays a new window
   * It destroys the existing window
   */
  public void openWindow(Window newWindow)
  {
    contents.removeAll();
		
    newWindow.show(window);
		
    window.setSize(640, 480);
    window.repaint();
    window.setVisible(true);
  }  
	
  /**
   * The Main thread of the application
   */
  public static void main(String[] args)
  {
    database.openBusDatabase();
    window = new MainGUI();
    window.openWindow(new LoginGUI());
  }
}
