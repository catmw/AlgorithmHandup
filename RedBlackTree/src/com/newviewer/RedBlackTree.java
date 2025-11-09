package com.newviewer;


// java -cp bin com.newviewer.GraphPanel

/**
 * This is a "generic" Binary Search Tree - know the definition of what a BST is!
 * 
 * NOTE: To allow for our objects to be inserted (and found) properly they have to be COMPARED
 * to the objects in the tree. This is why we have <T extends Comparable<T>> instead of 
 * just <T> : We are effectively saying that the objects which can be stored MUST implement
 * the Comparable interface.
 * 
 * NOTE: Our Node class is an inner class in an inner class at the bottom of the code.
 * 
 * @author dermot.hegarty
 *
 * @param <T>
 */
public class RedBlackTree<T extends Comparable<T>> {
	/**
	 * Reference to the root of the tree
	 */
	public Node root;

	/**
	 * This is the public insert method, i.e. the one that the outside world will invoke.
	 * It then kicks off a recursive method to "walk" through down through the tree - this is 
	 * possible because each sub-tree is itself a tree.
	 * @param value Object to insert into the tree
	 */
	public void insert(T value){
		Node node = new Node(value); // Create the Node to add

		//Special case that cannot be handled recursively
		if ( root == null ) {
			root = node;
			node.nodeColourRed = false;
			return;
		}

		//Initially we start at the root. Each subsequent recursive call will be to a 
		//left or right subtree.
		insertRec(root, node);
		handleRedBlack(node);


	}

	void handleRedBlack(Node newNode)
{
    if (newNode == root) {
        newNode.nodeColourRed = false;
        return;
    }

	//Find the parent node
    Node parent = newNode.parent;

    if (parent == null) {
		return;
	}
    if (!parent.nodeColourRed) {
		return;
	}    
	//Find the grandparent node
    Node grandParent = parent.parent;

    if (grandParent == null) {
        parent.nodeColourRed = false;
        return;
    }

	//Find the uncle node
    Node uncle = (grandParent.left == parent) ? grandParent.right : grandParent.left;


    if (uncle != null && uncle.nodeColourRed) {
        parent.nodeColourRed = false;
        uncle.nodeColourRed  = false;
        grandParent.nodeColourRed = true;
        handleRedBlack(grandParent);
        return;
    }

//check if the parent is a left child
    boolean parentIsLeftChild = (grandParent.left == parent);
	//check if the newNode is a left child
    boolean nodeIsLeftChild   = (parent.left == newNode);

    if (parentIsLeftChild && nodeIsLeftChild) {
        //LeftLeft
        rotateRight(grandParent);
        parent.nodeColourRed = false;
        grandParent.nodeColourRed = true;
    } else if (parentIsLeftChild && !nodeIsLeftChild) {
        //LeftRight
        rotateLeft(parent);
        rotateRight(grandParent);
        newNode.nodeColourRed = false;
        if (newNode.left  != null) newNode.left.nodeColourRed  = true;
        if (newNode.right != null) newNode.right.nodeColourRed = true;
    } else if (!parentIsLeftChild && !nodeIsLeftChild) {
        //RightRight
        rotateLeft(grandParent);
        parent.nodeColourRed = false;
        grandParent.nodeColourRed = true;
    } else {
		//RightLeft
        rotateRight(parent);
        rotateLeft(grandParent);
        newNode.nodeColourRed = false;
        if (newNode.left  != null) newNode.left.nodeColourRed  = true;
        if (newNode.right != null) newNode.right.nodeColourRed = true;
    }

    root.nodeColourRed = false;
}

	/**
	 * 
	 * @param subTreeRoot The SubTree to insert into
	 * @param node The Node that we wish to insert
	 */
	public void insertRec(Node subTreeRoot, Node node){

		//Note the call to the compareTo() method. This is only possible if our objects implement
		//the Comparable interface.
		if ( node.value.compareTo(subTreeRoot.value) < 0){

			//This is our terminal case for recursion. We should be going left but there is 
			//no leaf node there so that is obviously where we must insert
			if ( subTreeRoot.left == null ){
				subTreeRoot.left = node;
				node.parent = subTreeRoot;
				return; //return here is unnecessary
			}
			else{ // Note that this allows duplicates!
				
				//Now our new "root" is the left subTree
				insertRec(subTreeRoot.left, node);
			}
		}
		//Same logic for the right subtree
		else{
			if (subTreeRoot.right == null){
				subTreeRoot.right = node;
				node.parent = subTreeRoot;
				return;
			}
			else{
				insertRec(subTreeRoot.right, node);
			}
		}
	}
	
	
	/**
	 * Should traverse the tree "in-order." See the notes
	 */
	public void inOrderTraversal()
	{
		//start at the root and recurse
		recInOrderTraversal(root);
	}
	
