package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.constants.HAJConstants;
import haj.com.entity.Doc;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyTemplates;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.TemplateTypeEnum;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.DocumentTrackerService;
import haj.com.service.HostingCompanyService;
import haj.com.service.HostingCompanyTemplatesService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyTemplatesUI.
 */
@ManagedBean(name = "hostingCompanyTemplatesUI")
@ViewScoped
public class HostingCompanyTemplatesUI extends AbstractUI {

	/**  Entity Layer. */
	private Doc doc;
	
	/** The tdoc. */
	private Doc tdoc;
	
	/** The template. */
	public HostingCompanyTemplates template = null;
	
	/** The hosting company. */
	private HostingCompany hostingCompany;

	/**  Service Layer. */
	private DocService docService = new DocService();
	
	/** The document tracker service. */
	private DocumentTrackerService documentTrackerService = new DocumentTrackerService();
	
	/** The service. */
	private HostingCompanyTemplatesService service = new HostingCompanyTemplatesService();
	
	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();

	/**  Lists. */
	private List<HostingCompanyTemplates> templateList = null;
	
	/** The templatefiltered list. */
	private List<HostingCompanyTemplates> templatefilteredList = null;
	
	/** The docs. */
	private List<Doc> docs;
	
	/** The docsfiltered. */
	private List<Doc> docsfiltered;
	
	/** The document trackers. */
	private List<DocumentTracker> documentTrackers;

	/**  TreeNode. */
	private TreeNode leftRoot;
	
	/** The selected node. */
	private TreeNode selectedNode;

	/**  Vars. */
	private boolean expand = false;
	
	/** The show doc form. */
	private boolean showDocForm = false;

