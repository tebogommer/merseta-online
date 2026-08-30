package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.QualificationEntryRequirement;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationEntryRequirementDAO.
 */
public class QualificationEntryRequirementDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationEntryRequirement.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception global exception
	 * @see    QualificationEntryRequirement
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationEntryRequirement> allQualificationEntryRequirement() throws Exception {
		return (List<QualificationEntryRequirement>)super.getList("select o from QualificationEntryRequirement o");
	}

	/**
	 * Get all QualificationEntryRequirement between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception global exception
	 * @see    QualificationEntryRequirement
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationEntryRequirement> allQualificationEntryRequirement(Long from, int noRows) throws Exception {
	 	String hql = "select o from QualificationEntryRequirement o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<QualificationEntryRequirement>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception global exception
	 * @see    QualificationEntryRequirement
	 */
	public QualificationEntryRequirement findByKey(Long id) throws Exception {
	 	String hql = "select o from QualificationEntryRequirement o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (QualificationEntryRequirement)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find QualificationEntryRequirement by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception global exception
	 * @see    QualificationEntryRequirement
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationEntryRequirement> findByName(String description) throws Exception {
	 	String hql = "select o from QualificationEntryRequirement o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<QualificationEntryRequirement>)super.getList(hql, parameters);
	}
}

