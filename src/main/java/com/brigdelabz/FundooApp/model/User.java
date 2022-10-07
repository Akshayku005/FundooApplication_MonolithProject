package com.brigdelabz.FundooApp.model;

import com.brigdelabz.FundooApp.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usertable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    private String confirmPassword;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Boolean isDeleted;
    private String dob;
    @Column(name = "Phone_Number")
    private String phoneNo;

    public User(UserDto userDto) {
    this.name= userDto.getName();
        this.email= userDto.getEmail();
        this.password= userDto.getPassword();
        this.confirmPassword= userDto.getConfirmPassword();
        this.dob= userDto.getDob();
        this.phoneNo= userDto.getPhoneNo();
    }
}
