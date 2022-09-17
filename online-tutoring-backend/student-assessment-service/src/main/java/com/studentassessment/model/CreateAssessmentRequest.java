package com.studentassessment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssessmentRequest {
    @NotEmpty(message = "Grade Id can't be empty")
    private long gradeId;
    @NotEmpty(message = "Subject Id can't be empty")
    private long subjectId;
    @NotEmpty(message = "Assessment Date can't be empty")
    private String assessmentDate;
    @NotEmpty(message = "Teacher Id can't be empty")
    private long teacherId;

}
