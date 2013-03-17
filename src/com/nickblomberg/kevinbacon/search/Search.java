package com.nickblomberg.kevinbacon.search;


import java.util.Map;
import java.util.Vector;

import com.nickblomberg.kevinbacon.exceptions.ActorNotFoundException;
import com.nickblomberg.kevinbacon.exceptions.EmptyQueueException;
import com.nickblomberg.kevinbacon.model.Actor;
import com.nickblomberg.kevinbacon.model.Movie;


/**
 * A class to search through the data structure and to find the shortest path
 * between 2 nodes (actors).
 * 
 * @author Nick Blomberg  (nib8)
 * @version 1.2
 */
public class Search {
	
	private Map<String,Actor> actors;
	private static final String ENDING_NODE = "Bacon, Kevin";
	private String startingNode;
	private Queue q;

	/**
	 * A constructor to set the hash table, and to initialise the queue.
	 * 
	 * @param actors A hash table containing all actors.
	 * @param startingNode The actor to get the bacon number of.
	 */
	public Search(Map<String,Actor> actors, String startingNode){
		this.actors = actors;
		this.startingNode = startingNode;
		q = new Queue();
	}
	
	/**
	 * This method attempts to find the shortest path between two nodes. If the hash table contains
	 * the starting node, it is added to the queue. While the queue isn't empty, the front node is
	 * dequeued. If the node is the ending node, the tracePath method is called. Otherwise the current nodes
	 * films are check for actors that have not yet been visited. These are added to the queue, setting the 
	 * previous actor at the same time.
	 * 
	 * @return The shortest path, elements alternate between actors and movies.
	 * @throws ActorNotFoundException The actor was not found in the hash table.
	 * @throws EmptyQueueException The internal queue was empty, critical error.
	 * @see tracePath()
	 */
	public Vector<String> findPath() throws ActorNotFoundException, EmptyQueueException {
		
		//Check that the actor to start from exists.
		if(actors.containsKey(startingNode)){
			q.enqueue(actors.get(startingNode));
			actors.get(startingNode).setVisited(true);
		} else {
			throw new ActorNotFoundException("There is no record of an actor called: " + startingNode);
		}
		
		while(!q.isEmpty()){		
			Actor currentNode;
			currentNode = q.dequeue();
			
			//Check if the node removed from the queue is the target node.
			if(currentNode.getName().equals(ENDING_NODE)) {
				return tracePath(currentNode);	
			} else {	
				for(Movie m : currentNode.getMovieAppearances()){
					if(!m.isVisited()){
						Vector<Actor> cast = m.getCast();	
						m.setVisited(true);
						
						/*For every actor who has been in a film with the node currently being examined,
						 *check if actor has been visited. If not add it to the queue of nodes to look through.
						 */
						for(Actor a : cast){
							if(!a.isVisited()){
								a.setVisited(true);
								a.setBaconNumber(currentNode.getBaconNumber() + 1);
								a.setPrev(currentNode);
								q.enqueue(a);
							}		
						}
					} else {
						continue;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * This method iterates through every actor in the hash table. It
	 * resets their bacon numbers and visited flags. The visited flag in
	 * each movie is also reset. This allows for consecutive searches without 
	 * re-loading in the text file.
	 */
	public void resetActorsMovies(){
		for(Actor a : actors.values()){
			a.setVisited(false);
			a.setBaconNumber(0);
			for(Movie m : a.getMovieAppearances()){
				m.setVisited(false);
			}
		}
	}
	
	/**
	 * The purpose of this method is to step back along the path followed by 
	 * the breadth first search in order to be able to print the path. For
	 * each node in the path, it gets the previous node. It searches through
	 * the previous nodes movies for one that it shares in common with the current
	 * node. If found, the movie and previous actor are added to the path. The current
	 * node is then set to the previous.
	 * 
	 * @param a The node found ENDING_NODE.
	 * @return The shortest path between startingNode and ENDING_NODE.
	 * @see findPath()
	 */
	public Vector<String> tracePath(Actor a) {
		Vector<String> path = new Vector<String>();
		Actor current = a;
		Actor prev = null;
		int depth = current.getBaconNumber();
		
		//For each level the graph has traversed.
		for(int i = 0; i < depth; i++) {
			if(i > 0){ 
				current = prev; 
			} else {
				path.add(current.getName());
			}
			
			//Retrieves the current actors previous actor.
			prev = current.getPrev();
			
			for(Movie m : prev.getMovieAppearances()){
				Vector<Actor> cast = m.getCast();
				
				//If the movies stars both actors, add to the path.
				if(cast.contains(current)){	
					path.add(m.getTitle());
					path.add(prev.getName());
					break;
				}
			}
		}
		return path;
	}
		
}
