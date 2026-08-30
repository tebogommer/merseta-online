package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ActiveContractTerminationRequest;

public class ActiveContractTerminationRequestDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ActiveContractTerminationRequest
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
 	 * @return a list of {@link haj.com.entity.ActiveContractTerminationRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractTerminationRequest> allActiveContractTerminationRequest() throws Exception {
		return (List<ActiveContractTerminationRequest>)super.getList("select o from ActiveContractTerminationRequest o");
	}

	/**
	 * Get all ActiveContractTerminationRequest between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ActiveContractTerminationRequest
 	 * @return a list of {@link haj.com.entity.ActiveContractTerminationRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractTerminationRequest> allActiveContractTerminationRequest(Long from, int noRows) throws Exception {
	 	String hql = "select o from ActiveContractTerminationRequest o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ActiveContractTerminationRequest>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ActiveContractTerminationRequest
 	 * @return a {@link haj.com.entity.ActiveContractTerminationRequest}
 	 * @throws Exception global exception
 	 */
	public ActiveContractTerminationRequest findByKey(Long id) throws Exception {
	 	String hql = "select o from ActiveContractTerminationRequest o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ActiveContractTerminationRequest)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ActiveContractTerminationRequest by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ActiveContractTerminationRequest
  	 * @return a list of {@link haj.com.entity.ActiveContractTerminationRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractTerminationRequest> findByName(String description) throws Exception {
	 	String hql = "select o from ActiveContractTerminationRequest o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ActiveContractTerminationRequest>)super.getList(hql, parameters);
	}
}

