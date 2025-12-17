package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    @JsonProperty("id")
    private Long idDto;
    @JsonProperty("name")
    private String nameDto;
    @JsonProperty("studentList")
    private List<Long >studentListDto;
    @JsonProperty("subjectList")
    private List<Long> subjectListDto;
}
