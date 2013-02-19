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
	
	private JLabel	lblWelcome			= new JLabel("Hello <DriverName>");
	private JButton	btnViewTimetable	= new JButton("View timetable"),
					btnSeeHolidays		= new JButton("See holidays"),
					btnRequestHoliday	= new JButton("Request holiday");
	
	public void show(MainGUI _window)
	{
		window = _window;
		contents = window.getContentPane();
		
		contents.setLayout(new GridLayout(4, 1));
		
		contents.add(lblWelcome);
		contents.add(btnViewTimetable);
		contents.add(btnSeeHolidays);
		contents.add(btnRequestHoliday);
		
		btnViewTimetable.addActionListener(this);
		btnSeeHolidays.addActionListener(this);
		btnRequestHoliday.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnViewTimetable)
			;
		else if(e.getSource() == btnSeeHolidays)
			;
		else if(e.getSource() == btnRequestHoliday)
			window.openWindow(new RequestHolidayGUI());
	}
}
