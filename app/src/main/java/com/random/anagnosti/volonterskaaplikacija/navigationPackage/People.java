package com.random.anagnosti.volonterskaaplikacija.navigationPackage;

/**
 * Osoba event-a sa svojim podacima./
 */
public class People extends PeopleId{
    String boss,email,role;

    /**
     * Neophodan prazan konstruktor.
     */
    public People(){

    }

    /**
     * Konstruktor koji preuzima email nadredjenog, email ove osobe, kao i ulogu osobe.
     * @param boss email nadredjenog
     * @param email email ove osobe
     * @param role uloga ove osobe
     */
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
