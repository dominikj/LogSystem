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
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

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
		setSize(580, 379);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable(modelTable);
		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelTable);
	    table.setRowSorter(sorter);
	    
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(12, 43, 547, 168);
		scroll.setEnabled(false);
		panel.add(scroll);
		
		send = new JButton("Usuń ");
		send.setBounds(420, 251, 117, 25);
		send.setEnabled(false);
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row;
				String log =(String) chooseEvent.getSelectedItem();
				if (( row = table.getSelectedRow()) != -1){
					String id = (String) modelTable.getValueAt(row,0);
				PrHandle.deleteEvent(log, id);
				}	
			}
		});
		panel.add(send);
		
		JLabel lblNewLabel = new JLabel("Zdarzenie:");
		lblNewLabel.setBounds(39, 223, 105, 15);
		panel.add(lblNewLabel);
		
		chooseEvent = new JComboBox();
		chooseEvent.setBounds(39, 251, 137, 25);
		chooseEvent.setEnabled(false);
		panel.add(chooseEvent);
		chooseEvent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseSelector();
				
			}
		});
		
	    info = new JLabel("Nie jesteś połączony");
		info.setBounds(39, 303, 187, 15);
		panel.add(info);
		JButton connectionDialog = new JButton("Serwer");
		connectionDialog.setBounds(420, 298, 117, 25);
		connectionDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				PrHandle.runConSetWin();
			}
		});
		panel.add(connectionDialog);

	}
	void chooseSelector(){
		String tmp =(String) chooseEvent.getSelectedItem();
		for(ArrayList<String> s: PrHandle.logs ){
			if(s.get(0).equals(tmp)){
				modelTable.setColumnCount(0);
				modelTable.setRowCount(0);
				modelTable.addColumn("ID");
				for(int i = 1; i < s.size(); ++i)
					 modelTable.addColumn(s.get(i));
				modelTable.addColumn("data i czas");
				modelTable.addColumn("kod przyczyny");
				modelTable.addColumn("Przewidywana groźność");
				ArrayList<ArrayList<String>> list = PrHandle.getLog(tmp);
				for(ArrayList<String> r : list){
					Object[] obj  = new Object[r.size()];
					obj[0] = r.get(0).split(" ")[1];
					for(int i = 1; i < r.size(); ++i){
						obj[i] = r.get(i);
					}
						
						modelTable.addRow(obj);
				}
				
				break;
			}
		}
		chooseEvent.setEditable(false);
		chooseEvent.setEnabled(true);
	}
}
