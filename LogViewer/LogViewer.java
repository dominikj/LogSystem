package LogViewer;

import javax.swing.SwingUtilities;

public class LogViewer {

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {

			@Override
			public void run() {
				new ProgramLogic();
				
			}
			
		});


	}

}
