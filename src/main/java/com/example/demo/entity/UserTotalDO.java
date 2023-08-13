package com.example.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2022/11/9
 */
@Data
public class UserTotalDO {

    private List<User> result;

    private Integer total;
}
