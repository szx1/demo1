package com.example.demo.entity;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2022/11/30
 */
public class Daughter extends Parent{
    @Override
    public List<String> getList() {
        List<String> list = super.getList();
        list.add("2");
        return list;
    }
}
