package com.vodafone.ft.controllers;



import com.vodafone.ft.entities.CustomerExtraworkWorkDone;
import com.vodafone.ft.controllers.util.JsfUtil;
import com.vodafone.ft.controllers.util.JsfUtil.PersistAction;
import com.vodafone.ft.beans.CustomerExtraworkWorkDoneFacade;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
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
import org.primefaces.event.RowEditEvent;

@Named("customerExtraworkWorkDoneController")
@SessionScoped
public class CustomerExtraworkWorkDoneController implements Serializable {

    @EJB
    private com.vodafone.ft.beans.CustomerExtraworkWorkDoneFacade ejbFacade;
    private List<CustomerExtraworkWorkDone> items = null;
    private CustomerExtraworkWorkDone selected;
    @Inject
    private UsersController usersController;
    @Inject
    private CustomerExtraworkPoController extraWorkPoController;

    public CustomerExtraworkWorkDoneController() {
    }

    public CustomerExtraworkWorkDone getSelected() {
        return selected;
    }

    public void setSelected(CustomerExtraworkWorkDone selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CustomerExtraworkWorkDoneFacade getFacade() {
        return ejbFacade;
    }

    public CustomerExtraworkWorkDone prepareCreate() {
        selected = new CustomerExtraworkWorkDone();
        initializeEmbeddableKey();
            selected.setCreator(usersController.getLoggedInUser());
             selected.setCreationTime(new Date());
             selected.setPoNumber(extraWorkPoController.getSelectedUserPo());
        return selected;
    }

    public CustomerExtraworkWorkDone create() {
        selected = persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CustomerExtraworkWorkDoneCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        return selected;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CustomerExtraworkWorkDoneUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CustomerExtraworkWorkDoneDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<CustomerExtraworkWorkDone> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private CustomerExtraworkWorkDone persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    selected = getFacade().merge(selected);
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
        return selected;
    }

    public CustomerExtraworkWorkDone getCustomerExtraworkWorkDone(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<CustomerExtraworkWorkDone> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CustomerExtraworkWorkDone> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CustomerExtraworkWorkDone.class)
    public static class CustomerExtraworkWorkDoneControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CustomerExtraworkWorkDoneController controller = (CustomerExtraworkWorkDoneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "customerExtraworkWorkDoneController");
            return controller.getCustomerExtraworkWorkDone(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CustomerExtraworkWorkDone) {
                CustomerExtraworkWorkDone o = (CustomerExtraworkWorkDone) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CustomerExtraworkWorkDone.class.getName()});
                return null;
            }
        }

    }

    public void updateValues(boolean workDoneValue){
        if(selected!=null){
            
           if(selected.getWorkDonePercentage()!=null && !workDoneValue){
               selected.setWorkDoneValue(selected.getPoNumber().getPoValue()*(selected.getWorkDonePercentage()/100));
           }
           if(selected.getWorkDoneValue()!=null && workDoneValue){
               selected.setWorkDonePercentage((selected.getWorkDoneValue()/selected.getPoNumber().getPoValue())*100);
           }
           validateWorkDone();
        }
    }
    
     public void createWorkDone(){
        if(selected!=null){
            if(validateWorkDone()){
            updateValues(true);
            selected = create();
            if(extraWorkPoController.getSelectedUserPo().getCustomerExtraworkWorkDoneCollection()!=null){
                extraWorkPoController.getSelectedUserPo().getCustomerExtraworkWorkDoneCollection().add(selected);
            }else{
                extraWorkPoController.getSelectedUserPo().setCustomerExtraworkWorkDoneCollection(Arrays.asList(selected));
            }
            extraWorkPoController.updateEdit();
            prepareCreate();
            }
        }
    }  
     
       public void onRowEdit(RowEditEvent event) {
        setSelected((CustomerExtraworkWorkDone) event.getObject());
        if(validateWorkDone()){
        updateValues(true);
        update();
        extraWorkPoController.updateEdit();
        }
    }
       
       public boolean validateWorkDone(){
       if(selected!=null){
           if(selected.getWorkDoneValue()>selected.getPoNumber().getRemainingFromPo()){
               selected.setWorkDoneValue(0.0);
               JsfUtil.addErrorMessage("Workdone value can't exceed remaining from PO");
               return false;
           }else{
               return true;
           }
       }
       return false;
   }
}
