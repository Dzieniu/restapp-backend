package com.github.dzieniu2.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeDto {

    @NotNull
    @Size(min = 2,max = 5)
    private int value;

    @NotNull
    @Min(1)
    private long student_id;

    @NotNull
    @Min(1)
    private long subject_id;
}
