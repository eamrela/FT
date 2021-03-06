package com.vodafone.ft.controllers;



import com.vodafone.ft.entities.SubDomain;
import com.vodafone.ft.controllers.util.JsfUtil;
import com.vodafone.ft.controllers.util.JsfUtil.PersistAction;
import com.vodafone.ft.beans.SubDomainFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@Named("subDomainController")
@SessionScoped
public class SubDomainController implements Serializable {

    @EJB
    private com.vodafone.ft.beans.SubDomainFacade ejbFacade;
    private List<SubDomain> items = null;
    private SubDomain selected;
    
    @Inject
    private UsersController usersController;

    public SubDomainController() {
    }

    public SubDomain getSelected() {
        return selected;
    }

    public void setSelected(SubDomain selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SubDomainFacade getFacade() {
        return ejbFacade;
    }

    public SubDomain prepareCreate() {
        selected = new SubDomain();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SubDomainCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SubDomainUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SubDomainDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SubDomain> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public SubDomain getSubDomain(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<SubDomain> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SubDomain> getItemsAvailableSelectOne() {
        if(usersController.getLoggedInUserRole().contains("SYS")){
        return getFacade().findAll();
        }else{
          return getFacade().findExtaWork();
        }
    }

    @FacesConverter(forClass = SubDomain.class)
    public static class SubDomainControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SubDomainController controller = (SubDomainController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "subDomainController");
            return controller.getSubDomain(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SubDomain) {
                SubDomain o = (SubDomain) object;
                return getStringKey(o.getSubdomainName());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SubDomain.class.getName()});
                return null;
            }
        }

    }

}
