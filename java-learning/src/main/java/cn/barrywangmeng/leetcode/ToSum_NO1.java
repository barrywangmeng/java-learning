package cn.barrywangmeng.leetcode;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 两数之和
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。<br>
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。<br>
 *<p>
 * 示例:<p>
 * 给定 nums = [2, 7, 11, 15], target = 9<p>
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9<p>
 * 所以返回 [0, 1]<p>
 *</p>
 * @author wangmeng
 * @date 2019/4/11
 */
public class ToSum_NO1 {

    /**
     * 自己的答案
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoSumSelf(int[] nums, int target) {
        int[] result = new int[2];
        breakLoop:
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    break breakLoop;
                }
            }
        }

        return result;
    }

    /**
     * 最优解
     * @param nums
     * @param target
     * @return
     */
    private static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = Maps.newHashMap();
        for (int i = 0; i < nums.length; i++) {
            int complete = target - nums[i];
            if (map.containsKey(complete)) {
                return new int[]{map.get(complete), i};
            }

            map.put(nums[i], i);
        }

        return null;
    }

    public static void main(String[] args) {
        int[] values = new int[]{2, 7, 11, 15};
        int[] index = twoSum(values, 22);
        if (index != null && index.length > 0) {
            for (int i : index) {
                System.out.println(i);
            }
        }
    }
}
