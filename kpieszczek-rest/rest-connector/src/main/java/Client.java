import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import pl.edu.agh.soa.models.*;

import javax.imageio.ImageIO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class Client {
    private static final String BASE_API_URL = "http://localhost:8080/rest-api/api";

    private ResteasyClient client;
    private ResteasyWebTarget target;
    private Response response;

    public Client() {
        client = new ResteasyClientBuilder().build();
        target = client.target(BASE_API_URL);
    }
    public void addStudent(Student student, String token) {
        System.out.println("Add new student");
        target = client.target(BASE_API_URL);
        response = null;
        target = target.path("students");
        response = target.request(MediaType.APPLICATION_JSON).header("Authorization", token).post(Entity.entity(student.toString(), MediaType.APPLICATION_JSON ));
        //System.out.println(student);
        System.out.println(response.getStatusInfo());

    }
    public List<Student> getStudents() {
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("Get all students");
        target = target.path("students");
        response = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response.getStatusInfo());

        List<Student> students = response.readEntity(new GenericType<List<Student>>() {});
        return students;
    }
    public List<Student> getStudentsFromSameFaculty(String faculty) {
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("Get all students from same faculty");
        target = target.path("students");
        target = target.queryParam("faculty",faculty);
        response = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response.getStatusInfo());

        List<Student> students = response.readEntity(new GenericType<List<Student>>() {});
        return students;
    }
    public List<Student> getStudentsFromSameField(String fieldOfStudy) {
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("Get all students from same field of study");
        target = target.path("students");
        target = target.queryParam("field",fieldOfStudy);
        response = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response.getStatusInfo());

        List<Student> students = response.readEntity(new GenericType<List<Student>>() {});

        return students;
    }
    public Student getStudent(int id){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("get student by id");
        target = target.path("students").path(Integer.toString(id));
        response = target.request().get();

        System.out.println(response.getStatusInfo());

        Student student = null;
        if (response.getStatusInfo() == Response.Status.OK) {
            student = response.readEntity(new GenericType<Student>() {});
        }
        return student;
    }

    public void updateStudent(Student student, String token){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("update student ");
        target = target.path("students").path(Integer.toString(student.getStudentId()));
        response = target.request().header("Authorization", token).put(Entity.entity(student.toString(), MediaType.APPLICATION_JSON ));
        System.out.println(response.getStatusInfo());
    }
    public void deleteStudent(int id, String token){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("delete student");
        target = target.path("students").path(Integer.toString(id));
        response = target.request().header("Authorization", token).delete();
        System.out.println(response.getStatusInfo());

    }
    public List<String> getStudentCourses(int id){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("get students courses");
        target = target.path("students").path(Integer.toString(id)).path("courses");
        response = target.request().get();

        System.out.println(response.getStatusInfo());
        return response.readEntity(new GenericType<List<String>>() {});
    }
    public void addCourse(int id, Course course, String token){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("add Course to student");
        target = target.path("students").path(Integer.toString(id)).path("courses");
        response = target.request().header("Authorization", token).post(Entity.entity(course, MediaType.APPLICATION_JSON ));

        System.out.println(response.getStatusInfo());

    }
    public void deleteCourse(int id, Course course, String token){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("delete Course from student");
        target = target.path("students").path(Integer.toString(id)).path("courses");

        response = target.request().header("Authorization", token).put(Entity.entity(course, MediaType.APPLICATION_JSON ));

        System.out.println(response.getStatusInfo());

    }
    public void getAvatar(){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("get avatar");
        target = target.path("students").path("avatar");
        response = target.request().get();

        System.out.println(response.getStatusInfo());

        String avatar = response.readEntity(String.class);
        byte[] decoded = Base64.getDecoder().decode(avatar);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(decoded);
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "png", new File("gotAvatarRest.png"));

            Desktop dt = Desktop.getDesktop();
            dt.open(new File("gotAvatarRest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public String getProtoBuffStudent() {
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("PROTOBUFF");
        target = target.path("students").path("proto");

        InputStream response = target
                .request()
                .header("accept", "application/protobuf")
                .get(InputStream.class);

        try {
            StudentOuterClass.Student student = StudentOuterClass.Student.parseFrom(IOUtils.toByteArray(response));
            return student.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
    public String getToken(String login, String password){
        target = client.target(BASE_API_URL);
        response = null;
        System.out.println("get token");
        User user = new User(login,password);
        target = target.path("auth").path("login");

        response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON));

        String token = response.getHeaderString("Authorization");
        System.out.println(token);
        System.out.println(response.getStatusInfo());


        return token;
    }


}
