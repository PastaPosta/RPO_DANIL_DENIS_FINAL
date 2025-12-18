package com.example.demo.Service.Implementation;

import com.example.demo.DTO.GroupDTO;
import com.example.demo.Mapper.GroupMapper;
import com.example.demo.Model.Group;
import com.example.demo.Model.Student;
import com.example.demo.Model.Subject;
import com.example.demo.Repository.GroupRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.Service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImplementation implements GroupService {
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public List<GroupDTO> getAllGroups(){
        List<Group> groups = groupRepository.findAll();
        return groupMapper.toDtoList(groups);
    }
    @Override
    public GroupDTO getGroupById(Long id){
        Group group = groupRepository.findById(id).orElse(null);
        return groupMapper.toDto(group);
    }
    @Override
    public GroupDTO addGroup(GroupDTO groupDTO) {
        Group group = groupMapper.toEntity(groupDTO);

        if (group.getStudentList() != null) {
            List<Student> attachedStudents = new ArrayList<>();
            for (Student s : group.getStudentList()) {
                if (s.getId() != null) {
                    Student existingStudent = studentRepository.findById(s.getId()).orElse(null);
                    if (existingStudent != null) {
                        existingStudent.setGroup(group);
                        attachedStudents.add(existingStudent);
                    }
                }
            }
            group.setStudentList(attachedStudents);
        }

        if (group.getSubjectList() != null) {
            List<Subject> attachedSubjects = new ArrayList<>();
            for (Subject subj : group.getSubjectList()) {
                Subject existingSubject = subjectRepository.findById(subj.getId()).orElse(null);
                if (existingSubject != null) {
                    if (existingSubject.getGroupList() == null) {
                        existingSubject.setGroupList(new ArrayList<>());
                    }
                    existingSubject.getGroupList().add(group);
                    attachedSubjects.add(existingSubject);
                }
            }
            group.setSubjectList(attachedSubjects);
        }

        groupRepository.save(group);

        return groupMapper.toDto(group);
    }


    @Override
    public boolean updateGroup(long id, GroupDTO groupDTO){
        Group selectedGroup = groupRepository.findById(id).orElse(null);

        if (selectedGroup != null) {
            if (groupDTO.getNameDto() != null){selectedGroup.setName(groupDTO.getNameDto());}

            if (groupDTO.getStudentListDto() != null){
                if (selectedGroup.getStudentList() != null){
                    for (Student oldStudent : selectedGroup.getStudentList()) {
                        oldStudent.setGroup(null);
                    }
                }

                List<Student> updatedStudents = new ArrayList<>();
                for (Long studentId : groupDTO.getStudentListDto()) {
                    Student student = studentRepository.findById(studentId).orElse(null);
                    if (student != null) {
                        student.setGroup(selectedGroup);
                        updatedStudents.add(student);
                    }
                }
                selectedGroup.setStudentList(updatedStudents);
            }

            if (groupDTO.getSubjectListDto() != null) {
                if (selectedGroup.getSubjectList() != null) {
                    for (Subject oldSubject : new ArrayList<>(selectedGroup.getSubjectList())) {
                        if (oldSubject.getGroupList() != null) {
                            oldSubject.getGroupList().remove(selectedGroup);
                        }
                    }
                    selectedGroup.getSubjectList().clear();
                }

                List<Subject> updatedSubjects = new ArrayList<>();
                for (Long subjectId : groupDTO.getSubjectListDto()) {
                    Subject subject = subjectRepository.findById(subjectId).orElse(null);
                    if (subject != null) {
                        if (subject.getGroupList() == null) {
                            subject.setGroupList(new ArrayList<>());
                        }
                        if (!subject.getGroupList().contains(selectedGroup)) {
                            subject.getGroupList().add(selectedGroup);
                        }
                        updatedSubjects.add(subject);
                    }
                }
                selectedGroup.setSubjectList(updatedSubjects);
            }
            groupRepository.save(selectedGroup);
            return true;
        } else {
            return false;
        }

    }
    @Override
    public boolean deleteGroup(long id){
        Group group = groupRepository.findById(id).orElse(null);

        if (group != null){
            if (group.getStudentList() != null){
                for (Student student : group.getStudentList()){
                    student.setGroup(null);
                }
                studentRepository.saveAll(group.getStudentList());
            }

            if  (group.getSubjectList() != null){
                for (Subject subject : group.getSubjectList()){
                    subject.getGroupList().remove(group);
                }
                subjectRepository.saveAll(group.getSubjectList());
            }

            groupRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
