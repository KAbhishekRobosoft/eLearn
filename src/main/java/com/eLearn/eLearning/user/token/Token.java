package com.eLearn.eLearning.user.token;
import com.eLearn.eLearning.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tokens")
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;

    @OneToOne
    @JoinColumn(name = "userid",insertable= false,updatable = false)
    private User user;

    private int userid;


    public int getId() {
        return id;
    }

    public Token(String token, int userid) {
        this.token = token;
        this.userid = userid;
    }

    public Token(int id, String token, int userid) {
        this.id = id;
        this.token = token;
        this.userid = userid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

}
