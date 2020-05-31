package pl.edu.agh.soa.rest;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FacultyEntity.class)
public abstract class FacultyEntity_ {

	public static volatile ListAttribute<FacultyEntity, StudentEntity> studentsFromSameFaculty;
	public static volatile SingularAttribute<FacultyEntity, String> faculty;

}

