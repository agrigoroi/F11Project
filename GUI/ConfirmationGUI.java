import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ConfirmationGUI extends Window implements ActionListener
{
  private MainGUI window;
  public Container contents;
  private Driver driver;
  
  private JLabel lblWelcome = new JLabel("Hello <DriverName>"),
                 info = new JLabel("You have requested holiday "),
                 start_date = new JLabel("from <start_date>"),
                 end_date = new JLabel("to <end_date>");
       
  
  private JButton btnReturn = new JButton("Confirm");
  
  public ConfirmationGUI(Driver driver)
  {
	  this.driver = driver;
  }
  
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
		
    contents.setLayout(new GridLayout(5, 1));
    
    contents.add(lblWelcome);
    contents.add(info);
    contents.add(start_date);
    contents.add(end_date);
    contents.add(btnReturn);
    
    btnReturn.addActionListener(this);
    
  }
 
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnReturn)
      driver.showWelcome();
  }
  
}  
