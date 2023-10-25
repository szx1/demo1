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

        System.out.println(Type.STRING.name());

        String txt="license:\n" +
                "HJgJ8pnzMC5SuRHwLPQDIXS5vthvZCMYM+yibV2ty4kyGVAQvRdAxG53br84rPVepBJmM19aJUT2ZqO3dwOw1d+sPrB21TVqUDjkcWELgSXM0rpdn5qsuk06oqWYF6+ZmlG8oWhbc5+FNqukF448F3ls0PjP1uUyKLxSYOv/C1k=\n" +
                "publicKey:\n" +
                "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAO1pNiTWG4iXIIFHl0OoGAib4bbC53m8fvwVAqbFS1x5H1JYUE_vCqnPKfgcyDBOdpDiYC6we16lISBlK0SFeQsCAwEAAQ";
        String[] split = txt.split("\n");
        System.out.println(split.length);
    }
}
