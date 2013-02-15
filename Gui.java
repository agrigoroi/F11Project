import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Gui extends JFrame
{
	private static final int WIDTH = 350;
	private static final int HEIGHT = 200;
	
	private JLabel lengthL, widthL, loginText, blank;
	private JTextField lengthTF, widthTF;
	private JButton calculateB, exitB;
	
	//Button handlers:
	private CalculateButtonHandler cbHandler;
	private ExitButtonHandler ebHandler;
	
	public Gui()
	{
		lengthL = new JLabel("User ID", SwingConstants.LEFT);
		widthL = new JLabel("Password", SwingConstants.LEFT);
		loginText = new JLabel ("Please enter your details", SwingConstants.LEFT);
		blank = new JLabel ("", SwingConstants.LEFT);
		
		lengthTF = new JTextField(10);
		widthTF = new JTextField(10);

		//SPecify handlers for each button and add (register) ActionListeners to each button.
		calculateB = new JButton("Login");
		cbHandler = new CalculateButtonHandler();
		calculateB.addActionListener(cbHandler);
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		
		setTitle("Login");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(4, 2));
		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(lengthL);
		pane.add(lengthTF);
		pane.add(widthL);
		pane.add(widthTF);
		pane.add(loginText);
		pane.add(blank);
		pane.add(exitB);
		pane.add(calculateB);

		
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class CalculateButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
		  	String password = "Random String", inputPassword = widthTF.getText();
			int inputDriverID = 0;
			try
			{
				inputDriverID = Integer.parseInt(lengthTF.getText());
			}
			catch(Exception ex)
			{
			  			  loginText.setText("Wrong details");

			}
			try
			{			
        			password = DriverInfo.getPass(inputDriverID);
			      }
			      catch(Exception ex)
			      {
							  			  loginText.setText("Wrong details");
}										
			if (inputPassword.equals(password))
			  //do whatever
			  loginText.setText("Correct");
			else
			  			  			  loginText.setText("Wrong details");

			//If incorrect set this to wrong details
		}
	}
	
	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
	  	database.openBusDatabase();
		Gui Guibox = new Gui();
	}
	
}