	public void preOrderTraversal()
	{
		//start at the root and recurse
		recPreOrderTraversal(root);
	}
	
	public void postOrderTraversal()
	{
		//start at the root and recurse
		recPostOrderTraversal(root);
	}

    public T findMaximum(){
        Node current = root;
        while (current.right != null){
            current = current.right;
        }
        return current.value;
    }

    public T findMinimum(){
        return recFindMinimum(root);
    }

    public T recFindMinimum(Node subTreeRoot){
        if (subTreeRoot.left != null){
            return recFindMinimum(subTreeRoot.left);
            
        }
            return subTreeRoot.value;
    }

	//Week 2 Rotate Tree Right
	public void rotateTreeRight(){
		Node pivot = root.left;
		Node t2 = pivot.right;
		pivot.right = root;

		root.left = t2;
		root = pivot;
	}

	//Week 2 Rotate SubTree Left
	public void rotateTreeLeft(){
		root = rotateSubTreeLeft(root);
	}

	public Node rotateSubTreeLeft(Node subTreeRoot){
		if(subTreeRoot == null || subTreeRoot.right == null){
			return null;
		}
		Node pivot = subTreeRoot.right;
		Node t2 = pivot.left;
		pivot.left = subTreeRoot;

		subTreeRoot.right = t2;

		return pivot;
	}

	//left rotate around oldRoot
    public void rotateLeft(Node oldRoot) {
        if (oldRoot == null) {
             return;
            }
        Node newRoot = oldRoot.right;
        if (newRoot == null){ 
            return;
        }
    
        oldRoot.right = newRoot.left;
        if (newRoot.left != null) {
            newRoot.left.parent = oldRoot;
        }
    
        newRoot.parent = oldRoot.parent;
        if (oldRoot.parent == null) {
            root = newRoot;
        } else if (oldRoot.parent.left == oldRoot) {
            oldRoot.parent.left = newRoot;
        } else {
            oldRoot.parent.right = newRoot;
        }
    
        newRoot.left = oldRoot;
        oldRoot.parent = newRoot;
    }

    //Right rotate around oldRoot
    public void rotateRight(Node oldRoot) {
        if (oldRoot == null){
            return;
           }
        Node newRoot = oldRoot.left;
        if (newRoot == null) {
            return;
           } 
    
        oldRoot.left = newRoot.right;
        if (newRoot.right != null) {
            newRoot.right.parent = oldRoot;
        }
        newRoot.parent = oldRoot.parent;
        if (oldRoot.parent == null) {
            root = newRoot;
        } else if (oldRoot.parent.left == oldRoot) {
            oldRoot.parent.left = newRoot;
        } else {
            oldRoot.parent.right = newRoot;
        }
        newRoot.right = oldRoot;
        oldRoot.parent = newRoot;
    }

	/**
	 * This allows us to recursively process the tree "in-order". Note that it is private
	 * @param subTreeRoot
	 */
	protected void recInOrderTraversal(Node subTreeRoot)
	{
		if(subTreeRoot == null) return;
		
		recInOrderTraversal(subTreeRoot.left);
		processNode(subTreeRoot);
		recInOrderTraversal(subTreeRoot.right);
	}
	
	protected void recPreOrderTraversal (Node subTreeRoot)
	{
		if(subTreeRoot == null) return;
		
		processNode(subTreeRoot);
		recPreOrderTraversal(subTreeRoot.left);
		recPreOrderTraversal(subTreeRoot.right);
	}
	
	protected void recPostOrderTraversal (Node subTreeRoot)
	{
		if(subTreeRoot == null) return;
		
		recPostOrderTraversal(subTreeRoot.left);
		recPostOrderTraversal(subTreeRoot.right);
		processNode(subTreeRoot);
	}

	protected void processNode(Node currNode)
	{
		System.out.println(currNode.toString());
	}
	
	/**
	 * 
	 * @return The number of nodes in the tree
	 */
	public int countNodes()
	{
		return recCountNodes(root);
	}
	
	
	/**
	 * Note: This is a practical example of a simple usage of pre-order traversal
	 * @param subTreeRoot
	 * @return
	 */
	protected int recCountNodes(Node subTreeRoot)
	{
		if (subTreeRoot == null) return 0;
		
		return 1 + recCountNodes(subTreeRoot.left) + recCountNodes(subTreeRoot.right);
	}
	
	/**
	 * Our Node contains a value and a reference to the left and right subtrees (initially null)
	 * @author dermot.hegarty
	 *
	 */
	protected class Node {
		public T value; //value is the actual object that we are storing
		public Node left;
		public Node right;
		public Node parent;
		public boolean nodeColourRed;

		public Node(T value) {
			this.value = value;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.nodeColourRed = true;
		}

		@Override
		public String toString() {
			String colour = nodeColourRed ? "Red":"Black";
			return "Node [value=" + value + " colour= "+ colour + "]";
		}
	}
}