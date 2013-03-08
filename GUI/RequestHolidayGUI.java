import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import com.toedter.calendar.*;


/**
 * Class
 *
 * @author Alex Davies
 * @author Alex Grigoroi
 */
public class RequestHolidayGUI extends Window implements ActionListener
{
  public Container contents;
  private Driver driver;
	
  private JLabel lblWelcome = new JLabel("Welcome"),
                 lblError = new JLabel(""),
                 lblStart = new JLabel("Holiday Starting Date"),
                 lblEnd = new JLabel("Holiday Ending Date)"),
                 lblRemaining = new JLabel("You have 0 days" +
                                           "remaining"),
                 lblSelected = new JLabel("You have selected 0 days");
  
  //private JTextField txtStart, txtEnd;
  private JCalendar calStart =new JCalendar(), calEnd = new JCalendar();
  
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
	// Create new window
    contents = window.getContentPane();
    // Set the layout to Grid Bag Layout
    contents.setLayout(new GridBagLayout());
    // Set the layout constraints
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weighty = 0.2;
    
    // Add the welcome message
    c.weightx = 0.33;
    c.gridx = 0;
    c.gridy = 0;
    contents.add(lblWelcome, c);
    
    // Add the error message
    c.weightx = 0.66;
    c.gridx = 1;
    c.gridy = 0;
    contents.add(lblError, c);
    
    // Set weight x back to normal
    c.weightx = 0.5;
    
    // Add the text for startDate
    c.gridx = 0;
    c.gridy = 1;
    contents.add(lblStart, c);
    
    // Add the text for startDate
    c.gridx = 1;
    c.gridy = 1;
    contents.add(lblEnd, c);
    
    // Add the calendars. They should take more space
    c.weighty = 0.4;
    
    // Add calendar for start date
    c.gridx = 0;
    c.gridy = 2;
    contents.add(calStart, c);
    
    // Add calendar for end date
    c.gridx = 1;
    c.gridy = 2;
    contents.add(calEnd, c);
    
    // Change weight y back
    c.weighty = 0.2;

    // Add the number of remaining days
    c.gridx = 0;
    c.gridy = 3;
    contents.add(lblRemaining, c);
    
    // Add the number of selected days
    c.gridx = 1;
    c.gridy = 3;
    contents.add(lblSelected, c);
    
    // Add one button
    c.gridx = 0;
    c.gridy = 4;
    contents.add(btnVerify, c);
    btnVerify.addActionListener(this);
    
    // Add the other button
    c.gridx = 1;
    c.gridy = 4;
    contents.add(btnBack, c);
    btnBack.addActionListener(this);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnVerify)
    {
      try
      {
    	  Request request = new Request(calStart.getDate(), calEnd.getDate(), driver);
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
