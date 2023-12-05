package com.sid.sb.jwt.app.controller;

import com.sid.sb.jwt.app.contants.AppConstants;
import com.sid.sb.jwt.app.util.EntityResponse;
import com.sid.sb.jwt.app.service.RoleService;
import com.sid.sb.jwt.app.service.UserService;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminAccessController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping(value = AppConstants.ADMIN_USER_LIST_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUserList() {

        return EntityResponse.generateResponse("Admin Fetch User List", HttpStatus.OK,
                userService.retrieveAllUserList());
    }

    @GetMapping(value = AppConstants.ADMIN_ROLE_LIST_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllRoleList() {

        return EntityResponse.generateResponse("Admin Fetch Role List", HttpStatus.OK,
                roleService.findAllRole());
    }

}