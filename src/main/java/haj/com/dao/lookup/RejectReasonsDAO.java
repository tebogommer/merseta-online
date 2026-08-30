package haj.com.dao.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.service.RejectReasonMultipleSelectionService;

// TODO: Auto-generated Javadoc
/**
 * The Class RejectReasonsDAO.
 */
public class RejectReasonsDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all RejectReasons.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasons
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasons> allRejectReasons() throws Exception {
		return (List<RejectReasons>) super.getList("select o from RejectReasons o");
	}

	/**
	 * Get all RejectReasons between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasons
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasons> allRejectReasons(Long from, int noRows) throws Exception {
		String hql = "select o from RejectReasons o ";
		Map<String, Object> parameters = new HashMap<>();

		return (List<RejectReasons>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasons
	 */
	public RejectReasons findByKey(Long id) throws Exception {
		String hql = "select o from RejectReasons o where o.id = :id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return (RejectReasons) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find RejectReasons by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             global exception
	 * @see RejectReasons
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByName(String description) throws Exception {
		String hql = "select o from RejectReasons o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<RejectReasons>) super.getList(hql, parameters);
	}

	/**
	 * Find by process.
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByProcess(ConfigDocProcessEnum process) {
		String hql = "select o from RejectReasons o where o.forProcess = :process";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByProcessAndSoftDelete(ConfigDocProcessEnum process, Boolean softDelete) {
		String hql = "select o from RejectReasons o where o.forProcess = :process and o.softDeleted = :softDelete";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("softDelete", softDelete);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByProcessSeniorManager(ConfigDocProcessEnum process, Boolean booleanValue) {
		String hql = "select o from RejectReasons o where o.forProcess = :process and o.forSeniorManager = :booleanValue";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("booleanValue", booleanValue);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByBooleans(ConfigDocProcessEnum process, Boolean forSeniorManager, Boolean forManager,Boolean forExecutive,Boolean forCrm) {
		String hql = "select o from RejectReasons o where o.forProcess = :process and o.forSeniorManager = :forSeniorManager";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("forSeniorManager", forSeniorManager);
		parameters.put("forManager", forManager);
		parameters.put("forExecutive", forExecutive);
		parameters.put("forCrm", forCrm);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	

	/**
	 * Find by process and boolean value
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByProcessAndBooleanSelection(ConfigDocProcessEnum process, Boolean booleanValue) throws Exception {
		String hql = "select o from RejectReasons o where o.forProcess = :process and o.forCrm = :booleanValue";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("booleanValue", booleanValue);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByProcessAndBooleanSelectionAndSoftDelete(ConfigDocProcessEnum process, Boolean booleanValue, Boolean softDelete) throws Exception {
		String hql = "select o from RejectReasons o where o.forProcess = :process and o.forCrm = :booleanValue and o.softDeleted = :softDelete";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("process", process);
		parameters.put("booleanValue", booleanValue);
		parameters.put("softDelete", softDelete);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<RejectReasons> findLinkedByMultipleSelection(Long targetKey, String targetClass) throws Exception {
		String hql = "select o from RejectReasons o where o.id in "
				+ "(select x.rejectReason from RejectReasonMultipleSelection x where x.targetClass = :targetClass and x.targetKey = :targetKey) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		return (List<RejectReasons>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<RejectReasons> locateReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass, ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from RejectReasons o where o.id in "
				+ "(select x.rejectReason from RejectReasonMultipleSelection x where "
				+ "x.targetClass = :targetClass and " + "x.targetKey = :targetKey and " + "x.forProcess = :process) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> findByTargetKeyClassAndProcessAndResolveRejectDate(Long targetKey, String targetClass,ConfigDocProcessEnum process) throws Exception {
		String hql = "select o from RejectReasonMultipleSelection o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.forProcess = :process";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		List<RejectReasonMultipleSelection> rrmList=(List<RejectReasonMultipleSelection>) super.getList(hql, parameters);
		 List<RejectReasons> list=new ArrayList<>();
		if(rrmList !=null && rrmList.size()>0)
		{
			for(RejectReasonMultipleSelection rrm:rrmList)
			{
				rrm.getRejectReason().setRejectDate(rrm.getCreateDate());
				list.add(rrm.getRejectReason());
			}
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(Long targetKey, String targetClass,ConfigDocProcessEnum process) throws Exception {
		RejectReasonMultipleSelectionService rejectReasonMultipleSelectionService=new RejectReasonMultipleSelectionService();
		List<RejectReasons>  list=new ArrayList<>();
		List<RejectReasonMultipleSelection> rrmList= rejectReasonMultipleSelectionService.findByTargetKeyClassNameAndProcess(targetKey, targetClass, process);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			
			RejectReasons rejectReasons=rrm.getRejectReason();
			rejectReasons.setRejectDate(rrm.getCreateDate());
			if(rrm.getAdditionalInformation() !=null)
			{
				rejectReasons.setAdditionalInformation(rrm.getAdditionalInformation());
			}
			list.add(rejectReasons);
			
		}
		
		return list;
	}


	public long countReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass, ConfigDocProcessEnum process) throws Exception {
		String hql = "select count(o) from RejectReasons o where o.id in "
				+ "(select x.rejectReason from RejectReasonMultipleSelection x where "
				+ "x.targetClass = :targetClass and " + "x.targetKey = :targetKey and " + "x.forProcess = :process) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass.trim());
		parameters.put("process", process);
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> locateReasonsForDgVerificationByVerificationIdAndLastestEntry(Long dgVerificationID, Boolean lastestEntry) throws Exception {
		String hql = "select o from RejectReasons o where o.id in "
				+ "(select x.rejectReason from DgVerificationRejectionInformation x where "
				+ "x.dgVerification.id = :dgVerificationID and x.latestEntry = :lastestEntry )";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("dgVerificationID", dgVerificationID);
		parameters.put("lastestEntry", lastestEntry);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<RejectReasons> locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(Long dgVerificationID, Long roleId, Boolean lastestEntry) throws Exception {
		String hql = "select o from RejectReasons o where o.id in "
				+ "(select x.rejectReason from DgVerificationRejectionInformation x where "
				+ "x.dgVerification.id = :dgVerificationID and x.latestEntry = :lastestEntry and x.role.id = :roleId )";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("dgVerificationID", dgVerificationID);
		parameters.put("lastestEntry", lastestEntry);
		parameters.put("roleId", roleId);
		return (List<RejectReasons>) super.getList(hql, parameters);
	}
	
}