package haj.com.service;

import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.primefaces.model.SortOrder;

import haj.com.dao.UsersTrainingProviderDAO;
import haj.com.entity.Company;
import haj.com.entity.Users;
import haj.com.entity.UsersTrainingProvider;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTrainingProviderService.
 */
public class UsersTrainingProviderService extends AbstractService {
	/** The dao. */
	private UsersTrainingProviderDAO dao = new UsersTrainingProviderDAO();

	/**
	 * Get all UsersTrainingProvider.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception             the exception
	 * @see UsersTrainingProvider
	 */
	public List<UsersTrainingProvider> allUsersTrainingProvider() throws Exception {
		return dao.allUsersTrainingProvider();
	}

	/**
	 * Create or update UsersTrainingProvider.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UsersTrainingProvider
	 */
	public void create(UsersTrainingProvider entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Creates the users training provider.
	 *
	 * @param user the user
	 * @param trainingProvider the training provider
	 * @throws Exception the exception
	 * @throws ValidationException the validation exception
	 */
	public void createUsersTrainingProvider(Users user, Company trainingProvider) throws Exception, ValidationException {
		UsersTrainingProvider utp = null;
		utp = findByUserCompany(user.getId(), trainingProvider.getId());
		if (utp == null) {
			utp = new UsersTrainingProvider();
			utp.setCompany(trainingProvider);
			utp.setUser(user);
			this.dao.create(utp);
		} else 
			new ValidationException("Learner already registered with training provider");
	}

	/**
	 * Update UsersTrainingProvider.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UsersTrainingProvider
	 */
	public void update(UsersTrainingProvider entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete UsersTrainingProvider.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UsersTrainingProvider
	 */
	public void delete(UsersTrainingProvider entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception
	 *             the exception
	 * @see UsersTrainingProvider
	 */
	public UsersTrainingProvider findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find by user company.
	 *
	 * @param userId the user id
	 * @param companyId the company id
	 * @return the users training provider
	 * @throws Exception the exception
	 */
	public UsersTrainingProvider findByUserCompany(Long userId, Long companyId) throws Exception {
		UsersTrainingProvider temp = new UsersTrainingProvider();
		temp = dao.findByUserCompany(userId, companyId);
		if (temp == null)
			temp = new UsersTrainingProvider();
		return temp;
	}

	/**
	 * Find UsersTrainingProvider by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception
	 *             the exception
	 * @see UsersTrainingProvider
	 */
	public List<UsersTrainingProvider> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load UsersTrainingProvider.
	 *
	 * @param first            from key
	 * @param pageSize            no of rows to fetch
	 * @return a list of {@link haj.com.entity.UsersTrainingProvider}
	 * @throws Exception             the exception
	 */
	public List<UsersTrainingProvider> allUsersTrainingProvider(int first, int pageSize) throws Exception {
		return dao.allUsersTrainingProvider(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of UsersTrainingProvider for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UsersTrainingProvider
	 * @throws Exception             the exception
	 */
	public Long count() throws Exception {
		return dao.count(UsersTrainingProvider.class);
	}

	/**
	 * Lazy load UsersTrainingProvider with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1            UsersTrainingProvider class
	 * @param first            the first
	 * @param pageSize            the page size
	 * @param sortField            the sort field
	 * @param sortOrder            the sort order
	 * @param filters            the filters
	 * @return a list of {@link haj.com.entity.UsersTrainingProvider} containing
	 *         data
	 * @throws Exception             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersTrainingProvider> allUsersTrainingProvider(Class<UsersTrainingProvider> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<UsersTrainingProvider>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of UsersTrainingProvider for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity            UsersTrainingProvider class
	 * @param filters            the filters
	 * @return Number of rows in the UsersTrainingProvider entity
	 * @throws Exception             the exception
	 */
	public int count(Class<UsersTrainingProvider> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
}
