import java.util.ArrayList;

public class BSTOrderedDictionary implements BSTOrderedDictionaryADT{
	private BSTNode root; // root of the binary search tree
	private int numInternalNodes; // number of internal nodes in this tree
	
	// constructor
	public BSTOrderedDictionary() {
		root = new BSTNode(); // root is a leaf BSTNode
		this.numInternalNodes = 0;
	}
	
	public BSTNode getRoot() {
		return this.root;
	}
	   
	public int getNumInternalNodes() {
		return this.numInternalNodes;
	}
	
	// getnode(r, k) returns the BSTNode with given k, or null if there is no k
	private BSTNode getnode(BSTNode r, String k) {
		if (r.isLeaf()) { // r is leaf
			return r;
		} else {
			int compare = k.compareTo(r.getData().getName()); // < 0 if k < r
			if (compare == 0) { // key found
				return r;
			} else if (compare < 0) {
				return getnode(r.getLeftChild(), k);
			} else {
				return getnode(r.getRightChild(), k);
			}
		}
	}
	
	public ArrayList<MultimediaItem> get(BSTNode r, String k) {
		if (r.isLeaf()) { // r is leaf
			return null;
		} else {
			int compare = k.compareTo(r.getData().getName()); // < 0 if k < r
			if (compare == 0) { // key found
				return r.getData().getMedia();
			} else if (compare < 0) { // k is lexicographically less than r
				return get(r.getLeftChild(), k);
			} else { // k is lexicographically larger than r
				return get(r.getRightChild(), k);
			}
		}
	}
	
	public void put(BSTNode r, String name, String content, int type) {
		BSTNode p = getnode(r, name); // fetch the node p
		NodeData newData; // new data node
		MultimediaItem newMI = new MultimediaItem(content, type); // new multimedia item
		
		if (!p.isLeaf()) { // p is an internal node
			p.getData().add(newMI);
		} else {
			// create new data with its associated new multimedia item
			newData = new NodeData(name);
			newData.add(newMI);
			p.setData(newData);
			
			// set up new left child
			BSTNode lc = new BSTNode(); // left child
			lc.setParent(p);
			p.setLeftChild(lc);
			
			// set up new right child
			BSTNode rc = new BSTNode(); // right child
			rc.setParent(p);
			p.setRightChild(rc);
			
			numInternalNodes++; // increment node count
		}
		
	}
	
	// smallestNode(r) returns the smallest node of binary search tree associated with root r
	private BSTNode smallestNode(BSTNode r) {
		if (r.isLeaf()) {
			return null;
		} else {
			BSTNode p = r; // node variable pointing r
			while (!p.isLeaf()) {
				p = p.getLeftChild();
			}
			return p.getParent();
		}
	}
	
	public NodeData smallest(BSTNode r) {
		return smallestNode(r).getData(); 
	}
	
	public NodeData largest(BSTNode r) {
		if (r.isLeaf()) {
			return null;
		} else {
			BSTNode p = r;// node variable pointing r
			while (!p.isLeaf()) {
				p = p.getRightChild();
			}
			return p.getParent().getData();
		}
	}
	
	public void remove(BSTNode r, String k) throws DictionaryException {
		BSTNode p = getnode(r, k); // fetch the node p
		if (p.isLeaf()) {
			throw new DictionaryException("No node stores the given key.");
		} else {
			BSTNode parent = p.getParent(); // parent node of p
			BSTNode lc = p.getLeftChild(); // left child of p
			BSTNode rc = p.getRightChild(); // right child of p
			BSTNode oc; // other child
			BSTNode s; // smallest node
			
			if (!lc.isLeaf() && !rc.isLeaf()) { // no child are leaves
				s = smallestNode(p.getRightChild());
				p.setData(s.getData());
				remove(s, s.getData().getName());
			} else { // at least one child is leaf
				if (lc.isLeaf()) { // left child is leaf
					oc = rc;
				} else {
					oc = lc;
				}
				if (parent == null) { // parent is empty
					this.root = oc;
				} else { // parent is not empty
					if (parent.getLeftChild() == p) { // p is parent's left child
						parent.setLeftChild(oc);
					} else { // p is parent's right child
						parent.setRightChild(oc);
					}
					oc.setParent(parent);
				}
				numInternalNodes--; // decrement node count
			}
		}
	}
	
	public void remove(BSTNode r, String k, int type) throws DictionaryException {	
		BSTNode p = getnode(r, k); // fetch the node p
		if (p.isLeaf()) {
			throw new DictionaryException("No node stores the given key.");
		} else {
			ArrayList<MultimediaItem> list = p.getData().getMedia(); // list points to p's multimedia
			NodeData newData = new NodeData(k); // create new NodeData holding k
			
			// copies the elements of arraylist except the ones with specified type
			for (int i = 0; i < list.size(); i++) {
				MultimediaItem curItem = list.get(i); // current multimedia item of list at i
				if (curItem.getType() != type) {
					newData.add(curItem);
				}
			}
			p.setData(newData);
		}
		
		if (p.getData().getMedia().isEmpty()) { // media is empty
			remove(r, k);
		}
	}
	
	public NodeData successor(BSTNode r, String k) {
		BSTNode p = getnode(r, k); // fetch the node p
		BSTNode rc = p.getRightChild(); // right child
		if (!rc.isLeaf()) { // rc is internal
			return smallest(rc);
		} else {
			BSTNode parent = p.getParent(); // parent node
			while (parent != null && p == parent.getRightChild()) {
				p = parent;
				parent = parent.getParent();
			}
			if (parent == null) {
				return null;
			}
			return parent.getData();
		}
	}
	
	public NodeData predecessor(BSTNode r, String k) {
		BSTNode p = getnode(r, k); // fetch the node p
		BSTNode lc = p.getLeftChild(); // left child
		if (!lc.isLeaf()) { // rc is internal
			return largest(lc);
		} else {
			BSTNode parent = p.getParent(); // parent node
			while (parent != null && p == parent.getLeftChild()) {
				p = parent;
				parent = parent.getParent();
			}
			if (parent == null) {
				return null;
			}
			return parent.getData();
		}
	}
	// case 1: what if parent = null?
} 
