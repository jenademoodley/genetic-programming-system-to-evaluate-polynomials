package part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Read {

	// reads the target values and X values from the text file
	public static Map<Float, Float> readTargetValue(String filename)
			throws FileNotFoundException {
		File file = new File(filename);
		Scanner input = new Scanner(file);
		Map<Float, Float> map = new TreeMap<>();
		// Values separated by spaces
		while (input.hasNext()) {
			float x = input.nextFloat();
			float values = input.nextFloat();
			map.put(x, values);
		}
		input.close();
		return map;
	}

}
