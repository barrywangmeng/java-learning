package cn.barrywangmeng.collection.jdk8;

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
     * @param e 集合中的元素
     * @return 是否添加成功
     */
    public boolean add(E e) {
        // 判断数组是否需要扩容
//        ensureCapacityInternal(size + 1);
        // 设置值
        elementData[size++] = e;
        return true;
    }

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        System.out.println(list);
    }
}