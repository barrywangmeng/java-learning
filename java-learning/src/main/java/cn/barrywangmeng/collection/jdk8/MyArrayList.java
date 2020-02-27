package cn.barrywangmeng.collection.jdk8;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 自己实现ArrayList，主要是扩容步骤
 *
 * @author wangmeng2
 * @date 2020/2/278:31
 */
public class MyArrayList<E> {

    /**
     * 数组默认大小
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * 初始化为一个空数组
     */
    private static final Object[] EMPTY_ELEMENT_DATA = {};

    /**
     * 默认使用的数组
     */
    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};

    /**
     * 数组
     */
    private transient Object[] elementData;

    /**
     * 数组大小
     */
    private int size;

    /**
     * 带有指定容量的list构造函数
     */
    private MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity :" + initialCapacity);
        }
    }

    /**
     * 默认空构造函数
     */
    private MyArrayList() {
        this.elementData = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    /**
     * 添加一个元素
     *
     * @param e 要添加的元素
     * @return 是否添加成功
     */
    public boolean add(E e) {
        // 判断数组是否需要扩容
        ensureCapacityInternal(size + 1);
        // 设置值
        elementData[size++] = e;
        return true;
    }

    /**
     * 指定位置插入数据
     *
     * @param index 指定的位置
     * @param element 要添加的元素
     */
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
        }

        ensureCapacityInternal(size +1);
        // 从原来的element第index位置开始拷贝，拷贝size - index个元素
        // 到新的element上
        /**
         * 比如 elementData[0, 1, 2, 3, 4, 5]  index = 1
         * System.arraycopy(elementData, 1, elementData, 2, 5);
         *
         * 拷贝后变成elementData[0, 1, 1, 2, 3, 4, 5]
         */
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * 计算是否需要扩容
     */
    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    /**
     * 判断是否扩容
     */
    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - elementData.length > 0) {
            // 扩容
            grow(minCapacity);
        }
    }

    /**
     * 扩容
     */
    private void grow(int minCapacity) {
        // 扩容的原理是使用Arrays.copyOf
        // 扩容大小为之前容量大小的1.5倍
        int oldCapacity = elementData.length;
        // 位操作，右移1位 oldCapacity / 2
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        if (newCapacity - Integer.MAX_VALUE > 0) {
            throw new OutOfMemoryError();
        }
        // 数组拷贝
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 为数组设置初始化容量
     * @param elementData 数组 第一次时数组为空
     * @param minCapacity 数组大小
     * @return 数组大小
     */
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            return Math.max(DEFAULT_SIZE, minCapacity);
        }

        // 如果初始化传递的有capacity，则使用传递的capacity
        return minCapacity;
    }

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        System.out.println(list);

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(1, 6);
        System.out.println("aaa");

        int[] arrays = {0, 1, 2, 3, 4, 5};
        System.arraycopy(arrays, 1, arrays, 2, 5);
        System.out.println("aaa");
    }
}