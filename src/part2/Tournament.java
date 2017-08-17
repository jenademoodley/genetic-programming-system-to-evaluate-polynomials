package part2;

import java.util.ArrayList;
import java.util.Random;

import part1.Tree;

public class Tournament {
	// Variables
	private static int win = 0;
	private static ArrayList<Integer> count = new ArrayList<>();

	public static int getIndexWinner() {
		return count.get(win);
	}

	// Makes the Tournament from the population
	public static Tree[] makeTournament(ArrayList<Tree> tree, int size,
			long seed) {
		Random random = new Random(seed);
		Tree[] population = new Tree[size];
		int i = 0;
		// Removes trees from population so that no tree is chosen twice for the
		// tournament
		while (i < size) {
			int index = random.nextInt(tree.size());
			population[i] = tree.get(index);
			count.add(index);
			tree.remove(index);
			i++;
		}

		// Tournament is with selection so add removed trees to population again
		for (int j = 0; j < population.length; j++)
			tree.add(population[j]);

		return population;
	}

	// Outputs the participants(Trees) chosen as participants as well as their
	// fitness levels
	public static void participants(Tree[] tree) {
		for (int i = 0; i < tree.length; i++) {
			System.out.println("Participant " + (i + 1));
			tree[i].display();
			System.out.println("\nFitness: " + tree[i].getFitness() + "\n");
		}
	}

	// Displays the winner of the tournament
	public static void displayWinner() {
		System.out.println("Winner is Participant " + (win + 1));
	}

	// Calculates the winner of the Tournament. Winner has the LOWEST Raw
	// Fitness Value
	public static Tree winner(Tree[] tree) {
		int index = 0;
		float count = tree[0].getFitness();
		float temp = 0;
		for (int i = 0; i < tree.length; i++) {
			temp = tree[i].getFitness();
			if (count > temp) {
				count = temp;
				index = i;
			}
		}
		win = index;
		return tree[win];
	}

}
