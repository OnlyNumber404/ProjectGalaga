package Week_8_Lab;

public class LinkedList<T> 
{
	private Node<T> head;
	private int count;
	
	public LinkedList()
	{
		this.head = null;
		this.count = 0;
	}
	
	public Node<T> GetLinkedListHead()
	{
		return this.head;
	}
	
	public void AddNode(Node<T> newNode)
	{
		//1. First Scenario: if it's an empty list, 
		//then add it to the 1st element
		if(this.head == null) 
		{
			this.head = newNode;
		}
		else 
		{
			//2. Second Scenario: if it's not empty, 
			//then add it to the next element as the last element
			Node<T> temp = this.head;
			
			//2.b. Get the last element, and then add it after that.
			while(temp.GetNext() != null)
			{
				temp = temp.GetNext();
			}
			temp.SetNext(newNode);
		}
		
		this.count++;
	}
	
	public void RemoveNode()
	{
		//remove the node from the back
		if(this.head != null)
		{
			Node<T> curr = this.head;
			Node<T> prev = null;
			
			//2.b. Get the last element, and then remove it
			while(curr.GetNext() != null)
			{
				prev = curr;
				curr = curr.GetNext();
			}
			
			if(prev == null)
			{
				//if there is only one element in the LinkedList,
				//then set the head to null, to make an empty list.
				this.head = null;
			}
			else 
			{
				//if there are more than one element, remove the last one
				prev.SetNext(null);
			}
			this.count--;
		}
	}
	
	public void RemoveAll(Node<T> current, Node<T> prev, int counter)
	{
		if(current != null)
		{
			RemoveAll(current.GetNext(), current, counter+1);
			if(counter == 0)
			{
				this.head = null;
			}
			else 
			{
				prev.SetNext(null);
			}
		}
	}
	
	public int Count()
	{
		return this.count;
	}
	//tambahan untuk remove musuh / laser
	public void RemoveNodeByData(T data) {
		if (this.head == null) return;
		
		Node<T> curr = this.head;
		Node<T> prev = null;
		
		while (curr != null) {
			if (curr.GetNodeData().equals(data)) {
				if (prev == null) {
					this.head = curr.GetNext();			
				} else {
					prev.SetNext(curr.GetNext());
				}
				
				this.count--;
				return;
			}
			prev = curr;
			curr = curr.GetNext();
		}
	}
}
