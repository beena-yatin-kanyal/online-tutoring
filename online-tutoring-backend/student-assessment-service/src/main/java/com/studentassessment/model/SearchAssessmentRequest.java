package com.studentassessment.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SearchAssessmentRequest {

    private Long teacherId;
    private Long subjectId;
    private Long gradeId;
}
