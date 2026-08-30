package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.constants.HAJConstants;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyTemplates;
import haj.com.entity.enums.TemplateTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyService;
import haj.com.service.HostingCompanyTemplatesService;

// TODO: Auto-generated Javadoc
/**
 * The Class TemplateViewUI.
 */
@ManagedBean(name = "templateViewUI")
@ViewScoped
public class TemplateViewUI extends AbstractUI {

	/**  Entity Layer. */
	private HostingCompany hostingCompany;
	
	/** The selected template. */
	private HostingCompanyTemplates selectedTemplate;

	/**  Service Layer. */
	private DocService docService = new DocService();
	
	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	
	/** The hosting company templates service. */
	private HostingCompanyTemplatesService hostingCompanyTemplatesService = new HostingCompanyTemplatesService();

	/**  TreeNode. */
	private TreeNode leftRoot;
	
	/** The selected node. */
	private TreeNode selectedNode;

	/**  Lists. */
	private List<Doc> documentList;

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
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
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
		// populates runit information
		defaultInformation();
		// sets tree view
		leftRoot = buildTree();
	}

	/**
	 * Default information.
	 *
	 * @throws Exception the exception
	 */
	private void defaultInformation() throws Exception {
		expand = false;
		hostingCompany = hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA);
	}

	/**
	 * Builds the tree for users to view all information and documents.
	 *
	 * @return TreeNode
	 * @throws Exception the exception
	 */
	private TreeNode buildTree() throws Exception {
		HostingCompanyTemplates t = new HostingCompanyTemplates();
		t.setTitle("Templates");
		t.setTemplateType(TemplateTypeEnum.Chapter);
		TreeNode leftRoot = new DefaultTreeNode(t, null);
		leftRoot.setExpanded(expand);
		hostingCompanyTemplatesService.startBuild(leftRoot, getSessionUI().getActiveUser(), hostingCompany, expand,
				hostingCompanyTemplatesService.findRoots(hostingCompany));
		return leftRoot;
	}

	/**
	 * When user clicks on expand option.
	 */
	public void expandSelect() {
		try {
			leftRoot = buildTree();
//			showDocForm = false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * When user clicks on expand option.
	 */
	public void dragDrop() {
		try {
			leftRoot = buildTree();
			addWarningMessage("You can not re-order or change the folders structure!");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * When node is selected.
	 *
	 * @param event the event
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		try {
			this.selectedTemplate = (HostingCompanyTemplates) event.getTreeNode().getData();
			if (this.selectedTemplate.getTemplateType() == TemplateTypeEnum.Document) {
				showDocForm = true;
				findDocs();
			} else {
				showDocForm = false;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Locate documents by selected template.
	 *
	 * @throws Exception the exception
	 */
	private void findDocs() throws Exception {
		this.documentList = docService.searchByHostingCompanyTemplate(selectedTemplate);
	}

	/**
	 *  Getters and Setters.
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
	 * Gets the left root.
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
	 * Gets the selected template.
	 *
	 * @return the selected template
	 */
	public HostingCompanyTemplates getSelectedTemplate() {
		return selectedTemplate;
	}

	/**
	 * Sets the selected template.
	 *
	 * @param selectedTemplate the new selected template
	 */
	public void setSelectedTemplate(HostingCompanyTemplates selectedTemplate) {
		this.selectedTemplate = selectedTemplate;
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
	 * Gets the document list.
	 *
	 * @return the document list
	 */
	public List<Doc> getDocumentList() {
		return documentList;
	}

	/**
	 * Sets the document list.
	 *
	 * @param documentList the new document list
	 */
	public void setDocumentList(List<Doc> documentList) {
		this.documentList = documentList;
	}

}
