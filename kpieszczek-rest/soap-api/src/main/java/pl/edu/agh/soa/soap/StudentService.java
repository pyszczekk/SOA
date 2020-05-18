package pl.edu.agh.soa.soap;

import pl.edu.agh.soa.soap.models.Student;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService
public interface StudentService {

    public Student addStudent(@WebParam(name="name") String name,
                             @WebParam(name="lastName") String lastName,
                             @WebParam(name="faculty") String faculty,
                             @WebParam(name="fieldOfStudy") String fieldOfStudy,
                             @WebParam(name="studentId") int studentId);
    public Student getStudent(@WebParam(name="studentId") int studentId);
    public String deleteStudent(@WebParam(name="studentId") int studentId);
    public Student addCourseToStudent(@WebParam(name="studentId") int studentId,
                                      @WebParam(name="course") String course);
    public Student deleteCourseFromStudent(@WebParam(name="studentId") int studentId,
                                          @WebParam(name="course") String course);
    public List<String> getStudentCourses(@WebParam(name="studentId") int studentId);

    public List<Student> getAllStudents();
    public List<Student> getStudentsFromSameFaculty(@WebParam(name="faculty") String facultyFilter);
    public List<Student> getStudentsFromSameField(@WebParam(name="fieldOfStudy") String fieldOfStudyFilter);
    public byte[] getAvatar();


}
