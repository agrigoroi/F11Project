import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 * @author Jack Farrelly
 *
 * Controll GUI class
 * Provides interface for controller functions
 * 
 */
public class ControllerPanelGUI extends Window implements ActionListener
{
  private MainGUI window;
  private Container contents;
  
  protected static JButton  btnRoster		= new JButton("Create roster");
  private          JButton  btnDelays   = new JButton("Impose delays"),
                            btnLogout		= new JButton("Log out");
	
  public ControllerPanelGUI()
  {
    
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(5, 1));
		
    contents.add(btnRoster);
    contents.add(btnDelays);
    contents.add(btnLogout);
		
    btnRoster.addActionListener(this);
    btnDelays.addActionListener(this);
    btnLogout.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == btnRoster)
    {
      Runnable r = new Runnable()
      {
        public void run()
        {
          Roster roster = new Roster();
          roster.run();
          ControllerPanelGUI.unloading();
        }
      };
      
      ControllerPanelGUI.loading();
      Thread t = new Thread(r);
      t.start();
    }
    else if(e.getSource() == btnDelays)
    {
      window.openWindow(new ControllerPanelDelaysGUI());
    }
    else if(e.getSource() == btnLogout)
    {
      window.openWindow(new LoginGUI());
    }
  }
  
  protected static void loading()
  {
    ControllerPanelGUI.btnRoster.setText("Creating roster, please wait...");
    ControllerPanelGUI.btnRoster.setEnabled(false);
  }
  protected static void unloading()
  {
    ControllerPanelGUI.btnRoster.setText("Roster created!");
    ControllerPanelGUI.btnRoster.setEnabled(true);
  }
}
