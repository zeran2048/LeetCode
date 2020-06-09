package main;

/**
 * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 *
 * 示例 1:
 *
 * 输入: 12258
 * 输出: 5
 * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
 *
 */
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
