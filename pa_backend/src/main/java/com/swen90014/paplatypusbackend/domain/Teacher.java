
package com.swen90014.paplatypusbackend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("teacher")
public class Teacher {
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Long teacherId;

    private Long id;

    private String name;

    @JsonProperty("sis_user_id")
    private String sisUserId;

    @JsonProperty("login_id")
    private String loginId;

    private String type;

    @JsonProperty("course_id")
    private Long courseId;

    private String email;

    private boolean access;
}
