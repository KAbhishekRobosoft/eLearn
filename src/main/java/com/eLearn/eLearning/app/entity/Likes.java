package com.eLearn.eLearning.app.entity;
import com.eLearn.eLearning.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name="likes")
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userid",insertable= false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subtopicid",insertable= false,updatable = false)
    private SubTopic subTopic;

    private int userid;

    private int subtopicid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public SubTopic getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(SubTopic subTopic) {
        this.subTopic = subTopic;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getSubtopicid() {
        return subtopicid;
    }

    public void setSubtopicid(int subtopicid) {
        this.subtopicid = subtopicid;
    }

    public Likes(int id, int userid, int subtopicid) {
        this.id = id;
        this.userid = userid;
        this.subtopicid = subtopicid;
    }

    public Likes(int userid, int subtopicid) {
        this.userid = userid;
        this.subtopicid = subtopicid;
    }
}
