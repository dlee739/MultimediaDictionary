
public class BSTNode {
	private BSTNode parent; // reference to the parent of this node
	private BSTNode leftChild; // reference to the left child of this node
	private BSTNode rightChild; // reference to the right child of this node
	private NodeData data; // reference to the information stored in this node
	
	// Constructor 1
	// creates a new BSTNode object in which all its instance variables have value null.
	// This constructor is used when creating a leaf node
	BSTNode() {
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
		this.data = null;
	}
	
	// Constructor 2
	// creates a new BSTNode in which the instance variables take the
	// values specified in the corresponding parameters
	BSTNode(BSTNode newParent, BSTNode newLeftChild, BSTNode newRightChild, NodeData newData) {
		this.parent = newParent;
		this.leftChild = newLeftChild;
		this.rightChild = newRightChild;
		this.data = newData;
	}

	public BSTNode getParent() {
		return this.parent;
	}
	
	public BSTNode getLeftChild() {
		return this.leftChild;
	}
	
	public BSTNode getRightChild() {
		return this.rightChild;
	}
	
	public NodeData getData() {
		return this.data;
	}
	
	public void setParent(BSTNode newParent)  {
		this.parent = newParent;
	}
	
	public void setLeftChild(BSTNode newLeftChild)  {
		this.leftChild = newLeftChild;
	}
	
	public void setRightChild(BSTNode newRightChild)  {
		this.rightChild = newRightChild;
	}
	
	public void setData(NodeData newData)  {
		this.data = newData;
	}
	
	public boolean isLeaf() {
		return (this.data == null) && (this.leftChild == null) && (this.rightChild == null);
	}
	
	
}
