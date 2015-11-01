package LogClient;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private JTable table;
	public MainWindow() {
		setTitle("Log Client");
		setSize(426, 331);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(12, 12, 402, 200);
		panel.add(table);
		
		JButton send = new JButton("Wyślij ");
		send.setBounds(283, 219, 117, 25);
		panel.add(send);
		
		JLabel lblNewLabel = new JLabel("Zdarzenie:");
		lblNewLabel.setBounds(38, 224, 105, 15);
		panel.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(32, 239, 137, 25);
		panel.add(comboBox);
		
		JLabel label = new JLabel("New label");
		label.setBounds(42, 276, 187, 15);
		panel.add(label);
		
		JButton connectionDialog = new JButton("Serwer");
		connectionDialog.setBounds(283, 256, 117, 25);
		panel.add(connectionDialog);

	}
}
