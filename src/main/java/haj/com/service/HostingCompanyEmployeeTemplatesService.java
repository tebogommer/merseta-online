package haj.com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.dao.HostingCompanyEmployeeTemplatesDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyEmployeeTemplates;
import haj.com.entity.Users;
import haj.com.entity.enums.TemplateTypeEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployeeTemplatesService.
 */
public class HostingCompanyEmployeeTemplatesService extends AbstractService {

	/** The dao. */
	private HostingCompanyEmployeeTemplatesDAO dao = new HostingCompanyEmployeeTemplatesDAO();
	
	/** The instance. */
	private static HostingCompanyEmployeeTemplatesService instance = null;

	/**
	 * All template.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyEmployeeTemplates> allTemplate() throws Exception {
		return dao.allHostingCompanyEmployeeTemplates();
	}

	/**
	 * Instantiates a new hosting company templates service.
	 */
	public HostingCompanyEmployeeTemplatesService() {
		super();
	}

	/**
	 * Instantiates a new hosting company templates service.
	 *
	 * @param auditlog the auditlog
	 */
	public HostingCompanyEmployeeTemplatesService(Map<String, Object> auditlog) {
		super(auditlog);
	}

	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void create(HostingCompanyEmployeeTemplates entity) throws Exception {
		entity.setCreateDate(new java.util.Date());
		this.dao.create(entity);
		uniqueId(entity);
//		GenericAuditLog.log(auditlog, "targetKey", entity.getId(), "taskType", TaskTypeEnum.Admin.getID(),
//					"Create Template", entity.getTitle() + " - Template created." + "<br/>" + "Type: " + entity.getTemplateType().getFriendlyName());	
	}

	/**
	 * Update.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void update(HostingCompanyEmployeeTemplates entity) throws Exception {
		preUpdateChecks(entity);
		this.dao.update(entity);
		uniqueId(entity);
//		GenericAuditLog.log(auditlog, "targetKey", entity.getId(), "taskType", TaskTypeEnum.Admin.getID(),
//				"Update Template", entity.getTitle() + " - Template Updated." + "<br/>" + "Type: " + entity.getTemplateType().getFriendlyName());	
	}

	/**
	 * Pre update checks.
	 *
	 * @param cdoc the cdoc
	 * @throws Exception the exception
	 */
	private void preUpdateChecks(HostingCompanyEmployeeTemplates cdoc) throws Exception {
		if (cdoc.getTemplateType() == TemplateTypeEnum.Document) {
			List<HostingCompanyEmployeeTemplates> l = findByParent(cdoc.getHostingCompany(), cdoc);
			if (l != null && l.size() > 0) {
				throw new Exception(
						"You cannot change the type to document since this is a chapter with other documents under it!");
			}
		}
	}

