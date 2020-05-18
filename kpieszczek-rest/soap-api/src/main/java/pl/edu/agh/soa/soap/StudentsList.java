package pl.edu.agh.soa.soap;

import pl.edu.agh.soa.soap.models.Student;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class StudentsList {
    @XmlElementWrapper(name="students")
    @XmlElement(name="student")
    private List<Student> studentsList;
    private static StudentsList instance;

    private StudentsList(){
        studentsList = new ArrayList<>();

    }
    public static StudentsList getInstance(){
        if(instance == null){
            instance = new StudentsList();
        }
        return instance;
    }
    public Student getStudent(int studentId){
        Student foundStudent = null;

        for (Student student : studentsList){
            if (student.getStudentId() == studentId) {
                foundStudent = student;
                break;
            }
        }
        return foundStudent;
    }
    public void addStudent(Student student){
        studentsList.add(student);
    }
    public void removeStudent(Student student){
        studentsList.remove(student);
    }
    public List<Student> getAllStudents(){
        return studentsList;
    }


}
