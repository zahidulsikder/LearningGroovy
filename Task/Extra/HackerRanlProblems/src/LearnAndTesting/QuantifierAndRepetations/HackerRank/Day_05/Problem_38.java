package LearnAndTesting.QuantifierAndRepetations.HackerRank.Day_05;

import java.util.Scanner;

public class Problem_38 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.startsWith("hackerrank") && line.endsWith("hackerrank")) {
                System.out.println(0);
            } else if (line.startsWith("hackerrank")) {
                System.out.println(1);
            } else if (line.endsWith("hackerrank")) {
                System.out.println(2);
            } else {
                System.out.println(-1);
            }
        }

        scanner.close();
    }
}
