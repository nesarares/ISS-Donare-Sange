import java.util.*;

/**
 * 
 */
public class DoctorProfile {

    /**
     * Default constructor
     */
    public DoctorProfile() {
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
    private String hospital;

    /**
     * 
     */
    private int idUser;


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

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}