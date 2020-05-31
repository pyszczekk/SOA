package pl.edu.agh.soa.rest;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fieldOfStudy")
public class FieldOfStudyEntity {
    @Id
    @Column(name = "fieldOfStudy", nullable = false)
    private String fieldOfStudy;

    @OneToMany( mappedBy = "fieldOfStudy",cascade=CascadeType.ALL,  orphanRemoval = true)
    private List<StudentEntity> studentsFromSameFieldOfStudy;

    public FieldOfStudyEntity(){super();}
    public FieldOfStudyEntity(String name){
        super();
        this.fieldOfStudy=name;
    }
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public List<StudentEntity> getStudentsFromSameFieldOfStudy() {
        return studentsFromSameFieldOfStudy;
    }
}
