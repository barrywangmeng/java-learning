package cn.barrywangmeng.sort;

/**
 * @Description:选择排序 https://juejin.im/post/5c40837751882525487c5394
 * 选择排序可以说是冒泡排序的改良版，不再是前一个数跟后一个数相比较，
 * 而是在每一次循环内都由一个数去跟 所有的数都比较一次，每次比较都选取相对较小的那个数来进行下一次的比较，
 * 并不断更新较小数的下标 这样在一次循环结束时就能得到最小数的下标，再通过一次交换将最小的数放在最前面，
 * 通过n-1次循环之后完成排序。这样相对于冒泡排序来说，比较的次数并没有改变，但是数据交换的次数大大减少。
 * @Date: 2019/1/24-20:36
 */
public class SelectSort {
    private static void selectSort(int[] sortItems) {
        int length = sortItems.length;
        int minIndex = 0;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (sortItems[minIndex] > sortItems[j]) {
                    minIndex = j;
                }
            }

            if (minIndex > 0) {
                int temp = sortItems[i];
                sortItems[i] = sortItems[minIndex];
                sortItems[minIndex] = temp;
            }
        }

        for (int sortItem : sortItems) {
            System.out.println(sortItem);
        }
    }

    public static void main(String[] args) {
        selectSort(new int[]{3, 5, 2, 1, 6});
    }
}
