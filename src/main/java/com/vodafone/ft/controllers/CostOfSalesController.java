package com.vodafone.ft.controllers;





import com.vodafone.ft.beans.CostOfSalesFacade;
import com.vodafone.ft.controllers.util.JsfUtil;
import com.vodafone.ft.controllers.util.JsfUtil.PersistAction;
import com.vodafone.ft.entities.AspExtraworkGrn;
import com.vodafone.ft.entities.AspServiceGrn;
import com.vodafone.ft.entities.CostOfSales;
import com.vodafone.ft.entities.CostOfSalesJAspExtraworkGrn;
import com.vodafone.ft.entities.CostOfSalesJAspExtraworkGrnPK;
import com.vodafone.ft.entities.CostOfSalesJAspServiceGrn;
import com.vodafone.ft.entities.CostOfSalesJAspServiceGrnPK;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

@Named("costOfSalesController")
@SessionScoped
public class CostOfSalesController implements Serializable {

    @EJB
    private CostOfSalesFacade ejbFacade;
    private List<CostOfSales> items = null;
    private CostOfSales selected;
    private CostOfSales selectedUserCos;
    
    private List<AspExtraworkGrn> availableExtraWorkGrn;
    private List<AspServiceGrn> availableServiceWorkGrn;
    
    private List<CostOfSalesJAspExtraworkGrn> selectedExtraWorkGrn;
    private AspExtraworkGrn selectedEWGRN;
    private CostOfSalesJAspExtraworkGrn selectedJEWGRN;
    
    private List<CostOfSalesJAspServiceGrn> selectedServiceWorkGrn;
    private AspServiceGrn selectedSRGRN;
    private CostOfSalesJAspServiceGrn selectedJSRGRN;
    
    @Inject
    private AspExtraworkGrnController aspExtaWorkGrnController;
    @Inject
    private AspServiceGrnController aspServiceGrnController;

    public CostOfSalesController() {
    }

    public CostOfSales getSelected() {
        return selected;
    }

    public void setSelected(CostOfSales selected) {
        this.selected = selected;
    }

    public CostOfSales getSelectedUserCos() {
        return selectedUserCos;
    }

    public void setSelectedUserCos(CostOfSales selectedUserCos) {
        this.selectedUserCos = selectedUserCos;
    }
    
    

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CostOfSalesFacade getFacade() {
        return ejbFacade;
    }

