package com.example.demo.TestMapper;

import com.example.demo.DTO.GroupDTO;
import com.example.demo.Mapper.GroupMapper;

import com.example.demo.Model.Group;
import com.example.demo.Model.Student;
import com.example.demo.Model.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestGroupMapper {
    @Autowired
    private GroupMapper groupMapper;

    private Group testGroup;
    private GroupDTO testGroupDto;
    private List<Group> testGroupList;

    @BeforeEach
    public void setUp() {

        testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group_name1");

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1L, "student_name1", 1, testGroup));
        studentList.add(new Student(2L, "student_name2", 2, testGroup));

        testGroup.setStudentList(studentList);

        List<Subject> subjectList = new ArrayList<>();
        Subject subject1 = new Subject(1L, "subject_name1", "teacher_name1", 1, new ArrayList<>());
        Subject subject2 = new Subject(2L, "subject_name2", "teacher_name2", 1, new ArrayList<>());
        subjectList.add(subject1);
        subjectList.add(subject2);

        testGroup.setSubjectList(subjectList);

        subject1.getGroupList().add(testGroup);
        subject2.getGroupList().add(testGroup);

        testGroupDto = groupMapper.toDto(testGroup);

        testGroupList = new ArrayList<>();
        testGroupList.add(testGroup);

    }


    @Test
    public void testToDto() {
        GroupDTO groupDto  = groupMapper.toDto(testGroup);

        Assertions.assertNotNull(groupDto);

        Assertions.assertNotNull(groupDto.getIdDto());
        Assertions.assertNotNull(groupDto.getNameDto());
        Assertions.assertNotNull(groupDto.getStudentListDto());
        Assertions.assertNotNull(groupDto.getSubjectListDto());

        Assertions.assertEquals(testGroup.getId(), groupDto.getIdDto());
        Assertions.assertEquals(testGroup.getName(), groupDto.getNameDto());
        Assertions.assertEquals(testGroup.getStudentList().size(),  groupDto.getStudentListDto().size());
        Assertions.assertEquals(testGroup.getSubjectList().size(),  groupDto.getSubjectListDto().size());

        for (int i=0; i<testGroup.getStudentList().size(); i++){
            Student student = testGroup.getStudentList().get(i);

            Assertions.assertNotNull(groupDto.getStudentListDto().get(i));
            Assertions.assertEquals(student.getId(), groupDto.getStudentListDto().get(i));
        }

        for (int i=0; i<testGroup.getSubjectList().size(); i++){
            Subject subject = testGroup.getSubjectList().get(i);

            Assertions.assertNotNull(groupDto.getSubjectListDto().get(i));
            Assertions.assertEquals(subject.getId(), groupDto.getSubjectListDto().get(i));
        }
    }

    @Test
    public void testToEntity() {
        Group group = groupMapper.toEntity(testGroupDto);
        Assertions.assertNotNull(group);

        Assertions.assertNotNull(group.getId());
        Assertions.assertNotNull(group.getName());
        Assertions.assertNotNull(group.getStudentList());
        Assertions.assertNotNull(group.getSubjectList());

        Assertions.assertEquals(testGroupDto.getIdDto(), group.getId());
        Assertions.assertEquals(testGroupDto.getNameDto(), group.getName());
        Assertions.assertEquals(testGroupDto.getStudentListDto().size(), group.getStudentList().size());
        Assertions.assertEquals(testGroupDto.getSubjectListDto().size(), group.getSubjectList().size());

        for (int i=0; i<testGroupDto.getStudentListDto().size(); i++){
            Long id = group.getStudentList().get(i).getId();

            Assertions.assertNotNull(id);
            Assertions.assertEquals(testGroupDto.getStudentListDto().get(i), id);
        }

        for (int i=0; i<testGroupDto.getSubjectListDto().size(); i++){
            Long id = group.getSubjectList().get(i).getId();

            Assertions.assertNotNull(id);
            Assertions.assertEquals(testGroupDto.getSubjectListDto().get(i), id);
        }
    }

    @Test
    public void testToDtoList() {
        List<GroupDTO> groupDtoList = groupMapper.toDtoList(testGroupList);

        Assertions.assertNotNull(groupDtoList);
        Assertions.assertNotEquals(0, groupDtoList.size());
        Assertions.assertEquals(testGroupList.size(), groupDtoList.size());

        for (int i=0; i<testGroupList.size(); i++){
            GroupDTO groupDto = groupDtoList.get(i);
            Group group = testGroupList.get(i);

            Assertions.assertNotNull(groupDto);

            Assertions.assertNotNull(groupDto.getIdDto());
            Assertions.assertNotNull(groupDto.getNameDto());
            Assertions.assertNotNull(groupDto.getStudentListDto());
            Assertions.assertNotNull(groupDto.getSubjectListDto());

            Assertions.assertEquals(groupDto.getIdDto(), group.getId());
            Assertions.assertEquals(groupDto.getNameDto(), group.getName());
            Assertions.assertEquals(groupDto.getStudentListDto().size(), group.getStudentList().size());
            Assertions.assertEquals(groupDto.getSubjectListDto().size(), group.getSubjectList().size());

            for (int j=0; j<group.getStudentList().size(); j++){
                Student student = group.getStudentList().get(j);
                Assertions.assertNotNull(groupDto.getStudentListDto().get(j));
                Assertions.assertEquals(student.getId(), groupDto.getStudentListDto().get(j));
            }

            for (int k=0; k<group.getSubjectList().size(); k++){
                Subject subject = group.getSubjectList().get(k);
                Assertions.assertNotNull(groupDto.getSubjectListDto().get(k));
                Assertions.assertEquals(subject.getId(), groupDto.getSubjectListDto().get(k));
            }
        }
    }
}
