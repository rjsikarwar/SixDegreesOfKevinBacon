

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * The purpose of this class is to read in data from the imdb text file,
 * and to parse it into the data structure. Actors are stored in a hash table
 * where this key is the actor's name, and the value is an Actor object. Each
 * actor object holds a list of movies in which the actor has appeared.
 * 
 * @author Nick Blomberg (nib8)
 * @version 1.2
 */
public class FileParser {
	
	private BufferedReader in;
	private static final String IMDB_FILE = "cast.txt";
	private Map<String,Actor> actors;
	
	public FileParser() {
		try {
			in = new BufferedReader(new FileReader(IMDB_FILE), 32768);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "The imdb file could not be found", "File not found", JOptionPane.ERROR_MESSAGE);
		}
		actors = new Hashtable<String, Actor>(1713251);
	}
	
	/**
	 * Method to read in data one line at a time, and only breaks if there are no 
	 * more lines to be read. Each line is split into tokens, which are then passed
	 * to the preocessLine method.
	 * 
	 * @return A hash table of all actors, where key is actor name, value is actor object.
	 * @see processLine()
	 */
	public Map<String, Actor> readLines() {
 		String line="";
		
		while(true){
			try {
				line = in.readLine();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "The imdb file was not formatted correctly", "Error reading file", JOptionPane.ERROR_MESSAGE);
			}
			
			if(line == null){ break; }
			
			String[] tokens = line.split("/");	
			processLine(tokens);
		}
		try{ in.close(); } catch(IOException e){}
		return actors;
	}
	
	/** 
	 * This method takes the tokens from a single line, and parses it into the data structure.
	 * The first token is always a movie, so a Movie object is made from it. Each of the remaining
	 * tokens is the name of an Actor, which is used to make an Actor object. For each actor, the hash
	 * table is checked for an existing entry. If the actors is already present, the current object is 
	 * updated, otherwise a new actor object is made and added to the hash table.
	 * 
	 * @param tokens A line from the text file split into individual data items.
	 * @see readLine()
	 */
	public void processLine(String[] tokens){
		Movie m = new Movie(tokens[0]);
		
		for(int i = 1;i < tokens.length;i++){
			
			Actor a;
			String actorName = tokens[i];
			
			if(actors.containsKey(actorName)){			
				a = actors.get(actorName);
			} else {
				a = new Actor(actorName);
				actors.put(actorName, a);
			}
			m.addActor(a);
			a.addMovie(m);
		}
	}	
	
}
