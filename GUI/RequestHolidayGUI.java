import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;


/**
 * Class
 *
 * @author Alex Davies
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
  
  public void actionPerformed(ActionEvent e)
  {
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
      catch(java.text.ParseException exception)
      {
    	  lblError.setText("There was an error with the dates input, "
	                     + "please make sure the dates are in the correct " 
	                     + "format");
    	  System.out.println(exception);
      }
      catch(Exception exception)
      {
    	  lblError.setText(exception.getMessage());
      }
    }
    else if(e.getSource() == btnBack)
      
      driver.showWelcome();
  }
}
