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

		//Test1
		System.out.println("Pre-order Traversal after inserting 1, 2, 3:");
		myTree.preOrderTraversal();
		System.out.println();


		myTree.insert(12);
	    myTree.insert(7);
	    myTree.insert(4);
		myTree.insert(5);

		//Test2 Print preorder traversal
		System.out.println("Pre-order Traversal after inserting 1, 2, 3, 12, 7, 4, 5:");
		myTree.preOrderTraversal();
		System.out.println();

		//Test3 Count node count
		System.out.println("Node count should be 7 == " + myTree.countNodes());

		//Test4 Check min value
        System.out.println("Min should be 1 == " + myTree.findMinimum());

		//Test5 Check max value
        System.out.println("Max should be 12 == " + myTree.findMaximum());

		//Test6 Print root value
		System.out.println("root: " + myTree.root.value);


	}

}