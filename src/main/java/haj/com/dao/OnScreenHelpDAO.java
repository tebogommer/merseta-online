package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.OnScreenHelp;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class OnScreenHelpDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all OnScreenHelp
 	 * @author TechFinium 
 	 * @see    OnScreenHelp
 	 * @return a list of {@link haj.com.entity.OnScreenHelp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OnScreenHelp> allOnScreenHelp() throws Exception {
		return (List<OnScreenHelp>)super.getList("select o from OnScreenHelp o");
	}

	@SuppressWarnings("unchecked")
	public List<OnScreenHelp> allActiveOnScreenHelp() throws Exception {
		return (List<OnScreenHelp>)super.getList("select o from OnScreenHelp o where o.active = true and ( o.screen  like 'pages/%' or  o.screen  like 'mis/%')  ");
	}
	
	/**
	 * Get all OnScreenHelp between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    OnScreenHelp
 	 * @return a list of {@link haj.com.entity.OnScreenHelp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OnScreenHelp> allOnScreenHelp(Long from, int noRows) throws Exception {
	 	String hql = "select o from OnScreenHelp o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<OnScreenHelp>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    OnScreenHelp
 	 * @return a {@link haj.com.entity.OnScreenHelp}
 	 * @throws Exception global exception
 	 */
	public OnScreenHelp findByKey(Long id) throws Exception {
	 	String hql = "select o from OnScreenHelp o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (OnScreenHelp)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find OnScreenHelp by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    OnScreenHelp
  	 * @return a list of {@link haj.com.entity.OnScreenHelp}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OnScreenHelp> findByName(String description) throws Exception {
	 	String hql = "select o from OnScreenHelp o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<OnScreenHelp>)super.getList(hql, parameters);
	}
	
	public OnScreenHelp findScreenName(String screen, boolean active) throws Exception {
	 	String hql = "select o from OnScreenHelp o where o.screen = :screen and o.active = :active " +
	 			"and o.screen not like 'crud/%' " + 
	 			"and o.screen not like 'admin/%' " + 
	 			"and o.screen not like 'errors/%' " + 
	 			"and o.screen not like 'WEB-INF/%' ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("screen", screen);
	    parameters.put("active", active);
		return (OnScreenHelp)super.getUniqueResult(hql, parameters);
	}
	
	
	public OnScreenHelp findScreenName(String screen) throws Exception {
	 	String hql = "select o from OnScreenHelp o where o.screen = :screen  " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("screen", screen);
		return (OnScreenHelp)super.getUniqueResult(hql, parameters);
	}
	
	
	public List<?> sortAndFilter(Class<?> entity, int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String hql = "select o from " + entity.getName() + " o ";
		 hql += 	"	where o.screen not like 'crud/%' " + 
		 			"and o.screen not like 'admin/%' " + 
		 			"and o.screen not like 'errors/%' " + 
		 			"and o.screen not like 'WEB-INF/%' ";
		if (filters != null) {
			boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (ft) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					ft = false;
				} else {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
			case ASCENDING:
				hql += " order by o." + sortField + " asc ";
				break;
			case DESCENDING:
				hql += " order by o." + sortField + " desc ";
				break;
			default:
				break;
			}
		}
		return getList(hql, filters, startingAt, pageSize);
	}	
	
	
	public Long count(Class<?> entity) throws Exception {
		return (Long) getUniqueResult("select count(o) from " + entity.getName() + " o where o.screen not like 'crud/%' " + 
				"	and o.screen not like 'admin/%' " + 
				"	and o.screen not like 'errors/%' " + 
				"	and o.screen not like 'WEB-INF/%'");
	}
}

