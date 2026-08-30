package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.TrainingComittee;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingComitteeDAO.
 */
public class TrainingComitteeDAO extends AbstractDAO {

	/** The Constant leftJoins. */
	private static final String leftJoins = " " 
			+ "left join fetch o.union u " 
			+ "left join fetch o.title t " 
			+ "left join fetch o.equity e " 
			+ "left join fetch o.gender g " 
			+ "left join fetch o.company comp "
			+ " ";
	
	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all TrainingComittee.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TrainingComittee}
	 * @throws Exception             global exception
	 * @see TrainingComittee
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComittee> allTrainingComittee() throws Exception {
		return (List<TrainingComittee>) super.getList("select o from TrainingComittee o");
	}

	/**
	 * Get all TrainingComittee between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.TrainingComittee}
	 * @throws Exception             global exception
	 * @see TrainingComittee
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComittee> allTrainingComittee(Long from, int noRows) throws Exception {
		String hql = "select o from TrainingComittee o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<TrainingComittee>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.TrainingComittee}
	 * @throws Exception             global exception
	 * @see TrainingComittee
	 */
	public TrainingComittee findByKey(Long id) throws Exception {
		String hql = "select o from TrainingComittee o" +leftJoins+" where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (TrainingComittee) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find TrainingComittee by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.TrainingComittee}
	 * @throws Exception             global exception
	 * @see TrainingComittee
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComittee> findByName(String description) throws Exception {
		String hql = "select o from TrainingComittee o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<TrainingComittee>) super.getList(hql, parameters);
	}

	
	/**
	 * Find by company.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComittee> findByCompany(Company company) throws Exception {
		String hql = "select o from TrainingComittee o "+leftJoins+" where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (List<TrainingComittee>) super.getList(hql, parameters);
	}
	
	/**
	 * Find by company count.
	 *
	 * @param company the company
	 * @return the long
	 * @throws Exception the exception
	 */
	public long findByCompanyCount(Company company) throws Exception {
		String hql = "select count(o) from TrainingComittee o where o.company.id = :companyID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyID", company.getId());
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TrainingComittee> findByComitteeAttributes(TrainingComittee trainingComittee,Company company) throws Exception {
		String hql = "select o from TrainingComittee o where (o.email = :email or o.rsaIDNumber = :rsaIDNumber or o.passportNumber = :passportNumber) and o.company.id = :companyId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", company.getId());
		parameters.put("email", trainingComittee.getEmail());
		parameters.put("rsaIDNumber", trainingComittee.getRsaIDNumber());
		parameters.put("passportNumber", trainingComittee.getPassportNumber());
		if (trainingComittee.getId() != null) {
			hql += " and o.id <> :userId";
			parameters.put("userId", trainingComittee.getId());
		}
		return (List<TrainingComittee>) super.getList(hql, parameters);
	}

}
