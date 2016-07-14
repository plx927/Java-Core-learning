package com.panlingxiao.relfect;

import org.junit.Test;
import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by panlingxiao on 2016/7/14.
 */
public class ReflectTest {


    class MyClass {
        @MethodAnnotation
        private String method1(@Param("num") @Param2("abc") Integer num, @Param("hello") String hello, @Param("args") Object... args) {
            return "hello";
        }
    }

    /**
     * 反射方法名称，返回值类型、参数类型、泛型类型参数
     * 如何获取方法的参数名称
     */
    @Test
    public void testReflectMethod() {
        Method[] declaredMethods = MyClass.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();
            Class[] parameterTypes = method.getParameterTypes();
            System.out.println("方法名:" + methodName + ",方法返回值:" + returnType + ",方法参数类型:" + Arrays.asList(parameterTypes));
            //默认情况下是无法获取到方法的参数名称
            System.out.println(method);
            //获取方法上使用的注解
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                System.out.println(annotation);
            }
            System.out.println("-------------------------");
            //获取参数的注解,每个参数可以由多个注解修饰
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                for (Annotation paramAnn : parameterAnnotation) {
                    System.out.println(paramAnn);
                }
            }

        }


    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Param {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @interface Param2 {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface MethodAnnotation {

    }


    /**
     * 获取指定类指定方法的参数名
     *
     * @param clazz  要获取参数名的方法所属的类
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表，如果没有参数，则返回null
     */
    public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0) {
            return null;
        }
        final Type[] types = new Type[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            types[i] = Type.getType(parameterTypes[i]);
        }
        final String[] parameterNames = new String[parameterTypes.length];

        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(".");
        className = className.substring(lastDotIndex + 1) + ".class";
        InputStream is = clazz.getResourceAsStream(className);
        try {
            ClassReader classReader = new ClassReader(is);
            classReader.accept(new ClassVisitor(Opcodes.ASM4) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    // 只处理指定的方法
                    Type[] argumentTypes = Type.getArgumentTypes(desc);
                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
                        return null;
                    }
                    return new MethodVisitor(Opcodes.ASM4) {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                            // 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this
                            if (Modifier.isStatic(method.getModifiers())) {
                                parameterNames[index] = name;
                            } else if (index > 0) {
                                parameterNames[index - 1] = name;
                            }
                        }
                    };

                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameterNames;
    }
}
