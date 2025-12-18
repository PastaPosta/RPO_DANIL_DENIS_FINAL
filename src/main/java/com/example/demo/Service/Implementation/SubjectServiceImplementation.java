package com.example.demo.Service.Implementation;

import com.example.demo.DTO.SubjectDTO;
import com.example.demo.Mapper.SubjectMapper;
import com.example.demo.Model.Group;
import com.example.demo.Model.Subject;
import com.example.demo.Repository.GroupRepository;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.Service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImplementation implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final GroupRepository groupRepository;

    @Override
    public List<SubjectDTO> getALlSubjects(){
        List<Subject>  subjects = subjectRepository.findAll();
        return subjectMapper.toDtoList(subjects);
    }

    @Override
    public SubjectDTO getSubjectById(Long id){
        Subject subject = subjectRepository.findById(id).orElse(null);
        return subjectMapper.toDto(subject);
    }

    @Override
    public SubjectDTO addSubject(SubjectDTO subjectDTO){
        Subject subject = subjectMapper.toEntity(subjectDTO);

        if(subjectDTO.getGroupListDto() != null){

            List<Group> groups = new ArrayList<>();
            for (Long groupId : subjectDTO.getGroupListDto()) {
                Group group = groupRepository.findById(groupId).orElse(null);

                if (group != null) {
                    if (group.getSubjectList() == null) {
                        group.setSubjectList(new ArrayList<>());
                    }
                    group.getSubjectList().add(subject);
                    groups.add(group);
                }
            }

            subject.setGroupList(groups);
        }

        subjectRepository.save(subject);

        return subjectMapper.toDto(subject);
    }

    @Override
    public boolean updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject selectedSubject = subjectRepository.findById(id).orElse(null);
        if (selectedSubject != null) {
            if (subjectDTO.getNameDto() != null) selectedSubject.setName(subjectDTO.getNameDto());
            if (subjectDTO.getTeacherDto() != null) selectedSubject.setTeacher(subjectDTO.getTeacherDto());
            if (subjectDTO.getLengthDto() != 0) selectedSubject.setLength(subjectDTO.getLengthDto());

            if (subjectDTO.getGroupListDto() != null) {

                if (selectedSubject.getGroupList() != null) {
                    for (Group oldGroup : selectedSubject.getGroupList()) {
                        if (oldGroup.getSubjectList() != null) {
                            oldGroup.getSubjectList().remove(selectedSubject);
                        }
                    }
                }

                List<Group> updatedGroups = new ArrayList<>();
                for (Long groupId : subjectDTO.getGroupListDto()) {
                    Group group = groupRepository.findById(groupId).orElse(null);

                    if (group != null) {
                        if (group.getSubjectList() == null) {
                            group.setSubjectList(new ArrayList<>());
                        }

                        group.getSubjectList().add(selectedSubject);
                        updatedGroups.add(group);
                    }
                }

                selectedSubject.setGroupList(updatedGroups);
            }

            subjectRepository.save(selectedSubject);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteSubject(Long id){
        Subject subject =  subjectRepository.findById(id).orElse(null);
        if (subject != null){

            if (subject.getGroupList() != null){
                for (Group group : subject.getGroupList()){
                    group.getSubjectList().remove(subject);
                }
                groupRepository.saveAll(subject.getGroupList());
            }

            subjectRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
