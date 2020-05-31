package pl.edu.agh.soa.rest;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculty")
public class FacultyEntity {
    @Id
    @Column(name = "faculty", nullable = false)
    private String faculty;

    @OneToMany( mappedBy = "faculty",cascade=CascadeType.ALL,  orphanRemoval = true)
    private List<StudentEntity> studentsFromSameFaculty;
    public FacultyEntity(){
        super();
    }
    public FacultyEntity(String name){
        super();
        this.faculty=name;
    }

    public String getFaculty() {
        return faculty;
    }

    public List<StudentEntity> getStudentsFromSameFaculty() {
        return studentsFromSameFaculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

}
