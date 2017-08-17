package part2;

import part1.TreeNode;

public class EvaluateExpressionTree {
	// Variables
	static float var = 1;

	// Sets the Value of X
	public static void setVar(float v) {
		var = v;
	}

	// Checks if a String is a number
	private static boolean isNo(String no) {
		try {
			Float.parseFloat(no);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	// Checks if a string matches the variable
	private static boolean isVariable(String var) {
		if (var.equals("X"))
			return true;
		return false;
	}

	// Evaluates the Expression Tree
	public static float evaluate(TreeNode<String> root) {
		if (root == null)
			return 0;
		float left = 0;
		float right = 0;
		if (root.getArity() != 0) {
			left = evaluate(root.getChild(0));
			right = evaluate(root.getChild(1));
		}

		if (isNo(root.getElement())) {
			return Float.parseFloat(root.getElement());
		}

		else if (isVariable(root.getElement()))
			return var;

		else
			return getResult(left, right, root.getElement());

	}

	private static float getResult(float left, float right, String data) {

		switch (data) {
		case "+":
			return left + right;
		case "-":
			return left - right;
		case "*":
			return left * right;
		case "/":
			if (right == 0)
				return 1;
			return left / right;
		default:
			throw new RuntimeException("Invalid expression");

		}
	}

}