import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Jack Farrelly
 *
 * GUI Class
 * GUI that acts as a 'hub' for the application
 * From here the user can access a variety of separate windows
 * that each perform a basic task such as requesting holidays,
 * viewing holidays and viewing timetables
 */
public class TimetableGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private Driver driver;
  
  private JLabel lblWeekday   = new JLabel("Weekday"),
                 lblSaturday  = new JLabel("Saturday"),
                 lblSunday    = new JLabel("Sunday");
  
  private JButton btnBack = new JButton("Back");
	
	private String outputTxt[] = new String[3];
	
  public TimetableGUI(Driver _driver)
  {
    driver = _driver;
    
    int driverID = driver.getID();
    
    String folders[] = new String[3];
    folders[0] = "../roster/weekday/" + driverID;
    folders[1] = "../roster/saturday/" + driverID;
    folders[2] = "../roster/sunday/" + driverID;
    
    for(int i = 0; i < folders.length; i++)
    {
      BufferedReader br = null;
   
		  try
		  {
        br = new BufferedReader(new FileReader(folders[i]));
        
        String currentLine = "";
        outputTxt[i] = "";
        while((currentLine = br.readLine()) != null)
        {
          outputTxt[i] += currentLine + "\n";
				}
   
		  }
		  catch (IOException e)
		  {
			  e.printStackTrace();
		  }
		  finally
		  {
			  try
			  {
				  if(br != null)
				    br.close();
			  }
			  catch (IOException ex)
			  {
				  ex.printStackTrace();
			  }
		  }
		}
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(3, 3));
		
    contents.add(lblWeekday);
    contents.add(lblSaturday);
    contents.add(lblSunday);
    
    JTextArea ta = new JTextArea(outputTxt[0]);
    ta.setLineWrap(true);
    JScrollPane sbrText = new JScrollPane(ta);
    sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
    contents.add(sbrText);
    
    JTextArea ta2 = new JTextArea(outputTxt[1]);
    ta2.setLineWrap(true);
    JScrollPane sbrText2 = new JScrollPane(ta2);
    sbrText2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
    contents.add(sbrText2);
    
    JTextArea ta3 = new JTextArea(outputTxt[2]);
    ta3.setLineWrap(true);
    JScrollPane sbrText3 = new JScrollPane(ta3);
    sbrText3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
    contents.add(sbrText3);
    
    contents.add(btnBack);
    contents.add(new JLabel(""));
    contents.add(new JLabel(""));
    
    btnBack.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnBack)
      driver.showWelcome();
  }
}
