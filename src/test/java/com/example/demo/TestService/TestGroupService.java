package com.example.demo.TestService;

import com.example.demo.DTO.GroupDTO;
import com.example.demo.Service.GroupService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TestGroupService {

    @Autowired
    private GroupService groupService;

    Random random = new Random();

    @Test
    void testGetAllGroups(){
        List<GroupDTO> groups = groupService.getAllGroups();

        Assertions.assertNotNull(groups);
        Assertions.assertNotEquals(0,groups.size());

        for (int i=0;i<groups.size();i++){
            GroupDTO groupDto = groups.get(i);
            Assertions.assertNotNull(groupDto);
            Assertions.assertNotNull(groupDto.getIdDto());
            Assertions.assertNotNull(groupDto.getNameDto());
        }
    }

    @Test
    void testGetGroupById(){
        int randomIndex =  random.nextInt(groupService.getAllGroups().size());
        Long id = groupService.getAllGroups().get(randomIndex).getIdDto();
        GroupDTO group = groupService.getGroupById(id);

        Assertions.assertNotNull(group);

        Assertions.assertNotNull(group.getIdDto());
        Assertions.assertNotNull(group.getNameDto());

        Assertions.assertNotNull(group.getStudentListDto());
        Assertions.assertNotEquals(0, group.getStudentListDto().size());

        Assertions.assertNotNull(group.getSubjectListDto());
        Assertions.assertNotEquals(0, group.getSubjectListDto().size());

        group = groupService.getGroupById(-1L);
        Assertions.assertNull(group);
    }

    @Test
    void testAddGroup(){
        GroupDTO group = new  GroupDTO();
        group.setNameDto("group_name");

        group.setStudentListDto(new ArrayList<>());
        group.getStudentListDto().add(1L);

        group.setSubjectListDto(new ArrayList<>());
        group.getSubjectListDto().add(1L);

        GroupDTO createdGroup = groupService.addGroup(group);
        GroupDTO checkGroup = groupService.getGroupById(createdGroup.getIdDto());

        Assertions.assertNotNull(checkGroup);
        Assertions.assertNotNull(checkGroup.getIdDto());
        Assertions.assertNotNull(checkGroup.getNameDto());
        Assertions.assertNotNull(checkGroup.getStudentListDto());
        Assertions.assertNotEquals(0, checkGroup.getStudentListDto().size());
        Assertions.assertNotNull(checkGroup.getSubjectListDto());
        Assertions.assertNotEquals(0, checkGroup.getSubjectListDto().size());

        Assertions.assertEquals(createdGroup.getIdDto(),checkGroup.getIdDto());
        Assertions.assertEquals(createdGroup.getNameDto(),checkGroup.getNameDto());

        Assertions.assertEquals(createdGroup.getStudentListDto().size(),checkGroup.getStudentListDto().size());
        for (int i=0;i<createdGroup.getStudentListDto().size();i++){
            Assertions.assertNotNull(checkGroup.getStudentListDto().get(i));
            Assertions.assertEquals(createdGroup.getStudentListDto().get(i),checkGroup.getStudentListDto().get(i));
        }

        Assertions.assertEquals(createdGroup.getSubjectListDto().size(),checkGroup.getSubjectListDto().size());
        for (int i=0;i<createdGroup.getSubjectListDto().size();i++){
            Assertions.assertNotNull(checkGroup.getSubjectListDto().get(i));
            Assertions.assertEquals(createdGroup.getSubjectListDto().get(i),checkGroup.getSubjectListDto().get(i));
        }

    }

    @Test
    void testUpdateGroup(){
        int randomIndex = random.nextInt(groupService.getAllGroups().size());
        Long id = groupService.getAllGroups().get(randomIndex).getIdDto();

        GroupDTO group = new GroupDTO();
        group.setNameDto("changed_group_name");
        group.setStudentListDto(new ArrayList<>());
        group.getStudentListDto().add(1L);

        group.setSubjectListDto(new ArrayList<>());
        group.getSubjectListDto().add(1L);

        groupService.updateGroup(id,group);
        GroupDTO updatedGroup = groupService.getGroupById(id);
        Assertions.assertNotNull(updatedGroup);

        Assertions.assertNotNull(updatedGroup.getIdDto());
        Assertions.assertNotNull(updatedGroup.getNameDto());

        Assertions.assertNotNull(updatedGroup.getStudentListDto());
        Assertions.assertNotEquals(0, updatedGroup.getStudentListDto().size());

        Assertions.assertNotNull(updatedGroup.getSubjectListDto());
        Assertions.assertNotEquals(0, updatedGroup.getSubjectListDto().size());

        Assertions.assertEquals(id, updatedGroup.getIdDto());
        Assertions.assertEquals(updatedGroup.getNameDto(),group.getNameDto());

        for (int i=0;i<updatedGroup.getStudentListDto().size();i++){
            Assertions.assertNotNull(updatedGroup.getStudentListDto().get(i));
            Assertions.assertEquals(updatedGroup.getStudentListDto().get(i),group.getStudentListDto().get(i));
        }

        Assertions.assertEquals(updatedGroup.getSubjectListDto().size(),group.getSubjectListDto().size());
        for (int i=0;i<updatedGroup.getSubjectListDto().size();i++){
            Assertions.assertNotNull(updatedGroup.getSubjectListDto().get(i));
            Assertions.assertEquals(updatedGroup.getSubjectListDto().get(i),group.getSubjectListDto().get(i));
        }
    }

    @Test
    void testDeleteGroup(){
        int randomIndex = random.nextInt(groupService.getAllGroups().size());
        Long id =  groupService.getAllGroups().get(randomIndex).getIdDto();

        Assertions.assertTrue(groupService.deleteGroup(id));

        GroupDTO group = groupService.getGroupById(id);
        Assertions.assertNull(group);
    }
}

