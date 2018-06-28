package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

public class People extends PeopleId{
    String boss,email,role;

    public People(){

    }
    public People(String boss, String email, String role) {

        this.boss = boss;
        this.email = email;
        this.role = role;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
