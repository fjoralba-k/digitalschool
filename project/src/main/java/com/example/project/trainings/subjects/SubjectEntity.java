package org.zerogravitysolutions.digitalschool.trainings.subjects;

import org.zerogravitysolutions.digitalschool.commons.BaseEntity;
import org.zerogravitysolutions.digitalschool.trainings.TrainingEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "subjects")
public class SubjectEntity extends BaseEntity {

    private String title;
    private String description;
    private String icon;

    @ManyToOne
    @JoinColumn(name = "training_id")
    // @JsonIgnoreProperties("subjects")
    @JsonIgnore
    private TrainingEntity training;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TrainingEntity getTraining() {
        return training;
    }

    public void setTraining(TrainingEntity training) {
        this.training = training;
    }
}
