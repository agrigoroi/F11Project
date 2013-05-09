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
 * @author Jack Farrelly
 *
 * GUI Class
 * GUI that acts as a 'hub' for the application
 * From here the user can access a variety of separate windows
 * that each perform a basic task such as requesting holidays,
 * viewing holidays and viewing timetables
 */
public class PassengerTimetableGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private JButton btnBack = new JButton("Back"),
                  btnSubmit = new JButton("Submit");
  
  private JComboBox cmbFrom, cmbTo, cmbTimeH, cmbTimeM;
  
  private JLabel lblFrom  = new JLabel("From:"),
                 lblTo = new JLabel("To:"),
                 lblTime = new JLabel("Time:");
	
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
    
    //build stops list
    ArrayList<String> stopNames = new ArrayList<String>();
    
    int[] routes = BusStopInfo.getRoutes();
    for(int i = 0; i < routes.length; i++)
    {
      int[] stops = BusStopInfo.getBusStops(routes[i]);
      
      for(int j = 0; j < stops.length; j++)
        stopNames.add(BusStopInfo.getFullName(stops[j]));
    }
    
    //remove duplicates
    HashSet<String> hs = new HashSet<String>();
    hs.addAll(stopNames);
    stopNames.clear();
    stopNames.addAll(hs);
    
    String[] stopsList = new String[stopNames.size()];
    stopsList = stopNames.toArray(stopsList);
    
    //from
    cmbFrom = new JComboBox(stopsList);
    
    contents.add(lblFrom);
    contents.add(cmbFrom);
    contents.add(new JLabel()); //filler
    
    //to
    cmbTo = new JComboBox(stopsList);
    
    contents.add(lblTo);
    contents.add(cmbTo);
    contents.add(new JLabel()); //filler
    
    //time
    String[] hours = new String[24], minutes = new String[60];
    
    for(int i = 0; i < 24; i++)
      hours[i] = (i < 10) ? "0" + i : Integer.toString(i);
    
    for(int i = 0; i < 60; i++)
      minutes[i] = (i < 10) ? "0" + i : Integer.toString(i);
    
    contents.add(lblTime);
    
    DateFormat format = new SimpleDateFormat("HH:mm");
    String date = format.format(new Date());
    
    String[] time = date.split(":");
    
    cmbTimeH = new JComboBox(hours);
    cmbTimeH.setSelectedIndex(Integer.parseInt(time[0]));
    cmbTimeM = new JComboBox(minutes);
    cmbTimeM.setSelectedIndex(Integer.parseInt(time[1]));
    
    contents.add(cmbTimeH);
    contents.add(cmbTimeM);
    
    contents.add(btnBack);
    btnBack.addActionListener(this);
    contents.add(new JLabel()); //filler
    contents.add(btnSubmit);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnBack)
      window.back();
    else if(e.getSource() == btnSubmit)
      ;//...
  }
}
