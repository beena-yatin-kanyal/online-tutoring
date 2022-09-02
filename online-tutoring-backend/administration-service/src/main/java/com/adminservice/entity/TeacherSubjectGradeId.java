package com.adminservice.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSubjectGradeId implements Serializable {
    private Long teacherId;
    private Long subjectId;
    private Long gradeId;
}
