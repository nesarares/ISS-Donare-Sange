import java.util.*;

/**
 * 
 */
public class DonorProfile {

    /**
     * Default constructor
     */
    public DonorProfile() {
    }

    /**
     * 
     */
    private String firstName;

    /**
     * 
     */
    private String lastName;

    /**
     * 
     */
    private Date birthDate;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private String nationality;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private int idUser;

    /**
     * 
     */
    private float weight;

    /**
     * 
     */
    private int height;

    /**
     * 
     */
    private String CNP;

    /**
     * 
     */
    private String residence;

    /**
     * 
     */
    private RhBloodGroup rhBloodGroup;

    /**
     * 
     */
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