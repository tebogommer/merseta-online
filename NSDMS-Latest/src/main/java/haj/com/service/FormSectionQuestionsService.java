package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.FormSectionQuestionsDAO;
import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.framework.AbstractService;

public class FormSectionQuestionsService extends AbstractService {
	/** The dao. */
	private FormSectionQuestionsDAO dao = new FormSectionQuestionsDAO();

	/**
	 * Get all FormSectionQuestions
	 * 
	 * @author TechFinium
	 * @see FormSectionQuestions
	 * @return a list of {@link haj.com.entity.FormSectionQuestions}
	 * @throws Exception
	 *             the exception
	 */
	public List<FormSectionQuestions> allFormSectionQuestions() throws Exception {
		return dao.allFormSectionQuestions();
	}

	/**
	 * Create or update FormSectionQuestions.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see FormSectionQuestions
	 */
	public void create(FormSectionQuestions entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update FormSectionQuestions.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see FormSectionQuestions
	 */
	public void update(FormSectionQuestions entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete FormSectionQuestions.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see FormSectionQuestions
	 */
	public void delete(FormSectionQuestions entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.FormSectionQuestions}
	 * @throws Exception
	 *             the exception
	 * @see FormSectionQuestions
	 */
	public FormSectionQuestions findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find FormSectionQuestions by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.FormSectionQuestions}
	 * @throws Exception
	 *             the exception
	 * @see FormSectionQuestions
	 */
	public List<FormSectionQuestions> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load FormSectionQuestions
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.FormSectionQuestions}
	 * @throws Exception
	 *             the exception
	 */
	public List<FormSectionQuestions> allFormSectionQuestions(int first, int pageSize) throws Exception {
		return dao.allFormSectionQuestions(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of FormSectionQuestions for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the FormSectionQuestions
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(FormSectionQuestions.class);
	}

	/**
	 * Lazy load FormSectionQuestions with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            FormSectionQuestions class
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
	 * @return a list of {@link haj.com.entity.FormSectionQuestions} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormSectionQuestions> allFormSectionQuestions(Class<FormSectionQuestions> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<FormSectionQuestions>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of FormSectionQuestions for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            FormSectionQuestions class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the FormSectionQuestions entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<FormSectionQuestions> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
}
