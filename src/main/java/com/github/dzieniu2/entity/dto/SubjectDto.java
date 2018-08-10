package com.github.dzieniu2.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SubjectDto {

    @NotNull
    @Size(min = 3,max = 20)
    private String name;

    @Min(1)
    private long teacher_id;
}
