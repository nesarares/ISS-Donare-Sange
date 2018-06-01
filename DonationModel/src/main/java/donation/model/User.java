package donation.model;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "user")
public class User implements Serializable {


    public User() {
    }


    public User(int id, String username, String passHash, UserType type) {
        this.id = id;
        this.username = username;
        this.passHash = passHash;
        this.type = type;
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "username")
    private String username;


    @Column(name = "passHash")
    private String passHash;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (passHash != null ? !passHash.equals(user.passHash) : user.passHash != null) return false;
        return type == user.type;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (passHash != null ? passHash.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return id + " " + username + " " + passHash + " " + type;
    }
}