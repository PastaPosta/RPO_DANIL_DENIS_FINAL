package com.example.demo.Service;

import com.example.demo.DTO.SubjectDTO;
import java.util.List;

public interface SubjectService {
    List<SubjectDTO> getALlSubjects();
    SubjectDTO getSubjectById(Long id);
    SubjectDTO addSubject(SubjectDTO subjectDTO);
    boolean updateSubject(Long id, SubjectDTO subjectDTO);
    boolean deleteSubject(Long id);
}
