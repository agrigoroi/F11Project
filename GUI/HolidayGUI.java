import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HolidayGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;  
  
  private JLabel lblWelcome = new JLabel("Hello <DriverName>"),
                 start_date = new JLabel("<start_date>"),
                 end_date = new JLabel("<end_date>"),
                 holidays_left = new JLabel("<holidays_left>");
       
  
  private JButton btnReturn = new JButton("Return");
  
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
		
    contents.setLayout(new GridLayout(5, 1));
    
    contents.add(lblWelcome);
    contents.add(start_date);
    contents.add(end_date);
    contents.add(holidays_left);
    contents.add(btnReturn);
    
    btnReturn.addActionListener(this);
    
  }
 
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnReturn)
      window.openWindow(new WelcomeGUI());
  }
 
} // HolidayGUI
