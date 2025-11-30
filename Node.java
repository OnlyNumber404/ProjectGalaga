package Week_8_Lab;

public class Node<T> 
{
	private T data;
	private Node<T> next;
	
	public Node(T newData)
	{
		this.data = newData;
		this.next = null;
	}
	
	public T GetNodeData()
	{
		return this.data;
	}
	
	public void SetNext(Node<T> nextNode)
	{
		this.next = nextNode;
	}
	
	public Node<T> GetNext() 
	{
		return this.next;
	}
}