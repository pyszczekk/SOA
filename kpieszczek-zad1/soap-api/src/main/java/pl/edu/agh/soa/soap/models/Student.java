package pl.edu.agh.soa.soap.models;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    private String name;
    private String lastName;
    private String faculty;
    private String fieldOfStudy;
    private int studentId;
    @XmlElementWrapper(name="courses")
    @XmlElement(name="course")
    private List<String> courses;

    public Student(){};

    public Student(String name, String lastName, String faculty, String fieldOfStudy, int studentId) {
        this.name = name;
        this.lastName = lastName;
        this.faculty = faculty;
        this.fieldOfStudy = fieldOfStudy;
        this.studentId = studentId;
        this.courses = new ArrayList<>();

    }
    public String getName(){
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getFieldOfStudy(){
        return fieldOfStudy;
    }

    public int getStudentId() {
        return studentId;
    }

    public List<String> getCourses() {
        return courses;
    }
    public void addCourse(String course){
        this.courses.add(course);
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
        return "Student{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", faculty='" + faculty + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", studentId=" + studentId +
                ", courses=" + courses +
                '}';
    }
}
