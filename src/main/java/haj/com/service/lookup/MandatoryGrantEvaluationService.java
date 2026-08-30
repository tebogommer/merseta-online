package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.lookup.MandatoryGrantEvaluationDAO;
import haj.com.entity.Wsp;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.MandatoryGrantEvaluation;
import haj.com.entity.lookup.SizeOfCompany;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class MandatoryGrantEvaluationService extends AbstractService {
	/** The dao. */
	private MandatoryGrantEvaluationDAO dao = new MandatoryGrantEvaluationDAO();

	/**
	 * Get all MandatoryGrantEvaluation
	 * 
	 * @author TechFinium
	 * @see MandatoryGrantEvaluation
	 * @return a list of {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             the exception
	 */
	public List<MandatoryGrantEvaluation> allMandatoryGrantEvaluation() throws Exception {
		return dao.allMandatoryGrantEvaluation();
	}

	/**
	 * Create or update MandatoryGrantEvaluation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantEvaluation
	 */
	public void create(MandatoryGrantEvaluation entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update MandatoryGrantEvaluation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantEvaluation
	 */
	public void update(MandatoryGrantEvaluation entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MandatoryGrantEvaluation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantEvaluation
	 */
	public void delete(MandatoryGrantEvaluation entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantEvaluation
	 */
	public MandatoryGrantEvaluation findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find MandatoryGrantEvaluation by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             the exception
	 * @see MandatoryGrantEvaluation
	 */
	public List<MandatoryGrantEvaluation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MandatoryGrantEvaluation
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.MandatoryGrantEvaluation}
	 * @throws Exception
	 *             the exception
	 */
	public List<MandatoryGrantEvaluation> allMandatoryGrantEvaluation(int first, int pageSize) throws Exception {
		return dao.allMandatoryGrantEvaluation(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MandatoryGrantEvaluation for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the MandatoryGrantEvaluation
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(MandatoryGrantEvaluation.class);
	}

	/**
	 * Lazy load MandatoryGrantEvaluation with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            MandatoryGrantEvaluation class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.MandatoryGrantEvaluation} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantEvaluation> allMandatoryGrantEvaluation(Class<MandatoryGrantEvaluation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<MandatoryGrantEvaluation>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of MandatoryGrantEvaluation for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            MandatoryGrantEvaluation class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the MandatoryGrantEvaluation entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<MandatoryGrantEvaluation> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<MandatoryGrantEvaluation> findMandatoryGrantEvaluationLookups(SizeOfCompany sizeOfCompany, WspTypeEnum wspTypeEnum) throws Exception {
		return dao.findMandatoryGrantEvaluationLookups(sizeOfCompany, wspTypeEnum);
	}

	public List<MandatoryGrantEvaluation> findMandatoryGrantEvaluationByWSPValuation(Wsp wsp) throws Exception {
		return dao.findMandatoryGrantEvaluationByWSPValuation(wsp);
	}
	
	public List<MandatoryGrantEvaluation> findMandatoryGrantEvaluationByWSPNotValuation(Wsp wsp) throws Exception {
		return dao.findMandatoryGrantEvaluationByWSPNotValuation(wsp);
	}
	
	public Long findMandatoryGrantEvaluationByWSPNotValuationTotal(Wsp wsp) throws Exception {
		return dao.findMandatoryGrantEvaluationByWSPNotValuationTotal(wsp);
	}

	public Long findMandatoryGrantEvaluationByWSPCount(Wsp wsp) throws Exception {
		return dao.findMandatoryGrantEvaluationByWSPCount(wsp);
	}

	public void generateEvaluationForm(Wsp wsp) throws Exception {
		long count = findMandatoryGrantEvaluationByWSPCount(wsp);
		if (count == 0) {
			SizeOfCompany sizeOfCompany =  SizeOfCompanyService.instance().findCompanySize(wsp.getCompany());
			List<IDataEntity> dataEntities = new ArrayList<>();
			List<MandatoryGrantEvaluation> mandatoryGrantEvaluations = findMandatoryGrantEvaluationLookups(sizeOfCompany, wsp.getWspType());
			for (MandatoryGrantEvaluation mandatoryGrantEvaluation : mandatoryGrantEvaluations) {
				MandatoryGrantEvaluation clone = mandatoryGrantEvaluation.clone();
				clone.setWsp(wsp);
				clone.setYesNoEnum(YesNoEnum.No);
				dataEntities.add(clone);
			}
			dao.createBatch(dataEntities);
		}
	}
}
