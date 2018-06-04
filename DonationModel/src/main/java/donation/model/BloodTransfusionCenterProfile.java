package donation.model;

import donation.model.interfaces.HasAddress;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 */
@Entity
@Table(name = "bloodTransfusionCenterProfile")
public class BloodTransfusionCenterProfile implements Serializable, HasAddress<BloodTransfusionCenterProfile> {


    public BloodTransfusionCenterProfile() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;


    @Column(name = "address")
    private String address;


    @Column(name = "idUser")
    private int idUser;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object e) {
        if (e == null) return false;
        return ((BloodTransfusionCenterProfile) e).getID() == getID();
    }
}