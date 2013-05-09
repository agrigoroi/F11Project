import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * @author
 *
 * GUI Class
 * Initial window for the application, provides a log in screen
 * for a driver. Driver uses his driver number as an ID
 */

public class PassengerJourneyPlannerResultGUI extends Window implements ActionListener
{
  public Container pane;
  private JLabel lblOutput = new JLabel("");
  private JButton btnBack = new JButton("Back");
  
  public static MainGUI window;
  
  String to, from, hours, minutes;
  
  public PassengerJourneyPlannerResultGUI(String _to, String _from, String _hours, String _minutes)
  {
    to = _to;
    from = _from;
    hours = _hours;
    minutes = _minutes;
    
    /*
    hours = hours departing
    minutes = minutes departing
    
    not sure how you implement them in your code so you have to put them in your dijkstra's algorithm
    */
  }
	
  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    pane = window.getContentPane();
    pane.setLayout(new GridLayout(2, 2));
    
    database.openBusDatabase();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		
		ArrayList<Journey> journeys = JourneyPlanner.dijkstra(to, from, new Date());
		String output = "<html>";
		for(Journey journey: journeys)
		{
			output += "Take bus " + journey.getService().getRoute().getName() +" from " + BusStopInfo.getFullName(journey.getDepartBusStop()) + " to " + BusStopInfo.getFullName(journey.getArrivalBusStop()) + "<br>";
			output += "Leaves at " + simpleDateFormat.format(journey.getDepartTime()) + " and arrives at " +simpleDateFormat.format(journey.getArrivalTime())+"<br><br>";
		}
    output += "</html>";
    
    lblOutput.setText(output);
    
    pane.add(lblOutput);
    
    pane.add(btnBack);
    btnBack.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnBack)
      window.back();
  }
}
