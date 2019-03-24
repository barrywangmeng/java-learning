package cn.barrywangmeng.sort;

/**
 * @Description: 将两个有序的集合合并
 * @Author: wangmeng
 * @Date: 2019/1/29-18:45
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] a = {10, 30, 51, 58, 62, 68, 79, 90};
        int[] b = {9, 12, 36, 60};
        merge(a, a.length, b, b.length);
    }

    private static int[] merge(int[] a, int aLength, int[] b, int bLength) {
        int[] newArray = new int[aLength + bLength];
        while (aLength > 0 && bLength > 0) {
            if (a[aLength - 1] > b[bLength - 1]) {
                newArray[aLength + bLength - 1] = a[aLength - 1];
                aLength--;
            } else {
                newArray[aLength + bLength - 1] = b[bLength - 1];
                bLength--;
            }
        }

        return newArray;
    }
}