    public CostOfSales prepareCreate() {
        selected = new CostOfSales();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "CostOfSalesCreated");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void prepareEdit(){
        selectedExtraWorkGrn = new ArrayList<>();
        selectedServiceWorkGrn = new ArrayList<>();
        availableExtraWorkGrn = new ArrayList<>();
        availableServiceWorkGrn = new ArrayList<>();
        if(selectedUserCos!=null){
            availableServiceWorkGrn = aspServiceGrnController.getSettelmentGRNs(selectedUserCos);
            availableExtraWorkGrn = aspExtaWorkGrnController.getSettelmentGRNs(selectedUserCos);
        }
    }
    public void update() {
        persist(PersistAction.UPDATE, "CostOfSalesUpdated");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "CostOfSalesDeleted");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            selectedUserCos = null;
        }
    }

    public List<CostOfSales> getItems() {
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
                    JsfUtil.addErrorMessage(ex, "PersistenceErrorOccured");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "PersistenceErrorOccured");
            }
        }
    }

    public CostOfSales getCostOfSales(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<CostOfSales> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<CostOfSales> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = CostOfSales.class)
    public static class CostOfSalesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CostOfSalesController controller = (CostOfSalesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "costOfSalesController");
            return controller.getCostOfSales(getKey(value));
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
            if (object instanceof CostOfSales) {
                CostOfSales o = (CostOfSales) object;
                return getStringKey(o.getCostId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CostOfSales.class.getName()});
                return null;
            }
        }

    }

    public void createCOS(){
        if(selected!=null){
            selected.setOriginalAmount(selected.getCostAmount());
            create();
            prepareCreate();
            try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FT/app/raw_cosns/view_cos.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ExtraWorkController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void updateEdit(){
        selected = selectedUserCos;
        selected.setCostAmount(selected.getOriginalAmount()-selected.getGrnIssued());
        for (CostOfSalesJAspExtraworkGrn selectedExtraWorkGrn1 : selectedExtraWorkGrn) {
            if(selectedExtraWorkGrn1.getSettlePercentage()!=null){
            if(selected.getCostOfSalesJAspExtraworkGrnCollection()!=null){
                    selected.getCostOfSalesJAspExtraworkGrnCollection().add(selectedExtraWorkGrn1);
            }else{
                selected.setCostOfSalesJAspExtraworkGrnCollection(new ArrayList<CostOfSalesJAspExtraworkGrn>());
                selected.getCostOfSalesJAspExtraworkGrnCollection().add(selectedExtraWorkGrn1);
            }
            selected.setCostAmount(selected.getCostAmount()-(selectedExtraWorkGrn1.getAspExtraworkGrn().getGrnValue()
                                                        *selectedExtraWorkGrn1.getSettlePercentage()));
            }
        }
        for (CostOfSalesJAspServiceGrn selectedServieGrn1 : selectedServiceWorkGrn) {
            if(selectedServieGrn1.getSettlePercentage()!=null){
            if(selected.getCostOfSalesJAspServiceGrnCollection()!=null){
                    selected.getCostOfSalesJAspServiceGrnCollection().add(selectedServieGrn1);
            }else{
                selected.setCostOfSalesJAspServiceGrnCollection(new ArrayList<CostOfSalesJAspServiceGrn>());
                selected.getCostOfSalesJAspServiceGrnCollection().add(selectedServieGrn1);
            }
            selected.setCostAmount(selected.getCostAmount()-(selectedServieGrn1.getAspServiceGrn().getGrnValue()*
                                                            selectedServieGrn1.getSettlePercentage()));
            }
        }
        update();
    }

    public void deleteCos(){
        selected = selectedUserCos;
        destroy();
    }

    

    public CostOfSalesFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CostOfSalesFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    

    public AspExtraworkGrnController getAspExtaWorkGrnController() {
        return aspExtaWorkGrnController;
    }

    public void setAspExtaWorkGrnController(AspExtraworkGrnController aspExtaWorkGrnController) {
        this.aspExtaWorkGrnController = aspExtaWorkGrnController;
    }

    public AspServiceGrnController getAspServiceGrnController() {
        return aspServiceGrnController;
    }

    public void setAspServiceGrnController(AspServiceGrnController aspServiceGrnController) {
        this.aspServiceGrnController = aspServiceGrnController;
    }

    public void setItems(List<CostOfSales> items) {
        this.items = items;
    }

    public List<AspExtraworkGrn> getAvailableExtraWorkGrn() {
        return availableExtraWorkGrn;
    }

    public void setAvailableExtraWorkGrn(List<AspExtraworkGrn> availableExtraWorkGrn) {
        this.availableExtraWorkGrn = availableExtraWorkGrn;
    }

    public List<AspServiceGrn> getAvailableServiceWorkGrn() {
        return availableServiceWorkGrn;
    }

    public void setAvailableServiceWorkGrn(List<AspServiceGrn> availableServiceWorkGrn) {
        this.availableServiceWorkGrn = availableServiceWorkGrn;
    }

    public List<CostOfSalesJAspExtraworkGrn> getSelectedExtraWorkGrn() {
        return selectedExtraWorkGrn;
    }

    public void setSelectedExtraWorkGrn(List<CostOfSalesJAspExtraworkGrn> selectedExtraWorkGrn) {
        this.selectedExtraWorkGrn = selectedExtraWorkGrn;
    }

    public AspExtraworkGrn getSelectedEWGRN() {
        return selectedEWGRN;
    }

    public void setSelectedEWGRN(AspExtraworkGrn selectedEWGRN) {
        this.selectedEWGRN = selectedEWGRN;
    }

    public List<CostOfSalesJAspServiceGrn> getSelectedServiceWorkGrn() {
        return selectedServiceWorkGrn;
    }

    public void setSelectedServiceWorkGrn(List<CostOfSalesJAspServiceGrn> selectedServiceWorkGrn) {
        this.selectedServiceWorkGrn = selectedServiceWorkGrn;
    }

    public AspServiceGrn getSelectedSRGRN() {
        return selectedSRGRN;
    }

    public void setSelectedSRGRN(AspServiceGrn selectedSRGRN) {
        this.selectedSRGRN = selectedSRGRN;
    }

    public CostOfSalesJAspExtraworkGrn getSelectedJEWGRN() {
        return selectedJEWGRN;
    }

    public void setSelectedJEWGRN(CostOfSalesJAspExtraworkGrn selectedJEWGRN) {
        this.selectedJEWGRN = selectedJEWGRN;
    }

    public CostOfSalesJAspServiceGrn getSelectedJSRGRN() {
        return selectedJSRGRN;
    }

    public void setSelectedJSRGRN(CostOfSalesJAspServiceGrn selectedJSRGRN) {
        this.selectedJSRGRN = selectedJSRGRN;
    }

    
    

    public void addSelectedServicePo(){
        if(selectedSRGRN!=null){
            CostOfSalesJAspServiceGrn cos = new CostOfSalesJAspServiceGrn();
            cos.setAspServiceGrn(selectedSRGRN);
            cos.setCostOfSales(selectedUserCos);
            cos.setCostOfSalesJAspServiceGrnPK(new CostOfSalesJAspServiceGrnPK(cos.getCostOfSales().getCostId(),
                                            cos.getAspServiceGrn().getId()));
            selectedServiceWorkGrn.add(cos);
            availableServiceWorkGrn.remove(selectedSRGRN);
        }
    }
    
    public void addSelectedExtaworkPo(){
        if(selectedEWGRN!=null){
            CostOfSalesJAspExtraworkGrn cos = new CostOfSalesJAspExtraworkGrn();
            cos.setAspExtraworkGrn(selectedEWGRN);
            cos.setCostOfSales(selectedUserCos);
            cos.setCostOfSalesJAspExtraworkGrnPK(new CostOfSalesJAspExtraworkGrnPK(cos.getCostOfSales().getCostId(),
                                            cos.getAspExtraworkGrn().getId()));
            selectedExtraWorkGrn.add(cos);
            availableExtraWorkGrn.remove(selectedEWGRN);
        }
    }
    
    public void removeSelectedServicePo(){
        if(selectedJSRGRN!=null){
            selectedServiceWorkGrn.remove(selectedJSRGRN);
            availableServiceWorkGrn.add(selectedJSRGRN.getAspServiceGrn());
        }
    }
    
    public void removeSelectedExtaworkPo(){
        if(selectedJEWGRN!=null){
            selectedExtraWorkGrn.remove(selectedJEWGRN);
            availableExtraWorkGrn.add(selectedJEWGRN.getAspExtraworkGrn());
        }
    }
}
