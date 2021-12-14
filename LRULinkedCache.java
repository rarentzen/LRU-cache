package lru;


/**
 * 
 * @author esahe2
 *
 * @param <K> The type of the key
 * @param <V> The type of the value
 */


public class LRULinkedCache<K,V> 
{
	
	
	
	
	/*************
	 *	attributes
	 ************/
	
	/** current number of items in cache */
	private int theSize;
	
	/** The capacity of cache. */
	private int capacity;
	
	/** reference to list header node */
	private CacheNode<K,V> head;
	
	/** reference to list tail node */
	private CacheNode<K,V> tail;
	
		/***************
	 *	constructors
	 **************/

	/**
	 *	return a new, empty cache with a given capacity
	 */
	public LRULinkedCache(int capacity)
	{
		this.capacity=capacity;
		// empty this LinkedList
		clear();
	}


	/**********
	 *	methods
	 *********/


	
	/**************************************
	 *	methods inherited from class Object
	 *************************************/

	/**
	 *	return a String representation of the LinkedList
	 *
	 *	items are listed in order from beginning to end in comma-delimited fashion
	 */
	public String toString()
	{
		String s = "";
		
		CacheNode<K,V> current= head.next;
		while(current!=tail)
		{
			s+= "("+current.key +"," +current.value+")";
			if (current.next!= tail)
				s+=",";
			current= current.next;
		}
		
		return s;
	}


	/**********************************************
	 *	methods inherited from interface Collection
	 *********************************************/
	
	

	/**
	 *	empty the LRUCache
	 *	size will be set to zero
	 */
	public void clear()
	{
		// reset header node
		head = new CacheNode<K,V>(null,null,null ,null);
		// reset tail node
        tail = new CacheNode<K,V>(null,null,head, null);
        head.next=tail;
        tail.previous=head;
        // reset size to 0
		theSize = 0;
		
		
	}


	/**
	 *	return the number of items in the cache
	 */
	public int size()
	{
		return theSize;
	}


	/**
	 *	return true if the cache is empty
	 */
	public boolean isEmpty()
	{
		return theSize == 0;
	}

		
	
	/**
	 *	This operation returns the value for a given key in the cache. It returns null if the data is not currently in the cache.
	 *  It also moves the data that is accessed to the end of the list and inserts it before tail
	 */
	public V LRUGet(K key)
	{
		//To do: Your implementation goes here
		CacheNode<K,V> current = head.next;
		for (int i=0; i<theSize; i++) {
			if (current.key.equals(key)) {
				addLast(current);
				remove(current);
				return current.value;
				
			}
			current = current.next;
		}
		
		
		return null;
	}
	
	
	/**
	 * puts a new node with key and value in the cache and adds it to the end of the cache
	 * If the cache is full, it removes the first node (The least recently used node)before adding the new node.
	 * If a node with the given key already exists in the cache, it updates the value for the key and moves the node with the key to the
	 * end of the cache
	 * @param key
	 * @param value
	 */
	public void LRUPut(K key, V value)
	{
		//To Do: Your implementation goes here
		if (theSize == capacity) {
			remove(head.next);
		}
		
		CacheNode<K,V> current = head.next;
		for (int i = 0; i<theSize; i++) {
			
			if (current.key.equals(key)) {
				current.value = value;
				addLast(current);
				remove(current);
				break;
			}		
			current = current.next;
		}
		CacheNode<K,V> newNode = new CacheNode<K,V> (key, value, null, null);
		addLast(newNode);
	}
	
	public void remove(CacheNode<K,V> node)
	{
		
		CacheNode<K,V> previousNeighbor = node.previous;
		CacheNode<K,V> nextNeighbor = node.next;
		nextNeighbor.previous = previousNeighbor;
		previousNeighbor.next = nextNeighbor;
		
		// size of cache -1
		theSize--;
	
	}
	//method to add the node to the end(most recently used) position of the cache
	public void addLast(CacheNode<K,V> p) {
		CacheNode<K,V> q = tail.previous;
		CacheNode<K,V> newNode = new CacheNode<K,V> (p.key, p.value, q, tail);
		newNode.previous.next = newNode;
		tail.previous = newNode;
		
		// cache size +1
		theSize++;
	}
	
	
	/**
	 *	nested class ListNode
	 *
	 *	encapsulates the fundamental building block of a LRU cache node
	 *	contains a key and value, as well as references to both the next and previous nodes
	 *	in the cache
	 *K is the type of the key and V is the type of value
	 */
	private static class CacheNode<K,V>
	{

		/*************
		 *	attributes
		 ************/
		K key;
		V value;
		CacheNode<K,V> next;
		CacheNode<K,V> previous;
		
		//TODO: define attributes and constructor for CacheNode
		
		public CacheNode(K k, V v, CacheNode<K,V> previousNode, CacheNode<K,V> nextNode)
		{
			key = k;
			value = v;
			previous = previousNode;
			next = nextNode;
		}
	}
	

}



