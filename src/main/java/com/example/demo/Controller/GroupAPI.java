package com.example.demo.Controller;

import com.example.demo.DTO.GroupDTO;
import com.example.demo.Service.Implementation.GroupServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupAPI {
    private final GroupServiceImplementation groupService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllGroups(){
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getGroup(@PathVariable("id") Long id){
        return new ResponseEntity<>(groupService.getGroupById(id), HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') ")
    public ResponseEntity<?> createGroup(@RequestBody GroupDTO groupDto){
        groupService.addGroup(groupDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateGroup(@PathVariable("id") Long id, @RequestBody GroupDTO groupDto){
        if (groupService.updateGroup(id, groupDto)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") Long id){
        if (groupService.deleteGroup(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
