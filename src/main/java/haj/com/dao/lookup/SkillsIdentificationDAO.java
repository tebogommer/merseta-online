package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SkillsIdentification;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsIdentificationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsIdentification
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
 	 * @return a list of {@link haj.com.entity.SkillsIdentification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsIdentification> allSkillsIdentification() throws Exception {
		return (List<SkillsIdentification>)super.getList("select o from SkillsIdentification o");
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsIdentification> allSkillsIdentificationWithoutOther() throws Exception {
		return (List<SkillsIdentification>)super.getList("select o from SkillsIdentification o where o.description not like 'Other'");
	}
	

	/**
	 * Get all SkillsIdentification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SkillsIdentification
 	 * @return a list of {@link haj.com.entity.SkillsIdentification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsIdentification> allSkillsIdentification(Long from, int noRows) throws Exception {
	 	String hql = "select o from SkillsIdentification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SkillsIdentification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SkillsIdentification
 	 * @return a {@link haj.com.entity.SkillsIdentification}
 	 * @throws Exception global exception
 	 */
	public SkillsIdentification findByKey(Long id) throws Exception {
	 	String hql = "select o from SkillsIdentification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SkillsIdentification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SkillsIdentification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SkillsIdentification
  	 * @return a list of {@link haj.com.entity.SkillsIdentification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsIdentification> findByName(String description) throws Exception {
	 	String hql = "select o from SkillsIdentification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SkillsIdentification>)super.getList(hql, parameters);
	}
}

