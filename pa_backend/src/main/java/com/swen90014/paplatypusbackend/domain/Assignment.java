package com.swen90014.paplatypusbackend.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("assignment")
public class Assignment {
    private Long id;
    private String description;
    @JsonProperty("due_at")
    private Timestamp dueAt;

    @JsonProperty("unlock_at")
    private Timestamp unlockAt;

    @JsonProperty("lock_at")
    private Timestamp lockAt;

    @JsonProperty("points_possible")
    private BigDecimal pointsPossible;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    @JsonProperty("updated_at")
    private Timestamp updatedAt;

    @JsonProperty("published")
    private Boolean published;

    @JsonProperty("course_id")
    private Long courseId;

    private String name;

    private Integer participationWeighting;

    private String type;

    private Integer defaultParticipationWeighting;

    private String method;
    @JsonProperty("default_method")
    private String defaultMethod;
}
