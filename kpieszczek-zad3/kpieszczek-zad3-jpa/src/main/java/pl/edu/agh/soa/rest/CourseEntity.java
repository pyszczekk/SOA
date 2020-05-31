package pl.edu.agh.soa.rest;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="courseId", nullable = false)
    private int courseId;

    @Column(name="name", nullable = false)
    private String name;


    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<StudentEntity> students;

    public CourseEntity(){super();};
    public CourseEntity(String name){
        super();
        this.name=name;
    }
    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }
}
