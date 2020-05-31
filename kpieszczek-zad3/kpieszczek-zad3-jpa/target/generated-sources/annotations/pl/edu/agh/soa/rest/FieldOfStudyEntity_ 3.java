package pl.edu.agh.soa.rest;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FieldOfStudyEntity.class)
public abstract class FieldOfStudyEntity_ {

	public static volatile ListAttribute<FieldOfStudyEntity, StudentEntity> studentsFromSameFieldOfStudy;
	public static volatile SingularAttribute<FieldOfStudyEntity, String> fieldOfStudy;

}

