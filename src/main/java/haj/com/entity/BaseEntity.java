package haj.com.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import haj.com.framework.IDataEntity;

@MappedSuperclass
public abstract class BaseEntity implements IDataEntity
{
	@Column(name = "deleted",  columnDefinition="BIT default false")
	 private boolean deleted;


}
