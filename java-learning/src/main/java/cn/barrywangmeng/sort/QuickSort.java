package cn.barrywangmeng.sort;

/**
 * 快排
 * 快速排序时基于分治模式处理的，在数据集之中，选择一个元素作为”基准”（pivot），所有小于”基准”的元素，都移到”基准”的左边；所有大于”基准”的元素，都移到”基准”的右边。这个操作称为分区 (partition) 操作，分区操作结束后，基准元素所处的位置就是最终排序后它的位置。对”基准”左边和右边的两个子集，不断重复第一步和第二步，直到所有子集只剩下一个元素为止。
 *
 * http://www.jb51.net/article/83519.htm
 * http://ginobefunny.com/post/java_other_interview_questions/
 */
public class QuickSort {
    private static void quickSort(int[] sortItems) {
        if (sortItems.length > 0) {
            quickSort(sortItems, 0, sortItems.length - 1);
        }

        for (int sortItem : sortItems) {
            System.out.println(sortItem);
        }
    }

    private static void quickSort(int[] sortItems, int low, int high) {
        if (low < high) {
            int middle = getMiddle(sortItems, low, high);
            quickSort(sortItems, low, middle - 1);
            quickSort(sortItems, middle + 1, high);
        }
    }

    private static int getMiddle(int[] sortItems, int low, int high) {
        int temp = sortItems[low];
        while (low < high) {
            //先从右往左找，如果大于temp则继续找，直到找到小于temp的值
            while (low < high && sortItems[high] > temp) {
                high--;
            }
            sortItems[low] = sortItems[high];

            //再从左往右找，如果小于temp则继续找，直到找到大于temp的值
            while (low < high && sortItems[low] < temp) {
                low++;
            }
            sortItems[high] = sortItems[low];
        }

        sortItems[low] = temp;
        //这个是执行完后low的位置
        return low;
    }

    public static void main(String[] args) {
        quickSort(new int[]{3, 5, 2, 10, 6, 9, 20});
    }
}
