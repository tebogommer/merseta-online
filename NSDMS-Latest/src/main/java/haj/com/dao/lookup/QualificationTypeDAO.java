package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.QualificationType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationTypeDAO.
 */
public class QualificationTypeDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all QualificationType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.QualificationType}
	 * @throws Exception             global exception
	 * @see QualificationType
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationType> allQualificationType() throws Exception {
		return (List<QualificationType>) super.getList("select o from QualificationType o");
	}

	/**
	 * Find qualification type autocomplete.
	 *
	 * @param description the description
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationType> findQualificationTypeAutocomplete(String description) throws Exception {
		String hql = "select o from QualificationType o where o.description LIKE :description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		return (List<QualificationType>) super.getList(hql, parameters);
	}

	/**
	 * Get all QualificationType between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.QualificationType}
	 * @throws Exception             global exception
	 * @see QualificationType
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationType> allQualificationType(Long from, int noRows) throws Exception {
		String hql = "select o from QualificationType o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<QualificationType>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.QualificationType}
	 * @throws Exception             global exception
	 * @see QualificationType
	 */
	public QualificationType findByKey(Long id) throws Exception {
		String hql = "select o from QualificationType o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (QualificationType) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the qualification type
	 * @throws Exception the exception
	 */
	public QualificationType findByCode(String code) throws Exception {
		String hql = "select o from QualificationType o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (QualificationType) super.getUniqueResult(hql, parameters);
	}	
	
	/**
	 * Find QualificationType by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.QualificationType}
	 * @throws Exception             global exception
	 * @see QualificationType
	 */
	@SuppressWarnings("unchecked")
	public List<QualificationType> findByName(String description) throws Exception {
		String hql = "select o from QualificationType o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<QualificationType>) super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param qualificationType the qualification type
	 * @return the qualification type
	 * @throws Exception the exception
	 */
	public QualificationType findUniqueCode(QualificationType qualificationType) throws Exception {
	 	String hql = "select o from QualificationType o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (qualificationType.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", qualificationType.getId());
	 	}
	   
	    parameters.put("code", qualificationType.getCode());
		return (QualificationType)super.getUniqueResult(hql, parameters);
	}
}
