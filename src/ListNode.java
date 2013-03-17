

/**
 * Class to represent nodes in a Linked list style queue
 * for actor objects.
 * 
 * @author  Nick Blomberg (nib8)
 * @version 1.0
 */
class ListNode {
	
	private Actor element;
	private ListNode next;
    
    public ListNode(Actor a) {
        this.element = a;
    }

	public Actor getElement() {
		return element;
	}

	public ListNode getNext() {
		return next;
	}

	public ListNode setNext(ListNode next) {
		this.next = next;
		return next;
	}
        
}

