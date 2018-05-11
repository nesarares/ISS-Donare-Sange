package donation.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name = "bloodTransfusionCenterProfile")
public class BloodTransfusionCenterProfile implements Serializable {

    /**
     * Default constructor
     */
    public BloodTransfusionCenterProfile() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    /**
     * 
     */
    @Column(name = "address")
    private String address;

    /**
     * 
     */
    @Column(name = "idUser")
    private int idUser;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return address;
    }
}