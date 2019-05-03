package com.questionnaire.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "response")
public class Response implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection()
    @CollectionTable(
            name = "answers"
    )
    @MapKeyColumn(name = "field_id")
    @MapKeyClass(Field.class)
    @Column(name = "value")
    private Map<Field, String> map = new HashMap<Field, String>();

    @JsonIgnore
    @ManyToOne
    private User user;

    public Response() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Field, String> getMap() {
        return map;
    }

    public void setMap(Map<Field, String> map) {
        this.map = map;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", map=" + map.toString() +
                ", user=" + user.toString() +
                '}';
    }
}
