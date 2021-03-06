package com.vodafone.ft.controllers;



import com.vodafone.ft.entities.ApprovalStatus;
import com.vodafone.ft.controllers.util.JsfUtil;
import com.vodafone.ft.controllers.util.JsfUtil.PersistAction;
import com.vodafone.ft.beans.ApprovalStatusFacade;

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

@Named("approvalStatusController")
@SessionScoped
public class ApprovalStatusController implements Serializable {

    @EJB
    private com.vodafone.ft.beans.ApprovalStatusFacade ejbFacade;
    private List<ApprovalStatus> items = null;
    private ApprovalStatus selected;

    public ApprovalStatusController() {
    }

    public ApprovalStatus getSelected() {
        return selected;
    }

    public void setSelected(ApprovalStatus selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ApprovalStatusFacade getFacade() {
        return ejbFacade;
    }

    public ApprovalStatus prepareCreate() {
        selected = new ApprovalStatus();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ApprovalStatusCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ApprovalStatusUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ApprovalStatusDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ApprovalStatus> getItems() {
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

    public ApprovalStatus getApprovalStatus(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<ApprovalStatus> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ApprovalStatus> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ApprovalStatus.class)
    public static class ApprovalStatusControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ApprovalStatusController controller = (ApprovalStatusController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "approvalStatusController");
            return controller.getApprovalStatus(getKey(value));
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
            if (object instanceof ApprovalStatus) {
                ApprovalStatus o = (ApprovalStatus) object;
                return getStringKey(o.getStatusName());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ApprovalStatus.class.getName()});
                return null;
            }
        }

    }

}
