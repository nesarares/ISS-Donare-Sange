package donation.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="bloodComponentQuantity")
public class BloodComponentQuantity implements Serializable {

    /**
     * Default constructor
     */
    public BloodComponentQuantity() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "idDonation")
    private int IDdonation;

    @Column(name = "idRequest")
    private int IDrequest;

    @Column(name = "IDTransfusionCenter")
    private int IDTransfusionCenter;

    /**
     * 
     */
    @Column(name="quantity")
    private int quantity;

    /**
     * 
     */
    @Column(name="expirationDate")
    private Date expirationDate;

    /**
     * 
     */
    @Column(name="bloodComponent")
    private BloodComponent bloodComponent;

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

    /**
     * 
     */
    @Column(name="bloodStatus")
    private BloodStatus bloodStatus;


    public int getIDdonation() {
        return IDdonation;
    }

    public void setIDdonation(int IDdonation) {
        this.IDdonation = IDdonation;
    }

    public int getIDrequest() {
        return IDrequest;
    }

    public void setIDrequest(int IDrequest) {
        this.IDrequest = IDrequest;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BloodComponent getBloodComponent() {
        return bloodComponent;
    }

    public void setBloodComponent(BloodComponent bloodComponent) {
        this.bloodComponent = bloodComponent;
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

    public BloodStatus getBloodStatus() {
        return bloodStatus;
    }

    public void setBloodStatus(BloodStatus bloodStatus) {
        this.bloodStatus = bloodStatus;
    }

    public int getIDTransfusionCenter() {
        return IDTransfusionCenter;
    }

    public void setIDTransfusionCenter(int IDTransfusionCenter) {
        this.IDTransfusionCenter = IDTransfusionCenter;
    }
}