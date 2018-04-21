package donation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="donorProfile")
public class DonorProfile implements Serializable{

    /**
     * Default constructor
     */
    public DonorProfile() {
    }

    /**
     * 
     */
    @Column(name="firstName")
    private String firstName;

    /**
     * 
     */
    @Column(name="lastName")
    private String lastName;

    /**
     * 
     */
    @Column(name="birthDate")
    private Date birthDate;

    /**
     * 
     */
    @Column(name="address")
    private String address;

    /**
     * 
     */
    @Column(name="nationality")
    private String nationality;

    /**
     * 
     */
    @Column(name="email")
    private String email;

    /**
     * 
     */
    @Column(name="phone")
    private String phone;

    /**
     * 
     */
    @Column(name="idUser")
    private int idUser;

    /**
     * 
     */
    @Column(name="weight")
    private float weight;

    /**
     * 
     */
    @Column(name="height")
    private int height;

    /**
     * 
     */
    @Column(name="CNP")
    private String CNP;

    /**
     * 
     */
    @Column(name="residence")
    private String residence;

    /**
     * 
     */
    @Column(name="rhBloodGroup")
    private RhBloodGroup rhBloodGroup;

    /**
     * 
     */
    @Column(name="aboBloodGroup")
    private ABOBloodGroup aboBloodGroup;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public RhBloodGroup getRhBloodGroup() {
        return rhBloodGroup;
    }

    public void setRhBloodGroup(RhBloodGroup rhBloodGroup) {
        this.rhBloodGroup = rhBloodGroup;
    }

    public ABOBloodGroup getAboBloodGroup() {
        return aboBloodGroup;
    }

    public void setAboBloodGroup(ABOBloodGroup aboBloodGroup) {
        this.aboBloodGroup = aboBloodGroup;
    }
}