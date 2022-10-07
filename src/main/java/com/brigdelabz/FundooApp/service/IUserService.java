package com.brigdelabz.FundooApp.service;

import com.brigdelabz.FundooApp.dto.ResponseDTO;
import com.brigdelabz.FundooApp.dto.UserDto;
import com.brigdelabz.FundooApp.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    String addUser(UserDto userDto);

    User updateUser(UserDto userDto, String token);

    List<User> getAllUsers(String token);

    Optional<User> getUserById(String token);

    ResponseDTO login(String email, String password);

    ResponseDTO resetPassword(String emailId);

    User changePassword(String token, String password);

    ResponseDTO deleteUser(String token);

    Boolean validateUser(String token);

    ResponseDTO restoreUser(String token);

    ResponseDTO permanentDelete(String token);
    Boolean validateEmail(String emailId);
    ResponseDTO makeActivation(String token);

}
