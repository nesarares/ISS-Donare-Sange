package donation.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="bloodRequest")
public class BloodRequest {

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
//    @OneToMany(mappedBy = "IDrequest", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @Fetch(FetchMode.SELECT)
    //private List<BloodComponentQuantity> bloodComponentQuantities;

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
}