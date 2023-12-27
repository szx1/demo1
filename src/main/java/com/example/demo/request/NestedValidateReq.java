package com.example.demo.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author zengxi.song
 * @date 2023/11/29
 */
@Setter
@Getter
public class NestedValidateReq {

    @Length(max = 5)
    private String note;
}
