package com.sid.sb.jwt.app.service;

import com.sid.sb.jwt.app.entity.Role;
import com.sid.sb.jwt.app.repository.IRoleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleService {
    private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private IRoleRepository roleReposiroty;

    public Role save(Role role) {
        return roleReposiroty.save(role);
    }

    public List<Role> findAllRole() {
        return roleReposiroty.findAll();
    }

    public Role findDefaultRole() {
        return findAllRole().stream().findFirst().orElse(null);
    }

    public Role findRoleByName(String role) {
        return findAllRole().stream().filter(r -> r.getName().equals(role)).findFirst().orElse(null);
    }

}