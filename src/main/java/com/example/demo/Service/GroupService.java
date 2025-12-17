package com.example.demo.Service;

import com.example.demo.DTO.GroupDTO;
import java.util.List;

public interface GroupService {
    List<GroupDTO> getAllGroups();
    GroupDTO getGroupById(Long id);
    GroupDTO addGroup(GroupDTO groupDTO);
    boolean updateGroup(long id, GroupDTO groupDTO);
    boolean deleteGroup(long id);
}
