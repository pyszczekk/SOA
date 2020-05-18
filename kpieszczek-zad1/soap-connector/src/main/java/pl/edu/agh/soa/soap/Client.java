package pl.edu.agh.soa.soap;


import javax.xml.ws.BindingProvider;
import java.util.List;
import java.awt.*;
import java.io.*;
import java.util.Base64;


public class Client {

    public static void main(String[] args){
        StudentServiceImplService service = new StudentServiceImplService();
        StudentService serviceI = service.getStudentPort();


        Student addedStudent = serviceI.addStudent("Karolina", "Pieszczek","EAIiIB","Informatyka",123);
        System.out.println("Student1: "+ printStudent(addedStudent));

        Student addedStudent2 = serviceI.addStudent("Anna", "Nowak","IEiT","Informatyka",234);
        System.out.println("student2: " +printStudent(addedStudent2));

        Student addedStudent3 = serviceI.addStudent("Jakub", "Kowalski","EAIiIB","Inżynieria Biomedyczna",345);
        System.out.println("student3: "+printStudent(addedStudent3));

        Student addedCourse = serviceI.addCourseToStudent(234, "UNIX");
        System.out.println("student after adding course: "+printStudent(addedCourse));
        Student addedCourse2 = serviceI.addCourseToStudent(123, "SOA");
        System.out.println("student after adding course: "+printStudent(addedCourse2));
        Student addedCourse3 = serviceI.addCourseToStudent(123, "Teoria Obliczeń");
        System.out.println("student after adding course: "+printStudent(addedCourse3));

        List<Student> allStudents =  serviceI.getAllStudents();
        System.out.println("all students") ;
        printStudentList(allStudents);
        List<String> courses = serviceI.getStudentCourses(123);
        System.out.println("courses of student 123: " + courses);
        Student deletedCourse = serviceI.deleteCourseFromStudent(123,"Teoria Obliczeń");
        System.out.println("student after deleting course"+printStudent(deletedCourse));

        Student student = serviceI.getStudent(234);
        System.out.println("student id 234:"+printStudent(student));

        byte[] avatar = serviceI.getAvatar();
        avatar = Base64.getDecoder().decode(avatar);
        System.out.println("avatar!");
        String FILEPATH = "gotAvatar.png";
        File file = new File(FILEPATH);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(avatar);
            os.close();
            Desktop dt = Desktop.getDesktop();
            dt.open(file);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }



        List<Student> sameFaculty = serviceI.getStudentsFromSameFaculty("EAIiIB");
        System.out.println("Same faculty: EAIiIB");
        printStudentList(sameFaculty);

        List<Student> sameField = serviceI.getStudentsFromSameField("Informatyka");
        System.out.println("Same Fiels of study: Informatyka");
        printStudentList(sameField);

        String deleted = serviceI.deleteStudent(234);
        System.out.println("student delete operation: "+deleted);
        List<Student> allStudents2 =  serviceI.getAllStudents();
        System.out.println("all students") ;
        printStudentList(allStudents2);

        HelloSecurityService security = new HelloSecurityService();
        SecurityDomain securityI = security.getSecurityPort();

        setCredentials((BindingProvider) securityI);
        String response = securityI.securityHello();
        System.out.println("auth:" + response);
    }

    private static void setCredentials(BindingProvider port) {
        port.getRequestContext().put("javax.xml.ws.security.auth.username","User");
        port.getRequestContext().put("javax.xml.ws.security.auth.password","user");

    }
    private static String printStudent(Student student){
        return "Student{" +
                "name='" + student.getName() + '\'' +
                ", lastName='" + student.getLastName() + '\'' +
                ", faculty='" + student.getFaculty() + '\'' +
                ", fieldOfStudy='" + student.getFieldOfStudy() + '\'' +
                ", studentId=" + student.getStudentId() +
                ", courses=" +  student.getCourses().getCourse() +
                '}';
    }

    private static void printStudentList(List<Student> students) {

        for (Student student : students) {

           System.out.println(printStudent(student));
        }

    }
}
