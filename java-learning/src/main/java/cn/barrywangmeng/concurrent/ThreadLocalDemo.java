package cn.barrywangmeng.concurrent;

/**
 * @Description: ThreadLocalDemo
 * @Author: wangmeng
 * @Date: 2020/5/7 21:03
 */
public class ThreadLocalDemo {

    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) {
        int hashCode = 0;
        for(int i=0; i< 16; i++){
            hashCode = i * HASH_INCREMENT+HASH_INCREMENT;
            int bucket = hashCode & 15;
            System.out.println(i + " 在桶中的位置:" + bucket);
        }
    }
}
