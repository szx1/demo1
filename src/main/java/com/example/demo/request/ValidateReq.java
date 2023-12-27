package com.example.demo.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zengxi.song
 * @date 2023/11/29
 */
@Setter
@Getter
public class ValidateReq {

    @Length(max = 10, message ="{max.error}")
    private String name;

    @NotNull
    private Integer age;

    @Valid
    @NotNull
    private NestedValidateReq nestedValidateReq;
}
