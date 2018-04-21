import java.util.*;

/**
 * 
 */
public class BloodRequest {

    /**
     * Default constructor
     */
    public BloodRequest() {
    }

    /**
     * 
     */
    private List<BloodComponentQuantity> bloodComponentQuantities;

    /**
     * 
     */
    private User sender;

    /**
     * 
     */
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