package com.example.demo.TestMapper;

import com.example.demo.DTO.SubjectDTO;
import com.example.demo.Mapper.SubjectMapper;
import com.example.demo.Model.Group;
import com.example.demo.Model.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestSubjectMapper {
    @Autowired
    private SubjectMapper subjectMapper;
    private Subject testSubject;
    private SubjectDTO testSubjectDto;
    private List<Subject> testSubjectList;

    @BeforeEach
    public void setUp(){
        testSubject = new Subject(1L, "subject_name1", "teacher_name1", 1, new ArrayList<>());

        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group(1L, "group_name1", null, null));
        groupList.add(new Group(2L, "group_name2", null, null));

        testSubject.setGroupList(groupList);

        testSubjectDto = subjectMapper.toDto(testSubject);

        testSubjectList = new ArrayList<>();
        testSubjectList.add(testSubject);
    }

    @Test
    public void testToDto(){
        SubjectDTO subjectDto = subjectMapper.toDto(testSubject);

        Assertions.assertNotNull(subjectDto);

        Assertions.assertNotNull(subjectDto.getIdDto());
        Assertions.assertNotNull(subjectDto.getNameDto());
        Assertions.assertNotNull(subjectDto.getTeacherDto());
        Assertions.assertNotEquals(0, subjectDto.getLengthDto());
        Assertions.assertNotNull(subjectDto.getGroupListDto());

        Assertions.assertEquals(testSubject.getId(), subjectDto.getIdDto());
        Assertions.assertEquals(testSubject.getName(), subjectDto.getNameDto());
        Assertions.assertEquals(testSubject.getTeacher(), subjectDto.getTeacherDto());
        Assertions.assertEquals(testSubject.getLength(), subjectDto.getLengthDto());
        Assertions.assertEquals(testSubject.getGroupList().size(), subjectDto.getGroupListDto().size());

        for(int i = 0; i < testSubject.getGroupList().size(); i++){
            Group  group = testSubject.getGroupList().get(i);
            Assertions.assertNotNull(subjectDto.getGroupListDto().get(i));
            Assertions.assertEquals(group.getId(), subjectDto.getGroupListDto().get(i));
        }
    }

    @Test
    public void testToEntity(){
        Subject subject =  subjectMapper.toEntity(testSubjectDto);

        Assertions.assertNotNull(subject);

        Assertions.assertNotNull(subject.getId());
        Assertions.assertNotNull(subject.getName());
        Assertions.assertNotNull(subject.getTeacher());
        Assertions.assertNotEquals(0, subject.getLength());
        Assertions.assertNotNull(subject.getGroupList());

        Assertions.assertEquals(testSubjectDto.getIdDto(), subject.getId());
        Assertions.assertEquals(testSubjectDto.getNameDto(), subject.getName());
        Assertions.assertEquals(testSubjectDto.getTeacherDto(), subject.getTeacher());
        Assertions.assertEquals(testSubjectDto.getLengthDto(), subject.getLength());
        Assertions.assertEquals(testSubject.getGroupList().size(), subject.getGroupList().size());

        for(int i = 0; i < testSubjectDto.getGroupListDto().size(); i++){
            Long id = testSubjectDto.getGroupListDto().get(i);

            Assertions.assertNotNull(subject.getGroupList().get(i).getId());
            Assertions.assertEquals(id, subject.getGroupList().get(i).getId());
        }


    }

    @Test
    public void testToDtoList(){
        List<SubjectDTO> subjectDtoList = subjectMapper.toDtoList(testSubjectList);

        Assertions.assertNotNull(subjectDtoList);
        Assertions.assertNotEquals(0, subjectDtoList.size());
        Assertions.assertEquals(testSubjectList.size(), subjectDtoList.size());

        for(int i = 0; i < testSubjectList.size(); i++){
            Subject subject = testSubjectList.get(i);
            SubjectDTO subjectDto = subjectDtoList.get(i);

            Assertions.assertNotNull(subjectDto);

            Assertions.assertNotNull(subjectDto.getIdDto());
            Assertions.assertNotNull(subjectDto.getNameDto());
            Assertions.assertNotNull(subjectDto.getTeacherDto());
            Assertions.assertNotEquals(0, subjectDto.getLengthDto());
            Assertions.assertNotNull(subjectDto.getGroupListDto());

            Assertions.assertEquals(subject.getId(), subjectDto.getIdDto());
            Assertions.assertEquals(subject.getName(), subjectDto.getNameDto());
            Assertions.assertEquals(subject.getTeacher(), subjectDto.getTeacherDto());
            Assertions.assertEquals(subject.getLength(), subjectDto.getLengthDto());
            Assertions.assertEquals(subject.getGroupList().size(), subjectDto.getGroupListDto().size());

            for(int j = 0; j < subject.getGroupList().size(); j++){
                Group group = subject.getGroupList().get(j);
                Long id = subjectDto.getGroupListDto().get(j);

                Assertions.assertNotNull(id);
                Assertions.assertEquals(group.getId(), id);
            }
        }
    }

}
