package pl.edu.agh.soa.models;

import org.hibernate.validator.constraints.*;


import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @NotEmpty
    private String name;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String faculty;
    @NotEmpty
    private String fieldOfStudy;
    @NotNull
    private int studentId;
    @XmlElementWrapper(name="courses")
    @XmlElement(name="courses")
    protected List<String> courses;

    public Student(){};

    public Student(String name, String lastName, String faculty, String fieldOfStudy, int studentId) {
        this.name = name;
        this.lastName = lastName;
        this.faculty = faculty;
        this.fieldOfStudy = fieldOfStudy;
        this.studentId = studentId;
        this.courses = new ArrayList<String>();


    }
    public Student(String name, String lastName, String faculty, String fieldOfStudy, int studentId, List<String> courses) {
        this.name = name;
        this.lastName = lastName;
        this.faculty = faculty;
        this.fieldOfStudy = fieldOfStudy;
        this.studentId = studentId;
        this.courses =courses;


    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFieldOfStudy(){
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public List<String> getCourses() {
        return courses;
    }
    public void addCourse(String course){
        if(this.courses==  null){

            this.courses= new ArrayList<String>();
        }
        this.courses.add(course);
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public void deleteCourse(String course){
        this.courses.remove(course);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId &&
                Objects.equals(name, student.name) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(faculty, student.faculty) &&
                Objects.equals(fieldOfStudy, student.fieldOfStudy) &&
                Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, faculty, fieldOfStudy, studentId, courses);
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + "\"" +
                ", \"lastName\":\"" + lastName + '\"' +
                ", \"faculty\":\"" + faculty + '\"' +
                ", \"fieldOfStudy\":\"" + fieldOfStudy + '\"' +
                ", \"studentId\":" + studentId +
                ", \"courses\":" + courses +
                '}';
    }
}
