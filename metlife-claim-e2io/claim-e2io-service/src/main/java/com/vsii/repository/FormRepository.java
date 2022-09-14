package com.vsii.repository;

import com.vsii.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {
    Form findFormByFormIdAndFormActiveAndFormClassname(String formId, String formActive, String formClass);

    List<Form> findByFormActiveAndFormClassname(String formActive, String formClass);
}
