import java.util.Scanner;

public class Levenstein {
    static int needed_n;
    static int needed_m;
    static char[][] numbers;
    static String first;
    static String second;

    static int editDistTd(int i, int j) {
        if (i == 0) return j;
        if (j == 0) return i;
        if (numbers[i][j] == '∞') {
            int ins = editDistTd(i, j - 1) + 1;
            int del = editDistTd(i - 1, j) + 1;
            int sub = editDistTd(i - 1, j - 1) + (first.charAt(i - 1) != second.charAt(j - 1) ? 1 : 0);
            numbers[i][j] = (char) Integer.min(ins, Integer.min(del, sub));
        }
        return numbers[i][j];
    }

    static int[][] editDistBU() {
        int[][] values = new int[needed_n][needed_m];
        for (int i = 0; i < needed_n; i++) values[i][0] = i;
        for (int j = 0; j < needed_m; j++) values[0][j] = j;
        for (int i = 1; i < needed_n; i++)
            for (int j = 1; j < needed_m; j++)
                values[i][j] = Integer.min(values[i - 1][j] + 1, Integer.min(values[i][j - 1] + 1, values[i - 1][j - 1] + (first.charAt(i - 1) != second.charAt(j - 1) ? 1 : 0)));
        return values;
    }

    static void reincarnate(int[][] values, String first_input, String second_input) {
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        int first_length = first_input.length();
        int second_length = second_input.length();
        while (first_length > 0 && second_length > 0) {
            if (values[first_length][second_length] == values[first_length - 1][second_length] + 1) {
                first.insert(0, first_input.charAt(first_length - 1));
                second.insert(0, "_");
                first_length--;
            } else if (values[first_length][second_length] == values[first_length][second_length - 1] + 1) {
                first.insert(0, "_");
                second.insert(0, second_input.charAt(second_length - 1));
                second_length--;
            } else if (values[first_length][second_length] == values[first_length - 1][second_length - 1] + (first_input.charAt(first_length - 1) != second_input.charAt(second_length - 1) ? 1 : 0)) {
                first.insert(0, first_input.charAt(first_length - 1));
                second.insert(0, second_input.charAt(second_length - 1));
                first_length--;
                second_length--;
            }
        }
        System.out.println("Using data from editDistBU, program has found changes:");
        System.out.println("Changes in first word: " + first);
        System.out.println("Changes in second word: " + second);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Good day! Please, enter your words:\nFirst: ");
        first = in.next();
        System.out.print("Great! And now second: ");
        second = in.next();
        needed_n = first.length();
        needed_m = second.length();
        numbers = new char[needed_n + 1][needed_m + 1];
        for (int i = 0; i <= needed_n; i++)
            for (int j = 0; j <= needed_m; j++)
                numbers[i][j] = '∞';
        System.out.println("Minimum Edit Distance, according to editDistTD: " + editDistTd(needed_n, needed_m));
        needed_n++;
        needed_m++;
        int[][] result_BU = editDistBU();
        System.out.println("Minimum Edit Distance, according to editDistBU: " + result_BU[--needed_n][--needed_m]);
        reincarnate(result_BU, first, second);
    }
}
