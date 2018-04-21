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

    /**
     * 
     */
    @OneToMany(mappedBy = "bCQ", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<BloodComponentQuantity> bloodComponentQuantities;

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


    public List<BloodComponentQuantity> getBloodComponentQuantities() {
        return bloodComponentQuantities;
    }

    public void setBloodComponentQuantities(List<BloodComponentQuantity> bloodComponentQuantities) {
        this.bloodComponentQuantities = bloodComponentQuantities;
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