package com.questionnaire.repository;

import com.questionnaire.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    Field findByLabel(String label);
}