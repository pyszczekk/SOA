package pl.edu.agh.soa.rest;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

import java.util.List;

import io.swagger.annotations.*;
import pl.edu.agh.soa.models.Student;
import pl.edu.agh.soa.models.StudentOuterClass;
import pl.edu.agh.soa.models.StudentsList;
import pl.edu.agh.soa.models.Course;
import pl.edu.agh.soa.rest.jwt.JWTTokenNeeded;

@Api(value = "Student Service")
@Path("/students")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class Students {
    @GET
    @ApiOperation(value = "Get all students")
    @ApiResponses({@ApiResponse(code=200, message="Success - all students returned")})
    public List<Student> getAll(@ApiParam(value = "filter faculty", required = false) @QueryParam("faculty") String faculty, @ApiParam(value = "filter field of study", required = false)@QueryParam("field") String field) {
        if(faculty != null){
            List<Student> facultyStudents = new ArrayList<>();

            for (Student student : StudentsList.getInstance().getAllStudents()){
                if(student.getFaculty().equals(faculty)){
                    facultyStudents.add(student);
                }
            }
            return facultyStudents;
        }
        if(field!=null){
            List<Student> sameFieldStudents = new ArrayList<>();

            for (Student student : StudentsList.getInstance().getAllStudents()){
                if(student.getFieldOfStudy().equals(field)){
                    sameFieldStudents.add(student);
                }
            }
            return sameFieldStudents;
        }
        return StudentsList.getInstance().getAllStudents();
    }



    @POST
    @JWTTokenNeeded
    @ApiOperation(value = "Add student", authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt"  )
    @ApiResponses({@ApiResponse(code=201, message="Created new student"), @ApiResponse(code=409, message="Conflict - student already exist")})
    public Response save(@Valid Student student) {
        if (StudentsList.getInstance().getStudent(student.getStudentId()) == null){
            StudentsList.getInstance().addStudent(student);
        return Response.ok().status(Response.Status.CREATED).build();
        }else{
            return Response.notModified().status(Response.Status.CONFLICT).build();
        }
    }


    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get student by id")
    @ApiResponses({@ApiResponse(code=200, message="Success - returned student"), @ApiResponse(code=404, message = "Not found")})
    public Response getStudent(@ApiParam(value = "id of student to return", required=true) @Min(0) @PathParam("id") int id) {
        if(StudentsList.getInstance().getStudent(id)!=null) {
            return Response.ok(StudentsList.getInstance().getStudent(id)).status(Response.Status.OK).build();
        }
        return Response.notModified().status(Response.Status.NOT_FOUND).build();
    }
    @PUT
    @Path("/{id}")
    @JWTTokenNeeded
    @ApiOperation(value = "Update student", authorizations = {@Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt" )
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code=409, message = "Conflict")})
    public Response updateStudent(@ApiParam(value = "id of student to update", required = true) @Min(0) @PathParam("id") int id,
                                  @ApiParam(value = "Student to update", required = true) @Valid Student student) {
        if(StudentsList.getInstance().getStudent(id)!=null){
            if(id == student.getStudentId()){
                StudentsList.getInstance().updateStudent(student);
                return Response.ok().status(Response.Status.OK).build();
            }
            return Response.notModified().status(Response.Status.CONFLICT).build();

        }
        return Response.notModified().status(Response.Status.NOT_FOUND).build();
    }
    @DELETE
    @Path("/{id}")
    @JWTTokenNeeded
    @ApiOperation(value = "Delete student by id", authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt"  )
    @ApiResponses({@ApiResponse(code=200, message="Deleted"), @ApiResponse(code=404, message = "Not Found")})
    public Response deleteStudent(@ApiParam(value = "id of student to be deleted", required=true) @Min(0) @PathParam("id") int id) {
        if(StudentsList.getInstance().getStudent(id)!=null){
           StudentsList.getInstance().removeStudent(StudentsList.getInstance().getStudent(id));
           return Response.ok().status(Response.Status.NO_CONTENT).build();
        }
        return Response.notModified().status(Response.Status.NOT_FOUND).build();
    }
    @GET
    @Path("/{id}/courses")
    @ApiOperation(value = "Get student courses")
    @ApiResponses({@ApiResponse(code=200, message="Success"), @ApiResponse(code=404, message = "Not Found")})
    public Response getStudentCourses(@ApiParam(value = "id of student to show courses", required=true)@Min(0) @PathParam("id") int id) {
        if(StudentsList.getInstance().getStudent(id)!=null) {
            return Response.ok(StudentsList.getInstance().getStudent(id).getCourses()).status(Response.Status.OK).build();
        }
        return Response.notModified().status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/{id}/courses")
    @JWTTokenNeeded
    @ApiOperation(value =  "Add course to student",authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt"   )
    @ApiResponses({@ApiResponse(code=201,message="Added course"), @ApiResponse(code=404, message = "Not Found student")})
    public Response addCourseToStudent(@ApiParam(value = "id of student to add course", required=true) @Min(0) @PathParam("id") int id, @ApiParam(value = "Course to add", required = true) @Valid Course course){
        if(StudentsList.getInstance().getStudent(id)!=null) {
            StudentsList.getInstance().getStudent(id).addCourse(course.getName());
            return Response.ok().status(Response.Status.CREATED).build();
        }
        return Response.notModified().status(Response.Status.NOT_FOUND).build();
    }
    @PUT
    @Path("/{id}/courses")
    @JWTTokenNeeded
    @ApiOperation(value = "Delete course from student ", authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt" )
    @ApiResponses({@ApiResponse(code=204, message="Deleted course"), @ApiResponse(code=404, message = "Not Found")})
    public Response deleteCourseFromStudent(@ApiParam(value = "id of student to delete course", required=true) @Min(0) @PathParam("id") int id,@ApiParam(value = "Course to delete", required = true) @Valid Course course){
        if(StudentsList.getInstance().getStudent(id)!=null) {
            StudentsList.getInstance().getStudent(id).deleteCourse(course.getName());
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }
        return Response.notModified().status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/avatar")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Get app icon")
    @ApiResponses({@ApiResponse(code=200, message="Success")})
    public Response getIcon() throws IOException {

        String fileName = "/Users/pyszczekk/Desktop/wszystko/zabawa w studia/semestr VI/SOA/kpieszczek-rest/rest-api/src/main/java/pl/edu/agh/soa/rest/avatar.png";
        java.nio.file.Path path  = Paths.get(fileName);

        byte[] fileContent = Files.readAllBytes(path);

        return Response.ok(Base64.getEncoder().encode(fileContent)).status(Response.Status.OK).build();

    }
    @GET
    @Path("/proto")
    @Produces("application/protobuf")
    @ApiOperation(value = "ProtocolBuffer Student")
    @ApiResponses({@ApiResponse(code=200, message="Success")})
    public Response getProtoStudent() {
        StudentOuterClass.Student.Builder builder = StudentOuterClass.Student.newBuilder();

        List<String> courses = new ArrayList<>();
        courses.add("SOA");
        courses.add("UNIX");
        builder.addAllCourses(courses);
        builder
                .setName("Karolina")
                .setLastName("Pieszczek")
                .setFaculty("EAIiIB")
                .setField("Informatyka")
                .setStudentId(12);
        StudentOuterClass.Student student = builder.build();

        return Response.ok(student.toByteArray(), "application/protobuf").build();
    }

}