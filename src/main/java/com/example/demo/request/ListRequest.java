package com.example.demo.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zengxi.song
 * @date 2022/11/3
 */
@Setter
@Getter
public class ListRequest {

    private String name;

    private List<String> tags;
}
