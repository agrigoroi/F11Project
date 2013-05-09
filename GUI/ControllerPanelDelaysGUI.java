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
public class ControllerPanelDelaysGUI extends Window implements ActionListener
{
  private MainGUI window;
  private Container contents;
  
  private JComboBox cmbRoutes, cmbServices;
  
  private JTextField txtDelay = new JTextField();
  
  private JButton btnSubmit = new JButton("Submit"), btnBack = new JButton("Back");
  
  private int[][] serviceIDs;
  private String[] routeNames;
	
  public ControllerPanelDelaysGUI()
  {
    
  }

  /**
   * Shows the GUI window and adds the labels and buttons
   */
  public void show(MainGUI _window)
  {
    window = _window;
    contents = window.getContentPane();
    contents.setLayout(new GridLayout(4, 2));
		
    Route[] routes = Route.getAll();
    
    int[] routeIDs = new int[routes.length];
    /*int[][]*/ serviceIDs = new int[routes.length][];
    
    for(int i = 0; i < routes.length; i++)
      routeIDs[i] = routes[i].getID();
    
    for(int i = 0; i < routes.length; i++)
    {
      Service[] services = routes[i].getServices(TimetableInfo.timetableKind.weekday);
      serviceIDs[i] = new int[services.length];
      
      for(int j = 0; j < services.length; j++)
        serviceIDs[i][j] = services[j].getID();
    }
    
    /*String[]*/ routeNames = new String[routes.length];
    
    for(int i = 0; i < routes.length; i++)
      routeNames[i] = routes[i].getName();
    
    cmbRoutes = new JComboBox();
    cmbRoutes.addItem("Please select...");
    for(int i = 0; i < routes.length; i++)
      cmbRoutes.addItem(routeNames[i]);
    
    cmbRoutes.addActionListener(this);
    
    cmbServices = new JComboBox();
    
    contents.add(new JLabel("Route:"));
    contents.add(cmbRoutes);
    contents.add(new JLabel("Service:"));
    contents.add(cmbServices);
    contents.add(new JLabel("Delay (mins):"));
    contents.add(txtDelay);
    contents.add(btnBack);
    contents.add(btnSubmit);
    
    btnSubmit.addActionListener(this);
    btnBack.addActionListener(this);
  }
	
  /**
   * If a button has been pressed, do the appropriate response
   */
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == cmbRoutes)
    {
      cmbServices.removeAllItems();
      
      String routeName = (String) cmbRoutes.getSelectedItem();
      
      int routeID = -1;
      
      for(int i = 0; i < routeNames.length; i++)
        if(routeName.equals(routeNames[i]))
          routeID = i;
      
      if(routeID == -1)
        return;
      
      for(int i = 0; i < serviceIDs[routeID].length; i++)
        cmbServices.addItem(serviceIDs[routeID][i]);
    }
    else if(e.getSource() == btnSubmit)
    {
      
    }
    else if(e.getSource() == btnBack)
      window.back();
  }
}
