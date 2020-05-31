package pl.edu.agh.soa.rest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="coursesList")
public class StudentCoursesEntity implements Serializable {
    @Id
    @Column(name="studentId", nullable = false)
    private int studentId;
    @Id
    @Column(name="courseId", nullable = false)
    private int courseId;

    public StudentCoursesEntity(int id, int courseId){
        super();
        this.studentId=id;
        this.courseId=courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
