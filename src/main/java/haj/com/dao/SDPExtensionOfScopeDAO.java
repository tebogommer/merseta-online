package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.SDPExtensionOfScope;
import haj.com.entity.enums.ApprovalEnum;

public class SDPExtensionOfScopeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SDPExtensionOfScope
 	 * @author TechFinium 
 	 * @see    SDPExtensionOfScope
 	 * @return a list of {@link haj.com.entity.SDPExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPExtensionOfScope> allSDPExtensionOfScope() throws Exception {
		return (List<SDPExtensionOfScope>)super.getList("select o from SDPExtensionOfScope o");
	}

	/**
	 * Get all SDPExtensionOfScope between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SDPExtensionOfScope
 	 * @return a list of {@link haj.com.entity.SDPExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPExtensionOfScope> allSDPExtensionOfScope(Long from, int noRows) throws Exception {
	 	String hql = "select o from SDPExtensionOfScope o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SDPExtensionOfScope>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SDPExtensionOfScope
 	 * @return a {@link haj.com.entity.SDPExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	public SDPExtensionOfScope findByKey(Long id) throws Exception {
	 	String hql = "select o from SDPExtensionOfScope o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SDPExtensionOfScope)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SDPExtensionOfScope by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SDPExtensionOfScope
  	 * @return a list of {@link haj.com.entity.SDPExtensionOfScope}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SDPExtensionOfScope> findByName(String description) throws Exception {
	 	String hql = "select o from SDPExtensionOfScope o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SDPExtensionOfScope>)super.getList(hql, parameters);
	}

	public int countByReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda rcms)throws Exception  {
		String hql = "select count(o) from SDPExtensionOfScope o where o.reviewCommitteeMeetingAgenda.id =:id";
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", rcms.getId());
		return countWhere(filters, hql);
	}
	
	public int countOpenSDPExtensionOfScopeByProviderApplicationId(Long trainingProviderApplicationId, List<ApprovalEnum> openStatusList) throws Exception {
	 	String hql = "select count(o) from SDPExtensionOfScope o where o.trainingProviderApplication.id = :trainingProviderApplicationId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("trainingProviderApplicationId", trainingProviderApplicationId);
	    if (!openStatusList.isEmpty()) {
	    	hql += " and o.approvalStatus in ( ";
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

