package com.eis.czc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zcoaolas on 2017/4/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {
    private List<Timestamp> timeList;
    private List<String> imgUrlList;
    private String blogContent;
    private String blogTitle;
    private String blogPlace;
    private Integer blogCategory;
    private User author;
    private User firstReviewer;
    private User secondReviewer;
    private User thirdReviewer;
    private User editor;
}
