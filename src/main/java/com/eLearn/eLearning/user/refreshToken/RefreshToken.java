package com.eLearn.eLearning.user.refreshToken;
import com.eLearn.eLearning.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refreshtoken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "userid",insertable= false,updatable = false)
    private User user;
    @Column(nullable = false, unique = true)
    private String token;

    public RefreshToken(long id,String token, Instant expiryDate, int userid) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.userid = userid;
    }

    public RefreshToken() {
    }

    public RefreshToken(String token, Instant expiryDate, int userid) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.userid = userid;
    }

    @Column(nullable = false)
    private Instant expiryDate;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    private int userid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }


}