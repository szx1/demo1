package com.example.demo.entity;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2022/2/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;

    private Integer age;

    private List<String> notes;

    public static void main(String[] args) {
        List<Parent> list = getList();
        for (Parent parent : list) {
            List<String> list1 = parent.getList();
            System.out.println(list1);
        }
    }

    private static List<Parent> getList(){
        return Lists.newArrayList(new Son(),new Daughter());
    }
}
