package com.example.demo.Mapper;

import com.example.demo.DTO.SubjectDTO;
import com.example.demo.Model.Group;
import com.example.demo.Model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target="idDto", source="id")
    @Mapping(target="nameDto", source="name")
    @Mapping(target="teacherDto", source="teacher")
    @Mapping(target="lengthDto", source="length")
    @Mapping(target="groupListDto", expression = "java(mapGroupIds(subject.getGroupList()))" )
    SubjectDTO toDto(Subject subject);

    @Mapping(target="id", source="idDto")
    @Mapping(target="name", source="nameDto")
    @Mapping(target="teacher", source="teacherDto")
    @Mapping(target="length", source="lengthDto")
    @Mapping(target="groupList", expression="java(mapGroupEntities(subjectDto.getGroupListDto()))")
    Subject toEntity(SubjectDTO subjectDto);

    List<SubjectDTO> toDtoList(List<Subject> subjects);

    default List<Long> mapGroupIds(List<Group> groups) {
        if (groups==null){return null;}
        List<Long> groupIds = new ArrayList<>();
        for (Group group : groups) {
            if (group.getId()!=null){
                groupIds.add(group.getId());
            }
        }
        return groupIds;
    }

    default List<Group> mapGroupEntities(List<Long> groupIds) {
        if (groupIds==null){return null;}
        List<Group> groupEntities = new ArrayList<>();
        for (Long groupId : groupIds) {
            Group group = new Group();
            group.setId(groupId);
            groupEntities.add(group);
        }
        return groupEntities;
    }
}
