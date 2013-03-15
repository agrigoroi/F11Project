import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jack Farrelly
 *
 * Controll GUI class
 * Provides interface for controller functions
 * 
 */
public class ControllerPanelGUI extends Window implements ActionListener
{
  private MainGUI window;
  private Container contents;
  
  private JButton  btnRoster		= new JButton("Create roster");
	
  public ControllerPanelGUI()
  {
    
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(5, 1));
		
    contents.add(btnRoster);
		
    btnRoster.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    //...
  }
}
