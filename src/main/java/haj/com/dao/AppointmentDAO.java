package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.Appointment;

public class AppointmentDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Appointment
 	 * @author TechFinium 
 	 * @see    Appointment
 	 * @return a list of {@link haj.com.entity.Appointment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Appointment> allAppointment() throws Exception {
		return (List<Appointment>)super.getList("select o from Appointment o");
	}

	/**
	 * Get all Appointment between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Appointment
 	 * @return a list of {@link haj.com.entity.Appointment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Appointment> allAppointment(Long from, int noRows) throws Exception {
	 	String hql = "select o from Appointment o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Appointment>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Appointment
 	 * @return a {@link haj.com.entity.Appointment}
 	 * @throws Exception global exception
 	 */
	public Appointment findByKey(Long id) throws Exception {
	 	String hql = "select o from Appointment o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Appointment)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Appointment by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Appointment
  	 * @return a list of {@link haj.com.entity.Appointment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Appointment> findByName(String description) throws Exception {
	 	String hql = "select o from Appointment o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Appointment>)super.getList(hql, parameters);
	}
}


