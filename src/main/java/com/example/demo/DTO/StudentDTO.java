package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    @JsonProperty("id")
    private Long idDto;

    @JsonProperty("fullName")
    private String fullNameDto;

    @JsonProperty("age")
    private int ageDto;

    @JsonProperty("groupId")
    private Long groupIdDto;

}
