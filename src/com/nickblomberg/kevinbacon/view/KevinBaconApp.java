package com.nickblomberg.kevinbacon.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.nickblomberg.kevinbacon.data.FileParser;
import com.nickblomberg.kevinbacon.exceptions.ActorNotFoundException;
import com.nickblomberg.kevinbacon.exceptions.EmptyQueueException;
import com.nickblomberg.kevinbacon.model.Actor;
import com.nickblomberg.kevinbacon.model.Movie;
import com.nickblomberg.kevinbacon.search.Search;


/**
 * This class creates the GUI which is presented to the user. The window
 * is of a fixed size to accommodate for bacon numbers up to 8. This may 
 * need to be changed if a higher number is found. Results from searches are
 * grouped into a JPanel within the main frame so that they can be positioned
 * more easily.
 * 
 * @author Nick Blomberg (nib8)
 * @version 1.1
 */
public class KevinBaconApp{
	
	private Map<String, Actor> actors;
	
	private JFrame frame;
	private JPanel resultPanel;
	
	private JTextField searchName;
	private JLabel loadTime, totalActors, findTime, baconNumber;
	
	public KevinBaconApp() {
		actors = new Hashtable<String, Actor>(1713251);
		loadTime = new JLabel("");
		
		setup();	//Called to build the data structure.
		
		//Create the main window.
		frame = new JFrame();
		frame.setTitle("The Six Degrees of Kevin Bacon Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,650);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage((new ImageIcon("kevin.png")).getImage());  
		
		SpringLayout layout = new SpringLayout();
		frame.setLayout(layout);  
		
		JLabel title = new JLabel("The Six Degrees of Kevin Bacon Game");
		frame.add(title);
		
		//Label to hold total number of actors.
		totalActors = new JLabel("Actors: " + actors.size());
		frame.add(totalActors);
		
		//Label to hold total number of movies.
		JLabel totalMovies = new JLabel("Movies: " + getTotalMovies());
		frame.add(totalMovies);
		
		JLabel searchNameLabel = new JLabel("Actor name");
		frame.add(searchNameLabel);
		
		searchName = new JTextField(16);
		frame.add(searchName);
		
		JButton searchButton = new JButton("Search");
		frame.add(searchButton);
		
		//Label for the bacon number of the actor being searched for.
		baconNumber = new JLabel("bacon number: ", new ImageIcon("bacon.png"), JLabel.LEADING);
		frame.add(baconNumber);
		
		//Creates a panel to group labels created from the search results.
		resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 250, 1));
		resultPanel.setPreferredSize(new Dimension(360, 500));
		resultPanel.setVisible(true);
		frame.add(resultPanel);
		
		frame.add(loadTime);
		
		//Label to hold the time taken to find the actor
		findTime = new JLabel("");
		frame.add(findTime);
		
		//Adds an action listener to the search button
		ButtonListener b = new ButtonListener(this);
		searchButton.addActionListener(b);
		
		//MAIN WINDOW: COMPONENT POSITIONING=================================================
		//Position the title JLabel
		layout.putConstraint(SpringLayout.NORTH, title, 2, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.WEST, title, 90, SpringLayout.WEST, frame);
		
		//Position totalActors JLabel
		layout.putConstraint(SpringLayout.NORTH, totalActors, 15, SpringLayout.NORTH, title);
		layout.putConstraint(SpringLayout.WEST, totalActors, 95, SpringLayout.WEST, frame);
		
		//Position totalMovies JLabel
		layout.putConstraint(SpringLayout.NORTH, totalMovies, 15, SpringLayout.NORTH, title);
		layout.putConstraint(SpringLayout.WEST, totalMovies, 30, SpringLayout.EAST, totalActors);
		
		//Position the searchActorLabel JLabel
		layout.putConstraint(SpringLayout.NORTH, searchNameLabel, 43, SpringLayout.NORTH, title);
		layout.putConstraint(SpringLayout.WEST, searchNameLabel, 26, SpringLayout.WEST, frame);
		
		//Position the searchActor JTextField
		layout.putConstraint(SpringLayout.NORTH, searchName, 41, SpringLayout.NORTH, title);
		layout.putConstraint(SpringLayout.WEST, searchName, 80, SpringLayout.WEST, searchNameLabel);
		
		//Position the searchButton JButton
		layout.putConstraint(SpringLayout.NORTH, searchButton, 40, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.WEST, searchButton, 12, SpringLayout.EAST, searchName);	
		
