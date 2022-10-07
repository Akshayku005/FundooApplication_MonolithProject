package com.brigdelabz.FundooApp.controller;


import com.brigdelabz.FundooApp.dto.ResponseDTO;
import com.brigdelabz.FundooApp.dto.UserDto;
import com.brigdelabz.FundooApp.model.User;
import com.brigdelabz.FundooApp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * @param userDTO
     * @purpose ability to add user or admin
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@Valid @RequestBody UserDto userDTO) {
        String newUser = userService.addUser(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User Registered Successfully", newUser);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    /**
     * @param userDTO
     * @param id
     * @param token
     * @purpose ability to update data
     */
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateRecordById( @PathVariable String token, @Valid @RequestBody UserDto userDTO) {
        User entity = userService.updateUser(userDTO, token);
        ResponseDTO dto = new ResponseDTO("User Record updated successfully", entity);
        return new ResponseEntity(dto, HttpStatus.ACCEPTED);
    }

    /**
     * @param token
     * @purpose ability to get all data
     */
    @GetMapping("/getallusers/{token}")
    public ResponseEntity<ResponseDTO> getAllUsers(@PathVariable String token) {
        List<User> userModel = userService.getAllUsers(token);
        ResponseDTO response = new ResponseDTO("get all method is successfully executed ", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param token
     * @purpose ability to get user by token
     */
    @GetMapping("/getUser/{token}")
    public ResponseEntity<ResponseDTO> getAllUserDataByToken(@PathVariable String token) {
        Object user = this.userService.getUserById(token);
        ResponseDTO response = new ResponseDTO("Requested User : ", user);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * @param adminLoginDTO
     * @purpose ability to login
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestParam String email, @RequestParam String password) {
        ResponseDTO userModel = userService.login(email, password);
        ResponseDTO response = new ResponseDTO("User login successfull", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose:TO reset the password
     *
     * @Param: emailid
     */
    @PostMapping("/resetpassword")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String emailId) {
        ResponseDTO userModel = userService.resetPassword(emailId);
        ResponseDTO response = new ResponseDTO("Reset password successfull", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose:To change the password
     *
     * @Param: token and password
     */
    @PutMapping("/changepassword/{token}")
    public ResponseEntity<ResponseDTO> changePassword(@PathVariable String token, @RequestParam String password) {
        User userModel = userService.changePassword(token, password);
        ResponseDTO response = new ResponseDTO("Password changed successfully", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose:Validate user
     *
     * @Param: token
     */
    @GetMapping("/validateuser/{token}")
    public Boolean validateUser(@PathVariable String token) {
        return userService.validateUser(token);
    }
    @GetMapping("/validateemail/{emailId}")
    public Boolean validateEmail(@PathVariable String emailId) {
        return userService.validateEmail(emailId);
    }

    /**
     * Purpose:To delete the user
     *
     * @Param: token and id
     */
    @DeleteMapping("deleteuser/{token}")
    public ResponseEntity<ResponseDTO> deleteUser( @PathVariable String token) {
        ResponseDTO userModel = userService.deleteUser( token);
        ResponseDTO response = new ResponseDTO(" User deleted successfully", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose: To restore the user
     *
     * @Param: token and id
     */
    @GetMapping("restoreuser/{token}")
    public ResponseEntity<ResponseDTO> restoreUser( @PathVariable String token) {
        ResponseDTO userModel = userService.restoreUser( token);
        ResponseDTO response = new ResponseDTO("User restored successfully", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose:To delete the user permanently
     *
     * @Param: token and id
     */
    @DeleteMapping("permanentlydeleteuser/{token}")
    public ResponseEntity<ResponseDTO> permanentDelete( @PathVariable String token) {
        ResponseDTO userModel = userService.permanentDelete(token);
        ResponseDTO response = new ResponseDTO("User deleted permanently ", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("activateuser/{token}")
    public ResponseEntity<ResponseDTO> activateUser(@PathVariable String token){
        ResponseDTO userModel = userService.makeActivation(token);
        ResponseDTO response = new ResponseDTO("Account Activated successfully ", userModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}