package com.eLearn.eLearning.app.entity;

import com.eLearn.eLearning.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "result")
@NoArgsConstructor
public class Result {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userid",insertable= false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quizid",insertable= false,updatable = false)
    private Quiz quiz;

    private int quizid;

    private int userid;

    private  float totalmarks;
    private float marks;

    private int percentage;

    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getQuizid() {
        return quizid;
    }

    public void setQuizid(int quizid) {
        this.quizid = quizid;
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

    public float getMarks() {
        return marks;
    }

    public float getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(int totalmarks) {
        this.totalmarks = totalmarks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result(int id, int userid, float marks,int quizid, int percentage, String status,float totalmarks) {
        this.id = id;
        this.userid = userid;
        this.totalmarks= totalmarks;
        this.marks = marks;
        this.percentage = percentage;
        this.quizid= quizid;
        this.status= status;
    }

    public Result(int userid, float marks, int percentage,String status,float totalmarks,int quizid) {
        this.userid = userid;
        this.totalmarks= totalmarks;
        this.marks = marks;
        this.percentage = percentage;
        this.quizid= quizid;
        this.status= status;
    }
}
