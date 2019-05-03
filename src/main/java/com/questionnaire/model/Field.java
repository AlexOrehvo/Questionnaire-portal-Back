package com.questionnaire.model;

import com.questionnaire.model.enumeration.FieldType;
import com.questionnaire.model.enumeration.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "field")
@TypeDef(
        name = "psql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Field {

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
    private boolean isActive = false;

    @Column(name = "required")
    private boolean required = false;

    @ManyToOne
    private User user;

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
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return getId() == field.getId() &&
                isActive() == field.isActive() &&
                isRequired() == field.isRequired() &&
                getLabel().equals(field.getLabel()) &&
                getType() == field.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLabel(), getType(), isActive(), isRequired());
    }

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", type=" + type +
                ", isActive=" + isActive +
                ", required=" + required +
                '}';
    }
}
