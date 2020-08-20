package com.freeziyou.newcoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NewcoderApplication.class)
public class NewcoderApplicationTests {

    @Test
    public void test() {
        Integer a = new Integer(10);
        Integer b = new Integer(10);
        Object e = new Object();
        Object ee = new Object();
        Student stu1 = new Student(22, "dd");
        Student stu2 = new Student(22, "dd");


        HashMap<Object, Object> map = new HashMap();

        map.put(stu1, 1);
        map.put(stu2, 2);

        System.out.println(stu1.equals(stu2));
        System.out.println(map.get(stu1));
        System.out.println(map.get(stu2));
        System.out.println(map);
    }

    class Student {
        public int age;
        public String name;

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Student student = (Student) o;

            if (age != student.age) return false;
            return name != null ? name.equals(student.name) : student.name == null;
        }

//        @Override
//        public int hashCode() {
//            int result = age;
//            result = 31 * result + (name != null ? name.hashCode() : 0);
//            return result;
//        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
