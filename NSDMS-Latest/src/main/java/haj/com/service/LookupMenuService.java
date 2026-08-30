package haj.com.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import haj.com.bean.LookupMenuBean;
import haj.com.constants.HAJConstants;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class LookupMenuService.
 */
public class LookupMenuService extends AbstractService {
   
	/** The Constant path. */
	private static final String path = HAJConstants.APP_PATH;
	
	/** The Constant sub_path. */
	private static final String sub_path = "admin/lookup/";
	
	
	
	/**
	 * Lookup tables.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<String> lookupTables() throws Exception {
		List<String> l = new ArrayList<String>();
		 Files.list(Paths.get(path+sub_path))
	        .filter(Files::isRegularFile)
	        .forEach(a-> {
	        	    l.add(a.toString());
	        });
		return l;
	}
	
	/**
	 * Prep menu.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<LookupMenuBean> prepMenu() throws Exception {
		List<LookupMenuBean>  m = new ArrayList<LookupMenuBean>();
		 (lookupTables().stream().sorted(Comparator.comparing(n->n.toString())).collect(Collectors.toList())).forEach(a->{
			//Windows Dev fix
			if(a.contains("\\"))
				m.add(new LookupMenuBean(processHeading(a), a.substring(a.lastIndexOf('\\')+1).replaceAll("xhtml", "jsf")));
			else
				m.add(new LookupMenuBean(processHeading(a), a.substring(a.lastIndexOf('/')+1).replaceAll("xhtml", "jsf")));
		});
		return m;
	}
	
	/**
	 * Process heading.
	 *
	 * @param path the path
	 * @return the string
	 */
	private String processHeading(String path)  {
		try {
			String content = new String(Files.readAllBytes(Paths.get(path)));
			if (content.contains("<h1>")) {
				String name = content.substring(content.indexOf("<h1>")+4);
				name = name.substring(0,name.indexOf("</h1>"));
				return name;
			}
	
		} catch (IOException e) {
			logger.fatal(e);
			return path.substring(path.lastIndexOf('/'));
		}
	return "";
	}
	
}
