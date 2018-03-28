package cn.barrywangmeng.basic;

/**
 * 面试小米时候的笔试题
 * 2018年3月27日
 */
public class SwitchTest {
    public static void main(String[] args) {
        int i = 2;
        int result = 0;
        switch (i) {
            case 1 :
                result = result + i * 2;
            case 2 :
                result = result + i * 2;
            case 3 :
                result = result + i * 2;
        }

        //主要考察swith case，case 2 和case 3都会被执行。
        System.out.println(result);
    }
}
