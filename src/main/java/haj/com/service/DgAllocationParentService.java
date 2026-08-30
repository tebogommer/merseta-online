package haj.com.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.DgAllocationStatusReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.DgAllocationParentDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.AllocationChangesReasons;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DgAllocationParentService extends AbstractService {

	/** The dao. */
	private DgAllocationParentDAO dao = new DgAllocationParentDAO();

	private ConfigDocService configDocService = new ConfigDocService();

	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	private CompanyService companyService;

	private AllocationChangesReasonsService allocationChangesReasonsService = new AllocationChangesReasonsService();

	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();

	private RejectReasonsService rejectReasonService = new RejectReasonsService();

	/**
	 * Get all DgAllocationParent
	 * 
	 * @author TechFinium
	 * @see DgAllocationParent
	 * @return a list of {@link haj.com.entity.DgAllocationParent}
	 * @throws Exception
	 *             the exception
	 */
	public List<DgAllocationParent> allDgAllocationParent() throws Exception {
		return dao.allDgAllocationParent();
	}

	/**
	 * Create or update DgAllocationParent.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DgAllocationParent
	 */
	public void create(DgAllocationParent entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update DgAllocationParent.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DgAllocationParent
	 */
	public void update(DgAllocationParent entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DgAllocationParent.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see DgAllocationParent
	 */
	public void delete(DgAllocationParent entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.DgAllocationParent}
	 * @throws Exception
	 *             the exception
	 * @see DgAllocationParent
	 */
	public DgAllocationParent findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	public DgAllocationParent findByKey1(long id) throws Exception {
		return dao.findByKey(id);
	}

	public DgAllocationParent findByKeyAndPopulateDoc(DgAllocationParent dgAllocationParent,
			ActiveContracts activeContact) throws Exception {
		return populateAdditionalInformation(dgAllocationParent, activeContact);
	}

	/**
	 * Find DgAllocationParent by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.DgAllocationParent}
	 * @throws Exception
	 *             the exception
	 * @see DgAllocationParent
	 */
	public List<DgAllocationParent> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load DgAllocationParent
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgAllocationParent}
	 * @throws Exception
	 *             the exception
	 */
	public List<DgAllocationParent> allDgAllocationParent(int first, int pageSize) throws Exception {
		return dao.allDgAllocationParent(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of DgAllocationParent for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the DgAllocationParent
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(DgAllocationParent.class);
	}

	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> allDgAllocationParentReport(Class<DgAllocationParent> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateReportInformation((List<DgAllocationParent>) dao.sortAndFilterSearch(class1, first, pageSize, sortField, sortOrder, filters));
	}
	
	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> allDgAllocationParentReportByGrantYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer grantYear) throws Exception {
		String hql = "select o from DgAllocationParent o where o.wsp.finYear = :grantYear";
		filters.put("grantYear", grantYear);
		return populateReportInformation((List<DgAllocationParent>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllDgAllocationParentReportByGrantYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgAllocationParent o where o.wsp.finYear = :grantYear";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Lazy load DgAllocationParent with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            DgAllocationParent class
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
	 * @return a list of {@link haj.com.entity.DgAllocationParent} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgAllocationParent> allDgAllocationParent(Class<DgAllocationParent> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateInformation((List<DgAllocationParent>) dao.sortAndFilterSearch(class1, first, pageSize, sortField, sortOrder, filters));
	}

	private List<DgAllocationParent> populateReportInformation(List<DgAllocationParent> list) throws Exception {
		for (DgAllocationParent dgAllocationParent : list) {
			populate(dgAllocationParent);
		}
		return list;
	}

	private List<DgAllocationParent> populateInformation(List<DgAllocationParent> list) throws Exception {
		for (DgAllocationParent dgAllocationParent : list) {
			populate(dgAllocationParent);
		}
		return list;
	}

	private void populate(DgAllocationParent dgAllocationParent) throws Exception {
		if (dgAllocationParent.getWsp() != null) {

			Region r = getRegion(dgAllocationParent.getWsp());
			dgAllocationParent.setRegion(r);
			Users clo = getCLO(dgAllocationParent.getWsp());
			dgAllocationParent.setCloUser(clo);
			resolveStatus(dgAllocationParent);
			ActiveContractsService activeContractsService = new ActiveContractsService();
			ActiveContracts activeContracts = null;
			List<ActiveContracts> ls = activeContractsService.findActiveContractsByDgAllocationParent(dgAllocationParent.getId());
			if (ls.size() > 0) {
				activeContracts = ls.get(0);
			}
			if (companyService == null) {
				companyService = new CompanyService();
			}
			companyService.resolveCompanyData(dgAllocationParent.getWsp().getCompany());
			companyService.populateReportInfo(dgAllocationParent.getWsp().getCompany());
			// ActiveContracts activeContracts =
			// activeContractsService.findByWSP(dgAllocationParent.getWsp().getId());
			// ActiveContracts activeContracts =
			// activeContractsService.findByDgAllocationParent(dgAllocationParent.getId());
			if (activeContracts != null) {
				dgAllocationParent.setContractValue(activeContracts.getContractvalue());
			} else {
				if (findTotalContractValue(dgAllocationParent) != null) {
					dgAllocationParent.setContractValue(findTotalContractValue(dgAllocationParent).doubleValue());
				} else {
					dgAllocationParent.setContractValue(0.0);
				}
			}
		}

	}

	private DgAllocationParent resolveStatus(DgAllocationParent dgAllocationParent) throws Exception {
		if (dgAllocationParent != null) {
			if (dgAllocationParent.getStatus() == null) {
				dgAllocationParent.setStringStatus("N/A");
				dgAllocationParent.setReason("N/A");
			}else if (dgAllocationParent.getStatus() == ApprovalEnum.AcceptedMAO) {
				dgAllocationParent.setStringStatus(ApprovalEnum.AcceptedMAO.getFriendlyName());
				dgAllocationParent.setReason("N/A");
			} else if (dgAllocationParent.getStatus() == ApprovalEnum.Withdrawn) {
				dgAllocationParent.setStringStatus(ApprovalEnum.Withdrawn.getFriendlyName());
				if (dgAllocationParent.getDiscretionalWithdrawalAppealEnum() != null) {
					dgAllocationParent.setReason(dgAllocationParent.getDiscretionalWithdrawalAppealEnum().getFriendlyName());
				} else {
					dgAllocationParent.setReason("N/A");
				}
			} else if (dgAllocationParent.getStatus() == ApprovalEnum.HigherAllocationRequest) {
				dgAllocationParent.setStringStatus(ApprovalEnum.HigherAllocationRequest.getFriendlyName());
				resolveReason(dgAllocationParent);
			} else if (dgAllocationParent.getStatus() == ApprovalEnum.RequestedChange) {
				dgAllocationParent.setStringStatus(ApprovalEnum.RequestedChange.getFriendlyName());
				resolveReason(dgAllocationParent);
			} else if (dgAllocationParent.getStatus() == ApprovalEnum.Appealed) {
				dgAllocationParent.setStringStatus(ApprovalEnum.Appealed.getFriendlyName());
				if (dgAllocationParent.getAppealStatus() != null) {
					dgAllocationParent.setReason(dgAllocationParent.getAppealStatus().getFriendlyName());
				}else {
					dgAllocationParent.setReason("N/A");
				}
				
			} else if (dgAllocationParent.getStatus() == ApprovalEnum.Rejected) {
				dgAllocationParent.setStringStatus(ApprovalEnum.Rejected.getFriendlyName());
				resolveRejectRasons(dgAllocationParent);
			} else {
				dgAllocationParent.setStringStatus("N/A");
				dgAllocationParent.setReason("N/A");
			}
			return dgAllocationParent;
		} else {
			return null;
		}
	}

	private void resolveRejectRasons(DgAllocationParent dgAllocationParent) throws Exception {
		List<RejectReasonsChild> rejectReasonsChild = rejectReasonsService.findByTargetClassAndKey(DgAllocationParent.class.getName(), dgAllocationParent.getId());
		String reason = "";
		int size = 0;
		if (rejectReasonsChild.size() > 0) {
			for (RejectReasonsChild child : rejectReasonsChild) {
				size += 1;
				reason += child.getRejectReasons().getDescription();
				if (size == rejectReasonsChild.size()) {
					reason += ".";
				} else {
					reason += ", ";
				}
			}
		} else {
			List<RejectReasons> reasons = rejectReasonService.locateReasonsSelectedByTargetKeyClassAndProcess(dgAllocationParent.getId(), DgAllocationParent.class.getName(), ConfigDocProcessEnum.DG_ALLOCATION);
			if (reasons.size() > 0) {
				for (RejectReasons rea : reasons) {
					size += 1;
					reason += rea.getDescription();
					if (size == rejectReasonsChild.size()) {
						reason += ".";
					} else {
						reason += ", ";
					}
				}
			}
		}
		dgAllocationParent.setReason(reason);
	}

	private DgAllocationParent resolveReason(DgAllocationParent dgAllocationParent) throws Exception {
		List<AllocationChangesReasons> list = allocationChangesReasonsService.findDGAllocationChangesReasons(dgAllocationParent);
		if (list.size() > 0) {
			AllocationChangesReasons allocationChangesReasons = list.get(0);
			if (allocationChangesReasons != null) {
				dgAllocationParent.setReason(allocationChangesReasons.getAllocationChange().getDescription());
			} else {
				dgAllocationParent.setReason("N/A");
			}
		} else {
			dgAllocationParent.setReason("N/A");
		}
		return dgAllocationParent;
	}

	/**
	 * Get count of DgAllocationParent for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            DgAllocationParent class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the DgAllocationParent entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<DgAllocationParent> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public int countSearch(Class<DgAllocationParent> entity, Map<String, Object> filters) throws Exception {
		return dao.countSearch(entity, filters);
	}

	public List<DgAllocationParent> findByChildrenByParent(DgAllocationParent dgAllocationParent) throws Exception {
		return dao.findByChildrenByParent(dgAllocationParent.getId());
	}

	public List<DgAllocationParent> populateAdditionalInformationList(List<DgAllocationParent> parentList)
			throws Exception {
		for (DgAllocationParent dgAllocationParent : parentList) {
			populateAdditionalInformation(dgAllocationParent);
		}
		return parentList;
	}

	private DgAllocationParent populateAdditionalInformation(DgAllocationParent dgAllocationParent) throws Exception {
		dgAllocationParent.setDocs(DocService.instance().searchByTargetKeyAndClass(DgAllocationParent.class.getName(),
				dgAllocationParent.getId()));
		return dgAllocationParent;
	}

	private DgAllocationParent populateAdditionalInformation(DgAllocationParent dgAllocationParent,
			ActiveContracts activeContracts) throws Exception {
		return resolveActiveCotractDocs(dgAllocationParent, activeContracts);
	}

	public DgAllocationParent resolveActiveCotractDocs(DgAllocationParent dgAllocationParent,
			ActiveContracts activeContracts) throws Exception {
		// List<Doc>
		// activeContractDoc=DocService.instance().searchByTargetKeyAndClass(ActiveContracts.class.getName(),
		// activeContracts.getId());
		activeContracts.setDocs(DocService.instance().searchByTargetKeyAndClass(activeContracts.getClass().getName(),
				activeContracts.getId()));
		List<Doc> activeContractDoc = activeContracts.getDocs();
		if (activeContractDoc != null && activeContractDoc.size() > 0) {
			if (dgAllocationParent.getDocs().size() > 0) {
				for (Doc doc : dgAllocationParent.getDocs()) {
					activeContractDoc.add(doc);
				}
			}
			dgAllocationParent.setDocs(activeContractDoc);
		}

		return dgAllocationParent;
	}

	public void populateAppealDocUpload(DgAllocationParent dgAllocationParent) throws Exception {
		if (dgAllocationParent.getDocs() == null) {
			dgAllocationParent.setDocs(new ArrayList<>());
		}
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(dgAllocationParent.getClass().getName(),
				dgAllocationParent.getId(), ConfigDocProcessEnum.DG_ALLOCATION_APPEAL, CompanyUserTypeEnum.Company);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				dgAllocationParent.getDocs().add(new Doc(a));
			});
		}
	}

	public void appleadDgParent(DgAllocationParent dgAllocationParent, Users sessionUser) throws Exception {
		dgAllocationParent.setDateAppealed(getSynchronizedDate());
		dgAllocationParent.setAppealStatus(ApprovalEnum.PendingApproval);
		dgAllocationParent.setUserAppealed(sessionUser);
		update(dgAllocationParent);
		TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, dgAllocationParent.getId(),
				DgVerification.class.getName(), true, ConfigDocProcessEnum.DG_ALLOCATION_APPEAL, null, null);
	}

	public void finalApproveParent(DgAllocationParent dgAllocationParent, Users sessionUser, Tasks task)
			throws Exception {
		// update first for audit purposes
		dgAllocationParent.setAppealStatus(ApprovalEnum.Approved);
		dgAllocationParent.setUserApproveRejectedAppeal(sessionUser);
		dgAllocationParent.setDateAppealedApprovedRejected(getSynchronizedDate());
		update(dgAllocationParent);

		// second update for re-allocation
		dgAllocationParent.setStatus(null);
		dgAllocationParent.setAllocationParent(null);

		dgAllocationParent.setUserApproveRejectedAppeal(null);
		dgAllocationParent.setDateAppealedApprovedRejected(null);
		dgAllocationParent.setUserAppealed(null);
		dgAllocationParent.setAppealStatus(null);
		dgAllocationParent.setApprovalDate(null);
		dgAllocationParent.setDateAppealed(null);
		dgAllocationParent.setAppealStatus(null);

		update(dgAllocationParent);
		TasksService.instance().completeTask(task);

		Region r = new Region();
		if (dgAllocationParent.getWsp() != null) {
			r = getRegion(dgAllocationParent.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		if (dgAllocationParent.getWsp() != null) {
			if (dgAllocationParent.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService
						.locateFirstPrimarySDF(dgAllocationParent.getWsp().getCompany());
				if (sDFCompany != null) {
					String appealDate = "";
					if (dgAllocationParent.getDateAppealed() != null) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
						appealDate = format.format(dgAllocationParent.getDateAppealed());
					}
					// (Users user, Company company, Users clo, String
					// cloRegion, String grantyear, String Appealsubmissiondate)
					sendDGApplicationAppealSuccessfulOutcomeEmail(sDFCompany.getSdf(),
							dgAllocationParent.getWsp().getCompany(), getCLO(dgAllocationParent.getWsp()), region,
							String.valueOf(dgAllocationParent.getWsp().getFinYear()), appealDate);
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	public void finalRejectParent(DgAllocationParent dgAllocationParent, Users sessionUser, Tasks task,
			List<RejectReasons> selectedRejectReason) throws Exception {
		dgAllocationParent.setAppealStatus(ApprovalEnum.Rejected);
		dgAllocationParent.setUserApproveRejectedAppeal(sessionUser);
		dgAllocationParent.setDateAppealedApprovedRejected(new Date());
		update(dgAllocationParent);
		TasksService.instance().completeTask(task);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(
				dgAllocationParent.getId(), AssessorModeratorApplication.class.getName(), selectedRejectReason,
				sessionUser, ConfigDocProcessEnum.DG_ALLOCATION_APPEAL);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		Region r = new Region();
		if (dgAllocationParent.getWsp() != null) {
			r = getRegion(dgAllocationParent.getWsp());
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		if (dgAllocationParent.getWsp() != null) {
			if (dgAllocationParent.getWsp().getCompany() != null) {
				SDFCompany sDFCompany = sdfCompanyService
						.locateFirstPrimarySDF(dgAllocationParent.getWsp().getCompany());
				if (sDFCompany != null) {
					String appealDate = "";
					if (dgAllocationParent.getDateAppealed() != null) {
						appealDate = String.valueOf(dgAllocationParent.getDateAppealed());
					}

					sendDGApplicationAppealUnsuccessfulOutcomeEmail(sDFCompany.getSdf(),
							dgAllocationParent.getWsp().getCompany(), getCLO(dgAllocationParent.getWsp()), region,
							String.valueOf(dgAllocationParent.getWsp().getFinYear()), appealDate, selectedRejectReason);
				} else {
					throw new Exception("No Sdf assigned to a company");
				}

			} else {
				throw new Exception("No company assigned to a wsp");
			}
		} else {
			throw new Exception("No wsp assigned to a DG Allocation");
		}
	}

	/**
	 * Find DgAllocationParent by WSP
	 * 
	 * @author TechFinium
	 * @param wspID
	 *            the wspID
	 * @see DgAllocationParent
	 * @return a list of {@link haj.com.entity.DgAllocationParent}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public DgAllocationParent findByWSP(Long wspID) throws Exception {
		return dao.findByWSP(wspID);
	}

	public List<DgAllocationParent> findByWSPReturnList(Long wspID) throws Exception {
		return dao.findByWSPReturnList(wspID);
	}

	public void sendDGApplicationAppealSuccessfulOutcomeEmail(Users user, Company company, Users clo, String cloRegion,
			String grantyear, String Appealsubmissiondate) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail = clo.getEmail();

		Users ceo = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}

		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("cloFullName", cloFullName);
		params.put("appealsubmissiondate", Appealsubmissiondate);

		byte[] bites = JasperService.instance().convertJasperReportToByte(
				"DGD-TP-003-Notification_Discretionary_Grants_Application_Outcome_Appeal_Successful.jasper", params);

		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>"
				+ "<p>DISCRETIONARY GRANT APPLICATION APPEAL: #COMPANY# (#ENTITYID#) </p>"
				+ "<p>We wish to inform you that the merSETA has considered the discretionary grant application appeal "
				+ "for your organisation submitted on #Appealsubmissiondate#. </p>"

				+ "<p>We are pleased to inform you that an award will be made according to the merSETA Grant Policy. "
				+ "Please note that you will need to complete the project implementation plan on the NSDMS and "
				+ "submit together with the signed Memorandum of Agreement (MOA) within 30 business days from the date of receipt.</p>"

				+ "<p>Please note that should the enclosed MOA is not uploaded on the NSDMS within 30 business days from the date of issue, the merSETA may withdraw the MOA.</p>"

				+ "<p>For further assistance/support, kindly contact the designated Client Liaison Officer: "
				+ "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the #Region# Office. </p>"

				+ "<p>Yours sincerely,</p>" + "<p>Chief Executive Officer of the merSETA</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#grantyear#", grantyear);
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#",
				clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);
		welcome = welcome.replaceAll("#Appealsubmissiondate#", Appealsubmissiondate);

		// GenericUtility.sendMail(user.getEmail(), "DISCRETIONARY GRANT
		// APPLICATION
		// APPEAL", welcome, null);
		GenericUtility.sendMailWithAttachement(user.getEmail(), "DISCRETIONARY GRANT APPLICATION APPEAL", welcome,
				bites, "DISCRETIONARY_GRANT_APPLICATION_APPEAL.pdf", "pdf", null);
	}

	public void sendDGApplicationAppealUnsuccessfulOutcomeEmail(Users user, Company company, Users clo,
			String cloRegion, String grantyear, String Appealsubmissiondate, List<RejectReasons> selectedRejectReason)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		Users ceo = findCEO();
		String ceoFullname = "";
		if (ceo != null) {
			ceoFullname = ceo.getFirstName() + " " + ceo.getLastName();
		}

		String cloFullName = clo.getFirstName() + " " + clo.getLastName();
		String cloEmail = clo.getEmail();
		String reason = convertToHTML(selectedRejectReason);

		params.put("ceoFullname", ceoFullname);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", cloFullName);
		params.put("cloEmail", cloEmail);
		params.put("grantyear", grantyear);
		params.put("reason", reason);
		params.put("cloFullName", cloFullName);
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(selectedRejectReason));

		byte[] bites = JasperService.instance().convertJasperReportToByte(
				"DGD-TP-004-Notification_Discretionary_Grants_Application_Appeal _Unsuccessful.jasper", params);

		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String welcome = "<p>Dear #Title# #FirstName# #Surname#,</p>"
				+ "<p>DISCRETIONARY GRANT APPLICATION APPEAL FOR:  #COMPANY# (#ENTITYID#) </p>"

				+ "<p>We write to inform you that the merSETA has considered the discretionary grant application appeal "
				+ "for your organisation submitted on #Appealsubmissiondate#.</p>"

				+ "<p>We regret to advise that the outcome for the discretionary grant application appeal is unsuccessful for the following reason:</p>"

				+ reason

				+ "<p>For further assistance/support, kindly contact the designated Client Liaison Officer: "
				+ "#ClientLiaisonOfficerNameandSurname# (Email: #ClientLiaisonOfficerEmail#) or the #Region# Office. </p>"

				+ "<p>Yours sincerely,</p>" + "<p>Chief Executive Officer of the merSETA</p>" + "<br/>";
		welcome = welcome.replaceAll("#Title#", title);
		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#grantyear#", grantyear);
		welcome = welcome.replaceAll("#COMPANY#", company.getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameandSurname#",
				clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerEmail#", clo.getFirstName() + " " + clo.getEmail());
		welcome = welcome.replaceAll("#Region#", cloRegion);
		welcome = welcome.replaceAll("#Appealsubmissiondate#", Appealsubmissiondate);

		// GenericUtility.sendMail(user.getEmail(), "DISCRETIONARY GRANT
		// APPLICATION
		// APPEAL", welcome, null);
		GenericUtility.sendMailWithAttachement(user.getEmail(), "DISCRETIONARY GRANT APPLICATION APPEAL", welcome,
				bites, "DISCRETIONARY_GRANT_APPLICATION_APPEAL.pdf", "pdf", null);
	}

	public Users getCLO(Wsp wsp) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Users cloUser = rt.getClo().getUsers();
		return cloUser;
	}

	public Region getRegion(Wsp wsp) throws Exception {
		RegionService regionService = new RegionService();
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Region r = regionService.findByKey(rt.getRegion().getId());
		return r;
	}

	public String convertToHTML(List<RejectReasons> selectedRejectReason) {
		String sb = "<ul>";
		for (RejectReasons r : selectedRejectReason) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}

	public String convertStringToHTML(String r) {
		String sb = "<ul>" + r + "</ul>";
		return sb;
	}

	public Users findCEO() {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		List<Users> ceoList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
		Users ceo = null;
		if (ceoList.size() > 0) {
			ceo = ceoList.get(0);
		}
		return ceo;
	}

	public BigDecimal findTotalContractValue(DgAllocationParent dgAllocationParent) throws Exception {
		return dao.findTotalContractValue(dgAllocationParent);
	}

	public void validiateParentStatus(Wsp wsp) throws Exception {
		DgAllocationParent dgAllocationParent = findByWSP(wsp.getId());
		boolean canView = false;
		if (dgAllocationParent != null) {
			switch (dgAllocationParent.getStatus()) {
			case Approved:
				canView = true;
				break;
			case Rejected:
				canView = true;
				break;
			case Withdrawn:
				canView = true;
				break;
			case AcceptedMAO:
				canView = true;
				break;
			case HigherAllocationRequest:
				canView = true;
				break;
			default:
				break;
			}
		}
		if (!canView) {
			throw new Exception("Allocation For: " + wsp.getFinYear() + "  still being processed.");
		}
	}
	
	public Integer countByWspId(Long wspID) throws Exception {
		return dao.countByWspId(wspID);
	}
	
	public List<DgAllocationStatusReportBean> populateNativeAllocationStatusList(Integer grantYear) throws Exception {
		return dao.populateNativeAllocationStatusList(grantYear);
	}
}
