import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * @author Anthony Glover
 *
 * GUI Class
 * GUI that shows the passenger the timetable
 */
public class PassengerTimetableResultGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private JButton btnBack = new JButton("Back");
  private static String route, day;
  
  public PassengerTimetableResultGUI(String route, String day)
  {
    this.route = route;
    this.day = day;
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(2, 2));
    String output="";
    try{
    	File file = new File("../timetable/"+route+"_"+day);
    	BufferedReader in = new BufferedReader(new FileReader(file));
    	
    	String line = "";
    	
    	while((line = in.readLine()) != null){
    		output+=line;
    		output+="\n";
    	}
    	
    	in.close();
    }
    catch(Exception e){
    	System.out.println("could not output / read file");
    }
  
  
  JTextArea ta = new JTextArea(output);
  ta.setLineWrap(true);
  JScrollPane sbrText = new JScrollPane(ta);
  sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
  
  contents.add(sbrText);
  contents.add(btnBack);
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
