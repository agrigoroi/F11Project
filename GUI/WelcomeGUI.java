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
public class WelcomeGUI extends Window implements ActionListener
{
	private MainGUI window;
	public Container contents;
	private Driver driver;
	
	private JLabel	lblWelcome		= new JLabel("Hello <DriverName>");
	private JButton	btnViewTimetable	= new JButton("View timetable"),
			btnSeeHolidays		= new JButton("See holidays"),
			btnRequestHoliday	= new JButton("Request holiday"),
	    		btnLogout		= new JButton("Log Out");
	
	public WelcomeGUI(Driver driver)
	{
		this.driver = driver;
		lblWelcome.setText("Hello " + driver.getName());
	}

	public void show(MainGUI _window)
	{
		window = _window;
		contents = window.getContentPane();
		
		contents.setLayout(new GridLayout(4, 1));
		
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
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnViewTimetable)
			;
		else if(e.getSource() == btnSeeHolidays)
			driver.showHolidays();
		else if(e.getSource() == btnRequestHoliday)
			window.openWindow(new RequestHolidayGUI(driver));
		else if(e.getSource() == btnLogout)
		{
		  	driver = null;
		  	window.openWindow(new LoginGUI());
		}
	}
}
