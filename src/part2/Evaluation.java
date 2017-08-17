package part2;

import part1.Tree;

public class Evaluation {
	// Evaluates Expression Tree
	public static float evaluate(Tree tree) {
		float result = 0;
		try {
			result = EvaluateExpressionTree.evaluate(tree.getRoot());

		} catch (Exception e) {
			System.out
					.println("\nCannot Divide by Zero. Expression Tree is unsuitable");
		}
		return result;
	}

}
