

import javax.swing.JOptionPane;

public class EmptyQueueException extends Throwable {
	
	private static final long serialVersionUID = -1412159655760584252L;

	public EmptyQueueException(String message) {
		JOptionPane.showMessageDialog(null, message, "Critical error", JOptionPane.ERROR_MESSAGE);
	}
	
}
