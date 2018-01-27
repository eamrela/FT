package com.vodafone.ft.controllers;





import com.vodafone.ft.beans.NetSalesFacade;
import com.vodafone.ft.controllers.util.JsfUtil;
import com.vodafone.ft.controllers.util.JsfUtil.PersistAction;
import com.vodafone.ft.entities.CustomerExtraworkInvoice;
import com.vodafone.ft.entities.CustomerServiceInvoice;
import com.vodafone.ft.entities.NetSales;
import com.vodafone.ft.entities.NetsalesJCustomerExtraworkInvoice;
import com.vodafone.ft.entities.NetsalesJCustomerExtraworkInvoicePK;
import com.vodafone.ft.entities.NetsalesJCustomerServiceInvoice;
import com.vodafone.ft.entities.NetsalesJCustomerServiceInvoicePK;
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

@Named("netSalesController")
@SessionScoped
public class NetSalesController implements Serializable {

    @EJB
    private NetSalesFacade ejbFacade;
    private List<NetSales> items = null;
    private NetSales selected;
    private NetSales selectedUserNs;
    
    private List<CustomerExtraworkInvoice> availableExtraWorkInvoice;
    private List<CustomerServiceInvoice> availableServiceWorkInvoice;
    
    private List<NetsalesJCustomerExtraworkInvoice> selectedExtraWorkInvoice;
    private CustomerExtraworkInvoice selectedEWINVOICE;
    private NetsalesJCustomerExtraworkInvoice selectedJEWINVOICE;
    
    private List<NetsalesJCustomerServiceInvoice> selectedServiceWorkInvoice;
    private CustomerServiceInvoice selectedSRINVOICE;
    private NetsalesJCustomerServiceInvoice selectedJSRINVOICE;
    
    @Inject
    private CustomerExtraworkInvoiceController customerExtaWorkInvoiceController;
    @Inject
    private CustomerServiceInvoiceController customerServiceInvoiceController;

    public NetSalesController() {
    }

    public NetSales getSelected() {
        return selected;
    }

    public void setSelected(NetSales selected) {
        this.selected = selected;
    }

    public NetSales getSelectedUserNs() {
        return selectedUserNs;
    }

    public void setSelectedUserNs(NetSales selectedUserNs) {
        this.selectedUserNs = selectedUserNs;
    }

    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private NetSalesFacade getFacade() {
        return ejbFacade;
    }

    public void prepareEdit(){
        selectedExtraWorkInvoice = new ArrayList<>();
        selectedServiceWorkInvoice = new ArrayList<>();
        availableExtraWorkInvoice = new ArrayList<>();
        availableServiceWorkInvoice = new ArrayList<>();
        if(selectedUserNs!=null){
            availableServiceWorkInvoice = customerServiceInvoiceController.getSettelmentInvoices(selectedUserNs);
            availableExtraWorkInvoice = customerExtaWorkInvoiceController.getSettelmentInvoices(selectedUserNs);
        }
    }
    
     public void updateEdit(){
        selected = selectedUserNs;
        selected.setNetsalesAmount(selected.getOriginalAmount()-selected.getInvoiceIssued());
        for (NetsalesJCustomerExtraworkInvoice selectedExtraWorkInvoice1 : selectedExtraWorkInvoice) {
           if(selectedExtraWorkInvoice1.getSettlePercentage()!=null){
            if(selected.getNetsalesJCustomerExtraworkInvoiceCollection()!=null){
                    selected.getNetsalesJCustomerExtraworkInvoiceCollection().add(selectedExtraWorkInvoice1);
            }else{
                selected.setNetsalesJCustomerExtraworkInvoiceCollection(new ArrayList<NetsalesJCustomerExtraworkInvoice>());
                selected.getNetsalesJCustomerExtraworkInvoiceCollection().add(selectedExtraWorkInvoice1);
            }
            selected.setNetsalesAmount(selected.getNetsalesAmount()-(selectedExtraWorkInvoice1.getCustomerExtraworkInvoice().getInvoiceValue()
                    * selectedExtraWorkInvoice1.getSettlePercentage()));
           }
        }
        for (NetsalesJCustomerServiceInvoice selectedServieInvoice1 : selectedServiceWorkInvoice) {
           if(selectedServieInvoice1.getSettlePercentage()!=null){
            if(selected.getNetsalesJCustomerServiceInvoiceCollection()!=null){
                    selected.getNetsalesJCustomerServiceInvoiceCollection().add(selectedServieInvoice1);
            }else{
                selected.setNetsalesJCustomerServiceInvoiceCollection(new ArrayList<NetsalesJCustomerServiceInvoice>());
                selected.getNetsalesJCustomerServiceInvoiceCollection().add(selectedServieInvoice1);
            }
            selected.setNetsalesAmount(selected.getNetsalesAmount()-(selectedServieInvoice1.getCustomerServiceInvoice().getInvoiceValue()
                    * selectedServieInvoice1.getSettlePercentage()));
           }
        }
        update();
    }
     
    public NetSales prepareCreate() {
        selected = new NetSales();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "NetSalesCreated");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createNS(){
        if(selected!=null){
            selected.setOriginalAmount(selected.getNetsalesAmount());
            create();
            prepareCreate();
            try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/FT/raw_cosns/view_ns.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ExtraWorkController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void update() {
        persist(PersistAction.UPDATE, "NetSalesUpdated");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "NetSalesDeleted");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            selectedUserNs = null;
        }
    }
    
    public void deleteNs(){
        selected = selectedUserNs;
        destroy();
    }

