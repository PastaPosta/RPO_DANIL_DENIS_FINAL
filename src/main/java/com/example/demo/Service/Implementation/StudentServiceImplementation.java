package com.example.demo.Service.Implementation;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Mapper.StudentMapper;
import com.example.demo.Model.Group;
import com.example.demo.Model.Student;
import com.example.demo.Repository.GroupRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImplementation implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final GroupRepository groupRepository;

    @Override
    public List<StudentDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return studentMapper.toDtoList(students);
    }

    @Override
    public StudentDTO getStudentById(long id){
        Student student = studentRepository.findById(id).orElse(null);
        return studentMapper.toDto(student);

    }

    @Override
    public StudentDTO addStudent(StudentDTO studentDTO){
        Student student = studentMapper.toEntity(studentDTO);

        if (studentDTO.getGroupIdDto() != null) {
            Group group = groupRepository.findById(studentDTO.getGroupIdDto()).orElse(null);
            if (group != null) {
                student.setGroup(group);

                if (group.getStudentList() == null) {
                    group.setStudentList(new ArrayList<>());
                }

                group.getStudentList().add(student);
                groupRepository.save(group);
            }
        }

        studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    @Override
    public boolean updateStudent(Long id, StudentDTO studentDTO){
        Student selectedStudent = studentRepository.findById(id).orElse(null);

        if (selectedStudent != null){
            Student newInformation =  studentMapper.toEntity(studentDTO);
            if (studentDTO.getGroupIdDto()!=null && groupRepository.findById(studentDTO.getGroupIdDto()).orElse(null) != null){
                selectedStudent.setGroup(groupRepository.findById(newInformation.getId()).orElse(null));
            }
            if(studentDTO.getFullNameDto()!=null){
                selectedStudent.setFullName(newInformation.getFullName());
            }
            if(studentDTO.getAgeDto()!=0){
                selectedStudent.setAge(newInformation.getAge());
            }
            studentRepository.save(selectedStudent);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteStudent(Long id){
        Student selectedStudent = studentRepository.findById(id).orElse(null);
        if (selectedStudent != null){
            Group group = selectedStudent.getGroup();
            if (group != null && group.getStudentList() != null){
                group.getStudentList().remove(selectedStudent);
                selectedStudent.setGroup(null);
            }
            studentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
