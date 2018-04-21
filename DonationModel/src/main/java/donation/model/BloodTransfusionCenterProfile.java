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
@Table(name = "bloodTransfusionCenterProfile")
public class BloodTransfusionCenterProfile implements Serializable {

    /**
     * Default constructor
     */
    public BloodTransfusionCenterProfile() {
    }

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
}