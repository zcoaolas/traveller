package com.eis.czc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by zcoaolas on 2017/4/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    private Long id;
    private String ar_content;
    private String ar_title;
    private String ar_place;
    private String ar_category;
    private String ar_like_list;
    private String ar_collect_list;
    private String ar_read_list;

    private List<Article_time> ar_time_list;
    private List<Article_url> ar_url_list;
    private User ar_author;
    private List<User> ar_reviewer;
    private User ar_editor;
    private List<Article_review> ar_review_list;
    private List<Article_tag> ar_tag_list;

}
