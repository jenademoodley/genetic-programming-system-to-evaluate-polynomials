package part3;

import part1.Tree;

public class Reproduction {

	// Variables
	public static Tree tree;

	// Creates offspring using reproduction method
	public static void reproduce(Tree t) {
		tree = t;
	}

	// Returns the created offspring
	public static Tree getOffspring() {
		return tree;
	}

	// Displays Information for Mutation
	public static void display() {
		System.out.println("Parent");
		tree.display();
		System.out.println("\nOffspring");
		tree.display();
	}
}
