package com.hunter.eduservice.entity.frontVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String courseId;
    private String teacherId;
}
