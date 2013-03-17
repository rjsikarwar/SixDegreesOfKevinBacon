

import java.util.Vector;

/**
 * Concrete class to represent actors. Each actor object holds an vector
 * of movies in which they have appeared. Actor objects contain three other 
 * important fields: one to hold their bacon number, one to act as a visited 
 * flag when searching and another to keep track of the previous actor in the 
 * path.
 * 
 * @author  Nick Blomberg (nib8)
 * @version  1.0
 */
public class Actor {

	private String name;
	private Vector<Movie> appearances;
	private int baconNumber;
	private boolean visited;
	private Actor prev;
	
	/**
	 * Constructor to set the name of the actor upon creation.
	 * 
	 * @param name The name of the actor.
	 */
	public Actor(String name){
		this.name = name;
		appearances = new Vector<Movie>();
	}

	public String getName() {
		return name;
	}
	
	public void addMovie(Movie m){
		appearances.add(m);
	}

	public Vector<Movie> getMovieAppearances() {
		return appearances;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public int getBaconNumber() {
		return baconNumber;
	}

	public void setBaconNumber(int baconNumber) {
		this.baconNumber = baconNumber;
	}

	public Actor getPrev() {
		return prev;
	}

	public void setPrev(Actor prev) {
		this.prev = prev;
	}

}
