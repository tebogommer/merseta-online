package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.entity.ConfigDoc;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ConfigDocService;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigDocUI.
 */
@ManagedBean(name = "configdocUI")
@ViewScoped
public class ConfigDocUI extends AbstractUI {

	/** The service. */
	private ConfigDocService service = new ConfigDocService();
	
	/** The configdoc list. */
	private List<ConfigDoc> configdocList = null;
	
	/** The left root. */
	private TreeNode leftRoot;
	
	/** The selected node. */
	private TreeNode selectedNode;

	/** The document type. */
	private CompanyUserTypeEnum documentType;

	/** The configdoc. */
	private ConfigDoc configdoc = null;
	
	/** The child configdoc. */
	private ConfigDoc childConfigdoc = null;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all ConfigDoc and prepare a for a create of a new
	 * ConfigDoc.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see ConfigDoc
	 */
	private void runInit() throws Exception {
		prepareNew();
		configdocInfo();
	}

	/**
	 * Get all ConfigDoc for data table.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see ConfigDoc
	 */
	public void configdocInfo() throws Exception {

		if (getSessionUI().getHostingCompany() != null) {
			this.configdocList = service.allRootConfigDoc(getSessionUI().getHostingCompany());
		} else {
			this.configdocList = service.allRootConfigDoc();
		}

		createDocuments();

	}

