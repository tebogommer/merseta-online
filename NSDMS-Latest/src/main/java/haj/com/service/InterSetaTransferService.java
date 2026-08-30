package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.InterSetaTransferDAO;
import haj.com.entity.InterSetaTransfer;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class InterSetaTransferService.
 */
public class InterSetaTransferService extends AbstractService {
	/** The dao. */
	private InterSetaTransferDAO dao = new InterSetaTransferDAO();
	private DocService docService = new DocService();
	private UsersService usersService = new UsersService();

	/**
	 * Get all InterSetaTransfer.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             the exception
	 * @see InterSetaTransfer
	 */
	public List<InterSetaTransfer> allInterSetaTransfer() throws Exception {
		return dao.allInterSetaTransfer();
	}

	/**
	 * Create or update InterSetaTransfer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see InterSetaTransfer
	 */
	public void create(InterSetaTransfer entity) throws Exception {
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update InterSetaTransfer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see InterSetaTransfer
	 */
	public void update(InterSetaTransfer entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete InterSetaTransfer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see InterSetaTransfer
	 */
	public void delete(InterSetaTransfer entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             the exception
	 * @see InterSetaTransfer
	 */
	public InterSetaTransfer findByKey(long id) throws Exception {
		return resolveDocs(dao.findByKey(id));
	}

	public InterSetaTransfer findByGuid(String id) throws Exception {
		return resolveDocs(dao.findByGuid(id));
	}

	private InterSetaTransfer resolveDocs(InterSetaTransfer interSetaTransfer) throws Exception {
		if (interSetaTransfer.getUsers() != null) {
			interSetaTransfer.getUsers().setDocs(docService.searchByUser(interSetaTransfer.getUsers()));
		}
		if (interSetaTransfer.getCompany() != null) {
			interSetaTransfer.getCompany().setDocs(docService.searchByCompany(interSetaTransfer.getCompany()));
		}
		return interSetaTransfer;
	}

	/**
	 * Find InterSetaTransfer by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             the exception
	 * @see InterSetaTransfer
	 */
	public List<InterSetaTransfer> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load InterSetaTransfer.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.InterSetaTransfer}
	 * @throws Exception
	 *             the exception
	 */
	public List<InterSetaTransfer> allInterSetaTransfer(int first, int pageSize) throws Exception {
		return dao.allInterSetaTransfer(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of InterSetaTransfer for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the InterSetaTransfer
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(InterSetaTransfer.class);
	}

	/**
	 * Lazy load InterSetaTransfer with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            InterSetaTransfer class
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
	 * @return a list of {@link haj.com.entity.InterSetaTransfer} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<InterSetaTransfer> allInterSetaTransfer(Class<InterSetaTransfer> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {

		String hql = "select o from InterSetaTransfer o left join fetch o.company c left join fetch o.users u";
		return (List<InterSetaTransfer>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		// return (
		// List<InterSetaTransfer>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);

	}

	public void approveTransfer(InterSetaTransfer entity, Users user, Tasks task) throws Exception {
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.INTER_SETA_TRANSFER, entity.getId());
		if (task != null) {
			if (task.getProcessRole().getRolePermission() == UserPermissionEnum.Approve) {
				entity.setApproval(ApprovalEnum.WaitingForManager);
				task = TasksService.instance().findByKey(task.getId());
				TasksService.instance().findNextInProcessAndCreateTask("New inter-SETA Transfer for approval", user,
						entity.getId(), entity.getClass().getName(), true, task,
						MailDef.instance(MailEnum.InterSetaTransfer, MailTagsEnum.FirstName, user.getFirstName(),
								MailTagsEnum.LastName, user.getLastName()),
						null);
			} else {
				entity.setApproval(ApprovalEnum.Approved);
				task = TasksService.instance().findByKey(task.getId());

			}
			dao.update(entity);
		} else {
			List<Tasks> tasks = TasksService.instance()
					.findTasksByTypeAndKeyLast(ConfigDocProcessEnum.INTER_SETA_TRANSFER, entity.getId());
			if (tasks.size() > 0) {
				task = tasks.get(0);
				if (task.getProcessRole().getRolePermission() == UserPermissionEnum.Approve)
					entity.setApproval(ApprovalEnum.WaitingForManager);
				else
					entity.setApproval(ApprovalEnum.Approved);

				dao.update(entity);
				TasksService.instance().findNextInProcessAndCreateTask("New inter-SETA Transfer for approval", user,
						entity.getId(), entity.getClass().getName(), true, task,
						MailDef.instance(MailEnum.InterSetaTransfer, MailTagsEnum.FirstName, user.getFirstName(),
								MailTagsEnum.LastName, user.getLastName()),
						null);
			} else {
				entity.setApproval(ApprovalEnum.Approved);
				dao.update(entity);
			}
		}

	}

	public void rejectTransfer(InterSetaTransfer entity, Users user, Tasks task) throws Exception {
		entity.setApproval(ApprovalEnum.Rejected);
		dao.update(entity);
		TasksService.instance().completeTaskByTargetKey(ConfigDocProcessEnum.INTER_SETA_TRANSFER, entity.getId());
		if (task != null) {
			task = TasksService.instance().findByKey(task.getId());
			TasksService.instance().findPreviousInProcessAndCreateTask("Inter-SETA Transfer was rejected", user,
					entity.getId(), entity.getClass().getName(), true, task,
					MailDef.instance(MailEnum.InterSetaTransfer, MailTagsEnum.FirstName, user.getFirstName(),
							MailTagsEnum.LastName, user.getLastName()),
					null);
		} else {
			List<Tasks> tasks = TasksService.instance()
					.findTasksByTypeAndKeyLast(ConfigDocProcessEnum.INTER_SETA_TRANSFER, entity.getId());
			if (tasks.size() > 0) {
				TasksService.instance().findPreviousInProcessAndCreateTask("Inter-SETA Transfer was rejected", user,
						entity.getId(), entity.getClass().getName(), true, tasks.get(0),
						MailDef.instance(MailEnum.InterSetaTransfer, MailTagsEnum.FirstName, user.getFirstName(),
								MailTagsEnum.LastName, user.getLastName()),
						null);
			}
		}

	}

	/**
	 * Get count of InterSetaTransfer for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            InterSetaTransfer class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the InterSetaTransfer entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<InterSetaTransfer> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public Users sessionUserSearch(Users activeUser) throws Exception {
		Users user = new Users();
		IdPassportEnum idnumber;

		if (org.apache.commons.lang3.StringUtils.isNotBlank(activeUser.getRsaIDNumber())) {
			idnumber = IdPassportEnum.RsaId;
		} else if (org.apache.commons.lang3.StringUtils.isNotBlank(activeUser.getPassportNumber())) {
			idnumber = IdPassportEnum.Passport;
		} else {
			throw new Exception("Null user details");
		}

		switch (idnumber) {
		case RsaId:
			user = usersService.findByRsaIdJoinAddress(activeUser.getRsaIDNumber());
			break;
		case Passport:
			user = usersService.findByPassportNumberJoinAddress(activeUser.getPassportNumber());
			break;
		default:
			user = new Users();
			break;
		}
		if (user == null) {
			user = new Users();
			user.setRsaIDNumber(activeUser.getRsaIDNumber());
			user.setPassportNumber(activeUser.getPassportNumber());
			user.setExistingUser(false);
			user.setRegFieldsDone(false);
		} else {
			user.setExistingUser(true);
			user.setRegFieldsDone(true);
		}
		if (idnumber == IdPassportEnum.RsaId) {
			GenericUtility.calcIDData(user);
		}
		user.setDoneSearch(true);
		return user;
	}
}
