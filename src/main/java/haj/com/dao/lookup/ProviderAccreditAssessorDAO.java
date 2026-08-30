package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.ProviderAccreditAssessor;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccreditAssessorDAO.
 */
public class ProviderAccreditAssessorDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception global exception
	 * @see    ProviderAccreditAssessor
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderAccreditAssessor> allProviderAccreditAssessor() throws Exception {
		return (List<ProviderAccreditAssessor>)super.getList("select o from ProviderAccreditAssessor o");
	}

	/**
	 * Get all ProviderAccreditAssessor between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception global exception
	 * @see    ProviderAccreditAssessor
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderAccreditAssessor> allProviderAccreditAssessor(Long from, int noRows) throws Exception {
	 	String hql = "select o from ProviderAccreditAssessor o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ProviderAccreditAssessor>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception global exception
	 * @see    ProviderAccreditAssessor
	 */
	public ProviderAccreditAssessor findByKey(Long id) throws Exception {
	 	String hql = "select o from ProviderAccreditAssessor o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ProviderAccreditAssessor)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ProviderAccreditAssessor by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception global exception
	 * @see    ProviderAccreditAssessor
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderAccreditAssessor> findByName(String description) throws Exception {
	 	String hql = "select o from ProviderAccreditAssessor o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ProviderAccreditAssessor>)super.getList(hql, parameters);
	}
	
	/**
	 * Find ProviderAccreditAssesor by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param providerAccreditAssessor the provider accredit assessor
	 * @return the provider accredit assessor
	 * @throws Exception the exception
	 */
	public ProviderAccreditAssessor findUniqueCode(ProviderAccreditAssessor providerAccreditAssessor) throws Exception {
	 	String hql = "select o from ProviderAccreditAssessor o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (providerAccreditAssessor.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", providerAccreditAssessor.getId());
	 	}
	   
	    parameters.put("code", providerAccreditAssessor.getCode());
		return (ProviderAccreditAssessor)super.getUniqueResult(hql, parameters);
	}
}

