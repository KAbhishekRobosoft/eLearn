package com.eLearn.eLearning.app.entity;

import com.eLearn.eLearning.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "quiz")
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @OneToMany(mappedBy ="quiz",cascade = CascadeType.ALL)
    private List<Question> questions;

    private String createdDate;

    @OneToOne
    @JoinColumn(name = "userid",insertable= false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sectionid", insertable= false,updatable = false)
    private Section section;

    private int sectionid;

    @OneToOne
    @JoinColumn(name = "subtopicid", insertable= false,updatable = false)
    private SubTopic subtopic;

    private int subtopicid;

    private int userid;

    @JsonIgnore
    public SubTopic getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(SubTopic subtopic) {
        this.subtopic = subtopic;
    }

    public int getSubtopicid() {
        return subtopicid;
    }

    public void setSubtopicid(int subtopicid) {
        this.subtopicid = subtopicid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonBackReference
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getSectionid() {
        return sectionid;
    }

    public void setSectionid(int sectionid) {
        this.sectionid = sectionid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @JsonManagedReference
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> exercises) {
        this.questions = exercises;
    }


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quiz(int id, String name, String description, String createdDate, int userid,int sectionid,int subtopicid ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.userid = userid;
        this.sectionid= sectionid;
        this.subtopicid= subtopicid;
    }

    public Quiz(String name, String description,int userid,int sectionid,int subtopicid) {
        this.name = name;
        this.description = description;
        this.userid = userid;
        this.sectionid= sectionid;
        this.subtopicid= subtopicid;
    }
}
