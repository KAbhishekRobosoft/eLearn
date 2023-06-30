package com.eLearn.eLearning.app.entity;
import com.eLearn.eLearning.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name="completion")
@NoArgsConstructor
public class Completion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "subtopicid",insertable= false,updatable = false)
    private SubTopic subtopic;

    @ManyToOne
    @JoinColumn(name = "userid",insertable= false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lessonid",insertable= false,updatable = false)
    private Lesson lesson;

    private int lessonid;

    private int subtopicid;

    private int userid;

    @JsonIgnore
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public int getLessonid() {
        return lessonid;
    }

    public void setLessonid(int lessonid) {
        this.lessonid = lessonid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public SubTopic getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(SubTopic subtopic) {
        this.subtopic = subtopic;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSubtopicid() {
        return subtopicid;
    }

    public void setSubtopicid(int subtopicid) {
        this.subtopicid = subtopicid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Completion(int id, int subtopicid, int userid,int lessonid) {
        this.id = id;
        this.subtopicid = subtopicid;
        this.userid = userid;
        this.lessonid= lessonid;
    }

    public Completion(int subtopicid, int userid,int lessonid) {
        this.subtopicid = subtopicid;
        this.userid = userid;
        this.lessonid= lessonid;
    }
}
