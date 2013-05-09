import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * GUI Class
 * Simple GUI window for a driver to confirm his Holiday request
 * The window displays the starting and ending date of the request
 */

public class ConfirmationGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private Driver driver;
  
  private JLabel lblWelcome = new JLabel("Hello <DriverName>"),
                 info = new JLabel("You have requested holiday "),
                 start_date = new JLabel("from <start_date>"),
                 end_date = new JLabel("to <end_date>");
       
  
  private JButton btnReturn = new JButton("Confirm");
  
  public ConfirmationGUI(Driver driver, Request request)
  {
	  this.driver = driver;
	  lblWelcome.setText("Hello " + driver.getName());
	  start_date.setText("from " + request.getStartDate());
	  end_date.setText("to " + request.getEndDate());
  }
  
  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
		
    contents.setLayout(new GridLayout(5, 1));
    
    contents.add(lblWelcome);
    contents.add(info);
    contents.add(start_date);
    contents.add(end_date);
    contents.add(btnReturn);
    
    btnReturn.addActionListener(this);
    
  }
 
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnReturn)
      driver.showWelcome();
  }
}  
