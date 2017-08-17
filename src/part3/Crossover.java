package part3;

import java.util.Random;

import part1.Tree;
import part1.TreeNode;

public class Crossover {

	// Variables
	private static int point1 = 0;
	private static int point2 = 0;
	private static Tree[] tree;
	private static Tree parent1;
	private static Tree parent2;

	// Displays Information for Crossover
	public static void display() {
		System.out.println("Crossover Point of Parent 1:\t"
				+ parent1.findNode(point1).getElement());
		System.out.println("ID of Crossover Point:\t\t" + point1);
		System.out.println("Depth of Crossover Point:\t"
				+ parent1.findNode(point1).getDepth());

		System.out.println("\nCrossover Point of Parent 2:\t"
				+ parent2.findNode(point2).getElement());
		System.out.println("ID of Crossover Point:\t\t" + point2);
		System.out.println("Depth of Crossover Point:\t"
				+ parent2.findNode(point2).getDepth());

		System.out.println("Parent 1");
		parent1.display();

		System.out.println("\n");

		System.out.println("Parent 2");
		parent2.display();

		for (int i = 0; i < tree.length; i++) {
			System.out.println("\nOffspring " + (i + 1));
			tree[i].display();
		}
	}

	// Returns the created offspring
	public static Tree[] getOffspring() {
		return tree;
	}

	// Creates offspring using crossover method

	public static void crossover(Tree tree1, Tree tree2, int size, Random random) {

		int offspringSize = random.nextInt(size) + 1;

		// Finds random crossover points in both parents
		int id1 = random.nextInt(tree1.getNumNodes());
		int id2 = random.nextInt(tree2.getNumNodes());

		Tree temp1 = new Tree(tree1);
		Tree temp3 = new Tree(tree1);

		Tree temp2 = new Tree(tree2);
		Tree temp4 = new Tree(tree2);

		TreeNode<String> node1 = temp1.findNode(id1);
		TreeNode<String> node3 = temp3.findNode(id1);

		TreeNode<String> node2 = temp2.findNode(id2);
		TreeNode<String> node4 = temp4.findNode(id2);

		node3.setNode(node2);
		node4.setNode(node1);

		temp3.resetID();
		temp4.resetID();

		// System.out.println("Size" + size);
		// System.out.println("Tree 1 " + temp3.getDepth());
		// System.out.println("Tree 2 " + temp3.getDepth());

		// Repeat process if offspring size is greater than the specified size
		while (temp3.getDepth() > offspringSize
				|| temp4.getDepth() > offspringSize) {

			offspringSize = random.nextInt(size) + 1;

			id1 = random.nextInt(tree1.getNumNodes());
			id2 = random.nextInt(tree2.getNumNodes());
			temp1 = new Tree(tree1);
			temp3 = new Tree(tree1);

			temp2 = new Tree(tree2);
			temp4 = new Tree(tree2);

			node1 = temp1.findNode(id1);
			node3 = temp3.findNode(id1);

			node2 = temp2.findNode(id2);
			node4 = temp4.findNode(id2);

			node3.setNode(node2);
			node4.setNode(node1);

			temp3.resetID();
			temp4.resetID();

			// System.out.println("Size" + size);
			// System.out.println("Tree 1 " + temp3.getDepth());
			// System.out.println("Tree 2 " + temp3.getDepth());

		}

		point1 = id1;
		point2 = id2;
		parent1 = tree1;
		parent2 = tree2;

		// Offspring returned in array form
		tree = new Tree[2];
		tree[0] = temp3;
		tree[1] = temp4;

	}

}
