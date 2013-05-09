import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JFrame;

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
  
  private ArrayList<Window> windowStack = new ArrayList<Window>();
	
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
    
    windowStack.add(newWindow);
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
  
  /**
   * Method to go back one window
   */
  public void back()
  {
    windowStack.remove(windowStack.size() - 1);
    
    if(windowStack.size() > 0)
    {
      Window lastWindow = windowStack.remove(windowStack.size() - 1);
      
      openWindow(lastWindow);
    }
  }
}
