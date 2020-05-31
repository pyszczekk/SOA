import pl.edu.agh.soa.models.Course;
import pl.edu.agh.soa.models.Student;

public class Test {
    public static void main(String[] args) {

        Client client = new Client();
        String token = client.getToken("User","user");
        Student student = new Student("Karolina", "Pieszczek","EAIiIB", "Informatyka",1);
        Student student2 = new Student("Tomasz", "Kowalski","IET", "Informatyka",2);
        Student student3 = new Student("Juliusz", "Cezar","EAIiIB", "Inżynieria Biomedyczna",3);
        client.addStudent(student, token); //201
        client.addStudent(student, token); //409
        client.addStudent(student2, token); //201
        client.addStudent(student3, token); //201
        System.out.println(client.getStudents()); // 200
        System.out.println(client.getStudentsFromSameFaculty("EAIiIB")); // 200
        System.out.println(client.getStudentsFromSameField("Informatyka")); // 200
        System.out.println(client.getStudent(1)); //200
        System.out.println(client.getStudent(100)); //204
        client.addCourse(1, new Course("UNIX"), token);//201
        client.addCourse(1, new Course("SOA"), token);//201
        System.out.println(client.getStudentCourses(1));//200
        client.deleteCourse(1,new Course("UNIX"), token);//204
        System.out.println(client.getStudentCourses(1));//200
        client.updateStudent(new Student("Juliusz", "Cezar Wielki","EAIiIB", "Inżynieria Biomedyczna",3),token); //200
        client.updateStudent(new Student("Juliusz", "Cezar Wielki","EAIiIB", "Inżynieria Biomedyczna",15),token); //404
        client.deleteStudent(3,token);//204
        System.out.println(client.getStudents()); // 200
        client.getAvatar(); // 200

        System.out.println(client.getProtoBuffStudent()); // 200



    }
}
