package LogClient;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ConnectionSettings extends JFrame {
	private JTextField address;
	private JTextField port;
	private JButton connect;
	
	public ConnectionSettings() {
		setResizable(false);
		setAlwaysOnTop(true);
		getContentPane().setLayout(null);
		setSize(300, 200);
		
		setTitle("Connection settings");
		JPanel panel = new JPanel();
		panel.setBounds(-55, 0, 488, 367);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAdres = new JLabel("Adres:");
		lblAdres.setBounds(105, 34, 70, 15);
		panel.add(lblAdres);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(105, 78, 70, 15);
		panel.add(lblPort);
		
		address = new JTextField();
		address.setBounds(193, 32, 114, 19);
		panel.add(address);
		address.setColumns(10);
		
		port = new JTextField();
		port.setBounds(193, 76, 114, 19);
		panel.add(port);
		port.setColumns(10);
		
	    connect = new JButton("Połącz");
		connect.setBounds(164, 117, 117, 25);
		panel.add(connect);
		setVisible(true);

	}
}
