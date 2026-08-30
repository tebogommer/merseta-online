package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.entity.lookup.Appraisals;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.ToolListTools;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class AppraisalsDAO extends AbstractDAO  {
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Appraisals
 	 * @author TechFinium 
 	 * @see    Appraisals
 	 * @return a list of {@link haj.com.entity.Appraisals}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Appraisals> allAppraisals() throws Exception {
		return (List<Appraisals>)super.getList("select o from Appraisals o");
	}

	/**
	 * Get all Appraisals between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Appraisals
 	 * @return a list of {@link haj.com.entity.Appraisals}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Appraisals> allAppraisals(Long from, int noRows) throws Exception {
	 	String hql = "select o from Appraisals o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Appraisals>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Appraisals
 	 * @return a {@link haj.com.entity.Appraisals}
 	 * @throws Exception global exception
 	 */
	public Appraisals findByKey(Long id) throws Exception {
	 	String hql = "select o from Appraisals o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Appraisals)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Appraisals by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Appraisals
  	 * @return a list of {@link haj.com.entity.Appraisals}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Appraisals> findByName(String description) throws Exception {
	 	String hql = "select o from Appraisals o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Appraisals>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Appraisals> allAppraisals(Appraisals appraisals) throws Exception{
		String hql = "select o from Appraisals o where o.appraisals.id = :appraisalsID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("appraisalsID", appraisals.getId());
		return (List<Appraisals>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<AppraisalChecklist> allAppraisalChecklist(Appraisals appraisals) throws Exception{
		String hql = "select o from AppraisalChecklist o where o.appraisals.id = :appraisalsID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("appraisalsID", appraisals.getId());
		return (List<AppraisalChecklist>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Appraisals> findByTrade(OfoCodes ofoCodes) throws Exception{
		String hql = "select o from Appraisals o where o.ofoCodes.id = :ofoCodesID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("ofoCodesID", ofoCodes.getId());
		return (List<Appraisals>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Appraisals> findByTrade(Qualification qualification) throws Exception{
		String hql = "select o from Appraisals o where o.qualification.id = :qualificationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationID", qualification.getId());
		return (List<Appraisals>) super.getList(hql, parameters);
	}
	

	public Appraisals findByOfocodes(OfoCodes ofoCodes) {
		String hql = "select o from Appraisals o where o.ofoCodes.id = :ofoCodesID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("ofoCodesID", ofoCodes.getId());
		return (Appraisals)super.getUniqueResult(hql, parameters);
	}
	
	public Appraisals findByQualification(Qualification qualification) {
		String hql = "select o from Appraisals o where o.qualification.id = :qualificationID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("qualificationID", qualification.getId());
		return (Appraisals)super.getUniqueResult(hql, parameters);
	}
}

