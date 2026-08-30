package haj.com.bean;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

// TODO: Auto-generated Javadoc
/**
 * The Class TechfiniumTreeNode.
 */
public class TechfiniumTreeNode extends DefaultTreeNode {
	
	
    /** The style. */
    private String style;

	/**
	 * Instantiates a new techfinium tree node.
	 */
	public TechfiniumTreeNode() {
       super();
    }
    
    /**
     * Instantiates a new techfinium tree node.
     *
     * @param data the data
     */
    public TechfiniumTreeNode(Object data) {
       super(data);
    }

	/**
	 * Instantiates a new techfinium tree node.
	 *
	 * @param data the data
	 * @param parent the parent
	 */
	public TechfiniumTreeNode(Object data, TreeNode parent) {
	  super(data,parent);
	}
	
	/**
	 * Instantiates a new techfinium tree node.
	 *
	 * @param type the type
	 * @param data the data
	 * @param parent the parent
	 */
	public TechfiniumTreeNode(String type, Object data, TreeNode parent) {
	  super(type,data,parent);
	}
    
    
	/**
	 * Gets the style.
	 *
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Sets the style.
	 *
	 * @param style the new style
	 */
	public void setStyle(String style) {
		this.style = style;
	}
}
