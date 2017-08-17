package part2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import part1.Tree;

public class Fitness {

	// Defines all actual values for all given values of X
	private static ArrayList<Float> actualValue(Tree tree, Set<Float> value) {
		ArrayList<Float> array = new ArrayList<>();
		for (float s : value) {
			EvaluateExpressionTree.setVar(s);
			array.add(Evaluation.evaluate(tree));
		}
		return array;
	}

	// Creates an array that contains the X values, Target Value of Expression
	// Tree, Actual Value of Expression Tree
	// and |s(i,j) - C(j)|
	public static float[][] getArray(Tree tree, String filename) {
		float[][] output = null;
		try {
			Map<Float, Float> map = Read.readTargetValue(filename);
			output = new float[map.size()][4];

			Set<Float> set = map.keySet();

			ArrayList<Float> array = Fitness.actualValue(tree, set);

			int count = 0;
			for (float s : set) {
				output[count][0] = s;
				output[count][1] = map.get(s);
				output[count][2] = array.get(count);
				output[count][3] = Math.abs(array.get(count) - map.get(s));
				count++;
			}

			tree.setFitness(getRaw(output));

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		return output;
	}

	// Displays the created array
	public static void displayArray(float[][] output) {
		System.out.println("X\t\tTarget\t\tActual\t\t|s(i,j) - C(j)|");
		for (int i = 0; i < output.length; i++) {
			for (int j = 0; j < output[0].length; j++) {
				System.out.print(output[i][j] + "\t\t");
			}
			System.out.println();
		}
	}

	// Calculates the Raw Fitness value of the tree
	public static float getRaw(float[][] array) {
		float sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i][3];
		}
		return sum;
	}

	// Checks if |s(i,j) - C(j)| is less than the specified bound
	private static boolean isBound(float num, float bound) {
		if (num < bound)
			return true;
		return false;
	}

	// Calculates Hit Ratio of the given Tree
	public static int getHits(float[][] array, float bound) {
		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			if (isBound(array[i][3], bound))
				sum++;
		}
		return sum;
	}
}
