package LogViewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	public JTable table;
	public JButton send;
	public JLabel info;
	public JComboBox chooseEvent;
	public ProgramLogic PrHandle;
	public DefaultTableModel modelTable = new DefaultTableModel();
	public MainWindow(ProgramLogic PrHand) {
		PrHandle = PrHand;
		setTitle("Log Client");
		setResizable(false);
		setSize(571, 249);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable(modelTable);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(12, 43, 547, 55);
		scroll.setEnabled(false);
		panel.add(scroll);
		
		send = new JButton("Wyślij ");
		send.setBounds(420, 110, 117, 25);
		send.setEnabled(false);
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrHandle.connect();
				PrHandle.sendEvent((String) chooseEvent.getSelectedItem());
				
			}
		});
		panel.add(send);
		
		JLabel lblNewLabel = new JLabel("Zdarzenie:");
		lblNewLabel.setBounds(39, 115, 105, 15);
		panel.add(lblNewLabel);
		
		chooseEvent = new JComboBox();
		chooseEvent.setBounds(42, 130, 137, 25);
		chooseEvent.setEnabled(false);
		panel.add(chooseEvent);
		chooseEvent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String tmp =(String) chooseEvent.getSelectedItem();
				for(ArrayList<String> s: PrHandle.logs ){
					if(s.get(0).equals(tmp)){
						modelTable.setColumnCount(0);
						modelTable.setRowCount(0);
						for(int i = 1; i < s.size(); ++i)
							 modelTable.addColumn(s.get(i));
						modelTable.addColumn("kod przyczyny");
						modelTable.addColumn("Przewidywana groźność");
						modelTable.addRow(new Object[] { "<nowe zdarzenie>" });
						break;
					}
				}
				chooseEvent.setEnabled(true);
				
			}
		});
		
	    info = new JLabel("Nie jesteś połączony");
		info.setBounds(42, 167, 187, 15);
		panel.add(info);
		JButton connectionDialog = new JButton("Serwer");
		connectionDialog.setBounds(420, 147, 117, 25);
		connectionDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PrHandle.runConSetWin();
			}
		});
		panel.add(connectionDialog);

	}
}
