package com.Entity.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Donnie on 2017/2/20.
 */
@Getter @Setter
public class Text {
    public Text(String content){
        this.content = content;
    }
    private String content;

}
