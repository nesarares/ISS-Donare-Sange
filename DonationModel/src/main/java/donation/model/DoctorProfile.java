package donation.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="doctorProfile")
public class DoctorProfile implements Serializable {

    /**
     * Default constructor
     */
    public DoctorProfile() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

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
    @Column(name="hospital")
    private String hospital;

    /**
     * 
     */
    @Column(name="idUser")
    private int idUser;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

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