package com.questionnaire.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "response")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection()
    @CollectionTable(
            name = "answers"
    )
    @MapKeyColumn(name = "field_id")
    @MapKeyClass(Field.class)
    @Column(name = "value")
    private Map<Field, String> map = new HashMap<>();

    @ManyToOne
    private User user;

    public Response() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
