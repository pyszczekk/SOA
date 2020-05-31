package pl.edu.agh.soa.rest;

import pl.edu.agh.soa.models.Student;

import javax.ejb.Stateless;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Stateless
public class Mapper {
    public StudentEntity studentToStudentEntity(Student student) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(student.getName());
        studentEntity.setFaculty(new FacultyEntity(student.getFaculty()));
        studentEntity.setLastName(student.getLastName());
        studentEntity.setStudentId(student.getStudentId());
        studentEntity.setFieldOfStudy(new FieldOfStudyEntity(student.getFieldOfStudy()));
        if(student.getCourses()!=null){
            List<CourseEntity> courseEntityList = student.getCourses().stream().map(this::toCourseEntity).collect(toList()); // c -> toCourseEntity(c)
            studentEntity.setCourses(courseEntityList);
        }
        return studentEntity;
    }

    public Student studentEntityToStudent(StudentEntity studentEntity) {
        Student student = new Student();
        student.setStudentId(studentEntity.getStudentId());
        student.setName(studentEntity.getName());
        student.setLastName(studentEntity.getLastName());
        student.setFaculty(studentEntity.getFaculty());
        student.setFieldOfStudy(studentEntity.getFieldOfStudy());
        List<String> courseList = studentEntity.getCourses().stream().map(this::toCourse).collect(toList());
        student.setCourses(courseList);
        return student;
    }
    public CourseEntity toCourseEntity(String course) {
        return new CourseEntity(course);
    }

    public String toCourse(CourseEntity courseEntity) {
        return courseEntity.getName();
    }
    public List<Student> studentEntityListToStudentList(List<StudentEntity> studentEntityList) {
        return studentEntityList.stream().map(this::studentEntityToStudent).collect(toList());
    }
    public List<String> toCoursesList(List<CourseEntity> courses){
        return courses.stream().map(this::toCourse).collect(toList());
    }
    public List<CourseEntity> toEntityCoursesList(List<String> courses){
        return courses.stream().map(this::toCourseEntity).collect(toList());
    }
}
