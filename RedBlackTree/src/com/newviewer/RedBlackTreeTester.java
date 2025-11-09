package com.newviewer;
//package com.adsg.tree.tester;

//import com.adsg.tree.BinarySearchTree;
//import com.misc.Person;

public class RedBlackTreeTester {

	public static void main(String[] args) {
		RedBlackTree<Integer> myTree = new RedBlackTree<Integer>();

	    myTree.insert(1);
	    myTree.insert(2);
	    myTree.insert(3);



	
		System.out.println("Pre-order Traversal:");
		myTree.preOrderTraversal();
		System.out.println();
	}

}