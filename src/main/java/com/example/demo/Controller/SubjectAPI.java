package com.example.demo.Controller;

import com.example.demo.DTO.SubjectDTO;
import com.example.demo.Service.Implementation.SubjectServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectAPI {
    private final SubjectServiceImplementation subjectService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllSubjects() {
        return new ResponseEntity<>(subjectService.getALlSubjects(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getSubjectById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(subjectService.getSubjectById(id), HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addSubject(@RequestBody SubjectDTO subjectDTO) {
        subjectService.addSubject(subjectDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateSubject(@PathVariable("id") Long id, @RequestBody SubjectDTO subjectDTO) {
        if (subjectService.updateSubject(id, subjectDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteSubject(@PathVariable("id") Long id) {
        if  (subjectService.deleteSubject(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
