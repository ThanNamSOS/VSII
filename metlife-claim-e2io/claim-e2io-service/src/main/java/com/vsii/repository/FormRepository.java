package com.vsii.repository;

import com.vsii.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, String> {
    List<Form> findByFormId(String formId);

    List<Form> findByFormActive(String formActive);
}
