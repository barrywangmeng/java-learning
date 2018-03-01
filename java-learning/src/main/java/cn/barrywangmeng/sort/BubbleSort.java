package cn.barrywangmeng.sort;

/**
 * 冒泡排序
 * 它重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。走访数列的工作是重复地进行直到没有再需要交换。
 * 算法的平均时间复杂度为O(n^2)。
 * 但是若在某趟排序中未发现气泡位置的交换，则说明待排序的无序区中所有气泡均满足轻者在上，重者在下的原则，即为正序。
 * 则冒泡排序过程可在此趟扫描后就终止，基于这种考虑，提出了第一种改进的算法。
 */
public class BubbleSort {
    public static void bubbleSort(int[] sortItems) {
        int size = sortItems.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (sortItems[j] > sortItems[j + 1]) {
                    int temp = sortItems[j + 1];
                    sortItems[j + 1] = sortItems[j];
                    sortItems[j] = temp;
                }
            }
        }

        for (int sortItem : sortItems) {
            System.out.println(sortItem);
        }
    }

    public static void main(String[] args) {
        bubbleSort(new int[]{3, 5, 2, 1, 6});
    }
}
