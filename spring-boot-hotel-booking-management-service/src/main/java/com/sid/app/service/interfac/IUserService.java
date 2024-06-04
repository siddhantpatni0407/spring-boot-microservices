package com.sid.app.service.interfac;

import com.sid.app.dto.LoginRequest;
import com.sid.app.dto.Response;
import com.sid.app.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUSerBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}