package com.github.dzieniu2.entity.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
public class SubjectDto {

    @NotNull
    @Size(min = 3,max = 20)
    private String name;

    @Min(1)
    private long teacher_id;
}
