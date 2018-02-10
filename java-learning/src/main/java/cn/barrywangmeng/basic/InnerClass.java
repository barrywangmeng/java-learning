package cn.barrywangmeng.basic;

/**
 * 内部类测试
 */
public class InnerClass {
    public String destionation(String str) {

        class Destination {
            private String label;
            private Destination(String whereTo) {
                label = whereTo;
            }

            public String readLabel() {
                return  label;
            }
        }

        return new Destination(str).readLabel();
    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        String wangmeng = innerClass.destionation("wangmeng");
        System.out.println(wangmeng);
    }
}
