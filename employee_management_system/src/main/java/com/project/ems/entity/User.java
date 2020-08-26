package com.project.ems.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GeneratorName2")
    @SequenceGenerator(name="GeneratorName2", sequenceName = "seq2",allocationSize = 1)
    private Integer id;
    @NotBlank(message = "{validation.username.notBlank}")
	@Size(max = 50)
	@Email(message = "{validation.username.format}")
    private String userName;
    @NotBlank(message = "{validation.password.notBlank}")
	@Size(max = 25,min = 6,message = "{validation.password.size}")
    private String password;
    private boolean active;
    @NotBlank(message = "{validation.roles.notBlank}")
    private String roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
