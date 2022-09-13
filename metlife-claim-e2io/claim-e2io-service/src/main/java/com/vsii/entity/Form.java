package com.vsii.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the FORMS database table.
 */
@Entity
@Table(name = "FORMS", schema = "IWS")
@NamedQuery(name = "Form.findAll", query = "SELECT f FROM Form f")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Form implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FORM_ID")
    private String formId;

    @Column(name = "FORM_ACTIVE")
    private String formActive;

    @Column(name = "FORM_CATEGORY")
    private String formCategory;

    @Column(name = "FORM_CLASSNAME")
    private String formClassname;

    @Column(name = "FORM_NAME")
    private String formName;

    @Lob
    @Column(name = "SEGMENT_CONFIG")
    private String segmentConfig;

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormActive() {
        return this.formActive;
    }

    public void setFormActive(String formActive) {
        this.formActive = formActive;
    }

    public String getFormCategory() {
        return this.formCategory;
    }

    public void setFormCategory(String formCategory) {
        this.formCategory = formCategory;
    }

    public String getFormClassname() {
        return this.formClassname;
    }

    public void setFormClassname(String formClassname) {
        this.formClassname = formClassname;
    }

    public String getFormName() {
        return this.formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getSegmentConfig() {
        return this.segmentConfig;
    }

    public void setSegmentConfig(String segmentConfig) {
        this.segmentConfig = segmentConfig;
    }

}