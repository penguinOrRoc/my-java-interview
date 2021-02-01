package com.penguin.java;

public class StringPool {
    public static void main(String[] args){
        String str01 = new StringBuilder("1.8.0").append("_281").toString();
        System.out.println(str01);
        System.out.println(str01.intern());
        System.out.println(str01 == str01.intern());
        System.out.println("----------------------------------");
        //public native String intern();
        String str02 = new StringBuilder("String").append("Pool").toString();
        System.out.println(str02);
        System.out.println(str02.intern());
        System.out.println(str02 == str02.intern());
        /**
         * 知识回顾：
         * 1.运行时常量池是方法区的一部分，JDK1.8中使用元空间，替代JDK1.7及以前永久代实现方法区
         * 永久代时期：通过-XX：PermSize 和-XX：MaxPermSize间接限制永久代大小
         * 2.intern()方法是一个本地方法。
         * 如果字符串常量池已经包含一个等于此String对象的字符串，则返回代表这个字符串的String对象的引用；
         * 否则，会将此String对象包含的字符串添加到常量池中，并返回该String对象的引用。
         */
        /**
         * 问题：为什莫只有字符串java结果为false？（JAVA、Java等也都为true）
         * 解答：sun.misc.Version类会在JDK类库初始化的过程中被加载并初始化，而在初始化时，它需要对静态常量指定的字段，根据指定值（ContantValue）做默认初始化，此时被sun.misc.Version.launcher静态常量引用的字符串字面量就被intern到HotSpot VM的字符串常量池（StringTable）
         * 拓展：sun.misc.Version.class中的几个常量都是false
         * 参考：《深入理解JAVA虚拟机》第3版 P63 intern()方法
         * 在JDK1.7及以后，只需要在常量池中记录首次出现位置即可；而该字符串在StringBuilder。toString()方法执行前，已经在常量池出现。*
         * 递推：
         * 1.System源码 > initializeSystemClass > sun.misc.Version.init() >sun.misc.Version > private static final java.lang.String launcher_name = "java";
         * 2.类加载器和rt.jar
         * 3.OpenJDK源码
         */


    }
}