	/**
	 * Delete.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void delete(HostingCompanyEmployeeTemplates entity) throws Exception {
		List<IDataEntity> delList = new ArrayList<IDataEntity>();
		buildPath(entity, delList);
		Collections.reverse(delList);
		delList.add(entity);
		dao.deleteBatch(delList);
//		GenericAuditLog.log(auditlog, "targetKey", entity.getId(), "taskType", TaskTypeEnum.Admin.getID(),
//				"Delete Template", entity.getTitle() + " - Template Deleted." + "<br/>" + "Type: " + entity.getTemplateType().getFriendlyName());	
	}

	/**
	 * Delete btype.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void deleteBtype(HostingCompanyEmployeeTemplates entity) throws Exception {
		List<IDataEntity> delList = new ArrayList<IDataEntity>();
		buildPath(entity, delList);
		Collections.reverse(delList);
		delList.add(entity);
		dao.deleteBatch(delList);
//		GenericAuditLog.log(auditlog, "targetKey", entity.getId(), "taskType", TaskTypeEnum.Admin.getID(),
//				"Delete Template", entity.getTitle() + " - Template Deleted.");	
	}

//	Concept of business type not required	
//	private void buildPath(HostingCompanyEmployeeTemplates template, List<IDataEntity> arrayList, BusinessType businessType) throws Exception {
//		List<HostingCompanyEmployeeTemplates> l = findByParent(template.getHostingCompany(), template, businessType);
//		for (HostingCompanyEmployeeTemplates template2 : l) {
//			arrayList.add(template2);
//			buildPath(template2, arrayList, businessType);
//		}
//	}

	/**
 * Find by key.
 *
 * @param id the id
 * @return the hosting company templates
 * @throws Exception the exception
 */
public HostingCompanyEmployeeTemplates findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}
	
	/**
	 * Find by key.
	 *
	 * @param hostingcompanyTemplate the hostingcompany template
	 * @return the hosting company templates
	 * @throws Exception the exception
	 */
	public HostingCompanyEmployeeTemplates findByKey(HostingCompanyEmployeeTemplates hostingcompanyTemplate) throws Exception {
		return dao.findByKey(hostingcompanyTemplate.getId());
	}

	/**
	 * Find by name.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyEmployeeTemplates> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Find by company.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyEmployeeTemplates> findByCompany(HostingCompany company) throws Exception {
		return dao.findByCompany(company.getId());
	}

	/**
	 * Find roots.
	 *
	 * @param hostingCompany the hosting company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyEmployeeTemplates> findRoots(HostingCompany hostingCompany) throws Exception {
		return dao.findRoots(hostingCompany.getId());
	}

	/**
	 * Find roots not in B type.
	 *
	 * @param hostingCompany the hosting company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyEmployeeTemplates> findRootsNotInBType(HostingCompany hostingCompany)
			throws Exception {
		return dao.findRootsNotInBType(hostingCompany.getId());
	}

	/**
	 * Find by parent.
	 *
	 * @param hostingCompany the hosting company
	 * @param parent the parent
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyEmployeeTemplates> findByParent(HostingCompany hostingCompany, HostingCompanyEmployeeTemplates parent) throws Exception {
		return dao.findByParent(hostingCompany.getId(), parent.getId());
	}

	/**
	 * Start build.
	 *
	 * @param leftRoot the left root
	 * @param user the user
	 * @param hostingCompany the hosting company
	 * @param expand the expand
	 * @param roots the roots
	 * @throws Exception the exception
	 */
	public void startBuild(TreeNode leftRoot, Users user, HostingCompany hostingCompany, boolean expand, List<HostingCompanyEmployeeTemplates> roots) throws Exception {
		for (HostingCompanyEmployeeTemplates template : roots) {
			if (template.getTemplateType() == TemplateTypeEnum.Chapter) {
				DefaultTreeNode d = new DefaultTreeNode(template, leftRoot);
				d.setExpanded(expand);
				createTree(d, template, hostingCompany);
			} else {
				DefaultTreeNode d = new DefaultTreeNode("document", template, leftRoot);
				d.setExpanded(expand);
			}
		}

	}

	/**
	 * Creates the tree.
	 *
	 * @param defaultTreeNode the default tree node
	 * @param parent the parent
	 * @param hostingCompany the hosting company
	 * @throws Exception the exception
	 */
	private void createTree(DefaultTreeNode defaultTreeNode, HostingCompanyEmployeeTemplates parent, HostingCompany hostingCompany)
			throws Exception {
		List<HostingCompanyEmployeeTemplates> templates = findByParent(hostingCompany, parent);
		for (HostingCompanyEmployeeTemplates template : templates) {
			if (template.getTemplateType() == TemplateTypeEnum.Document) {
				createTree(new DefaultTreeNode("document", template, defaultTreeNode), template, hostingCompany);
			} else {
				createTree(new DefaultTreeNode(template, defaultTreeNode), template, hostingCompany);
			}
		}
	}

	/**
	 * Unique id.
	 *
	 * @param cd the cd
	 * @throws Exception the exception
	 */
	private void uniqueId(HostingCompanyEmployeeTemplates cd) throws Exception {
		if (cd.getTemplateType() == TemplateTypeEnum.Document && StringUtils.isBlank(cd.getUniqueId())) {
			cd.setUniqueId("S" + String.format("%05d", cd.getId()));
			dao.update(cd);
		}
	}

	/**
	 * Builds the path.
	 *
	 * @param template the template
	 * @param arrayList the array list
	 * @throws Exception the exception
	 */
	public void buildPath(HostingCompanyEmployeeTemplates template, List<IDataEntity> arrayList) throws Exception {
		List<HostingCompanyEmployeeTemplates> l = findByParent(template.getHostingCompany(), template);
		for (HostingCompanyEmployeeTemplates template2 : l) {
			arrayList.add(template2);
			buildPath(template2, arrayList);
		}
	}
	
//	Concept of business type not required
//	public void startBuild(TreeNode rightRoot, Users user, HostingCompany hostingCompany, boolean expand,
//			BusinessType businessType, List<HostingCompanyEmployeeTemplates> roots) throws Exception {
//		// List<Template> roots = findRoots(hostingCompany,businessType);
//		for (HostingCompanyEmployeeTemplates template : roots) {
//			if (template.getTemplateType() == TemplateTypeEnum.Chapter) {
//				DefaultTreeNode d = new DefaultTreeNode(template, rightRoot);
//				d.setExpanded(expand);
//				createTree(d, template, hostingCompany, businessType);
//			} else {
//				DefaultTreeNode d = new DefaultTreeNode("document", template, rightRoot);
//				d.setExpanded(expand);
//			}
//		}
//	}
	
