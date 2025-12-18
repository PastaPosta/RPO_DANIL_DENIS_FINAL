package com.example.demo.Mapper;

import com.example.demo.DTO.GroupDTO;
import com.example.demo.Model.Group;
import com.example.demo.Model.Student;
import com.example.demo.Model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @Mapping(target="idDto", source="id")
    @Mapping(target="nameDto", source="name")
    @Mapping(target="studentListDto", expression = "java(mapStudentsIds(group.getStudentList()))")
    @Mapping(target="subjectListDto", expression = "java(mapSubjectsIds(group.getSubjectList()))")
    GroupDTO toDto(Group group);

    @Mapping(target="id", source ="idDto")
    @Mapping(target="name", source ="nameDto")
    @Mapping(target="studentList", expression = "java(mapStudentEntities(groupDTO.getStudentListDto()))")
    @Mapping(target="subjectList", expression = "java(mapSubjectEntities(groupDTO.getSubjectListDto()))")
    Group toEntity(GroupDTO groupDTO);

    List<GroupDTO> toDtoList(List<Group> groupList);

    default List<Long> mapStudentsIds(List<Student> students) {
        if (students == null) return null;
        List<Long> studentIds = new ArrayList<>();
        for (Student student : students) {
            if (student != null) {
                studentIds.add(student.getId());
            }
        }
        return studentIds;
    }

    default List<Long> mapSubjectsIds(List<Subject> subjects) {
        if (subjects == null) return null;
        List<Long> subjectIds = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject != null) {
                subjectIds.add(subject.getId());
            }
        }
        return subjectIds;
    }

    default List<Student> mapStudentEntities(List<Long> studentIds) {
        if (studentIds == null) return null;
        List<Student> studentEntities = new ArrayList<>();
        for (Long studentId : studentIds) {
            Student student = new Student();
            student.setId(studentId);
            studentEntities.add(student);
        }
        return studentEntities;
    }

    default List<Subject> mapSubjectEntities(List<Long> subjectIds) {
        if (subjectIds == null) return null;
        List<Subject> subjectEntities = new ArrayList<>();
        for (Long subjectId : subjectIds) {
            Subject subject = new Subject();
            subject.setId(subjectId);
            subjectEntities.add(subject);
        }
        return subjectEntities;
    }


}
