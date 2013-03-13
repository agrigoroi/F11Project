import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.text.DateFormat;

/**
 * GUI Class
 * GUI window that displays all the holidays a driver has requested
 * as well as any remaining days he has left to use
 */

public class HolidayGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private Driver driver;
  private Request[] requests;
  
  private JLabel lblWelcome = new JLabel("Hello <DriverName>"),
                 holidays_left = new JLabel("<holidays_left>");
  private JLabel[] start_date, end_date;
  private JButton btnReturn = new JButton("Return"); 
  
   
  public HolidayGUI(Driver driver, Request requests[])
  {
    this.driver = driver;
    this.requests = requests;
    this.start_date = new JLabel[requests.length];
    this.end_date = new JLabel[requests.length];	  
    lblWelcome.setText("Hello " + driver.getName());
    holidays_left.setText(driver.getHolidaysLeft() + " days left");
    for(int i = 0; i < requests.length; i++)
    {
      start_date[i] = new JLabel(DateFormat.getDateInstance().format(requests[i].getStartDate()));
      end_date[i] = new JLabel(DateFormat.getDateInstance().format(requests[i].getEndDate()));		
    }
  }
  
  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
		
    contents.setLayout(new GridLayout(requests.length + 3, 2));
    
    contents.add(lblWelcome);
    contents.add(holidays_left);
    contents.add( new JLabel("Start Dates"));
    contents.add(new JLabel("End Dates"));
    for(int i=0; i< requests.length; i++)
    {
      contents.add(start_date[i]);
      contents.add(end_date[i]);
    }
    contents.add(btnReturn);
    
    btnReturn.addActionListener(this);
    
  }
 
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnReturn)
      window.openWindow(new WelcomeGUI(driver));
  }
 
} // HolidayGUI
