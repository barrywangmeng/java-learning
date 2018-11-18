package cn.barrywangmeng.basic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransformTest {
    public static void main(String[] args) {
        List<Student> students = Lists.newArrayList();
        Student student1 = new Student();
        student1.setAge(10);
        student1.setName("test1");
        students.add(student1);

        Student student2 = new Student();
        student2.setAge(20);
        student2.setName("test2");
        students.add(student2);

        List<Student> newStudents = students.stream().peek(student -> student.setName("test3")).collect(Collectors.toList());
        for (Student newStudent : newStudents) {
            System.out.println(newStudent.toString());
        }

        Map<Long, String> map = Maps.newHashMap();
        map.put(1L, "222");
        Integer s = 1;
        System.out.println(map.get(s));
    }
}
