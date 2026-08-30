package haj.com.service.lookup;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.MailTemplatesDAO;
import haj.com.entity.Company;
import haj.com.entity.Users;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.MailTemplates;
import haj.com.framework.AbstractService;
import haj.com.service.MailDef;
import haj.com.utils.GenericUtility;

public class MailTemplatesService extends AbstractService {
	/** The dao. */
	private MailTemplatesDAO dao = new MailTemplatesDAO();

	private static MailTemplatesService mailTemplatesService = null;

	public static synchronized MailTemplatesService instance() {
		if (mailTemplatesService == null) {
			mailTemplatesService = new MailTemplatesService();
		}
		return mailTemplatesService;
	}

	/**
	 * Get all MailTemplates
	 * 
	 * @author TechFinium
	 * @see MailTemplates
	 * @return a list of {@link haj.com.entity.MailTemplates}
	 * @throws Exception
	 *             the exception
	 */
	public List<MailTemplates> allMailTemplates() throws Exception {
		return dao.allMailTemplates();
	}

	/**
	 * Create or update MailTemplates.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MailTemplates
	 */
	public void create(MailTemplates entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update MailTemplates.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MailTemplates
	 */
	public void update(MailTemplates entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete MailTemplates.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see MailTemplates
	 */
	public void delete(MailTemplates entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.MailTemplates}
	 * @throws Exception
	 *             the exception
	 * @see MailTemplates
	 */
	public MailTemplates findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find MailTemplates by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.MailTemplates}
	 * @throws Exception
	 *             the exception
	 * @see MailTemplates
	 */
	public List<MailTemplates> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load MailTemplates
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.MailTemplates}
	 * @throws Exception
	 *             the exception
	 */
	public List<MailTemplates> allMailTemplates(int first, int pageSize) throws Exception {
		return dao.allMailTemplates(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of MailTemplates for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the MailTemplates
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(MailTemplates.class);
	}

	/**
	 * Lazy load MailTemplates with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            MailTemplates class
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
	 * @return a list of {@link haj.com.entity.MailTemplates} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MailTemplates> allMailTemplates(Class<MailTemplates> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<MailTemplates>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of MailTemplates for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            MailTemplates class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the MailTemplates entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<MailTemplates> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void sendMail(Users user, String subject, MailDef mailDef, String description) {
		try {
			MailTemplates m = dao.findCode(mailDef.getMailEnum());
			if (m == null) {
				GenericUtility.sendMail(user, subject, description, null);
			} else {
				String body = m.getTemplateText().trim();
				for (Entry<MailTagsEnum, Object> e : mailDef.getTagMap().entrySet()) {
					logger.info(e.getKey().getTagName() + "\t" + e.getValue().getClass().getName());
					if (e.getValue() instanceof String) {
						body = body.replaceAll(e.getKey().getTagName(), e.getValue().toString());
						subject = subject.replaceAll(e.getKey().getTagName(), e.getValue().toString());
					} else if (e.getValue() instanceof Users) {
						body = body.replaceAll(e.getKey().getTagName(), ((Users) e.getValue()).getFirstName());
						subject = subject.replaceAll(e.getKey().getTagName(), ((Users) e.getValue()).getFirstName());
					} else if (e.getValue() instanceof Company) {
						body = body.replaceAll(e.getKey().getTagName(), ((Company) e.getValue()).getCompanyName());
						subject = subject.replaceAll(e.getKey().getTagName(), ((Company) e.getValue()).getCompanyName());
					}
				}
				GenericUtility.sendMail(user, m.getSubjectText().trim(), body, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}
}
