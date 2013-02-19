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
public class RequestHolidayGUI extends Window
{
	public Container contents;
	
	private JLabel	lblWelcome			= new JLabel("test");
	
	public void show(MainGUI window)
	{
		contents = window.getContentPane();
		
		contents.setLayout(new GridLayout(4, 1));
		
		contents.add(lblWelcome);
	}
}