	/**  Manage Property. */
	@ManagedProperty(value = "#{uploadDocUI}")
	private UploadDocUI uploadDocUI;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		service = new HostingCompanyTemplatesService(getSessionUI().getMap());
		hostingCompany = hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA);
		prepareNewTemplate();
		if (this.tdoc == null)
			this.tdoc = new Doc();
		templateInfo();
	}

	/**
	 * Template info.
	 */
	public void templateInfo() {
		try {
			leftRoot = buildTree();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Builds the tree.
	 *
	 * @return the tree node
	 * @throws Exception the exception
	 */
	private TreeNode buildTree() throws Exception {
		HostingCompanyTemplates t = new HostingCompanyTemplates();
		t.setTitle("Templates");
		t.setTemplateType(TemplateTypeEnum.Chapter);
		TreeNode leftRoot = new DefaultTreeNode(t, null);
		leftRoot.setExpanded(expand);
		service.startBuild(leftRoot, getSessionUI().getActiveUser(), hostingCompany, expand,
				service.findRoots(hostingCompany));
		return leftRoot;
	}

	/**
	 * Template insert.
	 */
	public void templateInsert() {
		try {
			service.create(this.template);
			addInfoMessage("Insert successful");
			templateInfo();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Template delete.
	 */
	public void templateDelete() {
		try {
			service.delete(this.template);
			templateInfo();
			super.addWarningMessage("Row deleted.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare new template.
	 */
	public void prepareNewTemplate() {
		template = new HostingCompanyTemplates();
		template.setUser(getSessionUI().getUser());
		template.setHostingCompany(hostingCompany);
	}

	/**
	 * Template update.
	 */
	public void templateUpdate() {
		try {
			service.update(this.template);
			addInfoMessage("Update successful");
			templateInfo();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<HostingCompanyTemplates> complete(String desc) {
		List<HostingCompanyTemplates> l = null;
		try {
			l = service.findByName(desc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Ready for add.
	 */
	public void readyForAdd() {
		if (selectedNode != null) {
			prepareNewTemplate();
			this.template.setParentTemplate((HostingCompanyTemplates) selectedNode.getData());
			if (this.template.getParentTemplate() != null
					&& this.template.getParentTemplate().getTemplateType() == TemplateTypeEnum.Document) {
				if (this.template.getParentTemplate().getParentTemplate() != null) {
					this.template.setParentTemplate(this.template.getParentTemplate().getParentTemplate());
				}
			}
			super.runClientSideExecute("PF('dlg').show()");
			super.runClientSideUpdate("templateInsForm");
		}
	}

	/**
	 * Delete node.
	 */
	public void deleteNode() {
		try {
			service.delete(service.findByKey((HostingCompanyTemplates) selectedNode.getData()));
			templateInfo();
			addInfoMessage("Delete Complete");
		} catch (org.hibernate.exception.ConstraintViolationException cv) {
			addErrorMessage("You can't delete since some documents are linked to this structure.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Ready for edit.
	 */
	public void readyForEdit() {
		try {
			if (selectedNode != null) {
				this.template = (HostingCompanyTemplates) selectedNode.getData();
				super.runClientSideExecute("PF('dlgUpd').show()");
				super.runClientSideUpdate("templateUpdForm");

			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * On node select.
	 *
	 * @param event the event
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		this.template = (HostingCompanyTemplates) event.getTreeNode().getData();
		if (this.template.getTemplateType() == TemplateTypeEnum.Document) {
			showDocForm = true;
			initDoc();
			findDocs();
		} else {
			showDocForm = false;
		}
		super.runClientSideUpdate("docForm");
	}

	/**
	 * Inits the doc.
	 */
	private void initDoc() {
		this.doc = new Doc();
		this.doc.setHostingCompanyTemplates(this.template);
		this.doc.setUsers(getSessionUI().getActiveUser());
	}

	/**
	 * Safe file.
	 *
	 * @param event the event
	 */
	public void safeFile(FileUploadEvent event) {
		try {
			docService.save(doc, event.getFile().getContents(), event.getFile().getFileName(), getSessionUI().getUser(),
					this.docs);
			addInfoMessage("Your file has been uploaded successfully!");
			initDoc();
			findDocs();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Find docs.
	 */
	private void findDocs() {
		try {
			this.docs = docService.searchByHostingCompanyTemplate(template);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Download.
	 */
	public void download() {
		try {
			DocumentTrackerService.create(getSessionUI().getUser(), doc, DocumentTrackerEnum.Downloaded);
			Faces.sendFile(doc.getData(), GenericUtility.buidFileName(doc), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Show history.
	 */
	public void showHistory() {
		try {
			this.documentTrackers = documentTrackerService.byRoot(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Builds the stream content.
	 */
	public void buildStreamContent() {
		getSessionUI().setSelDoc(doc);
		try {
			DocumentTrackerService.create(getSessionUI().getUser(), doc, DocumentTrackerEnum.Viewed);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	/**
	 * On drag drop.
	 *
	 * @param event the event
	 */
	public void onDragDrop(TreeDragDropEvent event) {
		try {
			TreeNode dragNode = event.getDragNode();
			TreeNode dropNode = event.getDropNode();
			// int dropIndex = event.getDropIndex();
			HostingCompanyTemplates drag = service.findByKey((HostingCompanyTemplates) dragNode.getData());
			HostingCompanyTemplates drop = service.findByKey((HostingCompanyTemplates) dropNode.getData());
			if (drop.getTemplateType() == TemplateTypeEnum.Document) {
				addErrorMessage("You can't add anything under a document");
				buildTree();
			} else {
				drag.setParentTemplate(drop);
				service.update(drag);
				addInfoMessage("Templates Moved");
				buildTree();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// public void deleteDoc(Integer pos) {
	// try {
	//
	// // System.out.println(getSessionUI().getUser());
	// Doc auditDocs = null;
	// if (doc.getVersionNo() == 1) {
	// auditDocs = docService.search(template).get(pos);
	// }
	// uploadDocUI.deleteDoc(doc, auditDocs);
	// this.template = service.findByKey(this.template.getId());
	// initDoc();
	// findDocs();
	// super.runClientSideUpdate("docForm");;
	// addInfoMessage("Document Successfully Deleted");
	// } catch (Exception e) {
	// addErrorMessage(e.getMessage(), e);
	// }
	// }

	/**
	 *  Getters and setters.
	 *
	 * @return the left root
	 */
	public TreeNode getLeftRoot() {
		return leftRoot;
	}

	/**
	 * Sets the left root.
	 *
	 * @param leftRoot the new left root
	 */
	public void setLeftRoot(TreeNode leftRoot) {
		this.leftRoot = leftRoot;
	}

	/**
	 * Gets the selected node.
	 *
	 * @return the selected node
	 */
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	/**
	 * Sets the selected node.
	 *
	 * @param selectedNode the new selected node
	 */
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * Checks if is expand.
	 *
	 * @return true, if is expand
	 */
	public boolean isExpand() {
		return expand;
	}

	/**
	 * Sets the expand.
	 *
	 * @param expand the new expand
	 */
	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	/**
	 * Gets the document trackers.
	 *
	 * @return the document trackers
	 */
	public List<DocumentTracker> getDocumentTrackers() {
		return documentTrackers;
	}

	/**
	 * Sets the document trackers.
	 *
	 * @param documentTrackers the new document trackers
	 */
	public void setDocumentTrackers(List<DocumentTracker> documentTrackers) {
		this.documentTrackers = documentTrackers;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * Checks if is show doc form.
	 *
	 * @return true, if is show doc form
	 */
	public boolean isShowDocForm() {
		return showDocForm;
	}

	/**
	 * Sets the show doc form.
	 *
	 * @param showDocForm the new show doc form
	 */
	public void setShowDocForm(boolean showDocForm) {
		this.showDocForm = showDocForm;
	}

	/**
	 * Gets the tdoc.
	 *
	 * @return the tdoc
	 */
	public Doc getTdoc() {
		return tdoc;
	}

	/**
	 * Sets the tdoc.
	 *
	 * @param tdoc the new tdoc
	 */
	public void setTdoc(Doc tdoc) {
		this.tdoc = tdoc;
	}

	/**
	 * Gets the upload doc UI.
	 *
	 * @return the upload doc UI
	 */
	public UploadDocUI getUploadDocUI() {
		return uploadDocUI;
	}

	/**
	 * Sets the upload doc UI.
	 *
	 * @param uploadDocUI the new upload doc UI
	 */
	public void setUploadDocUI(UploadDocUI uploadDocUI) {
		this.uploadDocUI = uploadDocUI;
	}

	/**
	 * Gets the docs.
	 *
	 * @return the docs
	 */
	public List<Doc> getDocs() {
		return docs;
	}

	/**
	 * Sets the docs.
	 *
	 * @param docs the new docs
	 */
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	/**
	 * Gets the docsfiltered.
	 *
	 * @return the docsfiltered
	 */
	public List<Doc> getDocsfiltered() {
		return docsfiltered;
	}

	/**
	 * Sets the docsfiltered.
	 *
	 * @param docsfiltered the new docsfiltered
	 */
	public void setDocsfiltered(List<Doc> docsfiltered) {
		this.docsfiltered = docsfiltered;
	}

	/**
	 * Gets the template list.
	 *
	 * @return the template list
	 */
	public List<HostingCompanyTemplates> getTemplateList() {
		return templateList;
	}

	/**
	 * Sets the template list.
	 *
	 * @param templateList the new template list
	 */
	public void setTemplateList(List<HostingCompanyTemplates> templateList) {
		this.templateList = templateList;
	}

	/**
	 * Gets the templatefiltered list.
	 *
	 * @return the templatefiltered list
	 */
	public List<HostingCompanyTemplates> getTemplatefilteredList() {
		return templatefilteredList;
	}

	/**
	 * Sets the templatefiltered list.
	 *
	 * @param templatefilteredList the new templatefiltered list
	 */
	public void setTemplatefilteredList(List<HostingCompanyTemplates> templatefilteredList) {
		this.templatefilteredList = templatefilteredList;
	}

	/**
	 * Gets the hosting company.
	 *
	 * @return the hosting company
	 */
	public HostingCompany getHostingCompany() {
		return hostingCompany;
	}

	/**
	 * Sets the hosting company.
	 *
	 * @param hostingCompany the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
	}

	/**
	 * Gets the template.
	 *
	 * @return the template
	 */
	public HostingCompanyTemplates getTemplate() {
		return template;
	}

	/**
	 * Sets the template.
	 *
	 * @param template the new template
	 */
	public void setTemplate(HostingCompanyTemplates template) {
		this.template = template;
	}
}
