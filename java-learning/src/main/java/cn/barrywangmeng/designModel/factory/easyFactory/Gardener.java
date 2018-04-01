package cn.barrywangmeng.designModel.factory.easyFactory;

public class Gardener {

    public static Fruit getFruit(String fruit) {
//        if ("apple".equalsIgnoreCase(fruit)) {
//            return new Apple();
//        } else if ("grape".equalsIgnoreCase(fruit)) {
//            return new Grape();
//        } else {
//            return null;
//        }

        /**
         * 进一步优化，因为这里违反了开闭原则，如果新增加一种Fruit实现类，那么这里就需要多加一层if else判断
         */
        try {
            Class<?> cs = Class.forName(fruit);
            return (Fruit) cs.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
