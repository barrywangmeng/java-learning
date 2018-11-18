package cn.barrywangmeng.sort;

/**
 * 插入排序
 *
 * @author: barry wang
 * @date: 2018/8/21 21:46
 */
public class InsertSort {
    public static void sort(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int currentNumber = numbers[i];
            int j = i - 1;

            while (j >= 0 && numbers[j] > currentNumber) {
                numbers[j + 1] = numbers[j];
                j--;
            }

            numbers[j + 1] = currentNumber;
        }
    }
}
