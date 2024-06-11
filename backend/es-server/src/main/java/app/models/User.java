package app.models;

import app.repositories.Identifiable;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity(name="app_user")
public class User implements Identifiable {
    @JsonView({ViewClasses.Summary.class})
    @SequenceGenerator(name = "Users_ids", initialValue = 100001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Users_ids")
    @Id
    private long id = 0L;

    @JsonView({ViewClasses.Summary.class})
    private String name;
    @JsonView({ViewClasses.Summary.class})
    private String email;
    private String hashedPassword;
    @JsonView({ViewClasses.Summary.class})
    private String role;
    public User() {
    }

    public User(long id, String hashedPassword, String email) {
        this.id = id;
        this.email = email;
        this.name = extractNameOfEmail(email);
        this.hashedPassword = hashedPassword;
        this.role = generateRole();
    }

    private String generateRole() {
        return "registered user";
    }

    public static String extractNameOfEmail(String email) {
        return email.split("@")[0];
    }

    public static User createSampleUser(long id, String email, String hashedPassword){
        User user = new User(id, email, hashedPassword);
        user.setName(user.getName());
        user.setRole(user.getRole());
        return user;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
