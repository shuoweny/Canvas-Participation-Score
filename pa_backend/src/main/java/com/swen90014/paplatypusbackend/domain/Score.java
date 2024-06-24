
package com.swen90014.paplatypusbackend.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("score")
public class Score {
    @TableId(value = "id")
    private Long id;

    private Double score;

    @JsonProperty("assignment_id")
    private Integer assignmentId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("submitted_at")
    private Timestamp submittedAt;

    private Boolean excused;

    private Boolean late;

    private Boolean missing;

    @JsonProperty("entered_score")
    private Double enteredScore;

    private Double participationScoreWeight;

    @JsonProperty("course_id")
    private Long courseId;

    private Double participationScoreComplete;
}

