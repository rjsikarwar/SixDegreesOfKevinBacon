package com.nickblomberg.kevinbacon.exceptions;


import javax.swing.JOptionPane;

public class ActorNotFoundException extends Exception {

	private static final long serialVersionUID = 5315654124393680446L;

	public ActorNotFoundException(String message) {
		JOptionPane.showMessageDialog(null, message, "Acot not found", JOptionPane.WARNING_MESSAGE);
	}

}
