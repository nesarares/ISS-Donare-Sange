package donation.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="bloodRequest")
public class BloodRequest implements Serializable {

    /**
     * Default constructor
     */
    public BloodRequest() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name="sender")
    private User sender;

    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name="receiver")
    private User receiver;

    @Column(name = "bloodRequestStatus")
    private BloodRequestStatus bloodRequestStatus;

    @Column(name = "patientName")
    private String patientName;

    @Column(name = "leukocytesQuantity")
    private int leukocytesQuantity;

    @Column(name = "thrombocytesQuantity")
    private int thrombocytesQuantity;

    @Column(name = "plasmaQuantity")
    private int plasmaQuantity;

    @Column(name = "bloodGroup")
    private ABOBloodGroup bloodGroup;

    @Column(name = "rhBloodGroup")
    private RhBloodGroup rhBloodGroup;

    @Column(name = "dateRequested")
    private Date dateRequested;

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public BloodRequestStatus getBloodRequestStatus() {
        return bloodRequestStatus;
    }

    public void setBloodRequestStatus(BloodRequestStatus bloodRequestStatus) {
        this.bloodRequestStatus = bloodRequestStatus;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getLeukocytesQuantity() {
        return leukocytesQuantity;
    }

    public void setLeukocytesQuantity(int leukocytesQuantity) {
        this.leukocytesQuantity = leukocytesQuantity;
    }

    public int getThrombocytesQuantity() {
        return thrombocytesQuantity;
    }

    public void setThrombocytesQuantity(int thrombocytesQuantity) {
        this.thrombocytesQuantity = thrombocytesQuantity;
    }

    public int getPlasmaQuantity() {
        return plasmaQuantity;
    }

    public void setPlasmaQuantity(int plasmaQuantity) {
        this.plasmaQuantity = plasmaQuantity;
    }

    public ABOBloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(ABOBloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public RhBloodGroup getRhBloodGroup() {
        return rhBloodGroup;
    }

    public void setRhBloodGroup(RhBloodGroup rhBloodGroup) {
        this.rhBloodGroup = rhBloodGroup;
    }
}