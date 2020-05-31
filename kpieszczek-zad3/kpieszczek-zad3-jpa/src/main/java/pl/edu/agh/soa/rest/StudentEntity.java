package pl.edu.agh.soa.rest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="student")
public class StudentEntity {
    @Id
    @Column(name="studentId", nullable = false)
    private Integer studentId;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="lastName", nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty")
    private FacultyEntity faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fieldOfStudy")
    private FieldOfStudyEntity fieldOfStudy;

    @ManyToMany(targetEntity = CourseEntity.class, cascade = CascadeType.ALL)//cascade = CascadeType.AL
    @JoinTable(
            name = "coursesList",
            joinColumns = @JoinColumn(
                    name = "studentId",
                    referencedColumnName = "studentId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "courseId",
                    referencedColumnName = "courseId"
            )
    )
    private List<CourseEntity> courses;
    public StudentEntity() {
        super();
    }

    public StudentEntity(int id, String name, String lastName, FacultyEntity faculty, FieldOfStudyEntity field) {
        super();
        this.studentId = id;
        this.name = name;
        this.lastName = lastName;
        this.faculty = faculty;
        this.fieldOfStudy = field;
        this.courses= new ArrayList<CourseEntity>();
    }


    public String getFieldOfStudy() {
        return fieldOfStudy.getFieldOfStudy();
    }


    public String getName() {
        return name;
    }

    public String getFaculty() {
        return faculty.getFaculty();
    }


    public void setFaculty(FacultyEntity faculty) {
        this.faculty = faculty;
    }

    public void setFieldOfStudy(FieldOfStudyEntity fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }


    public String getLastName() {
        return lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setCourses(List<CourseEntity> courses) {

        this.courses = courses;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addCourse(CourseEntity courseEntity) {
        if (this.courses == null)
            courses = new ArrayList<>();
        this.courses.add(courseEntity);
    }
    public void removeCourse(CourseEntity courseEntity) {
        if (this.courses == null)
            courses = new ArrayList<>();
        this.courses.remove(courseEntity);
    }


}
