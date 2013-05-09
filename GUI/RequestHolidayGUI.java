import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * @author Alex Davies
 *
 * GUI Class
 * GUI window for a driver to request holidays.
 * It displays the current number of holidays remaining
 * as well as the number of days currently selected.
 */
public class RequestHolidayGUI extends Window implements ActionListener
{
  public Container contents;
  private Driver driver;
	
  private JLabel lblWelcome = new JLabel("Welcome"),
                 lblError = new JLabel(""),
                 lblStart = new JLabel("Holiday Starting Date (dd/mm/yyyy)"),
                 lblEnd = new JLabel("Holiday Ending Date (dd/mm/yyyy)"),
                 lblRemaining = new JLabel("You have 0 days" +
                                           "remaining"),
                 lblSelected = new JLabel("You have selected 0 days");
  
  private JTextField txtStart, txtEnd;
  
  private JButton btnVerify = new JButton("Verify Dates");
  private JButton btnBack = new JButton("Back");
  
  public RequestHolidayGUI(Driver driver)
  {
    this.driver = driver;
    lblWelcome.setText("Hello " + driver.getName());
    lblRemaining.setText("You have " + driver.getHolidaysLeft() +
	                 " days remaining");
  }
	
  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI window)
  {
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(5, 2));
    contents.add(lblWelcome);
    contents.add(lblError);
    contents.add(lblStart);
    contents.add(lblEnd);
    
    txtStart = new JTextField(10);
    contents.add(txtStart);
    txtEnd = new JTextField(10);
    contents.add(txtEnd);
    
    contents.add(lblRemaining);
    contents.add(lblSelected);
    
    contents.add(btnVerify);
    btnVerify.addActionListener(this);
    contents.add(btnBack);
    btnBack.addActionListener(this);
  }
  
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    //Verify the input and then open a confirmation window if successful
    if(e.getSource() == btnVerify)
    {
      try
      {
    	  Request request = new Request(txtStart.getText(), txtEnd.getText(), driver);
    	  lblSelected.setText("You have selected " + request.getLength() + " days");
    	  lblError.setText("Successful Verification");
    	  request.save();
    	  MainGUI.window.openWindow(new ConfirmationGUI(driver, request));
      }
      //catch date format errors
      catch(java.text.ParseException exception)
      {
    	  lblError.setText("There was an error with the dates input,\n "
	                     + "please make sure the dates \n"
	                     + "are in the correct format");
    	  System.out.println(exception);
      }
      //catch any unexpected errors
      catch(Exception exception)
      {
    	  lblError.setText(exception.getMessage());
      }
    }
    //Open up previous window
    else if(e.getSource() == btnBack)
      driver.showWelcome();
  }
}
