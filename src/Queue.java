


/**
 * A simple queue structure used to implement a Breadth First Search
 * using Actor objects.
 * 
 * @author  Nick Blomberg (nib8)
 * @version  1.0
 */
public class Queue {
	
	private ListNode front;
	private ListNode back;
	
    public Queue( ) {
        front = null;
        back = null;
    }
    
    public boolean isEmpty( ) {
        return front == null;
    }
    
    /**
     * Wraps the actor object in a ListNode and adds it to the back of the queue. 
     * If the queue is empty, first and last refer to the same element.
     * 
     * @param a The actor to add to the queue.
     */
    public void enqueue(Actor a) {
        if(isEmpty()) {  
            back = front = new ListNode(a);
        } else {               
            back = back.setNext(new ListNode(a));
        }
    }
    
    /**
     * Removes the first Actor by setting the front node to
     * be the next actor in the queue.
     * 
     * @return The actor at the front of the queue.
     * @throws EmptyQueueException Thrown if the queue is empty.
     */
    public Actor dequeue( ) throws EmptyQueueException {
        if(isEmpty()) {
            throw new EmptyQueueException("A critical error occurred, please restart");
        }   
        Actor a = front.getElement();
        front = front.getNext();
        return a;
    }

}