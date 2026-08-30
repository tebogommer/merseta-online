package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AppraisalChecklistDAO extends AbstractDAO  {
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AppraisalChecklist
 	 * @author TechFinium 
 	 * @see    AppraisalChecklist
 	 * @return a list of {@link haj.com.entity.AppraisalChecklist}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalChecklist> allAppraisalChecklist() throws Exception {
		return (List<AppraisalChecklist>)super.getList("select o from AppraisalChecklist o");
	}

	/**
	 * Get all AppraisalChecklist between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AppraisalChecklist
 	 * @return a list of {@link haj.com.entity.AppraisalChecklist}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalChecklist> allAppraisalChecklist(Long from, int noRows) throws Exception {
	 	String hql = "select o from AppraisalChecklist o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AppraisalChecklist>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AppraisalChecklist
 	 * @return a {@link haj.com.entity.AppraisalChecklist}
 	 * @throws Exception global exception
 	 */
	public AppraisalChecklist findByKey(Long id) throws Exception {
	 	String hql = "select o from AppraisalChecklist o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AppraisalChecklist)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AppraisalChecklist by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AppraisalChecklist
  	 * @return a list of {@link haj.com.entity.AppraisalChecklist}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AppraisalChecklist> findByName(String description) throws Exception {
	 	String hql = "select o from AppraisalChecklist o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AppraisalChecklist>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<AppraisalChecklist> findAppraisalCategoryCodeByAppraisal(Long id) {
		String hql = "select o from AppraisalChecklist o where o.appraisals.id = :AppraisalID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("AppraisalID", id);
		return (List<AppraisalChecklist>)super.getList(hql, parameters);
	}
}

