package donation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 
 */
@Entity
@Table(name = "user")
public class User implements Serializable{

    /**
     * Default constructor
     */
    public User() {
    }


    public User(int id, String username, String passHash, UserType type) {
        this.id = id;
        this.username = username;
        this.passHash = passHash;
        this.type = type;
    }

    /**
     * 
     */
    @Id
    @Column(name = "id")
    private int id;

    /**
     * 
     */
    @Column(name = "username")
    private String username;

    /**
     * 
     */
    @Column(name = "passHash")
    private String passHash;

    /**
     * 
     */
    @Column(name = "type")
    private UserType type;

    public int
    getId() {
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

    @Override
    public  String toString(){
        return  id + " " + username + " " + passHash+ " " + type;
    }
}