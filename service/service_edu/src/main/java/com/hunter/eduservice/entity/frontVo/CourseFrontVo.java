package com.hunter.eduservice.entity.frontVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseFrontVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String teacherId;

    private String subjectParentId;

    private String subjectId;

    private String buyCountSort;

    private String gmtCreateSort;

    private String priceSort;

}
