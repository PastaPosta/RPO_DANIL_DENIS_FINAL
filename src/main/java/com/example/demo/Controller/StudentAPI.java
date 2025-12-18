package com.example.demo.Controller;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Service.Implementation.StudentServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentAPI {
    private final StudentServiceImplementation studentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id){
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO studentDto){
        studentService.addStudent(studentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, StudentDTO studentDto){
        if (studentService.updateStudent(id, studentDto)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id){
        if (studentService.deleteStudent(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
