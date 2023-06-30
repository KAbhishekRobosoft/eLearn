package com.eLearn.eLearning.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "section")
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String image;

    @ManyToOne
    @JoinColumn(name = "subjectid", insertable= false,updatable = false)
    private Subject subject;

    private int subjectid;

    @OneToMany(mappedBy ="section",cascade = CascadeType.ALL)
    private List<Lesson> lessonlist = new ArrayList<>();

    @OneToMany(mappedBy ="section",cascade = CascadeType.ALL)
    private List<Quiz> quizlist= new ArrayList<>();

    @JsonManagedReference
    public List<Lesson> getLessonlist() {
        return lessonlist;
    }

    public void setLessonlist(List<Lesson> lessonlist) {
        this.lessonlist = lessonlist;
    }

    @JsonManagedReference
    public List<Quiz> getQuizlist() {
        return quizlist;
    }

    public void setQuizlist(List<Quiz> quizlist) {
        this.quizlist = quizlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonBackReference
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public Section(int id, String name, String description, String image, int subjectid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.subjectid = subjectid;
    }

    public Section(String name, String description, int subjectid) {
        this.name = name;
        this.description = description;
        this.subjectid = subjectid;
    }
}
