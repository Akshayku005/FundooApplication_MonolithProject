package com.brigdelabz.FundooApp.service;

import com.brigdelabz.FundooApp.dto.ResponseDTO;
import com.brigdelabz.FundooApp.dto.UserDto;
import com.brigdelabz.FundooApp.exception.UserException;
import com.brigdelabz.FundooApp.model.User;
import com.brigdelabz.FundooApp.repository.UserRepository;
import com.brigdelabz.FundooApp.util.EmailSenderService;
import com.brigdelabz.FundooApp.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private EmailSenderService mailSender;
    @Autowired
    private TokenUtility util;

    @Override
    public String addUser(UserDto userDto) {

        Optional<User> user = repository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new UserException(HttpStatus.BAD_REQUEST, "user with this email Id already exists");
        } else {
            if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
                User newUser = new User(userDto);
                newUser.setIsActive(false);
                newUser.setIsDeleted(false);
                repository.save(newUser);
                String token = util.createToken((int) newUser.getId());
                System.out.println(token);
                mailSender.sendEmail(userDto.getEmail(), "Account created successfully",
                        "Created your account successfully please login to activate your acount");
                return token;
            } else {
                throw new UserException(HttpStatus.BAD_REQUEST, "Please check your password adn enter again");
            }
        }
    }

    @Override
    public User updateUser(UserDto userDto, String token) {
        Long userId = util.decodeToken(token);
        Optional<User> isUserPresent = repository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<User> user = repository.findByEmail(userDto.getEmail());
            if (user.isPresent()) {
                throw new UserException(HttpStatus.BAD_REQUEST, "user with this email Id already exists");
            } else {
                if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
                    User updatedUser = new User(userDto);
                    updatedUser.setIsActive(false);
                    updatedUser.setIsDeleted(false);
                    updatedUser.setUpdatedAt(LocalDateTime.now());
                    repository.save(updatedUser);
                    mailSender.sendEmail(userDto.getEmail(), "user updated Successfully",
                            "your account details updated sucessfully");
                } else {
                    throw new UserException(HttpStatus.BAD_REQUEST, "Please check your password adn enter again");
                }
            }
        }
        throw new UserException(HttpStatus.FOUND, "user not found with this Token ");
    }

    @Override
    public List<User> getAllUsers(String token) {
        Long userId = util.decodeToken(token);
        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            List<User> getUsers = repository.findAll();
            if (getUsers.isEmpty()) {
                throw new UserException(HttpStatus.NOT_FOUND, "There is no User added yet");
            } else return getUsers;
        } else throw new UserException
                (HttpStatus.NOT_FOUND, "data is not found on this token");

    }

    @Override
    public Optional<User> getUserById(String token) {
        Long decode = util.decodeToken(token);
        Optional<User> user = repository.findById(decode);
        if (user.isPresent()) {
            return repository.findById(decode);
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Record for provided userId is not found");
    }


    @Override
    public ResponseDTO login(String email, String password) {
        Optional<User> userData = repository.findByEmail(email);
        if (userData.isPresent()) {
            if (userData.get().getPassword().equals(password)) {
                String token = util.createToken((int) userData.get().getId());
                if (userData.get().getIsActive() == true) {
                    mailSender.sendEmail(userData.get().getEmail(), "Login successfull", "You have logged in successfully !!"
                            + userData.get().getName() + " Welcome back!! ");
                    return new ResponseDTO("User login succesfull", token);
                } else {
                    mailSender.sendEmail(userData.get().getEmail(), "Activation email", "please click below link to Activate your account "
                            + userData.get().getName() + " Please Click here -> "
                            + "http://localhost:8081/admin/activateuser/" + token);
                    return new ResponseDTO("Please check your mail to Activate your account", token);
                }
            }
            throw new UserException(HttpStatus.NOT_ACCEPTABLE, "Invalid credentials, password is incorrect !!");
        }
        throw new UserException(HttpStatus.NOT_FOUND, "invalid emailId");
    }


    @Override
    public ResponseDTO resetPassword(String emailId) {
        Optional<User> userData = repository.findByEmail(emailId);
        if (userData.isPresent()) {
            String token = util.createToken((int) userData.get().getId());
            String url = "http://localhost:8081/admin/resetpassword " + token;
            String subject = "reset password Successfully";
            mailSender.sendEmail(userData.get().getEmail(), subject, url);
            return new ResponseDTO("Reset password successfully completed ", token);
        }
        throw new UserException(HttpStatus.NOT_FOUND, "EmailNOtFound");
    }


    @Override
    public User changePassword(String token, String password) {
        Long decode = util.decodeToken(token);
        Optional<User> userData = repository.findById(decode);
        if (userData.isPresent()) {
            userData.get().setPassword(password);
            repository.save(userData.get());
            String body = "Password changed successfully with userId" + userData.get().getId();
            String subject = "Password changed Successfully";
            mailSender.sendEmail(userData.get().getEmail(), subject, body);
            return userData.get();
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Token not find");
    }


    @Override
    public ResponseDTO deleteUser(String token) {
        Long decode = util.decodeToken(token);
        Optional<User> userData = repository.findById(decode);
        if (userData.isPresent()) {
                userData.get().setIsActive(false);
                userData.get().setIsDeleted(true);
                repository.save(userData.get());
                return new ResponseDTO("success", userData.get());
            } else {
                throw new UserException(HttpStatus.NOT_FOUND, "User not present");
            }
        }

    @Override
    public Boolean validateUser(String token) {
        Long decode = util.decodeToken(token);
        Optional<User> isTokenPresent = repository.findById(decode);
        if (isTokenPresent.isPresent()) {
            return true;
        }
        throw new UserException(HttpStatus.NOT_FOUND, "Token not found");
    }

    @Override
    public ResponseDTO restoreUser(String token) {
        Long decode = util.decodeToken(token);
        Optional<User> isIdPresent = repository.findById(decode);
        if (isIdPresent.isPresent()) {
                isIdPresent.get().setIsActive(true);
                isIdPresent.get().setIsDeleted(false);
                repository.save(isIdPresent.get());
                return new ResponseDTO("successfully recovered ", isIdPresent.get());
            } else {
                throw new UserException(HttpStatus.NOT_FOUND, "User not present");
            }
        }
    @Override
    public ResponseDTO permanentDelete(String token) {
        Long userId = util.decodeToken(token);
        Optional<User> isIdPresent = repository.findById(userId);
        if (isIdPresent.isPresent()) {
                repository.delete(isIdPresent.get());
                String body = "User deleted successfully with userId" + isIdPresent.get().getId();
                String subject = "User deleted Successfull";
                mailSender.sendEmail(isIdPresent.get().getEmail(), subject, body);
                return new ResponseDTO("Permanently deleted ", isIdPresent.get());
            } else {
                throw new UserException(HttpStatus.NOT_FOUND, "User not found");
            }
        }



    @Override
    public Boolean validateEmail(String emailId) {
        Optional<User> isEmailPresent = repository.findByEmail(emailId);
        if (isEmailPresent.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ResponseDTO makeActivation(String token) {
        Long decode = util.decodeToken(token);
        Optional<User> user = repository.findById(decode);
        if (user.isPresent()) {
            user.get().setIsActive(true);
            repository.save(user.get());
            mailSender.sendEmail(user.get().getEmail(), "Account Activated", "Your account Activated successfully  "
                    + user.get().getName() + "Feel free to visit our wedsite ");
            return new ResponseDTO("Account Activated successfully", user);
        }
        throw new UserException(HttpStatus.FOUND, "user not found !!");
    }
}
