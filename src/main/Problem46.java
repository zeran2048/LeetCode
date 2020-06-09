package main;

public class Problem46 {

    public static void main(String[] args) {
        int count = translateNum(506);
        System.out.println(count);
    }


    private static int translateNum(int num) {
        String str = num + "";
        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        int count = 1;
        count += translateNum(0, ints);
        return count;
    }

    private static int translateNum(int start, int[] ints) {
        int count = 0;
        for (int i = start; i < ints.length - 1; i++) {
            int val = ints[i] * 10 + ints[i + 1];
            if (val <= 25 && val >= 0 && ints[i] != 0) {
                count++;
                count += translateNum(i + 2, ints);
            }
        }
        return count;
    }
}
