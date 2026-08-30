package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Office;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class OfficeDAO extends AbstractDAO  {
	
	private static final String leftJoins = " " 
			+ "left join fetch o.physicalAddress pa left join fetch pa.municipality paM "
			+ " ";

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Office
 	 * @author TechFinium 
 	 * @see    Office
 	 * @return a list of {@link haj.com.entity.Office}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Office> allOffice() throws Exception {
		return (List<Office>)super.getList("select o from Office o");
	}

	/**
	 * Get all Office between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Office
 	 * @return a list of {@link haj.com.entity.Office}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Office> allOffice(Long from, int noRows) throws Exception {
	 	String hql = "select o from Office o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Office>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Office
 	 * @return a {@link haj.com.entity.Office}
 	 * @throws Exception global exception
 	 */
	public Office findByKey(Long id) throws Exception {
	 	String hql = "select o from Office o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Office)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Office by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Office
  	 * @return a list of {@link haj.com.entity.Office}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Office> findByName(String description) throws Exception {
	 	String hql = "select o from Office o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Office>)super.getList(hql, parameters);
	}
	
	/**
     * Lazy load Office with pagination, filter, hql, sorting
     * @author TechFinium 
     * @param class1 Office class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Office} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Office> allOfficeHql(Class<Office> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Office o " + leftJoins ;
//		filters.put("description", ""+description.trim()+"%");
		return ( List<Office>)sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql);
	}
	
	/**
     * Get count of Office for lazy load with filters
     * @author TechFinium 
     * @param entity Office class
     * @param filters the filters
     * @return Number of rows in the Office entity
     * @throws Exception the exception     
     */	
	public int countHql(Class<Office> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Office o ";
		return countWhere(filters, hql);
	}
	
	/**
	 * Find Office by region id linked to address
	 * @param regionId the region Id
	 * @return  a list of {@link haj.com.entity.Office} containing data
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Office> allOfficesByRegionId(Long regionId) throws Exception {
	 	String hql = "select o from Office o where o.physicalAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId )" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("regionId", regionId);
		return (List<Office>)super.getList(hql, parameters);
	}
}

