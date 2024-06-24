package com.swen90014.paplatypusbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParticipationWeightDTO {
    private Long id;
    private String type;
    private Double participationScoreWeight;
    private Double participationScoreComplete;
    private Long assignmentId;
    private String name;
    private Long userId;
    private Double score;
    private String method;
}
