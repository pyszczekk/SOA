package pl.edu.agh.soa.rest;

import io.swagger.annotations.*;
import pl.edu.agh.soa.models.Course;
import pl.edu.agh.soa.models.Student;
import pl.edu.agh.soa.models.StudentsList;
import pl.edu.agh.soa.rest.jwt.JWTTokenNeeded;


import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Student Service")
@Path("/db/students")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class DataBaseStudentService {

    @EJB
    StudentServiceDB dao = new StudentServiceDB();

    @GET
    @ApiOperation(value = "Get all students")
    @ApiResponses({@ApiResponse(code=200, message="Success - all students returned")})
    public List<Student> getAll(@ApiParam(value = "filter faculty", required = false) @QueryParam("faculty") String faculty, @ApiParam(value = "filter field of study", required = false)@QueryParam("field") String field) {

        if(faculty != null && field==null){
            return dao.sameFacultyStudents(faculty);
        }
        if(field!=null && faculty==null){
            return dao.sameFieldStudents(field);
        }
        if(field!=null && faculty!=null){
            return dao.sameFieldAndFacultyStudents(field,faculty);
        }
        return dao.getAllStudents();
    }

    @GET
    @Path("courses/{name}")
    @ApiOperation(value = "Get students from same course")
    @ApiResponses({@ApiResponse(code=200, message="Success - returned students"), @ApiResponse(code=404, message = "Not found")})
    public Response getSameCourseStudents( @PathParam("name") String name){
        try {
            List<Student> student = dao.sameCourseStudent(name);
            return Response.ok(student).status(Response.Status.OK).build();    // 200
        }
        catch (Exception ex) {
            return Response.notModified().status(Response.Status.NOT_FOUND).build(); //404
        }

    }
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get student by id")
    @ApiResponses({@ApiResponse(code=200, message="Success - returned student"), @ApiResponse(code=404, message = "Not found")})
    public Response getStudent(@ApiParam(value = "id of student to return", required=true) @Min(0) @PathParam("id") int id) {

        try {
            Student student = dao.findStudent(id);
            return Response.ok(student).status(Response.Status.OK).build();    // 200
        }
        catch (Exception ex) {
            return Response.notModified().status(Response.Status.NOT_FOUND).build(); //404
        }

    }
    @GET
    @Path("/{id}/courses")
    @ApiOperation(value = "Get student by id")
    @ApiResponses({@ApiResponse(code=200, message="Success - returned student's courses"), @ApiResponse(code=404, message = "Not found")})
    public Response getStudentsCourse(@ApiParam(value = "id of student to return", required=true) @Min(0) @PathParam("id") int id) {

        try {
            List<String> courses = dao.getStudentCourses(id);
            return Response.ok(courses).status(Response.Status.OK).build();    // 200
        }
        catch (Exception ex) {
            return Response.notModified().status(Response.Status.NOT_FOUND).build(); //404
        }

    }


    @POST
    @JWTTokenNeeded
    @ApiOperation(value = "Add student", authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt"  )
    @ApiResponses({@ApiResponse(code=201, message="Created new student"), @ApiResponse(code=409, message="Conflict - student already exist")})
    public Response save(@Valid Student student) {
        try {

                dao.addStudent(student);
                return Response.ok().status(Response.Status.CREATED).build(); //.created(uri) // 201

        }catch (Exception ex){
            return Response.notModified().status(Response.Status.CONFLICT).build(); // 409 The request could not be completed due to a conflict with the current state of the resource.

        }

    }
    @POST
    @Path("/{id}/courses")
    @JWTTokenNeeded
    @ApiOperation(value =  "Add course to student",authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt"   )
    @ApiResponses({@ApiResponse(code=201,message="Added course"), @ApiResponse(code=404, message = "Not Found student")})
    public Response addCourseToStudent(@ApiParam(value = "id of student to add course", required=true) @Min(0) @PathParam("id") int id, @ApiParam(value = "Course to add", required = true) @Valid Course course){
        try {
            dao.addCourseToStudent(course, id);
            return Response.ok().status(Response.Status.OK).build();    // 200
        }
        catch (Exception ex) {
            return Response.notModified().status(Response.Status.NOT_FOUND).build(); //404
        }
    }
    @PUT
    @Path("/{id}/courses")
    @JWTTokenNeeded
    @ApiOperation(value = "Delete course from student ", authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt" )
    @ApiResponses({@ApiResponse(code=204, message="Deleted course"), @ApiResponse(code=404, message = "Not Found")})
    public Response deleteCourseFromStudent(@ApiParam(value = "id of student to delete course", required=true) @Min(0) @PathParam("id") int id,@ApiParam(value = "Course to delete", required = true) @Valid Course course){
        try {

            dao.deleteCourseFromStudent(id, course);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }
        catch(Exception ex) {


            return Response.notModified().status(Response.Status.NOT_FOUND).build();
        }
    }
    @PUT
    @Path("/{id}")
    @JWTTokenNeeded
    @ApiOperation(value = "Update student", authorizations = {@Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt" )
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 409, message = "Conflict"), @ApiResponse(code=409, message = "Conflict")})
    public Response updateStudent(@ApiParam(value = "id of student to update", required = true) @Min(0) @PathParam("id") int id,
                                  @ApiParam(value = "Student to update", required = true) @Valid Student student) {
        try {
            dao.updateStudent(student, id);
            return Response.ok().status(Response.Status.OK).build();    // 200
        }
        catch (Exception ex) {
            if(ex.getMessage()=="noStudent"){
                return Response.notModified().status(Response.Status.NOT_FOUND).build(); // 404
            }
            return Response.notModified().status(Response.Status.CONFLICT).build(); //409
        }
    }
    @DELETE
    @Path("/{id}")
    @JWTTokenNeeded
    @ApiOperation(value = "Delete student by id", authorizations = { @Authorization(value = "jwt")}, notes = "Wymagana autoryzacja jwt"  )
    @ApiResponses({@ApiResponse(code=204, message="Deleted"), @ApiResponse(code=404, message = "Not Found")})
    public Response deleteStudent(@ApiParam(value = "id of student to be deleted", required=true) @Min(0) @PathParam("id") int id) {
        try{
            dao.deleteStudent(id);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }
        catch(Exception ex){
            return Response.notModified().status(Response.Status.NOT_FOUND).build();
        }
    }
}
