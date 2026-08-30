package haj.com.service;

import java.util.List;

import haj.com.dao.AppointmentDAO;
import haj.com.entity.Appointment;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class AppointmentService extends AbstractService {
	/** The dao. */
	private AppointmentDAO dao = new AppointmentDAO();

	/**
	 * Get all Appointment
 	 * @author TechFinium 
 	 * @see   Appointment
 	 * @return a list of {@link haj.com.entity.Appointment}
	 * @throws Exception the exception
 	 */
	public List<Appointment> allAppointment() throws Exception {
	  	return dao.allAppointment();
	}


	/**
	 * Create or update Appointment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Appointment
	 */
	public void create(Appointment entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Appointment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Appointment
	 */
	public void update(Appointment entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Appointment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Appointment
	 */
	public void delete(Appointment entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Appointment}
	 * @throws Exception the exception
	 * @see    Appointment
	 */
	public Appointment findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Appointment by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Appointment}
	 * @throws Exception the exception
	 * @see    Appointment
	 */
	public List<Appointment> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Appointment
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Appointment}
	 * @throws Exception the exception
	 */
	public List<Appointment> allAppointment(int first, int pageSize) throws Exception {
		return dao.allAppointment(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Appointment for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the Appointment
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Appointment.class);
	}
	
    /**
     * Lazy load Appointment with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 Appointment class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Appointment} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Appointment> allAppointment(Class<Appointment> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Appointment>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Appointment for lazy load with filters
     * @author TechFinium 
     * @param entity Appointment class
     * @param filters the filters
     * @return Number of rows in the Appointment entity
     * @throws Exception the exception     
     */	
	public int count(Class<Appointment> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
