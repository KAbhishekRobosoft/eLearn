package com.eLearn.eLearning.app.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer")
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;


    @OneToOne
    @JoinColumn(name = "questionid", insertable= false,updatable = false)
    private Question question;

    private int questionid;


    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonBackReference
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Answer(int id, String text,Integer order,int questionid) {
        this.id = id;
        this.text = text;
        this.questionid= questionid;
    }

    public Answer(String text,int questionid) {
        this.text = text;
        this.questionid= questionid;
    }
}
