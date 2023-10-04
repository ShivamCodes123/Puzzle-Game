import java.util.Scanner;

public class PuzzleRunner {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a number greater than one to set the size of your puzzle (Reccomended 3)");
		int x = sc.nextInt();
		new Puzzle(x);
	}
}
