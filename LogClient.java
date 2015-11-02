package LogClient;

import javax.swing.SwingUtilities;

public class LogClient {

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {

			@Override
			public void run() {
				MainWindow win = new MainWindow();
				
			}
			
		});


	}

}
