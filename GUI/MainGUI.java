import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class
 *
 * @author Jack Farrelly
 */
public class MainGUI extends JFrame
{
	private static MainGUI window;
	public Container contents;
	
	public MainGUI()
	{
		contents = getContentPane();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void openWindow(Window newWindow)
	{
		contents.removeAll();
		
		newWindow.show(window);
		
		window.setSize(640, 480);
		window.repaint();
		window.setVisible(true);
	}
	
	public static void main(String[] args)
	{
	        database.openBusDatabase();
		window = new MainGUI();
		window.openWindow(new LoginGUI());
	}
}
