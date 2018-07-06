package com.panlingxiao.annotation;

import java.lang.annotation.Annotation;

/**
 * Created by panlingxiao on 2018/7/6.
 */
public class AnnotationTest {

    public static void main(String[] args) {
        Star star = Person.class.getAnnotation(Star.class);
        // Java中的注解实际上是一个继承Annotation的接口，注解的实现类是一个动态代理
        System.out.println(star.getClass().getName());

        System.out.println(Man.class.getDeclaredAnnotation(Star.class));
        System.out.println(Man.class.getAnnotation(Star.class));


        Annotation[] annotations = Person.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        annotations = Person.class.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

    }
}
