package com.sid.app.controller;

import com.sid.app.constants.AppConstants;
import com.sid.app.dto.Response;
import com.sid.app.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(value = AppConstants.ALL_USERS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping(value = AppConstants.USER_BY_ID_ENDPOINT + "/" + "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId) {
        Response response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping(value = AppConstants.DELETE_USER_ENDPOINT + "/" + "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId) {
        Response response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping(value = AppConstants.LOGGED_IN_USER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getLoggedInUSerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping(value = AppConstants.USER_BOOKINGS_ENDPOINT + "/" + "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getUSerBookingHistory(@PathVariable("userId") String userId) {
        Response response = userService.getUSerBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}