package com.gf.ims.annotation;

import java.util.HashMap;
import java.util.Map;
/**
 * 这个类专门用来测试注解使用
 */

@TestA(name="type",gid=Long.class)
// 使用了类注解
public class UserAnnotation {
    @TestA(name="param",id=1,gid=Long.class) // 使用了类成员注解
    private Integer age;

    @TestA(name="construct",id=2,gid=Long.class)// 使用了构造方法注解
    public UserAnnotation() {
    }

    @TestA(name="public method", id=3, gid=Long.class)// 使用了 public 方法注解
    public void a() {
        Map<String, String> m = new HashMap<String, String>(0);
    }

    @TestA(name="protected method", id=4, gid=Long.class)//protected 方法注解
    protected void b() { 
        Map<String, String> m = new HashMap<String, String>(0);
    }
    
    @TestA(name="private method " , id = 5, gid=Long.class) // private 方法注解
    private void c(){
        Map<String, String> m = new HashMap<String, String>(0);
    }
    
    public void b(Integer a){ 
    }
}
