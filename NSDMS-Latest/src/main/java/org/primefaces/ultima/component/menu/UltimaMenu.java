/*
 * Copyright 2009-2016 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.ultima.component.menu;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.primefaces.component.api.Widget;
import org.primefaces.component.menu.AbstractMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class UltimaMenu.
 */
@ListenerFor(sourceClass = UltimaMenu.class, systemEventClass = PostAddToViewEvent.class)
public class UltimaMenu extends AbstractMenu implements Widget,ComponentSystemEventListener {

    /** The Constant COMPONENT_TYPE. */
    public static final String COMPONENT_TYPE = "org.primefaces.component.UltimaMenu";
    
    /** The Constant COMPONENT_FAMILY. */
    public static final String COMPONENT_FAMILY = "org.primefaces.component";
    
    /** The Constant DEFAULT_RENDERER. */
    private static final String DEFAULT_RENDERER = "org.primefaces.component.UltimaMenuRenderer";
    
    /** The Constant LEGACY_RESOURCES. */
    private static final String[] LEGACY_RESOURCES = new String[]{"primefaces.css","jquery/jquery.js","jquery/jquery-plugins.js","primefaces.js"};
    
    /** The Constant MODERN_RESOURCES. */
    private static final String[] MODERN_RESOURCES = new String[]{"components.css","jquery/jquery.js","jquery/jquery-plugins.js","core.js"};
    
    /**
     * The Enum PropertyKeys.
     */
    protected enum PropertyKeys {

        /** The widget var. */
        widgetVar, /** The model. */
 model, /** The style. */
 style, /** The style class. */
 styleClass;

        /** The to string. */
        String toString;

        /**
         * Instantiates a new property keys.
         *
         * @param toString the to string
         */
        PropertyKeys(String toString) {
            this.toString = toString;
        }

        /**
         * Instantiates a new property keys.
         */
        PropertyKeys() {
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    /**
     * Instantiates a new ultima menu.
     */
    public UltimaMenu() {
        setRendererType(DEFAULT_RENDERER);
    }

    /* (non-Javadoc)
     * @see javax.faces.component.UIPanel#getFamily()
     */
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    /**
     * Gets the widget var.
     *
     * @return the widget var
     */
    public java.lang.String getWidgetVar() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }

    /**
     * Sets the widget var.
     *
     * @param _widgetVar the new widget var
     */
    public void setWidgetVar(java.lang.String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }

    /* (non-Javadoc)
     * @see org.primefaces.component.menu.AbstractMenu#getModel()
     */
    public org.primefaces.model.menu.MenuModel getModel() {
        return (org.primefaces.model.menu.MenuModel) getStateHelper().eval(PropertyKeys.model, null);
    }

    /**
     * Sets the model.
     *
     * @param _model the new model
     */
    public void setModel(org.primefaces.model.menu.MenuModel _model) {
        getStateHelper().put(PropertyKeys.model, _model);
    }

    /**
     * Gets the style.
     *
     * @return the style
     */
    public java.lang.String getStyle() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.style, null);
    }

    /**
     * Sets the style.
     *
     * @param _style the new style
     */
    public void setStyle(java.lang.String _style) {
        getStateHelper().put(PropertyKeys.style, _style);
    }

    /**
     * Gets the style class.
     *
     * @return the style class
     */
    public java.lang.String getStyleClass() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass, null);
    }

    /**
     * Sets the style class.
     *
     * @param _styleClass the new style class
     */
    public void setStyleClass(java.lang.String _styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, _styleClass);
    }

    /* (non-Javadoc)
     * @see org.primefaces.component.api.Widget#resolveWidgetVar()
     */
    public String resolveWidgetVar() {
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");

        if (userWidgetVar != null) {
            return userWidgetVar;
        } else {
            return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
        }
    }
    
    /* (non-Javadoc)
     * @see javax.faces.component.UIComponent#processEvent(javax.faces.event.ComponentSystemEvent)
     */
    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        if(event instanceof PostAddToViewEvent) {
            FacesContext context = getFacesContext();
            UIViewRoot root = context.getViewRoot();
            
            boolean isPrimeConfig;
            try {
                isPrimeConfig = Class.forName("org.primefaces.config.PrimeConfiguration") != null;
            } catch (ClassNotFoundException e) {
                isPrimeConfig = false;
            }

            String[] resources = (isPrimeConfig) ? MODERN_RESOURCES : LEGACY_RESOURCES;

            for(String res : resources) {
                UIComponent component = context.getApplication().createComponent(UIOutput.COMPONENT_TYPE);
                if(res.endsWith("css"))
                    component.setRendererType("javax.faces.resource.Stylesheet");
                else if(res.endsWith("js"))
                    component.setRendererType("javax.faces.resource.Script");

                component.getAttributes().put("library", "primefaces");
                component.getAttributes().put("name", res);

                root.addComponentResource(context, component);
            }
        }
    }
}
