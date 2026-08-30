package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.enums.ApprovalEnum;

public class SDPReAccreditationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SDPReAccreditation
 	 * @author TechFinium 
 	 * @see    SDPReAccreditation
 	 * @return a list of {@link haj.com.entity.SDPReAccreditation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPReAccreditation> allSDPReAccreditation() throws Exception {
		return (List<SDPReAccreditation>)super.getList("select o from SDPReAccreditation o");
	}

	/**
	 * Get all SDPReAccreditation between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SDPReAccreditation
 	 * @return a list of {@link haj.com.entity.SDPReAccreditation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPReAccreditation> allSDPReAccreditation(Long from, int noRows) throws Exception {
	 	String hql = "select o from SDPReAccreditation o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SDPReAccreditation>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SDPReAccreditation
 	 * @return a {@link haj.com.entity.SDPReAccreditation}
 	 * @throws Exception global exception
 	 */
	public SDPReAccreditation findByKey(Long id) throws Exception {
	 	String hql = "select o from SDPReAccreditation o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SDPReAccreditation)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SDPReAccreditation by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SDPReAccreditation
  	 * @return a list of {@link haj.com.entity.SDPReAccreditation}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPReAccreditation> findByName(String description) throws Exception {
	 	String hql = "select o from SDPReAccreditation o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SDPReAccreditation>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SDPReAccreditation> findByTrainingProviderApplication(Long id) throws Exception {
	 	String hql = "select o from SDPReAccreditation o where o.trainingProviderApplication.id = :id order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<SDPReAccreditation>)super.getList(hql, parameters);
	}
	
	public int countOpenSDPReAccreditationByProviderApplicationId(Long trainingProviderApplicationId, List<ApprovalEnum> openStatusList) throws Exception {
	 	String hql = "select count(o) from SDPReAccreditation o where o.trainingProviderApplication.id = :trainingProviderApplicationId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingProviderApplicationId", trainingProviderApplicationId);
	    if (!openStatusList.isEmpty()) {
	    	hql += " and o.status in ( ";
	    	Integer counter = 1;
	    	for (ApprovalEnum status : openStatusList) {
	    		hql += ":status" + counter.toString();
	    		parameters.put("status" + counter.toString(), status);
	    		if (counter != openStatusList.size()) {
	    			hql += " , ";
	    		}
				counter++;
			}
	    	hql += " ) ";
		}
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	
}