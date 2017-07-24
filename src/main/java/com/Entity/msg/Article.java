package com.Entity.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Donnie on 2017/2/27.
 */
@Getter @Setter
@NoArgsConstructor
public class Article {
    private String title;
    private String thumb_media_id;//suoluetu
    private String author;
    private String content_source_url;
    private String content;
    private String digest;
    private int show_cover_pic;

    public Article(String thumb_media_id, String title) {
        this.thumb_media_id = thumb_media_id;
        this.title = title;
    }
}
