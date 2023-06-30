package com.eLearn.eLearning.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subtopic")
@NoArgsConstructor
public class SubTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String image;

    private String video;

    @ManyToOne
    @JoinColumn(name = "lessonid", insertable= false,updatable = false)
    private Lesson lesson;

    private int lessonid;

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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @JsonBackReference
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

    public SubTopic(int id, String name, String description, String image, String video, int lessonid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.video = video;
        this.lessonid = lessonid;
    }

    public SubTopic(String name, String description, int lessonid) {
        this.name = name;
        this.description = description;
        this.lessonid = lessonid;
    }
}
