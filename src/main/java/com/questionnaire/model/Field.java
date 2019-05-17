package com.questionnaire.model;

import com.questionnaire.model.enumeration.FieldType;
import com.questionnaire.model.enumeration.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "field")
@TypeDef(
        name = "psql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Field implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private Long id;

    @NotNull
    @Column(name = "label")
    private String label;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "field_type")
    @Type(type = "psql_enum")
    private FieldType type;

    @Column(name = "isActive")
    private boolean active;

    @Column(name = "required")
    private boolean required;

    @ElementCollection
    private Set<String> options = new HashSet<>();

    public Field() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void isActive(boolean active) {
        active = active;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        this.options = options;
    }
}
