package runnable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import part1.RampedHalfAndHalf;
import part1.Tree;
import part2.Fitness;
import part2.Tournament;
import part3.Crossover;
import part3.Mutation;
import part3.Reproduction;

public class Runnable {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		int runs = 1;
		while (runs <= 1) {
			ArrayList<Tree> trees = new ArrayList<>();

			long seed = 0;
			ArrayList<Tree> initialPopulation = new ArrayList<>();
			ArrayList<Tree> offspring = new ArrayList<>();
			int noOfOffspring = 0;
			int size = 0;
			int depth = 0;
			int offspringDepth = 0;
			int method = 0;
			float bound = 0;
			int file = 0;
			String filename = "";
			float[][] array = null;
			Tree winner = null;
			Tree winner2 = null;
			int crossoverSize = 0;
			int mutationSize = 0;
			int reproductionSize = 0;
			int tournament = 10;
			int crossoverRate = 0;
			int mutationRate = 0;
			int reproductionRate = 0;
			try {
				Scanner sc = new Scanner(new File("input.txt"));

				while (!sc.hasNextLong())
					sc.next();

				seed = sc.nextLong();
				System.out.println("Seed:\t\t" + seed);

				while (!sc.hasNextInt())
					sc.next();
				size = sc.nextInt();
				System.out.println("Population:\t\t" + size);

				while (!sc.hasNextInt())
					sc.next();
				depth = sc.nextInt();
				System.out.println("Initial Tree Depth:\t\t\t" + depth);

				while (!sc.hasNextInt())
					sc.next();
				offspringDepth = sc.nextInt();
				System.out.println("Maximum Offspring Depth:\t\t\t" + depth);

				while (!sc.hasNextInt())
					sc.next();

				method = sc.nextInt();
				System.out.println("Method:\t\t\t" + method);

				while (!sc.hasNextDouble())
					sc.next();

				bound = (float) sc.nextDouble();
				System.out.println("Bound:\t\t\t" + bound);

				while (!sc.hasNextInt())
					sc.next();

				file = sc.nextInt();
				System.out.println("Equation to Answer:\t" + file);

				while (!sc.hasNextInt())
					sc.next();

				tournament = sc.nextInt();
				System.out.println("Tournament Size:\t" + tournament);

				while (!sc.hasNextInt())
					sc.next();

				crossoverRate = sc.nextInt();
				System.out.println("Crossover Rate:\t\t" + crossoverRate + "%");

				while (!sc.hasNextInt())
					sc.next();

				mutationRate = sc.nextInt();
				System.out.println("Mutation Rate:\t\t" + mutationRate + "%");

				while (!sc.hasNextInt())
					sc.next();

				reproductionRate = sc.nextInt();
				System.out.println("Reproduction Rate:\t" + reproductionRate
						+ "%");

				while (!sc.hasNextInt())
					sc.next();

				noOfOffspring = sc.nextInt();
				System.out.println("Number of Offspring:\t" + noOfOffspring
						+ "\n");

				sc.close();
			} catch (Exception e) {

			}

			Random random = new Random(seed);
			int sum = crossoverRate + mutationRate + reproductionRate;

			if (sum != 100) {
				System.out
						.println("Incorrect Operator Rates. Operator Rates do not equal to 100%");
				System.exit(0);
			}

			if (offspringDepth < depth) {
				System.out
						.println("Offspring Depth must be >= the initial tree depth");
				System.exit(0);
			}

			crossoverSize = (int) (size * ((crossoverRate * 1.0) / 100));

			if (crossoverSize % 2 != 0)
				crossoverSize++;

			mutationSize = (int) (int) (size * ((mutationRate * 1.0) / 100));

			reproductionSize = size - crossoverSize - mutationSize;

			System.out.println("Population for Crossover:\t" + crossoverSize);
			System.out.println("Population for Mutation:\t" + mutationSize);
			System.out.println("Population for Reproduction:\t"
					+ reproductionSize);

			System.out.println("Seed:\t" + seed);

			switch (method) {
			case 1:
				System.out.println("Grow Method");
				for (int i = 0; i < size; i++) {
					trees.add(new Tree(depth, 0, random));
					initialPopulation.add(trees.get(i));
					// System.out.print("Tree " + (i + 1) + "\n");
					// trees.get(i).display();
					// System.out.println();
				}
				break;

			case 2:
				System.out.println("Full Method");
				for (int i = 0; i < size; i++) {
					trees.add(new Tree(depth, 1, random));
					initialPopulation.add(trees.get(i));
					// System.out.print("Tree " + (i + 1) + "\n");
					// trees.get(i).display();
					// System.out.println();
				}
				break;

			case 3:
				trees = RampedHalfAndHalf.ramped(size, depth, random);
				for (int i = 0; i < trees.size(); i++)
					initialPopulation.add(trees.get(i));
				break;

			default:
				System.out.println("Invalid Option");
				break;
			}

			switch (file) {
			case 1:
				filename = "FitnessCases\\equation1.txt";
				break;
			case 2:
				filename = "FitnessCases\\equation2.txt";
				break;
			case 3:
				filename = "FitnessCases\\equation3.txt";
				break;

			case 4:
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter Filename");
				filename = sc.next();
				sc.close();
				break;

			default:
				System.out.println("Invalid Option");
				break;
			}

			int noOfInputs = 0;
			boolean hit = false;
			ArrayList<Tree> successes = new ArrayList<>();
			try {
				Scanner kb = new Scanner(new File(filename));
				while (kb.hasNextLine()) {
					noOfInputs++;
					kb.nextLine();
				}
				kb.close();
			} catch (Exception e) {

			}

			for (int i = 0; i < trees.size(); i++) {
				array = Fitness.getArray(trees.get(i), filename);
				// System.out.println("Tree " + (i + 1) + ": ");
				// trees.get(i).display();
				// trees.get(i).setFitness(Fitness.getRaw(array));
				// System.out.println("Raw Fitness: " +
				// trees.get(i).getFitness());
				int hitsratio = Fitness.getHits(array, bound);
				// System.out.println("Hits Ratio: " + hitsratio);
				trees.get(i).setHits(hitsratio);
				if (hitsratio == noOfInputs) {
					hit = true;
					successes.add(trees.get(i));
				}
				// System.out.println();
			}

			int counter = 1;

			while (counter <= noOfOffspring && !hit) {
				// System.out.println("Run " + counter);

				for (int i = 0; i < reproductionSize; i++) {
					winner = selectParent(trees, tournament, seed);
					Reproduction.reproduce(new Tree(winner));
					offspring.add(Reproduction.getOffspring());
					// Reproduction.display();
				}

				for (int i = 0; i < mutationSize; i++) {

					winner = selectParent(trees, tournament, seed);
					// System.out.println("Parent");
					// winner.display();
					// System.out.println();
					Mutation.mutate(new Tree(winner), offspringDepth, random);
					// Mutation.display();
					offspring.add(Mutation.getOffspring());
				}

				for (int i = 0; i < (crossoverSize / 2); i++) {

					// System.out.println("Choosing Parent 1");
					winner = selectParent(trees, tournament, seed);
					// System.out.println("\nChoosing Parent 2");
					winner2 = selectParent(trees, tournament, seed);

					Crossover.crossover(new Tree(winner), new Tree(winner2),
							offspringDepth, random);
					// Crossover.display();
					for (int j = 0; j < 2; j++)
						offspring.add(Crossover.getOffspring()[j]);
				}

				for (int i = 0; i < offspring.size(); i++) {
					array = Fitness.getArray(offspring.get(i), filename);
					// System.out.println("Tree " + (i + 1) + ": ");
					// offspring.get(i).display();
					// offspring.get(i).setFitness(Fitness.getRaw(array));
					// System.out.println("Raw Fitness: "
					// + offspring.get(i).getFitness());
					int hitsratio = Fitness.getHits(array, bound);
					// System.out.println("Hits Ratio: " + hitsratio);
					offspring.get(i).setHits(hitsratio);
					if (hitsratio == noOfInputs) {
						hit = true;
						successes.add(offspring.get(i));
					}
					// System.out.println();
				}
				counter++;
				trees = (ArrayList<Tree>) offspring.clone();
				offspring.clear();
			}

			BufferedWriter bw = null;
			FileWriter fw = null;
			String FILENAME = "output";
			int t = 1;
			try {

				File f = new File(FILENAME + t + ".txt");

				// if file doesnt exists, then create it

				while (f.exists()) {
					t++;
					f = new File(FILENAME + t + ".txt");
				}
				f.createNewFile();

				// true = append file
				fw = new FileWriter(f.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);

				System.out.println("Parameters");
				bw.write("Parameters\n");

				System.out.println("===========");
				bw.write("===========\n");

				System.out.println("Population Size:\t" + size);
				bw.write("Population Size:\t\t" + size + "\n");

				System.out.println("Initial Tree Depth:\t\t" + depth);
				bw.write("Tree Depth:\t\t\t\t" + depth + "\n");

				System.out.println("Maximum Offspring Size:\t\t"
						+ offspringDepth);
				bw.write("Maximum Offspring Size:\t\t\t\t" + offspringDepth
						+ "\n");

				System.out.print("Method:\t\t\t");
				bw.write("Method:\t\t\t\t\t");
				switch (method) {
				case 1:
					System.out.println("Grow Method");
					bw.write("Grow Method\n");
					break;
				case 2:
					System.out.println("Full Method");
					bw.write("Full Method\n");
					break;
				case 3:
					System.out.println("Ramped Half and Half Method");
					bw.write("Ramped Half and Half Method\n");
					break;
				}

				System.out.println("Bound:\t\t\t" + bound);
				bw.write("Bound:\t\t\t\t\t" + bound + "\n");

				System.out.print("Equation:\t\t");
				bw.write("Equation:\t\t\t\t");
				switch (file) {
				case 1:
					System.out.println("(X^4) + (X^3) + (X^2) + 1");
					bw.write("(X^4) + (X^3) + (X^2) + 1\n");
					break;
				case 2:
					System.out.println("(X^6) -2(X^4) + (X^2) + 1");
					bw.write("(X^6) -2(X^4) + (X^2) + 1\n");
					break;
				case 3:
					System.out
							.println("(X^6) + (X^5) + (X^4) + (X^3) + (X^2) + X");
					bw.write("(X^6) + (X^5) + (X^4) + (X^3) + (X^2) + X\n");
					break;
				}

				System.out.println("Tournament Size:\t" + tournament);
				bw.write("Tournament Size:\t\t" + tournament + "\n");

				System.out.println("Crossover Rate:\t" + crossoverRate + "%");
				bw.write("Crossover Rate:\t\t\t" + crossoverRate + "%\n");

				System.out.println("Mutation Rate:\t" + mutationRate + "%");
				bw.write("Mutation Rate:\t\t\t" + mutationRate + "%\n");

				System.out.println("Reproduction Rate:\t" + reproductionRate
						+ "%");
				bw.write("Reproduction Rate:\t\t" + reproductionRate + "%\n");

				System.out.println("Number of Offspring:\t" + noOfOffspring);
				bw.write("Number of Offspring:\t" + noOfOffspring + "\n\n");

				System.out.println("Seed used: " + ":\t" + seed);
				bw.write("Seed used: " + ":\t" + seed + "\n");

				System.out.println();
				System.out.println("Initial Population");
				bw.write("\nInitial Population\n");
				System.out.println("===================");
				bw.write("===================\n");

				for (int i = 0; i < initialPopulation.size(); i++) {
					System.out.println(initialPopulation.get(i).toString());
					bw.write(initialPopulation.get(i).toString());

					System.out.println("Fitness:\t"
							+ initialPopulation.get(i).getFitness());

					bw.write("Fitness:\t"
							+ initialPopulation.get(i).getFitness() + "\n");

					System.out.println("Hit Ratio:\t"
							+ initialPopulation.get(i).getHits());
					bw.write("Hit Ratio:\t"
							+ initialPopulation.get(i).getHits() + "\n\n\n");

					System.out.println();
				}

				System.out.println();
				System.out.println("Final Generation");
				bw.write("Final Generation\n");
				System.out.println("=================");
				bw.write("=================\n");

				for (int i = 0; i < trees.size(); i++) {
					System.out.println(trees.get(i).toString());
					bw.write(trees.get(i).toString());

					System.out
							.println("Fitness:\t" + trees.get(i).getFitness());

					bw.write("Fitness:\t" + trees.get(i).getFitness() + "\n");

					System.out.println("Hit Ratio:\t" + trees.get(i).getHits());
					bw.write("Hit Ratio:\t" + trees.get(i).getHits() + "\n\n\n");

					System.out.println();
				}

				System.out.println();

				if (successes.isEmpty()) {
					System.out.println("No successes");
					bw.write("No successes\n");
				} else {
					System.out.println("Successes");
					bw.write("Successes\n");
					System.out.println("Generation of Success: "
							+ (counter - 1));

					bw.write("Generation of Success: " + (counter - 1) + "\n");

					for (int i = 0; i < successes.size(); i++) {
						System.out.println(successes.get(i).toString());
						bw.write(successes.get(i).toString());

						System.out.println("Fitness:\t"
								+ successes.get(i).getFitness());

						bw.write("Fitness:\t" + successes.get(i).getFitness()
								+ "\n");

						System.out.println("Hit Ratio:\t"
								+ successes.get(i).getHits());
						bw.write("Hit Ratio:\t" + successes.get(i).getHits()
								+ "\n\n\n");

						System.out.println();
					}
				}

				System.out.println("Done");

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}
			}
			runs++;
		}

	}

	// Selects Parent to undergo generic operations
	public static Tree selectParent(ArrayList<Tree> trees, int t, long seed) {

		int tournament = t;

		Tree winner = null;

		Tree[] pop1 = null;
		// System.out.println("Tournament Participants\n");
		pop1 = Tournament.makeTournament(trees, tournament, seed);
		// Tournament.participants(pop1);
		winner = Tournament.winner(pop1);
		// Tournament.displayWinner();
		// System.out.println("Fitness " + winner.getFitness());
		return winner;
	}

}