    public List<NetSales> getItems() {
            items = getFacade().findAll();
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

    public NetSales getNetSales(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<NetSales> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<NetSales> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void addSelectedServicePo(){
        if(selectedSRINVOICE!=null){
            NetsalesJCustomerServiceInvoice ns = new NetsalesJCustomerServiceInvoice();
            ns.setCustomerServiceInvoice(selectedSRINVOICE);
            ns.setNetSales(selectedUserNs);
            ns.setNetsalesJCustomerServiceInvoicePK(new NetsalesJCustomerServiceInvoicePK(ns.getNetSales().getNetsalesId(),
                    ns.getCustomerServiceInvoice().getId()));
            selectedServiceWorkInvoice.add(ns);
            availableServiceWorkInvoice.remove(selectedSRINVOICE);
        }
    }
    
    public void addSelectedExtaworkPo(){
        if(selectedEWINVOICE!=null){
            NetsalesJCustomerExtraworkInvoice ns = new NetsalesJCustomerExtraworkInvoice();
            ns.setCustomerExtraworkInvoice(selectedEWINVOICE);
            ns.setNetSales(selectedUserNs);
            ns.setNetsalesJCustomerExtraworkInvoicePK(new NetsalesJCustomerExtraworkInvoicePK(ns.getNetSales().getNetsalesId(),
                    ns.getCustomerExtraworkInvoice().getId()));
            selectedExtraWorkInvoice.add(ns);
            availableExtraWorkInvoice.remove(selectedEWINVOICE);
        }
    }
    
    public void removeSelectedServicePo(){
        if(selectedJSRINVOICE!=null){
            selectedServiceWorkInvoice.remove(selectedJSRINVOICE);
            availableServiceWorkInvoice.add(selectedJSRINVOICE.getCustomerServiceInvoice());
        }
    }
    
    public void removeSelectedExtaworkPo(){
        if(selectedJEWINVOICE!=null){
            selectedExtraWorkInvoice.remove(selectedJEWINVOICE);
            availableExtraWorkInvoice.add(selectedJEWINVOICE.getCustomerExtraworkInvoice());
        }
    }
    
    @FacesConverter(forClass = NetSales.class)
    public static class NetSalesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NetSalesController controller = (NetSalesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "netSalesController");
            return controller.getNetSales(getKey(value));
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
            if (object instanceof NetSales) {
                NetSales o = (NetSales) object;
                return getStringKey(o.getNetsalesId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), NetSales.class.getName()});
                return null;
            }
        }

    }

    public NetSalesFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(NetSalesFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<CustomerExtraworkInvoice> getAvailableExtraWorkInvoice() {
        return availableExtraWorkInvoice;
    }

    public void setAvailableExtraWorkInvoice(List<CustomerExtraworkInvoice> availableExtraWorkInvoice) {
        this.availableExtraWorkInvoice = availableExtraWorkInvoice;
    }

    public List<CustomerServiceInvoice> getAvailableServiceWorkInvoice() {
        return availableServiceWorkInvoice;
    }

    public void setAvailableServiceWorkInvoice(List<CustomerServiceInvoice> availableServiceWorkInvoice) {
        this.availableServiceWorkInvoice = availableServiceWorkInvoice;
    }

    

    public CustomerExtraworkInvoice getSelectedEWINVOICE() {
        return selectedEWINVOICE;
    }

    public void setSelectedEWINVOICE(CustomerExtraworkInvoice selectedEWINVOICE) {
        this.selectedEWINVOICE = selectedEWINVOICE;
    }

    

    public CustomerServiceInvoice getSelectedSRINVOICE() {
        return selectedSRINVOICE;
    }

    public void setSelectedSRINVOICE(CustomerServiceInvoice selectedSRINVOICE) {
        this.selectedSRINVOICE = selectedSRINVOICE;
    }

    public CustomerExtraworkInvoiceController getCustomerExtaWorkInvoiceController() {
        return customerExtaWorkInvoiceController;
    }

    public void setCustomerExtaWorkInvoiceController(CustomerExtraworkInvoiceController customerExtaWorkInvoiceController) {
        this.customerExtaWorkInvoiceController = customerExtaWorkInvoiceController;
    }

    public CustomerServiceInvoiceController getCustomerServiceInvoiceController() {
        return customerServiceInvoiceController;
    }

    public void setCustomerServiceInvoiceController(CustomerServiceInvoiceController customerServiceInvoiceController) {
        this.customerServiceInvoiceController = customerServiceInvoiceController;
    }

    public List<NetsalesJCustomerExtraworkInvoice> getSelectedExtraWorkInvoice() {
        return selectedExtraWorkInvoice;
    }

    public void setSelectedExtraWorkInvoice(List<NetsalesJCustomerExtraworkInvoice> selectedExtraWorkInvoice) {
        this.selectedExtraWorkInvoice = selectedExtraWorkInvoice;
    }

    public NetsalesJCustomerExtraworkInvoice getSelectedJEWINVOICE() {
        return selectedJEWINVOICE;
    }

    public void setSelectedJEWINVOICE(NetsalesJCustomerExtraworkInvoice selectedJEWINVOICE) {
        this.selectedJEWINVOICE = selectedJEWINVOICE;
    }

    public List<NetsalesJCustomerServiceInvoice> getSelectedServiceWorkInvoice() {
        return selectedServiceWorkInvoice;
    }

    public void setSelectedServiceWorkInvoice(List<NetsalesJCustomerServiceInvoice> selectedServiceWorkInvoice) {
        this.selectedServiceWorkInvoice = selectedServiceWorkInvoice;
    }

    public NetsalesJCustomerServiceInvoice getSelectedJSRINVOICE() {
        return selectedJSRINVOICE;
    }

    public void setSelectedJSRINVOICE(NetsalesJCustomerServiceInvoice selectedJSRINVOICE) {
        this.selectedJSRINVOICE = selectedJSRINVOICE;
    }

    
    
}
