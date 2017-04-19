package com.eis.czc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zcoaolas on 2017/4/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article_review {
    private Long id;
    private String ar_comment;
    private Integer ar_result;
    private Integer ar_confidence;
    private Integer ar_point;
}
