package com.example.demo.entity;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2023/2/21
 */
@Data
public class UserCopy {

    private String Name;

    private Integer Age;

    private List<String> Notes;

    public static void main(String[] args) {
        User user =new User();
        user.setName("szx");
        user.setAge(22);
        user.setNotes(Lists.newArrayList("111"));

        UserCopy userCopy=new UserCopy();
        BeanUtils.copyProperties(user,userCopy);
        System.out.println(user);
        System.out.println(userCopy);
    }
}
