import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 * @author Anthony Glover
 *
 * GUI Class
 * Initial window for the application, provides a log in screen
 * for a driver. Driver uses his driver number as an ID
 */

public class LoginGUI extends Window implements ActionListener
{
  public Container pane;
  private JLabel idL = new JLabel("Driver Number"),
                 passwordL = new JLabel("Password"), 
                 loginText = new JLabel ("Please enter your details"), 
                 blank = new JLabel("");
  
  private JTextField idTF;
  private JPasswordField passwordTF;
  private JButton loginB, exitB, passengerB = new JButton("Passenger");
  
  public static MainGUI window;
	
  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    pane = window.getContentPane();
    pane.setLayout(new GridLayout(5, 2));
    
    pane.add(idL);
    idTF = new JTextField(10);
    pane.add(idTF);
    
    pane.add(passwordL);
    passwordTF = new JPasswordField(10);
    pane.add(passwordTF);
    
    pane.add(loginText);
    pane.add(blank);
    
    exitB = new JButton("Exit");
    exitB.addActionListener(this);
    pane.add(exitB);
    loginB = new JButton("Login");
    loginB.addActionListener(this);
    pane.add(loginB);
    
    passengerB.addActionListener(this);
    pane.add(passengerB);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == loginB)
    {
      try
      {
      	if(idTF.getText().equals("controller"))
		    {
			    LoginGUI.window.openWindow(new ControllerPanelGUI());
			
			    return;
		    }
      	
        Driver driver = new Driver(idTF.getText());
		
		    if(driver.checkPassword(new String(passwordTF.getPassword()) /*getText()*/))
          driver.showWelcome();
        else
          loginText.setText("Incorrect Login\n information");
      }
      catch(Exception ex)
      {
        loginText.setText("Incorrect Login\n information");
      }
    }
    else if(e.getSource() == exitB)
      System.exit(0);
    else if(e.getSource() == passengerB)
    {
      window.openWindow(new PassengerWelcomeGUI());
    }
  }
}
