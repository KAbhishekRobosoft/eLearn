package com.eLearn.eLearning.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lesson")
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "sectionid", insertable= false,updatable = false)
    private Section section;

    private int sectionid;

    private String image;


    @OneToMany(mappedBy ="lesson",cascade = CascadeType.ALL)
    private List<SubTopic> subtopiclist = new ArrayList<>();

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonManagedReference
    public List<SubTopic> getSubtopiclist() {
        return subtopiclist;
    }

    public void setSubtopiclist(List<SubTopic> subtopiclist) {
        this.subtopiclist = subtopiclist;
    }

    public Lesson(int id, String name, String description, int sectionid, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sectionid = sectionid;
        this.image = image;
    }

    public Lesson(String name, String description, int sectionid) {
        this.name = name;
        this.description = description;
        this.sectionid = sectionid;
    }
}
