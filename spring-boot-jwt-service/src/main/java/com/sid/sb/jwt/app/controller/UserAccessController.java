package com.sid.sb.jwt.app.controller;

import com.sid.sb.jwt.app.contants.AppConstants;
import com.sid.sb.jwt.app.dto.AuthenticationRequest;
import com.sid.sb.jwt.app.dto.AuthenticationResponse;
import com.sid.sb.jwt.app.dto.UserRegisterRequestDTO;
import com.sid.sb.jwt.app.service.UserService;
import com.sid.sb.jwt.app.util.EntityResponse;
import com.sid.sb.jwt.app.util.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAccessController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = AppConstants.USER_AUTHENTICATION_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception e) {
            return EntityResponse.generateResponse("Authentication", HttpStatus.UNAUTHORIZED,
                    "Invalid credentials, please check details and try again.");
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        return EntityResponse.generateResponse("Authentication", HttpStatus.OK,
                new AuthenticationResponse(token, refreshToken));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e.getCause());

        }
    }

    @PostMapping(value = AppConstants.USER_REGISTER_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody UserRegisterRequestDTO request) {

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return EntityResponse.generateResponse("Regsiter User", HttpStatus.OK, userService.createUser(request));
    }

    @GetMapping(value = AppConstants.USER_PROFILE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> retrieveUserProfile() {

        return EntityResponse.generateResponse("User Profile", HttpStatus.OK, userService.findCurrentUser());
    }

}