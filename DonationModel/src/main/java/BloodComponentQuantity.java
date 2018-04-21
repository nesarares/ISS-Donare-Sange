import java.util.*;

/**
 * 
 */
public class BloodComponentQuantity {

    /**
     * Default constructor
     */
    public BloodComponentQuantity() {
    }

    /**
     * 
     */
    private int quantity;

    /**
     * 
     */
    private Date expirationDate;

    /**
     * 
     */
    private BloodComponent bloodComponent;

    /**
     * 
     */
    private RhBloodGroup rhBloodGroup;

    /**
     * 
     */
    private ABOBloodGroup aboBloodGroup;

    /**
     * 
     */
    private BloodStatus bloodStatus;


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
}