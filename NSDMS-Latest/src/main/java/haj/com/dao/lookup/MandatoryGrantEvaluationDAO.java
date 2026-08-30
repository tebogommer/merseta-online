package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Wsp;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.MandatoryGrantEvaluation;
import haj.com.entity.lookup.SizeOfCompany;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MandatoryGrantEvaluationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MandatoryGrantEvaluation
	 * 
	 * @author TechFinium
	 * @see MandatoryGrantEvaluation
	 * @return a list of {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantEvaluation> allMandatoryGrantEvaluation() throws Exception {
		return (List<MandatoryGrantEvaluation>) super.getList("select o from MandatoryGrantEvaluation o");
	}

	/**
	 * Get all MandatoryGrantEvaluation between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see MandatoryGrantEvaluation
	 * @return a list of {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantEvaluation> allMandatoryGrantEvaluation(Long from, int noRows) throws Exception {
		String hql = "select o from MandatoryGrantEvaluation o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<MandatoryGrantEvaluation>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see MandatoryGrantEvaluation
	 * @return a {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             global exception
	 */
	public MandatoryGrantEvaluation findByKey(Long id) throws Exception {
		String hql = "select o from MandatoryGrantEvaluation o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (MandatoryGrantEvaluation) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MandatoryGrantEvaluation by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see MandatoryGrantEvaluation
	 * @return a list of {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantEvaluation> findByName(String description) throws Exception {
		String hql = "select o from MandatoryGrantEvaluation o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<MandatoryGrantEvaluation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantEvaluation> findMandatoryGrantEvaluationLookups(SizeOfCompany sizeOfCompany, WspTypeEnum wspTypeEnum) throws Exception {
		String hql = "select o from MandatoryGrantEvaluation o where o.wsp is null and o.sizeOfCompany.id = :sizeOfCompanyID and o.wspTypeEnum = :wspTypeEnum";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("sizeOfCompanyID", sizeOfCompany.getId());
		parameters.put("wspTypeEnum", wspTypeEnum);
		return (List<MandatoryGrantEvaluation>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<MandatoryGrantEvaluation> findMandatoryGrantEvaluationByWSPValuation(Wsp wsp) throws Exception {
		String hql = "select o from MandatoryGrantEvaluation o where o.wsp.id = :wspID and o.validationForm = true";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<MandatoryGrantEvaluation>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantEvaluation> findMandatoryGrantEvaluationByWSPNotValuation(Wsp wsp) throws Exception {
		String hql = "select o from MandatoryGrantEvaluation o where o.wsp.id = :wspID and (o.validationForm is null or o.validationForm = false)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (List<MandatoryGrantEvaluation>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public Long findMandatoryGrantEvaluationByWSPNotValuationTotal(Wsp wsp) throws Exception {
//		if (yesNoEnum == null) return 0.0;
//		double score = getNoScore();
//		if (yesNoEnum == YesNoEnum.Yes) score = getYesScore();
//		return score;
		String hql = "select sum(case when o.yesNoEnum = :yesNoEnum then o.yesScore else o.noScore end) from MandatoryGrantEvaluation o where o.wsp.id = :wspID and (o.validationForm is null or o.validationForm = false)";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		parameters.put("yesNoEnum", YesNoEnum.Yes);
		return (Long) super.getUniqueResult(hql, parameters);
	}

	public Long findMandatoryGrantEvaluationByWSPCount(Wsp wsp) throws Exception {
		String hql = "select count(o) from MandatoryGrantEvaluation o where o.wsp.id = :wspID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspID", wsp.getId());
		return (Long) super.getUniqueResult(hql, parameters);
	}
}
