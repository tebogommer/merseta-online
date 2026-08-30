package haj.com.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;

public class MailDef implements Serializable{

	private MailEnum mailEnum;
	private Map<MailTagsEnum,Object> tagMap;
	
	public static MailDef instance(Object... tag) {
		MailDef m = new MailDef();
	    m.tagMap = new HashMap<MailTagsEnum,Object>();
		m.prepare(tag);
		return m;
	}
	
	 public void prepare(Object... tags) {
	     if (tags!=null && tags.length>0) {
	    	    if (tags[0] instanceof MailEnum) {
	    	      	this.mailEnum = (MailEnum)tags[0];
	    	    }
	         for (int i = 2 ; i < tags.length ; i += 2) {
	           if (tags[i-1]!=null  )  {
	             tagMap.put((MailTagsEnum)tags[i-1], tags[i]);
	           }
	         }
	    }
	 }

	public MailEnum getMailEnum() {
		return mailEnum;
	}

	public Map<MailTagsEnum, Object> getTagMap() {
		return tagMap;
	}
	
}
