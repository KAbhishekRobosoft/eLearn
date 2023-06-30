package com.eLearn.eLearning.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String image;


    @OneToMany(mappedBy ="subject",cascade = CascadeType.ALL)
    private List<Section> sectionlist = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @JsonManagedReference
    public List<Section> getSectionlist() {
        return sectionlist;
    }

    public void setScetionlist(List<Section> lessonlist) {
        this.sectionlist = lessonlist;
    }

    public Subject(int id, String name, String description,String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image= image;
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
