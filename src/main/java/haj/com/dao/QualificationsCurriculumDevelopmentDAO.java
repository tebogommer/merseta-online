package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class QualificationsCurriculumDevelopmentDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationsCurriculumDevelopment
 	 * @author TechFinium 
 	 * @see    QualificationsCurriculumDevelopment
 	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationsCurriculumDevelopment> allQualificationsCurriculumDevelopment() throws Exception {
		return (List<QualificationsCurriculumDevelopment>)super.getList("select o from QualificationsCurriculumDevelopment o");
	}

	/**
	 * Get all QualificationsCurriculumDevelopment between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    QualificationsCurriculumDevelopment
 	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationsCurriculumDevelopment> allQualificationsCurriculumDevelopment(Long from, int noRows) throws Exception {
	 	String hql = "select o from QualificationsCurriculumDevelopment o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QualificationsCurriculumDevelopment>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    QualificationsCurriculumDevelopment
 	 * @return a {@link haj.com.entity.QualificationsCurriculumDevelopment}
 	 * @throws Exception global exception
 	 */
	public QualificationsCurriculumDevelopment findByKey(Long id) throws Exception {
	 	String hql = "select o from QualificationsCurriculumDevelopment o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QualificationsCurriculumDevelopment)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QualificationsCurriculumDevelopment by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    QualificationsCurriculumDevelopment
  	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationsCurriculumDevelopment> findByName(String description) throws Exception {
	 	String hql = "select o from QualificationsCurriculumDevelopment o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QualificationsCurriculumDevelopment>)super.getList(hql, parameters);
	}
	
	/**
	 * Find QualificationsCurriculumDevelopment by company ID
 	 * @author TechFinium 
 	 * @param companyId the company id 
 	 * @see    QualificationsCurriculumDevelopment
  	 * @return a list of {@link haj.com.entity.QualificationsCurriculumDevelopment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<QualificationsCurriculumDevelopment> findByCompany(Long companyId) throws Exception {
	 	String hql = "select o from QualificationsCurriculumDevelopment o where o.company.id = :companyId order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<QualificationsCurriculumDevelopment>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<QualificationsCurriculumDevelopment> findByUser(Long userID) throws Exception {
	 	String hql = "select o from QualificationsCurriculumDevelopment o where o.createUser.id = :userID order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", userID);
		return (List<QualificationsCurriculumDevelopment>)super.getList(hql, parameters);
	}
	

	public List<?> sortAndFilterWhereInfo(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {
		if (filters != null) {
			// boolean ft = true;
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
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().equals("companyName") || entry.getKey().equals("tradingName") || entry.getKey().equals("levyNumber")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
			}
			filters = parms;
		}

		if (sortField != null) {
			switch (sortOrder) {
				case ASCENDING:
					if (sortField.equals("companyName") || sortField.equals("tradingName") ||sortField.equals("levyNumber")) {
						hql += " order by o.company." + sortField + " asc ";
					}
					else{
						hql += " order by o." + sortField + " asc ";
					}
					
					break;
				case DESCENDING:
					if (sortField.equals("companyName") || sortField.equals("tradingName") ||sortField.equals("levyNumber")) {
						hql += " order by o.company." + sortField + " desc ";
					}
					
					else{
						hql += " order by o." + sortField + " desc ";
					}
					break;
				default:
					break;
			}
		}
		
		System.out.println(hql);
		return getList(hql, filters, startingAt, pageSize);
	}
	
	/**
	 * Count.
	 * @param filters
	 *            the filters
	 *
	 * @return the int
	 */
	public int countWhereInfo(Map<String, Object> filters, String hql) {
		if (filters != null) {
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
				
				if (!hql.contains(entry.getKey())) {
					if (entry.getKey().equals("companyName") || entry.getKey().equals("tradingName") || entry.getKey().equals("levyNumber")) {
						hql += " and o.company." + entry.getKey() + " like " + " :" + hvar;
					}
					else {
						hql += " and o." + entry.getKey() + " like " + " :" + hvar;
					}
					
				}
					
			
			}
			filters = parms;
		}
		return ((Long) getUniqueResult(hql, filters)).intValue();
	}
}

