import java.util.*;

/**
 * 
 */
public class User {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String passHash;

    /**
     * 
     */
    private UserType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}