import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Jack Farrelly
 *
 * GUI Class
 * GUI that acts as a 'hub' for the application
 * From here the user can access a variety of separate windows
 * that each perform a basic task such as requesting holidays,
 * viewing holidays and viewing timetables
 */
public class WelcomeGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private Driver driver;
	
  private JLabel   lblWelcome		= new JLabel("Hello <DriverName>");
  private JButton  btnViewTimetable	= new JButton("View timetable"),
		   btnSeeHolidays	= new JButton("See holidays"),
		   btnRequestHoliday	= new JButton("Request holiday"),
	    	   btnLogout		= new JButton("Log Out");
	
  public WelcomeGUI(Driver driver)
  {
    this.driver = driver;
    lblWelcome.setText("Hello " + driver.getName());
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
    contents.add(btnViewTimetable);
    contents.add(btnSeeHolidays);
    contents.add(btnRequestHoliday);
    contents.add(btnLogout);
		
    btnViewTimetable.addActionListener(this);
    btnSeeHolidays.addActionListener(this);
    btnRequestHoliday.addActionListener(this);
    btnLogout.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    //open up a timetable for the driver
    if(e.getSource() == btnViewTimetable)
      driver.showTimetable();
    //open up a window that displays a drivers current holidays
    else if(e.getSource() == btnSeeHolidays)
      driver.showHolidays();
    //open up a window to request more holidays
    else if(e.getSource() == btnRequestHoliday)
      window.openWindow(new RequestHolidayGUI(driver));
    //Log the driver out
    else if(e.getSource() == btnLogout)
    {
      driver = null;
      window.openWindow(new LoginGUI());
    }
  }
}
