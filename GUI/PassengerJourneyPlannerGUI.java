import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class PassengerJourneyPlannerGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  protected static JButton btnBack = new JButton("Back"),
                  btnSubmit = new JButton("Submit");
  
  private JComboBox cmbTimeH, cmbTimeM;
  
  private JLabel lblFrom  = new JLabel("From:"),
                 lblTo = new JLabel("To:"),
                 lblTime = new JLabel("Time:");
	
  public PassengerJourneyPlannerGUI()
  {
    
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  
  private static int[] areasID;
  private JComboBox stopListFrom = new JComboBox();
  private JComboBox stopListTo   = new JComboBox();
  
  private static void populate(JComboBox stopList, int index)
  {
      int id = areasID[index];
      int[] stopsID = BusStopInfo.getBusStopsInArea(id);
      String[] stopsName = new String[stopsID.length];
      stopList.removeAllItems();
      for (int i=0; i<stopsID.length; i++)
      {
          stopsName[i] = BusStopInfo.getFullName(stopsID[i]);
          boolean shouldAdd = true;
          for (int j = 0; j < stopList.getItemCount(); j++)
              if (stopList.getItemAt(j).equals(stopsName[i]))
              {
                  shouldAdd = false;
                  break;
              }
          if (shouldAdd)
              stopList.addItem(stopsName[i]);
      }
  }
  
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(4, 3));
   
    areasID = BusStopInfo.getAreas();
    String[] areasName = new String[areasID.length];
    for (int i = 0; i < areasID.length; i++)
        areasName[i] = BusStopInfo.getAreaName(areasID[i]);
    final JComboBox areaListFrom = new JComboBox(areasName);
    final JComboBox areaListTo   = new JComboBox(areasName);
    stopListFrom = new JComboBox();
    stopListTo   = new JComboBox();

    populate(stopListFrom, 0);
    populate(stopListTo, 0);
 
    areaListFrom.addActionListener (new ActionListener () 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	  populate(stopListFrom, areaListFrom.getSelectedIndex());
         }
    });
    areaListTo.addActionListener (new ActionListener () 
    {
        public void actionPerformed(ActionEvent e) 
        {
        	  populate(stopListTo, areaListTo.getSelectedIndex());
         }
    });
    
   
    
    //from
    
    contents.add(lblFrom);
    contents.add(areaListFrom);
    contents.add(stopListFrom); 
    
    
    contents.add(lblTo);
    contents.add(areaListTo);
    contents.add(stopListTo); 
    
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
    btnSubmit.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnBack)
      window.back();
    else if(e.getSource() == btnSubmit)
    {
//    	PassengerJourneyPlannerGUI.loading();
        PassengerJourneyPlannerResultGUI result = new PassengerJourneyPlannerResultGUI((String) stopListFrom.getSelectedItem(), (String) stopListTo.getSelectedItem(), (String) cmbTimeH.getSelectedItem(), (String) cmbTimeM.getSelectedItem());
        window.openWindow(result);
//      PassengerJourneyPlannerGUI.unloading();
    }    
  }
  
  protected static void loading()
  {
    PassengerJourneyPlannerGUI.btnSubmit.setText("Planning route, please wait...");
    PassengerJourneyPlannerGUI.btnSubmit.setEnabled(false);
  }
  protected static void unloading()
  {
    PassengerJourneyPlannerGUI.btnSubmit.setText("Submit");
    PassengerJourneyPlannerGUI.btnSubmit.setEnabled(true);
  }
}
