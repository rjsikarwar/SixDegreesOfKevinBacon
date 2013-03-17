

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * A simple button listener class to handle input
 * from the MainWindow.
 * 
 * @author Nick Blomberg (nib8)
 * @version 1.0
 */
public class ButtonListener implements ActionListener {

	private KevinBaconApp frame;
	
	public ButtonListener(KevinBaconApp frame){
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent evt) {
		String actionCommand = evt.getActionCommand();
		
		if(actionCommand.equals("Search")){
			try {
				frame.performSearch();
			} catch (ActorNotFoundException e) {} 
			  catch (EmptyQueueException e) {}
		}
	}
}
