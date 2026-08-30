package haj.com.framework;

import java.io.Serializable;

public interface AbstractUIInterface extends Serializable {
	
	 default void callBackMethod(Object object) {}
}
