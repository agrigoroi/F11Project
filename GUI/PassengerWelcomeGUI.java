import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @author Jack Farrelly
 *
 * GUI Class
 * GUI that acts as a 'hub' for the application
 * From here the user can access a variety of separate windows
 * that each perform a basic task such as requesting holidays,
 * viewing holidays and viewing timetables
 */
public class PassengerWelcomeGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
	
  private JLabel   lblWelcome		= new JLabel("Hello passenger");
  private JButton  btnPlanJourney	= new JButton("Plan journey"),
		   btnViewTimetable	= new JButton("View timetable"),
		   btnExit		= new JButton("Exit");
	
  public PassengerWelcomeGUI()
  {
    
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(4, 1));
		
    contents.add(lblWelcome);
    contents.add(btnPlanJourney);
    contents.add(btnViewTimetable);
    contents.add(btnExit);
		
    btnPlanJourney.addActionListener(this);
    btnViewTimetable.addActionListener(this);
    btnExit.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    //open up a timetable for the driver
    if(e.getSource() == btnPlanJourney)
      window.openWindow(new PassengerJourneyPlannerGUI());
    //open up a window that displays a drivers current holidays
    else if(e.getSource() == btnViewTimetable)
      window.openWindow(new PassengerTimetableGUI());
    //Log the driver out
    else if(e.getSource() == btnExit)
    {
      window.openWindow(new LoginGUI());
    }
  }
}