//	Concept of business type not required
//	private void createTree(DefaultTreeNode defaultTreeNode, HostingCompanyEmployeeTemplates parent, HostingCompany hostingCompany) throws Exception {
//		List<HostingCompanyEmployeeTemplates> templates = findByParent(hostingCompany, parent, businessType);
//		for (HostingCompanyEmployeeTemplates template : templates) {
//			if (template.getTemplateType() == TemplateTypeEnum.Document) {
//				createTree(new DefaultTreeNode("document", template, defaultTreeNode), template, hostingCompany,
//						businessType);
//			} else {
//				createTree(new DefaultTreeNode(template, defaultTreeNode), template, hostingCompany, businessType);
//			}
//		}
//
//	}

//	Concept of business type not required
//	private List<HostingCompanyEmployeeTemplates> findByParent(HostingCompany hostingCompany, HostingCompanyEmployeeTemplates parent, BusinessType businessType)
//			throws Exception {
//		return dao.findByParent(hostingCompany.getId(), parent.getId(), businessType.getId());
//	}
	
//	Concept of business type not required
//	public List<HostingCompanyEmployeeTemplates> findRoots(HostingCompany hostingCompany, BusinessType businessType) throws Exception {
//		return dao.findRoots(hostingCompany.getId(), businessType.getId());
//	}

	/**
 * Creates the.
 *
 * @param drag the drag
 * @param drop the drop
 * @throws Exception the exception
 */
public void create(HostingCompanyEmployeeTemplates drag, HostingCompanyEmployeeTemplates drop) throws Exception {
		List<HostingCompanyEmployeeTemplates> dupList = new ArrayList<HostingCompanyEmployeeTemplates>();
		dupList.add(drag);
		duplicate(dupList, drag);

		List<IDataEntity> list = new ArrayList<IDataEntity>();
		HostingCompanyEmployeeTemplates t = null;
		for (HostingCompanyEmployeeTemplates cf : dupList) {
			t = (HostingCompanyEmployeeTemplates) cf.clone();
			t.setParentTemplate(null);
//			t.setBusinessType(businessType);
			t.setOriginalTemplate(cf);
			t.setId(null);

			t.setUniqueId(null);
			list.add(t);
		}
		dao.createBatch(list);

		for (IDataEntity iDataEntity : list) {
			doParent(((HostingCompanyEmployeeTemplates) iDataEntity), dupList, list);
//			GenericAuditLog.log(auditlog, "targetKey", ((HostingCompanyEmployeeTemplates) iDataEntity).getTitle(), "taskType",
//					TaskTypeEnum.Admin.getID(), "Create Business Type Template",
//					((HostingCompanyEmployeeTemplates) iDataEntity).getTitle() + " Business Type Template created.");
		}

	}

	/**
	 * Do parent.
	 *
	 * @param template the template
	 * @param dupList the dup list
	 * @param newList the new list
	 * @throws Exception the exception
	 */
	private void doParent(HostingCompanyEmployeeTemplates template, List<HostingCompanyEmployeeTemplates> dupList, List<IDataEntity> newList) throws Exception {
		for (HostingCompanyEmployeeTemplates cf : dupList) {
			if (cf.hsCompareTo(template)) {
				if (cf.getParentTemplate() != null) {
					template.setParentTemplate(findNewParent(template, cf.getParentTemplate(), newList));
					dao.update(template);
				}
			}
		}
	}

	/**
	 * Find new parent.
	 *
	 * @param newTemplate the new template
	 * @param oldParent the old parent
	 * @param newList the new list
	 * @return the hosting company templates
	 */
	private HostingCompanyEmployeeTemplates findNewParent(HostingCompanyEmployeeTemplates newTemplate, HostingCompanyEmployeeTemplates oldParent, List<IDataEntity> newList) {
		for (IDataEntity cf : newList) {
			if (((HostingCompanyEmployeeTemplates) cf).hsCompareToOld(oldParent)) {
				return (HostingCompanyEmployeeTemplates) cf;
			}
		}
		return null;
	}

	/**
	 * Duplicate.
	 *
	 * @param dupList the dup list
	 * @param drag the drag
	 * @throws Exception the exception
	 */
	private void duplicate(List<HostingCompanyEmployeeTemplates> dupList, HostingCompanyEmployeeTemplates drag) throws Exception {
		List<HostingCompanyEmployeeTemplates> l = findByParent(drag.getHostingCompany(), drag);
		for (HostingCompanyEmployeeTemplates template2 : l) {
			dupList.add(template2);
			duplicate(dupList, template2);
		}

	}
	
//	concept of business type not required
//	public List<HostingCompanyEmployeeTemplates> findByBusinessType(HostingCompany company, BusinessType businessType) throws Exception {
//		return dao.findByBusinessType(company.getId(), businessType.getId());
//	}
}
