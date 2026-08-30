package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ActiveContractExtensionRequest;

public class ActiveContractExtensionRequestDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ActiveContractExtensionRequest
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
 	 * @return a list of {@link haj.com.entity.ActiveContractExtensionRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractExtensionRequest> allActiveContractExtensionRequest() throws Exception {
		return (List<ActiveContractExtensionRequest>)super.getList("select o from ActiveContractExtensionRequest o");
	}

	/**
	 * Get all ActiveContractExtensionRequest between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ActiveContractExtensionRequest
 	 * @return a list of {@link haj.com.entity.ActiveContractExtensionRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractExtensionRequest> allActiveContractExtensionRequest(Long from, int noRows) throws Exception {
	 	String hql = "select o from ActiveContractExtensionRequest o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ActiveContractExtensionRequest>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ActiveContractExtensionRequest
 	 * @return a {@link haj.com.entity.ActiveContractExtensionRequest}
 	 * @throws Exception global exception
 	 */
	public ActiveContractExtensionRequest findByKey(Long id) throws Exception {
	 	String hql = "select o from ActiveContractExtensionRequest o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ActiveContractExtensionRequest)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ActiveContractExtensionRequest by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ActiveContractExtensionRequest
  	 * @return a list of {@link haj.com.entity.ActiveContractExtensionRequest}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractExtensionRequest> findByName(String description) throws Exception {
	 	String hql = "select o from ActiveContractExtensionRequest o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ActiveContractExtensionRequest>)super.getList(hql, parameters);
	}
}

