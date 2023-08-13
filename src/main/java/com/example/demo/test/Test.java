package com.example.demo.test;

import com.example.demo.entity.User;
import com.example.demo.enums.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengxi.song
 * @date 2022/3/4
 */
public class Test {



    public static void main(String[] args) {
//        List<User> userList = new ArrayList<>();
//
//        for(int i=0;i<3;i++){
//           User user=new User();
//           user.setName("szx"+i);
//           for(int j=0;j<2;j++){
//
//           }
//        }
        System.out.println(Type.STRING.name());

        String test="1";
        System.out.println(test);
        System.out.println(testInt(1,null));
    }

    public void setTest(String test){
        test="szx";
    }

    public static boolean  testInt(Integer num,Integer num2){
       return num<=num2;
    }
}