	/**
	 * Insert ConfigDoc into database.
	 *
	 * @author TechFinium
	 * @see ConfigDoc
	 */
	public void configdocInsert() {
		try {
			service.create(this.configdoc);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			configdocInfo();

		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ConfigDoc in database.
	 *
	 * @author TechFinium
	 * @see ConfigDoc
	 */
	public void configdocUpdate() {
		try {
			service.update(this.configdoc);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			configdocInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ConfigDoc from database.
	 *
	 * @author TechFinium
	 * @see ConfigDoc
	 */
	public void configdocDelete() {
		try {
			if (selectedNode != null) {
				service.delete((ConfigDoc) selectedNode.getData());
				prepareNew();
				configdocInfo();
				super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ConfigDoc.
	 *
	 * @author TechFinium
	 * @see ConfigDoc
	 */
	public void prepareNew() {
		configdoc = new ConfigDoc();
		configdoc.setHostingCompany(getSessionUI().getHostingCompany());
	}

	/**
	 * Prep for add.
	 */
	public void prepForAdd() {
		childConfigdoc = new ConfigDoc();
		childConfigdoc.setHostingCompany(getSessionUI().getHostingCompany());
		if (selectedNode != null) {
			childConfigdoc.setParent((ConfigDoc) selectedNode.getData());
			if (childConfigdoc.getParent().getParent() == null) {
				super.runClientSideExecute("PF('addDialog').show()");
				super.runClientSideUpdate("addForm");
			} else {
				addErrorMessage(super.getEntryLanguage("you.cant.add.documents.under.documents"));
			}
		}

	}

	/**
	 * Prep for edit.
	 */
	public void prepForEdit() {
		if (selectedNode != null && selectedNode.getData() instanceof ConfigDoc) {
			childConfigdoc = ((ConfigDoc) selectedNode.getData());
			if (childConfigdoc != null) {
				if (childConfigdoc.getCompanyOrUserDocument() != null) {
					documentType = childConfigdoc.getCompanyOrUserDocument();
				}
				super.runClientSideExecute("PF('addDialog').show()");
			} else {
				addErrorMessage(super.getEntryLanguage("Please edit a document"));
			}
		} else {
			addWarningMessage("You can only edit documents");
		}

	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ConfigDoc> complete(String desc) {
		List<ConfigDoc> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the config doc list.
	 *
	 * @return the config doc list
	 */
	public List<ConfigDoc> getConfigDocList() {
		return configdocList;
	}

	/**
	 * Sets the config doc list.
	 *
	 * @param configdocList the new config doc list
	 */
	public void setConfigDocList(List<ConfigDoc> configdocList) {
		this.configdocList = configdocList;
	}

	/**
	 * Gets the configdoc.
	 *
	 * @return the configdoc
	 */
	public ConfigDoc getConfigdoc() {
		return configdoc;
	}

	/**
	 * Sets the configdoc.
	 *
	 * @param configdoc the new configdoc
	 */
	public void setConfigdoc(ConfigDoc configdoc) {
		this.configdoc = configdoc;
	}

	/**
	 * Creates the documents.
	 *
	 * @throws Exception the exception
	 */
	private void createDocuments() throws Exception {
		if (configdocList != null || configdocList.size() != 0) {
			ConfigDoc root = new ConfigDoc();
			root.setName("Documents");
			root.setConfigDocProcess(ConfigDocProcessEnum.SDF);

			leftRoot = new DefaultTreeNode(root, null);
			leftRoot.setExpanded(true);
			for (ConfigDoc configDoc : configdocList) {
				DefaultTreeNode d = new DefaultTreeNode("info", configDoc, leftRoot);
				d.setExpanded(true);
				if (configDoc.getDependants() != null) {
					if (configDoc.getConfigDocProcess() != ConfigDocProcessEnum.WSP) {
						DefaultTreeNode companyNode = new DefaultTreeNode("Company", d);
						companyNode.setExpanded(true);
						DefaultTreeNode userNode = new DefaultTreeNode("User", d);
						userNode.setExpanded(true);
						for (ConfigDoc child : configDoc.getDependants()) {
							if (child.getCompanyOrUserDocument() == CompanyUserTypeEnum.Company) {
								new DefaultTreeNode("documentCompany", child, companyNode);
							} else {
								new DefaultTreeNode("documentUser", child, userNode);
							}
						}
					} else {
						for (ConfigDoc child : configDoc.getDependants()) {
							new DefaultTreeNode("documentCompany", child, d);
						}
					}

				}
			}
		}
	}

	/**
	 * Gets the configdoc list.
	 *
	 * @return the configdoc list
	 */
	public List<ConfigDoc> getConfigdocList() {
		return configdocList;
	}

	/**
	 * Sets the configdoc list.
	 *
	 * @param configdocList the new configdoc list
	 */
	public void setConfigdocList(List<ConfigDoc> configdocList) {
		this.configdocList = configdocList;
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
	 * On node select.
	 *
	 * @param event the event
	 */
	public void onNodeSelect(NodeSelectEvent event) {
		this.configdoc = (ConfigDoc) event.getTreeNode().getData();
	}

	/**
	 * Gets the child configdoc.
	 *
	 * @return the child configdoc
	 */
	public ConfigDoc getChildConfigdoc() {
		return childConfigdoc;
	}

	/**
	 * Sets the child configdoc.
	 *
	 * @param childConfigdoc the new child configdoc
	 */
	public void setChildConfigdoc(ConfigDoc childConfigdoc) {
		this.childConfigdoc = childConfigdoc;
	}

	/**
	 * Configdoc child insert.
	 */
	public void configdocChildInsert() {
		try {
			this.childConfigdoc.setCompanyOrUserDocument(documentType);
			service.create(this.childConfigdoc);
			prepareNew();
			prepForAdd();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			configdocInfo();
			super.runClientSideExecute("PF('addDialog').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Configdoc child S ave.
	 */
	public void configdocChildSAve() {
		try {
			this.childConfigdoc.setCompanyOrUserDocument(documentType);
			service.update(this.childConfigdoc);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			configdocInfo();
			super.runClientSideExecute("PF('addDialog').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the document type.
	 *
	 * @return the document type
	 */
	public CompanyUserTypeEnum getDocumentType() {
		return documentType;
	}

	/**
	 * Sets the document type.
	 *
	 * @param documentType the new document type
	 */
	public void setDocumentType(CompanyUserTypeEnum documentType) {
		this.documentType = documentType;
	}

}
