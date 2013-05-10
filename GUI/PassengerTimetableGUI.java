import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * @author Anthony Glover
 *
 * GUI Class
 * GUI that shows the passenger the timetable
 */
public class PassengerTimetableGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private JButton btnBack = new JButton("Back"),
  				btnSubmit= new JButton("Submit");
  private JComboBox routeSelected, typeOfDay;

  public PassengerTimetableGUI()
  {
    
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(4, 3));
    
    String[] routes = {"358back","358out","383","384"};
    String[] days = {"weekday","saturday","sunday"};
    

    //sets up the day dropdown box
    typeOfDay = new JComboBox(days);
    typeOfDay.setSelectedIndex(2);
    typeOfDay.addActionListener(this);
    //Sets up the route dropdown
    routeSelected = new JComboBox(routes);
    routeSelected.setSelectedIndex(3);
    routeSelected.addActionListener(this);
    
    contents.add(routeSelected);
    contents.add(typeOfDay);
    contents.add(btnBack);
    btnBack.addActionListener(this);
    contents.add(btnSubmit);
    btnSubmit.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnBack)
      window.back();
    else if(e.getSource() == btnSubmit){
      String routess = (String)routeSelected.getSelectedItem();
      String typeOfDayss = (String)typeOfDay.getSelectedItem();
      PassengerTimetableResultGUI result = new PassengerTimetableResultGUI(routess, typeOfDayss);
      window.openWindow(result);
    }
  }
}
