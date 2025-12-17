package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    @JsonProperty("id")
    private Long idDto;

    @JsonProperty("name")
    private String nameDto;

    @JsonProperty("teacher")
    private String teacherDto;

    @JsonProperty("length")
    private int lengthDto;

    @JsonProperty("groupList")
    private List<Long> groupListDto;
}
