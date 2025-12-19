package com.example.demo.TestService;

import com.example.demo.DTO.GroupDTO;
import com.example.demo.DTO.SubjectDTO;
import com.example.demo.Model.Subject;
import com.example.demo.Service.SubjectService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Commit
public class TestSubjectService {

    @Autowired
    private SubjectService subjectService;

    Random random = new Random();

    @Test
    void testGetAll() {
        List<SubjectDTO> subjects = subjectService.getALlSubjects();

        Assertions.assertNotNull(subjects);
        Assertions.assertNotEquals(0, subjects.size());

        for (int i = 0; i < subjects.size(); i++) {
            SubjectDTO subject = subjects.get(i);
            Assertions.assertNotNull(subject);
            Assertions.assertNotNull(subject.getIdDto());
            Assertions.assertNotNull(subject.getNameDto());
            Assertions.assertNotNull(subject.getTeacherDto());
            Assertions.assertNotEquals(0, subject.getLengthDto());

            Assertions.assertNotNull(subject.getGroupListDto());
            Assertions.assertNotEquals(0, subject.getGroupListDto().size());
        }

    }

    @Test
    void testGetById() {
        int randomIndex = random.nextInt(subjectService.getALlSubjects().size());
        Long id =  subjectService.getALlSubjects().get(randomIndex).getIdDto();

        SubjectDTO subject = subjectService.getSubjectById(id);

        Assertions.assertNotNull(subject);
        Assertions.assertNotNull(subject.getIdDto());
        Assertions.assertNotNull(subject.getNameDto());
        Assertions.assertNotNull(subject.getTeacherDto());
        Assertions.assertNotEquals(0, subject.getLengthDto());
        Assertions.assertNotNull(subject.getGroupListDto());
        Assertions.assertNotEquals(0, subject.getGroupListDto().size());

        subject =  subjectService.getSubjectById(-1L);
        Assertions.assertNull(subject);
    }

    @Test
    void testCreate() {
        SubjectDTO subject = new SubjectDTO();

        subject.setNameDto("subject_name");
        subject.setTeacherDto("subject_teacher");
        subject.setLengthDto(5);
        subject.setGroupListDto(new ArrayList<>());
        subject.getGroupListDto().add(1L);

        SubjectDTO createdSubject = subjectService.addSubject(subject);
        SubjectDTO checkSubject = subjectService.getSubjectById(createdSubject.getIdDto());

        Assertions.assertNotNull(checkSubject);
        Assertions.assertNotNull(checkSubject.getIdDto());
        Assertions.assertNotNull(checkSubject.getNameDto());
        Assertions.assertNotNull(checkSubject.getTeacherDto());
        Assertions.assertNotEquals(0, checkSubject.getLengthDto());

        Assertions.assertNotNull(checkSubject.getGroupListDto());
        Assertions.assertNotEquals(0, checkSubject.getGroupListDto().size());

    }

    @Test
    void testUpdate() {
        int randomIndex = random.nextInt(subjectService.getALlSubjects().size());
        Long id =  subjectService.getALlSubjects().get(randomIndex).getIdDto();

        SubjectDTO subject = new SubjectDTO();
        subject.setNameDto("subject_name");
        subject.setTeacherDto("subject_teacher");
        subject.setLengthDto(5);
        subject.setGroupListDto(new ArrayList<>());
        subject.getGroupListDto().add(1L);

        subjectService.updateSubject(id, subject);
        SubjectDTO updatedSubject = subjectService.getSubjectById(id);

        Assertions.assertNotNull(updatedSubject);
        Assertions.assertNotNull(updatedSubject.getIdDto());
        Assertions.assertNotNull(updatedSubject.getNameDto());
        Assertions.assertNotNull(updatedSubject.getTeacherDto());
        Assertions.assertNotEquals(0, updatedSubject.getLengthDto());

        Assertions.assertNotNull(updatedSubject.getGroupListDto());
        Assertions.assertNotEquals(0, updatedSubject.getGroupListDto().size());
        Assertions.assertEquals(subject.getGroupListDto().size(), updatedSubject.getGroupListDto().size());

        for (int i = 0; i < updatedSubject.getGroupListDto().size(); i++) {
            Assertions.assertEquals(updatedSubject.getGroupListDto().get(i), subject.getGroupListDto().get(i));
        }


    }

    @Test
    void testDelete() {
        int randomIndex = random.nextInt(subjectService.getALlSubjects().size());
        Long id =  subjectService.getALlSubjects().get(randomIndex).getIdDto();

        Assertions.assertTrue(subjectService.deleteSubject(id));
        SubjectDTO subject = subjectService.getSubjectById(id);
        Assertions.assertNull(subject);
    }

}
