package haj.com.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.WspDisputeDAO;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspDispute;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspDisputeService.
 */
public class WspDisputeService extends AbstractService {
	/** The dao. */
	private WspDisputeDAO dao = new WspDisputeDAO();
	private SignoffService signoffService = new SignoffService();

	/**
	 * Get all WspDispute.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WspDispute}
	 * @throws Exception
	 *             the exception
	 * @see WspDispute
	 */
	public List<WspDispute> allWspDispute() throws Exception {
		return dao.allWspDispute();
	}

	/**
	 * Create or update WspDispute.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspDispute
	 */
	public void create(WspDispute entity) throws Exception {
		if (entity.getId() == null) {
			dao.create(entity);
		} else this.dao.update(entity);
	}

	public void submitSignOff(WspDispute entity, Users user, Tasks task) throws Exception {
//		resolveSignOff(entity);
		List<Signoff> signoffsExternal = entity.getSignOffs().stream().filter(sign -> sign.getUser() == null).collect(Collectors.toList());
		List<Signoff> signoffs = entity.getSignOffs().stream().filter(sign -> sign.getUser() != null && !sign.getUser().equals(user)).collect(Collectors.toList());
		entity.getSignOffs().stream().filter(sign -> sign.getUser() != null && sign.getUser().equals(user)).forEach(sign -> {
			try {
				sign.setAccept(true);
				sign.setCompleted(true);
				dao.update(sign);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		String mailDesc = "<p>Dispute for " + entity.getWsp().getCompany().getCompanyName() + "'s Grant Application has been resolved.<p>Please <a href='"+HAJConstants.PL_LINK+"externalSignOff.jsf?id=#SIGN_OFF_GUID#'>Sign Off<a/> The Dispute Form<p/>";
		String desc = "Dispute for " + entity.getWsp().getCompany().getCompanyName() + "'s Grant Application has been resolved. Please Sign Off.";
		
		TasksService.instance().createTask(signoffsExternal, WspDispute.class.getName(), entity.getId(), desc, mailDesc, user, true, true, task, null, MailDef.instance(MailEnum.Task, MailTagsEnum.CompanyName, entity.getWsp().getCompany().getCompanyName()));
		TasksService.instance().createTask(signoffs, Wsp.class.getName(), entity.getWsp().getId(), desc, mailDesc, user, true, true, task, null, MailDef.instance(MailEnum.Task, MailTagsEnum.CompanyName, entity.getWsp().getCompany().getCompanyName()));

		this.dao.update(entity);
	}

	/**
	 * Update WspDispute.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspDispute
	 */
	public void update(WspDispute entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WspDispute.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspDispute
	 */
	public void delete(WspDispute entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WspDispute}
	 * @throws Exception
	 *             the exception
	 * @see WspDispute
	 */
	public WspDispute findByKey(long id) throws Exception {
		return resolveSignOff(dao.findByKey(id));
	}

	/**
	 * Find WspDispute by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WspDispute}
	 * @throws Exception
	 *             the exception
	 * @see WspDispute
	 */
	public List<WspDispute> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WspDispute.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspDispute}
	 * @throws Exception
	 *             the exception
	 */
	public List<WspDispute> allWspDispute(int first, int pageSize) throws Exception {
		return dao.allWspDispute(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WspDispute for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the WspDispute
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WspDispute.class);
	}

	/**
	 * Lazy load WspDispute with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            WspDispute class
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
	 * @return a list of {@link haj.com.entity.WspDispute} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspDispute> allWspDispute(Class<WspDispute> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WspDispute>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WspDispute for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            WspDispute class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WspDispute entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WspDispute> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by wsp.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<WspDispute> findByWsp(Wsp wsp) throws Exception {
		List<WspDispute> disputes = dao.findByWsp(wsp);
		for (WspDispute wspDispute : disputes) {
			resolveSignOff(wspDispute);
		}
		return disputes;
	}

	public WspDispute resolveSignOff(WspDispute wspDispute) throws Exception {
		wspDispute.setSignOffs(signoffService.findByWspDispute(wspDispute));
		return wspDispute;
	}
}
