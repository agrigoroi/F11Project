import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginGUI extends Window
{
	private static final int WIDTH = 350;
	private static final int HEIGHT = 200;
	
	private JLabel idL, passwordL, loginText, blank;
	private JTextField idTF, passwordTF;
	private JButton loginB, exitB;
	
	//Button handlers:
	private LoginButtonHandler loginHandler;
	private ExitButtonHandler ebHandler;
	
	public static MainGUI window;
	
	public void show(MainGUI _window)
	{
		window = _window;
		
		idL = new JLabel("User ID", SwingConstants.LEFT);
		passwordL = new JLabel("Password", SwingConstants.LEFT);
		loginText = new JLabel ("Please enter your details", SwingConstants.LEFT);
		blank = new JLabel ("", SwingConstants.LEFT);
		
		idTF = new JTextField(10);
		passwordTF = new JTextField(10);

		//SPecify handlers for each button and add (register) ActionListeners to each button.
		loginB = new JButton("Login");
		loginHandler = new LoginButtonHandler();
		loginB.addActionListener(loginHandler);
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		
		//setTitle("Login");
		Container pane = window.getContentPane();
		pane.setLayout(new GridLayout(4, 2));
		
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(idL);
		pane.add(idTF);
		pane.add(passwordL);
		pane.add(passwordTF);
		pane.add(loginText);
		pane.add(blank);
		pane.add(exitB);
		pane.add(loginB);
	}
	
	private class LoginButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(Driver.checkDetails(idTF.getText(), passwordTF.getText()))
				LoginGUI.window.openWindow(new WelcomeGUI());
			else
				loginText.setText("Wrong details");
		}
	}
	
	public class ExitButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
}
