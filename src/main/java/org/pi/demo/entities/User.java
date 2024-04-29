package org.pi.demo.entities;

import org.pi.demo.utils.Type;

public class User {
    private int id;
    private int age;
    private String firstname;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String profession;
    private Type roles;

    public User(int id, String firstname, String name, String email, String phone, String password, String profession, Type roles) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profession = profession;
        this.roles = roles;
    }

    public User(int id, int age, String firstname, String name, String email, String phone, String password, String profession, Type roles) {
        this.id = id;
        this.age = age;
        this.firstname = firstname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profession = profession;
        this.roles = roles;

    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getProfession() {
        return profession;
    }

    public Type getRoles() {
        return roles;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setRoles(Type roles) {
        this.roles = roles;
    }

    public User(int id, int age, String firstname, String name, String email, String phone, String password, String profession, Type roles, Boolean is_active, Boolean is_banned) {
        this.id = id;
        this.age = age;
        this.firstname = firstname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profession = profession;
        this.roles = roles;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", firstname='" + firstname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", profession='" + profession + '\'' +
                ", roles=" + roles +
                '}';
    }

}
