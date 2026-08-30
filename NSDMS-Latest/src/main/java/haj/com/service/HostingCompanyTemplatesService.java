package haj.com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.dao.HostingCompanyTemplatesDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyTemplates;
import haj.com.entity.Users;
import haj.com.entity.enums.TemplateTypeEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyTemplatesService.
 */
public class HostingCompanyTemplatesService extends AbstractService {

	/** The dao. */
	private HostingCompanyTemplatesDAO dao = new HostingCompanyTemplatesDAO();
	
	/** The instance. */
	private static HostingCompanyTemplatesService instance = null;

	/**
	 * All template.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyTemplates> allTemplate() throws Exception {
		return dao.allHostingCompanyTemplates();
	}

	/**
	 * Instantiates a new hosting company templates service.
	 */
	public HostingCompanyTemplatesService() {
		super();
	}

	/**
	 * Instantiates a new hosting company templates service.
	 *
	 * @param auditlog the auditlog
	 */
	public HostingCompanyTemplatesService(Map<String, Object> auditlog) {
		super(auditlog);
	}

	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void create(HostingCompanyTemplates entity) throws Exception {
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
	public void update(HostingCompanyTemplates entity) throws Exception {
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
	private void preUpdateChecks(HostingCompanyTemplates cdoc) throws Exception {
		if (cdoc.getTemplateType() == TemplateTypeEnum.Document) {
			List<HostingCompanyTemplates> l = findByParent(cdoc.getHostingCompany(), cdoc);
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
	public void delete(HostingCompanyTemplates entity) throws Exception {
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
	public void deleteBtype(HostingCompanyTemplates entity) throws Exception {
		List<IDataEntity> delList = new ArrayList<IDataEntity>();
		buildPath(entity, delList);
		Collections.reverse(delList);
		delList.add(entity);
		dao.deleteBatch(delList);
//		GenericAuditLog.log(auditlog, "targetKey", entity.getId(), "taskType", TaskTypeEnum.Admin.getID(),
//				"Delete Template", entity.getTitle() + " - Template Deleted.");	
	}

//	Concept of business type not required	
//	private void buildPath(HostingCompanyTemplates template, List<IDataEntity> arrayList, BusinessType businessType) throws Exception {
//		List<HostingCompanyTemplates> l = findByParent(template.getHostingCompany(), template, businessType);
//		for (HostingCompanyTemplates template2 : l) {
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
public HostingCompanyTemplates findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}
	
	/**
	 * Find by key.
	 *
	 * @param hostingcompanyTemplate the hostingcompany template
	 * @return the hosting company templates
	 * @throws Exception the exception
	 */
	public HostingCompanyTemplates findByKey(HostingCompanyTemplates hostingcompanyTemplate) throws Exception {
		return dao.findByKey(hostingcompanyTemplate.getId());
	}

	/**
	 * Find by name.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyTemplates> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Find by company.
	 *
	 * @param company the company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyTemplates> findByCompany(HostingCompany company) throws Exception {
		return dao.findByCompany(company.getId());
	}

	/**
	 * Find roots.
	 *
	 * @param hostingCompany the hosting company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyTemplates> findRoots(HostingCompany hostingCompany) throws Exception {
		return dao.findRoots(hostingCompany.getId());
	}

	/**
	 * Find roots not in B type.
	 *
	 * @param hostingCompany the hosting company
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyTemplates> findRootsNotInBType(HostingCompany hostingCompany)
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
	public List<HostingCompanyTemplates> findByParent(HostingCompany hostingCompany, HostingCompanyTemplates parent) throws Exception {
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
	public void startBuild(TreeNode leftRoot, Users user, HostingCompany hostingCompany, boolean expand, List<HostingCompanyTemplates> roots) throws Exception {
		for (HostingCompanyTemplates template : roots) {
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
	private void createTree(DefaultTreeNode defaultTreeNode, HostingCompanyTemplates parent, HostingCompany hostingCompany)
			throws Exception {
		List<HostingCompanyTemplates> templates = findByParent(hostingCompany, parent);
		for (HostingCompanyTemplates template : templates) {
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
	private void uniqueId(HostingCompanyTemplates cd) throws Exception {
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
	public void buildPath(HostingCompanyTemplates template, List<IDataEntity> arrayList) throws Exception {
		List<HostingCompanyTemplates> l = findByParent(template.getHostingCompany(), template);
		for (HostingCompanyTemplates template2 : l) {
			arrayList.add(template2);
			buildPath(template2, arrayList);
		}
	}
	
//	Concept of business type not required
//	public void startBuild(TreeNode rightRoot, Users user, HostingCompany hostingCompany, boolean expand,
//			BusinessType businessType, List<HostingCompanyTemplates> roots) throws Exception {
//		// List<Template> roots = findRoots(hostingCompany,businessType);
//		for (HostingCompanyTemplates template : roots) {
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
//	private void createTree(DefaultTreeNode defaultTreeNode, HostingCompanyTemplates parent, HostingCompany hostingCompany) throws Exception {
//		List<HostingCompanyTemplates> templates = findByParent(hostingCompany, parent, businessType);
//		for (HostingCompanyTemplates template : templates) {
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
//	private List<HostingCompanyTemplates> findByParent(HostingCompany hostingCompany, HostingCompanyTemplates parent, BusinessType businessType)
//			throws Exception {
//		return dao.findByParent(hostingCompany.getId(), parent.getId(), businessType.getId());
//	}
	
//	Concept of business type not required
//	public List<HostingCompanyTemplates> findRoots(HostingCompany hostingCompany, BusinessType businessType) throws Exception {
//		return dao.findRoots(hostingCompany.getId(), businessType.getId());
//	}

	/**
 * Creates the.
 *
 * @param drag the drag
 * @param drop the drop
 * @throws Exception the exception
 */
public void create(HostingCompanyTemplates drag, HostingCompanyTemplates drop) throws Exception {
		List<HostingCompanyTemplates> dupList = new ArrayList<HostingCompanyTemplates>();
		dupList.add(drag);
		duplicate(dupList, drag);

		List<IDataEntity> list = new ArrayList<IDataEntity>();
		HostingCompanyTemplates t = null;
		for (HostingCompanyTemplates cf : dupList) {
			t = (HostingCompanyTemplates) cf.clone();
			t.setParentTemplate(null);
//			t.setBusinessType(businessType);
			t.setOriginalTemplate(cf);
			t.setId(null);

			t.setUniqueId(null);
			list.add(t);
		}
		dao.createBatch(list);

		for (IDataEntity iDataEntity : list) {
			doParent(((HostingCompanyTemplates) iDataEntity), dupList, list);
//			GenericAuditLog.log(auditlog, "targetKey", ((HostingCompanyTemplates) iDataEntity).getTitle(), "taskType",
//					TaskTypeEnum.Admin.getID(), "Create Business Type Template",
//					((HostingCompanyTemplates) iDataEntity).getTitle() + " Business Type Template created.");
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
	private void doParent(HostingCompanyTemplates template, List<HostingCompanyTemplates> dupList, List<IDataEntity> newList) throws Exception {
		for (HostingCompanyTemplates cf : dupList) {
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
	private HostingCompanyTemplates findNewParent(HostingCompanyTemplates newTemplate, HostingCompanyTemplates oldParent, List<IDataEntity> newList) {
		for (IDataEntity cf : newList) {
			if (((HostingCompanyTemplates) cf).hsCompareToOld(oldParent)) {
				return (HostingCompanyTemplates) cf;
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
	private void duplicate(List<HostingCompanyTemplates> dupList, HostingCompanyTemplates drag) throws Exception {
		List<HostingCompanyTemplates> l = findByParent(drag.getHostingCompany(), drag);
		for (HostingCompanyTemplates template2 : l) {
			dupList.add(template2);
			duplicate(dupList, template2);
		}

	}
	
//	concept of business type not required
//	public List<HostingCompanyTemplates> findByBusinessType(HostingCompany company, BusinessType businessType) throws Exception {
//		return dao.findByBusinessType(company.getId(), businessType.getId());
//	}
}
