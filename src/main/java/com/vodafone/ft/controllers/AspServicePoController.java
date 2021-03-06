package com.vodafone.ft.controllers;



import com.vodafone.ft.entities.AspServicePo;
import com.vodafone.ft.controllers.util.JsfUtil;
import com.vodafone.ft.controllers.util.JsfUtil.PersistAction;
import com.vodafone.ft.beans.AspServicePoFacade;
import com.vodafone.ft.entities.AspServiceWorkDone;
import com.vodafone.ft.entities.CostOfSales;
import java.io.IOException;

import java.io.Serializable;
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

@Named("aspServicePoController")
@SessionScoped
public class AspServicePoController implements Serializable {

    @EJB
    private com.vodafone.ft.beans.AspServicePoFacade ejbFacade;
    private List<AspServicePo> items = null;
    private AspServicePo selected;
    private AspServicePo selectedUserPo;
    
    @Inject
    private UsersController usersController;
    @Inject
    private CustomerExtraworkPoController customerExtraWorkPoController;

    public AspServicePoController() {
    }

    public AspServicePo getSelected() {
        return selected;
    }

    public AspServicePo getSelectedUserPo() {
        return selectedUserPo;
    }

    public void setSelectedUserPo(AspServicePo selectedUserPo) {
        this.selectedUserPo = selectedUserPo;
    }
    
    

    public void setSelected(AspServicePo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AspServicePoFacade getFacade() {
        return ejbFacade;
    }

    public AspServicePo prepareCreate() {
        selected = new AspServicePo();
        initializeEmbeddableKey();
         selected.setCreator(usersController.getLoggedInUser());
         selected.setCreationTime(new Date());
        return selected;
    }

    public AspServicePo create() {
        selected = persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AspServicePoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        return selected;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AspServicePoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AspServicePoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<AspServicePo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private AspServicePo persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    selected = getFacade().merge(selected);
                    return selected;
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
        return null;
    }

    public AspServicePo getAspServicePo(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<AspServicePo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AspServicePo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<AspServicePo> getSettelmentPOs(CostOfSales selectedUserCos) {
        return getFacade().findSettelmentPOs(selectedUserCos);
    }

    @FacesConverter(forClass = AspServicePo.class)
    public static class AspServicePoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AspServicePoController controller = (AspServicePoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "aspServicePoController");
            return controller.getAspServicePo(getKey(value));
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
            if (object instanceof AspServicePo) {
                AspServicePo o = (AspServicePo) object;
                return getStringKey(o.getPoNumber());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AspServicePo.class.getName()});
                return null;
            }
        }

    }

    public void updateValues(){
        if(selected!=null){
            if(selected.getFactor()!=null && selected.getServiceValue()!=null){
                selected.setPoValue(selected.getFactor()*selected.getServiceValue());
                selected.setRemainingFromPo(selected.getPoValue());
            }
        }
    }
    
    public void updateValuesEdit(){
       if(selectedUserPo!=null){
            if(selectedUserPo.getFactor()!=null && selectedUserPo.getServiceValue()!=null){
                selectedUserPo.setPoValue(selectedUserPo.getFactor()*selectedUserPo.getServiceValue());
                if(selectedUserPo.getAspServiceWorkDoneCollection()!=null){
                    Object[] workDoneCollection = selectedUserPo.getAspServiceWorkDoneCollection().toArray();
                    Double workDoneValue = 0.0;
                    for (Object workDoneCollection1 : workDoneCollection) {
                        workDoneValue += ((AspServiceWorkDone) workDoneCollection1).getWorkDoneValue();
                    }
                    selectedUserPo.setRemainingFromPo(selectedUserPo.getPoValue()-workDoneValue);
                }
            }
        } 
    }
    
    public void createAspPo(){
        if(selected!=null){
            updateValues();
            selected = create();
            
            if(!selected.getEarlyStart()){
                selected.setEarlyStartNumber(null);
            }else if(selected.getEarlyStartNumber()!=null){
                customerExtraWorkPoController.setSelected(customerExtraWorkPoController.getCustomerExtraworkPo(selected.getEarlyStartNumber()));
                customerExtraWorkPoController.correlatePoService(selected);
            }
            prepareCreate();
            try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FT/app/purchase_orders/view_asp_po.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ExtraWorkController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deletePo() {
        selected = selectedUserPo;
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AspServicePoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            selectedUserPo = null;
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void updateEdit(){
        updateValuesEdit();
        getFacade().edit(selectedUserPo);
        JsfUtil.addSuccessMessage("PO Updated");
    }
}
