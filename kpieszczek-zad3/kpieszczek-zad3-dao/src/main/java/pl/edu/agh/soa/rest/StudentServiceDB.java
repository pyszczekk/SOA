package pl.edu.agh.soa.rest;

import pl.edu.agh.soa.models.Course;
import pl.edu.agh.soa.models.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class StudentServiceDB {
    @PersistenceContext(unitName = "Student")
    EntityManager entityManager;
    private static final Mapper mapper = new Mapper();
    public Student findStudent(int studentId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> criteriaQuery = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> fromStudents = criteriaQuery.from(StudentEntity.class);
        criteriaQuery.select(fromStudents).where(cb.equal(fromStudents.get("studentId"),studentId));

        StudentEntity studentEntity = entityManager.createQuery(criteriaQuery).getSingleResult();

        //StudentEntity studentEntity = entityManager.find(StudentEntity.class, studentId);

        return mapper.studentEntityToStudent(studentEntity);
    }
    public List<Student> getAllStudents(){
        List<StudentEntity> list;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> criteriaQuery = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> fromStudents = criteriaQuery.from(StudentEntity.class);
        criteriaQuery.select(fromStudents);
        list = entityManager.createQuery(criteriaQuery).getResultList();
        return mapper.studentEntityListToStudentList(list);
    }

    @Transactional
    public void addStudent(Student student) throws Exception{
        FieldOfStudyEntity f = null;
        FacultyEntity fa =null;
        StudentEntity studentEntity = mapper.studentToStudentEntity(student);

        if(entityManager.find(StudentEntity.class, student.getStudentId())==null){
            f = entityManager.find(FieldOfStudyEntity.class, student.getFieldOfStudy());
            fa = entityManager.find(FacultyEntity.class, student.getFaculty());

            if(f==null){
                entityManager.persist(new FieldOfStudyEntity(student.getFieldOfStudy()));
                entityManager.flush();
            }
            if(fa==null ){
                entityManager.persist(new FacultyEntity((student.getFaculty())));
                entityManager.flush();
            }
            entityManager.persist(studentEntity);

        }else{
            throw new Exception("Student Already exist");
        }

    }

    public List<Student> sameFacultyStudents(String faculty){
        List<Student> students = new ArrayList<>();
        try{
            return mapper.studentEntityListToStudentList(entityManager.find(FacultyEntity.class, faculty).getStudentsFromSameFaculty());

        }
        catch(Exception er){

            return students;
        }

    }
    public List<Student> sameFieldStudents(String field){
        List<Student> students = new ArrayList<>();
        try{
            return mapper.studentEntityListToStudentList(entityManager.find(FieldOfStudyEntity.class, field).getStudentsFromSameFieldOfStudy());
        }
        catch(Exception er){

            return students;
        }

    }
    public List<Student> sameFieldAndFacultyStudents(String field, String faculty){
        List<StudentEntity> list;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> criteriaQuery = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> fromStudents = criteriaQuery.from(StudentEntity.class);
        Join<StudentEntity, FieldOfStudyEntity> fieldOfStudy = fromStudents.join("fieldOfStudy");
        Predicate fieldPred = cb.equal(fieldOfStudy.get("fieldOfStudy"),field);
        Join<StudentEntity, FacultyEntity> facultyName = fromStudents.join("faculty");
        Predicate facultyPred = cb.equal(facultyName.get("faculty"),faculty);
        criteriaQuery.select(fromStudents).where(cb.and(fieldPred, facultyPred));

        list = entityManager.createQuery(criteriaQuery).getResultList();

        return mapper.studentEntityListToStudentList(list);

    }

    public List<String> getStudentCourses(int studentId){
        List<CourseEntity> list = new ArrayList<>();
        StudentEntity studentEntity = entityManager.find(StudentEntity.class, studentId);
        list = studentEntity.getCourses();
        return mapper.toCoursesList(list);
    }
    @Transactional
    public void addCourseToStudent(Course course, int studentId){

        StudentEntity studentEntity = entityManager.find(StudentEntity.class, studentId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = cb.createQuery(CourseEntity.class);
        Root<CourseEntity> fromCourses = criteriaQuery.from(CourseEntity.class);
        criteriaQuery.select(fromCourses).where(cb.equal(fromCourses.get("name"),course.getName()));
        CourseEntity courseEntity = null;
            try {
                courseEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
                if (studentEntity != null) {
                    if (courseEntity != null) {
                        StudentCoursesEntity studentCourse = new StudentCoursesEntity(studentId, courseEntity.getCourseId());
                        entityManager.persist(studentCourse);
                    }
                }
            }
            catch(Exception ex){
                    if (studentEntity != null) {
                        if (courseEntity == null) {
                            studentEntity.addCourse(mapper.toCourseEntity(course.getName()));
                            entityManager.merge(studentEntity);
                        }
                    }
                }

     }

    @Transactional
    public void deleteStudent(int id)throws Exception{

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaDelete criteriaQuery = cb.createCriteriaDelete(StudentEntity.class);
            Root<StudentEntity> fromStudent = criteriaQuery.from(StudentEntity.class);
            criteriaQuery.where(cb.equal(fromStudent.get("studentId"),id));
            entityManager.createQuery(criteriaQuery).executeUpdate();
    }
    @Transactional
    public void updateStudent(Student student, int studentId){
        StudentEntity studentEntity = mapper.studentToStudentEntity(student);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate update = cb.createCriteriaUpdate(StudentEntity.class);
        Root e = update.from(StudentEntity.class);
        update.set("faculty", new FacultyEntity(student.getFaculty()));
        update.set("fieldOfStudy", new FieldOfStudyEntity(student.getFieldOfStudy()));
        update.set("name", student.getName());
        update.set("lastName", student.getLastName());
        update.where(cb.equal(e.get("studentId"), studentId));
        entityManager.createQuery(update).executeUpdate();
    }
    @Transactional
    public void deleteCourseFromStudent(int studentId, Course course){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = cb.createQuery(CourseEntity.class);
        Root<CourseEntity> fromCourse = criteriaQuery.from(CourseEntity.class);
        criteriaQuery.select(fromCourse).where(cb.equal(fromCourse.get("name"),course.getName()));

        CourseEntity courseEntity = entityManager.createQuery(criteriaQuery).getSingleResult();


        CriteriaDelete criteriaQuery2 = cb.createCriteriaDelete(StudentCoursesEntity.class);
        Root<StudentCoursesEntity> fromStudent = criteriaQuery2.from(StudentCoursesEntity.class);
        criteriaQuery2.where(cb.and(cb.equal(fromStudent.get("studentId"),studentId), cb.equal(fromStudent.get("courseId"),courseEntity.getCourseId())));
        entityManager.createQuery(criteriaQuery2).executeUpdate();

    }

    public List<Student> sameCourseStudent(String name){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CourseEntity> criteriaQuery = cb.createQuery(CourseEntity.class);
        Root<CourseEntity> fromStudents = criteriaQuery.from(CourseEntity.class);
        criteriaQuery.select(fromStudents).where(cb.equal(fromStudents.get("name"),name));

        CourseEntity courseEntity = entityManager.createQuery(criteriaQuery).getSingleResult();
        return mapper.studentEntityListToStudentList(courseEntity.getStudents());


    }




}