		//Position the baconNumber JLabel
		layout.putConstraint(SpringLayout.NORTH, baconNumber, 70, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.WEST, baconNumber, 30, SpringLayout.WEST, frame);
		
		//Position the result JPanel
		layout.putConstraint(SpringLayout.NORTH, resultPanel, 50, SpringLayout.NORTH, searchNameLabel);
		layout.putConstraint(SpringLayout.WEST, resultPanel, 17, SpringLayout.WEST, frame);
		
		//Position the loadTime JLabel
		layout.putConstraint(SpringLayout.NORTH, loadTime, 4, SpringLayout.SOUTH, resultPanel);
		layout.putConstraint(SpringLayout.WEST, loadTime, 24, SpringLayout.WEST, frame);
		
		//Position the findTime JLabel
		layout.putConstraint(SpringLayout.NORTH, findTime, 4, SpringLayout.SOUTH, resultPanel);
		layout.putConstraint(SpringLayout.WEST, findTime, 87, SpringLayout.EAST, loadTime);
		//===================================================================================
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		new KevinBaconApp();
	}
	
	/**
	 * This method handles converting the vector retrieved from a search. A for loop is
	 * used to iterate over each element in the vector, and to create a new label from 
	 * each element. Labels containing the delimiters are placed between actors and
	 * movies, alternating between 'was in' and 'with'. 
	 * 
	 * @throws ActorNotFoundException The search actor was not found in the hash table.
	 * @throws EmptyQueueException The search queue did not contain any actors, critical error.
	 *  
	 */
	public void performSearch() throws ActorNotFoundException, EmptyQueueException {	
		Search s = new Search(actors, searchName.getText().trim());
		
		Vector<String> path = new Vector<String>();
		JLabel delimiter;
		
		//Resets the result panel for the next set of results
		resultPanel.removeAll();
		resultPanel.revalidate();
		resultPanel.repaint();
		
		/*Times how long it takes to find the actor and return the path
		  so that the appropriate label can be set*/
		long start = System.currentTimeMillis();
		path = s.findPath();
		long finish = System.currentTimeMillis();
		long time = finish - start;
		findTime.setText("Actor found in: " + time + "ms");
		
		/*Calculates the bacon number based on the number of elements
		in the returned vector and sets the corresponding label.*/
		baconNumber.setText("bacon number: " + ((path.size() - 1)/2));
		
		//For every element in path, done in reverse to get correct order.
		for(int i = path.size() - 1;i >= 0;i--) {
			resultPanel.add(new JLabel(path.get(i)));		//Create new label from vector element.
			if(i == 0){
				continue;
			} else if(i%2 == 0){							//If the modulus of the element number is 0, 
				delimiter = new JLabel("was in");			//add 'was in' as delimiter.
				delimiter.setFont(new Font(Font.DIALOG, Font.BOLD, 9));
				delimiter.setForeground(new Color(175, 175, 175));
				resultPanel.add(delimiter);				
			} else {
				delimiter = new JLabel("with");				//Otherwise add 'with' as a delimiter.
				delimiter.setFont(new Font(Font.DIALOG, Font.BOLD, 9));
				delimiter.setForeground(new Color(175, 175, 175));
				resultPanel.add(delimiter);
			}
		}
		
		s.resetActorsMovies();
	}
	
	/**
	 * This method initialises the file parser, and instructs it to
	 * read in the data from the text file.  The time taken to load
	 * the file is calculated in order to displayed this in the 
	 * appropriate JLabel.
	 */
	public void setup() {
		FileParser f = new FileParser();
		
		long start = System.currentTimeMillis();
		actors = f.readLines();
		long finish = System.currentTimeMillis();
		long time = (finish - start);
		
		loadTime.setText("File loaded in: " + time + "ms");
	}
	
	
	/**
	 * This method calculates the total number of movies by iterating
	 * over every actor in the hash table. Once a movie has been counted,
	 * it's visitor flag is set so that it isn't counted again. For this 
	 * reason, we have to iterate again to reset all of the flags. 
	 * 
	 * @return The total number of movies in the hash table.
	 */
	public int getTotalMovies(){
		int movieCount = 0;
		
		//Count all movies
		for(Actor a : actors.values()){
			for(Movie m : a.getMovieAppearances()){
				if (!m.isVisited()){
					movieCount++;
					m.setVisited(true);
				}
			}
		}
		
		//Reset all of the movie flags
		for(Actor a : actors.values()){
			for(Movie m : a.getMovieAppearances()){
				if (m.isVisited()){
					m.setVisited(false);
				}
			}	
		}
		return movieCount;
	}

}
