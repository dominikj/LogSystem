package LogClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private JTable table;
	private JButton send;
	private JLabel info;
	private JComboBox chooseEvent;
	private ProgramLogic PrHandle;
	public MainWindow(ProgramLogic PrHand) {
		PrHandle = PrHand;
		setTitle("Log Client");
		setResizable(false);
		setSize(426, 331);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(12, 12, 402, 200);
		table.setEnabled(false);
		panel.add(table);
		
		send = new JButton("Wyślij ");
		send.setBounds(283, 219, 117, 25);
		send.setEnabled(false);
		panel.add(send);
		
		JLabel lblNewLabel = new JLabel("Zdarzenie:");
		lblNewLabel.setBounds(38, 224, 105, 15);
		panel.add(lblNewLabel);
		
		chooseEvent = new JComboBox();
		chooseEvent.setBounds(32, 239, 137, 25);
		chooseEvent.setEnabled(false);
		panel.add(chooseEvent);
		
	    info = new JLabel("Nie jesteś połączony");
		info.setBounds(42, 276, 187, 15);
		panel.add(info);
		
		JButton connectionDialog = new JButton("Serwer");
		connectionDialog.setBounds(283, 256, 117, 25);
		connectionDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PrHandle.runConSetWin();
			}
		});
		panel.add(connectionDialog);

	}
}
