package pl.edu.agh.soa.soap;

import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.soap.models.Student;

import javax.annotation.security.PermitAll;

import javax.ejb.Stateless;
import javax.jws.WebMethod;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.*;
import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Stateless
@WebService(name="StudentService", portName = "StudentPort", targetNamespace = "https://soap.soa.pl/soa/ws")
//@SecurityDomain("my-security-domain") // in standalone.xml
//@DeclareRoles({"MyRole"})
@WebContext(contextRoot="soa", urlPattern="/studentService")
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL)
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentServiceImpl implements StudentService {


    @Override
    @PermitAll
    @WebMethod(operationName = "addStudent")
    public Student addStudent(String name, String lastName, String faculty, String fieldOfStudy, int studentId) {
        Student student = new Student(name, lastName,faculty, fieldOfStudy, studentId);
        StudentsList.getInstance().addStudent(student);
        return student;
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "getStudent")
    public Student getStudent(int studentId) {
        return StudentsList.getInstance().getStudent(studentId);
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "deleteStudent")
    public String deleteStudent(int studentId) {
        if(StudentsList.getInstance().getStudent(studentId)!=null){
            StudentsList.getInstance().removeStudent(StudentsList.getInstance().getStudent(studentId));
            return "Removed";
        }
        return "couldn't find student with this id";
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "addCourseToStudent")
    public Student addCourseToStudent(int studentId, String course) {
        StudentsList.getInstance().getStudent(studentId).addCourse(course);
        return StudentsList.getInstance().getStudent(studentId);
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "deleteCourseFromStudent")
    public Student deleteCourseFromStudent(int studentId, String course) {
        StudentsList.getInstance().getStudent(studentId).deleteCourse(course);
        return StudentsList.getInstance().getStudent(studentId);
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "getStudentCourses")
    public List<String> getStudentCourses(int studentId) {
        return StudentsList.getInstance().getStudent(studentId).getCourses();
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "getAllStudents")
    public List<Student> getAllStudents() {
        return StudentsList.getInstance().getAllStudents();
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "getStudentsFromSameFaculty")
    public List<Student> getStudentsFromSameFaculty(String facultyFilter) {
        List<Student> facultyStudents = new ArrayList<>();

        for (Student student : StudentsList.getInstance().getAllStudents()){
           if(student.getFaculty().equals(facultyFilter)){
               facultyStudents.add(student);
           }
        }
        return facultyStudents;
    }

    @Override
    @PermitAll
    @WebMethod(operationName = "getStudentsFromSameField")
    public List<Student> getStudentsFromSameField(String fieldOfStudyFilter) {
        List<Student> sameFieldStudents = new ArrayList<>();

        for (Student student : StudentsList.getInstance().getAllStudents()){
            if(student.getFieldOfStudy().equals(fieldOfStudyFilter)){
                sameFieldStudents.add(student);
            }
        }
        return sameFieldStudents;
    }
    @Override
    @PermitAll
    @WebMethod(operationName = "getAvatar")
    public byte[] getAvatar() {

        try{
                //absolute path
            String fileName = "/Users/pyszczekk/Desktop/wszystko/zabawa w studia/semestr VI/SOA/kpieszczek-zad1/soap-api/src/main/java/pl/edu/agh/soa/soap/avatar.png";
            Path path  = Paths.get(fileName);

            byte[] fileContent = Files.readAllBytes(path);

            return Base64.getEncoder().encode(fileContent);

        } catch (IOException ex) {
            System.err.println(ex);
            throw new WebServiceException(ex);
        }
    }
}
