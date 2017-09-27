package retainall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Retainer {

	public static void main(String[] args) {
		List<String> listOne = new ArrayList<>();
		List<String> listTwo = new ArrayList<>();
		String[] toOne = {"a", "b", "c"};
		String[] toTwo = {"b", "d", "e"};
		listOne.addAll(Arrays.asList(toOne));
		listTwo.addAll(Arrays.asList(toTwo));
		Set<String> setOne = new HashSet<>();
		setOne.addAll(listOne);
		listTwo.removeIf(e -> !setOne.contains(e));
		System.out.println(listTwo);
	}
}
