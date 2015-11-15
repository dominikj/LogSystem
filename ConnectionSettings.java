package LogClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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
	private ProgramLogic plog;
	public ConnectionSettings(ProgramLogic pl) {
		plog = pl;
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
		address.setText("127.0.0.1");
		panel.add(address);
		address.setColumns(10);
		
		port = new JTextField();
		port.setBounds(193, 76, 114, 19);
		port.setText("10799");
		panel.add(port);
		port.setColumns(10);
		
	    connect = new JButton("Połącz");
		connect.setBounds(164, 117, 117, 25);
		panel.add(connect);
		connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String _address = address.getText().trim();
				String _port = port.getText().trim();
				if (!(_address.isEmpty()) && !(_port.isEmpty())){
					dispose();
					plog.ipAddress = _address;
					plog.port = Integer.parseInt(_port);
					plog.connect();
					plog.getLogs();
				
		}
				
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);

	}
}
