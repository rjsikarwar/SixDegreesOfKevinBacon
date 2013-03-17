

import java.util.Vector;

/**
 * Concrete class to hold information about a movie. Each Movie 
 * object holds an vector of objects representing  actors who 
 * appeared in that Movie.
 * 
 * @author  Nick Blomberg (nib8)
 * @version  1.0
 */
public class Movie {

	private String title;
	private Vector<Actor> cast;
	private boolean visited;
	
	/**
	 * Constructor to set the movies title on creation.
	 * 
	 * @param title The title of the movie.
	 */
	public Movie(String title){
		this.title = title;
		cast = new Vector<Actor>();
	}
	
	public String getTitle() {
		return title;
	}
		
	public void addActor(Actor a){
		cast.add(a);
	}
	
	public Vector<Actor> getCast(){
		return cast;
	}
	
	public void setVisited(boolean visited){
		this.visited = visited;
	}
	
	public boolean isVisited(){
		return visited;
	}
}
