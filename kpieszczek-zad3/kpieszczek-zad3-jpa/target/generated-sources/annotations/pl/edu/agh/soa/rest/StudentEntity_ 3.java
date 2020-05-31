package pl.edu.agh.soa.rest;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StudentEntity.class)
public abstract class StudentEntity_ {

	public static volatile SingularAttribute<StudentEntity, Integer> studentId;
	public static volatile SingularAttribute<StudentEntity, String> lastName;
	public static volatile ListAttribute<StudentEntity, CourseEntity> courses;
	public static volatile SingularAttribute<StudentEntity, String> name;
	public static volatile SingularAttribute<StudentEntity, FieldOfStudyEntity> fieldOfStudy;
	public static volatile SingularAttribute<StudentEntity, FacultyEntity> faculty;

}

