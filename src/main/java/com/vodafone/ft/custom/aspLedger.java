/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.custom;


import com.vodafone.ft.entities.AspExtraworkGrn;
import com.vodafone.ft.entities.AspExtraworkPo;
import com.vodafone.ft.entities.AspServiceGrn;
import com.vodafone.ft.entities.AspServicePo;
import com.vodafone.ft.entities.CostOfSales;
import com.vodafone.ft.entities.CreditNote;
import com.vodafone.ft.entities.Penalty;
import com.vodafone.ft.entities.Subcontractors;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eamrela
 */
@Named("aspLedgerController")
@SessionScoped
public class aspLedger implements Serializable{
    
    @PersistenceContext(unitName = "com.vodafone_FT_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    private Subcontractors asp;
    private List<CostOfSales> committedCost;
    private List<AspServicePo> remainingFromPOService;
    private List<AspExtraworkPo> remainingFromPOExtrawork;
    private List<AspServiceGrn> grnReceivedService;
    private List<AspExtraworkGrn> grnReceivedExtrawork;
    private List<AspServicePo> grnDeservedService;
    private List<AspExtraworkPo> grnDeservedExtrawork;
    private List<AspServicePo> poReceivedService;
    private List<AspExtraworkPo> poReceivedExtrawork;
    private List<CreditNote> creditNotesDetails;
    private List<Penalty> penaltyDetails;
    
    private Double committedCostFig=0.0;
    private Double extraWorkNoPOVF=0.0;
    private Double UMP=1.0;
    private Double remainingFromPO=0.0;
    private Double grnReceived=0.0;
    private Double grndeserved=0.0;
    private Double poReceived=0.0;
    private Double creditNotes=0.0;
    private Double penalty=0.0;
    private Double overall=0.0;
    
    private List<String[]> networkBreakDown;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<CostOfSales> getCommittedCost() {
        if(asp!=null){
            committedCost = em.createNativeQuery(" select * from cost_of_sales where asp_name='"+asp.getSubcontractorName()+"' "
                    + " and cost_amount > 100 ", CostOfSales.class).getResultList();
            for (CostOfSales x : committedCost) {
                committedCostFig+=x.getCostAmount();
            }
            
        }
        return committedCost;
    }

    public void setCommittedCost(List<CostOfSales> committedCost) {
        this.committedCost = committedCost;
    }

    public List<AspServicePo> getRemainingFromPOService() {
        if(asp!=null){
           remainingFromPOService = em.createNativeQuery(" select * from asp_service_po "
                   + " where remaining_from_po > 100 and subcontractor='"+asp.getSubcontractorName()+"' ", AspServicePo.class).getResultList(); 
           for (AspServicePo x : remainingFromPOService) {
                remainingFromPO+=x.getRemainingFromPo();
            }
        }
        return remainingFromPOService;
    }

    public void setRemainingFromPOService(List<AspServicePo> remainingFromPOService) {
        this.remainingFromPOService = remainingFromPOService;
    }

    public List<AspExtraworkPo> getRemainingFromPOExtrawork() {
        if(asp!=null){
            remainingFromPOExtrawork = em.createNativeQuery(" select * from asp_extrawork_po "
                   + " where remaining_from_po > 100 and subcontractor='"+asp.getSubcontractorName()+"' ", AspExtraworkPo.class).getResultList();
            for (AspExtraworkPo x : remainingFromPOExtrawork) {
                remainingFromPO+=x.getRemainingFromPo();
            }
        }
        return remainingFromPOExtrawork;
    }

    public void setRemainingFromPOExtrawork(List<AspExtraworkPo> remainingFromPOExtrawork) {
        this.remainingFromPOExtrawork = remainingFromPOExtrawork;
    }

    public List<AspServiceGrn> getGrnReceivedService() {
        if(asp!=null){
          grnReceivedService = em.createNativeQuery(" select * from asp_service_grn where \n " +
                " po_number in (select po_number from asp_service_po where subcontractor='"+asp.getSubcontractorName()+"')\n " +
                " and grn_value > 0 ", AspServiceGrn.class).getResultList();  
          for (AspServiceGrn x : grnReceivedService) {
                grnReceived+=x.getGrnValue();
            }
        }
        return grnReceivedService;
    }

    public void setGrnReceivedService(List<AspServiceGrn> grnReceivedService) {
        this.grnReceivedService = grnReceivedService;
    }

    public List<AspExtraworkGrn> getGrnReceivedExtrawork() {
        if(asp!=null){
           grnReceivedExtrawork = em.createNativeQuery(" select * from asp_extrawork_grn where \n " +
                " po_number in (select po_number from asp_extrawork_po where subcontractor='"+asp.getSubcontractorName()+"')\n " +
                " and grn_value > 0 ", AspExtraworkGrn.class).getResultList(); 
           for (AspExtraworkGrn x : grnReceivedExtrawork) {
                grnReceived+=x.getGrnValue();
            }
        }
        return grnReceivedExtrawork;
    }

    public void setGrnReceivedExtrawork(List<AspExtraworkGrn> grnReceivedExtrawork) {
        this.grnReceivedExtrawork = grnReceivedExtrawork;
    }

    public List<AspServicePo> getGrnDeservedService() {
        if(asp!=null){
          grnDeservedService = em.createNativeQuery(
                  "  select * \n" +
                  " from asp_service_po spo \n" +
                  " where ((po_value - remaining_from_po) - "
                          + "COALESCE((select sum(grn_value) from asp_service_grn where po_number = spo.po_number group by po_number),0))>100 "
                          + " and subcontractor='"+asp.getSubcontractorName()+"'" 
                                            , AspServicePo.class).getResultList(); 
          for (AspServicePo x : grnDeservedService) {
                grndeserved+=x.getGrnDeserved();
            }
        }
        return grnDeservedService;
    }

    public void setGrnDeservedService(List<AspServicePo> grnDeservedService) {
        this.grnDeservedService = grnDeservedService;
    }

    public List<AspExtraworkPo> getGrnDeservedExtrawork() {
        if(asp!=null){
          grnDeservedExtrawork = em.createNativeQuery("  select * \n" +
                  " from asp_extrawork_po spo \n" +
                  " where ((po_value - remaining_from_po) - "
                          + "COALESCE((select sum(grn_value) from asp_extrawork_grn where po_number = spo.po_number group by po_number),0))>100 "
                          + " and subcontractor='"+asp.getSubcontractorName()+"'" , AspExtraworkPo.class).getResultList();  
          for (AspExtraworkPo x : grnDeservedExtrawork) {
                grndeserved+=x.getGrnDeserved();
            }
        }
        return grnDeservedExtrawork;
    }

    public void setGrnDeservedExtrawork(List<AspExtraworkPo> grnDeservedExtrawork) {
        this.grnDeservedExtrawork = grnDeservedExtrawork;
    }

    public List<AspServicePo> getPoReceivedService() {
        if(asp!=null){
           poReceivedService = em.createNativeQuery("select * from asp_service_po where subcontractor='"+asp.getSubcontractorName()+"' ", AspServicePo.class).getResultList(); 
           for (AspServicePo x : poReceivedService) {
                poReceived+=x.getPoValue();
            }
        }
        return poReceivedService;
    }

    public void setPoReceivedService(List<AspServicePo> poReceivedService) {
        this.poReceivedService = poReceivedService;
    }

    public List<AspExtraworkPo> getPoReceivedExtrawork() {
        if(asp!=null){
         poReceivedExtrawork = em.createNativeQuery("select * from asp_extrawork_po where subcontractor='"+asp.getSubcontractorName()+"' ", AspExtraworkPo.class).getResultList();    
         for (AspExtraworkPo x : poReceivedExtrawork) {
                poReceived+=x.getPoValue();
            }
        }
        return poReceivedExtrawork;
    }

    public void setPoReceivedExtrawork(List<AspExtraworkPo> poReceivedExtrawork) {
        this.poReceivedExtrawork = poReceivedExtrawork;
    }

    public void setAsp(Subcontractors asp)  {
        this.asp = asp;
        clearValues();
        callValues();
    }
    
     public Subcontractors getAsp() {
        return asp;
    }

    public Double getCommittedCostFig() {
        return committedCostFig;
    }

    public void setCommittedCostFig(Double committedCostFig) {
        this.committedCostFig = committedCostFig;
    }

    

    public Double getExtraWorkNoPOVF() {
        return extraWorkNoPOVF;
    }

    public void setExtraWorkNoPOVF(Double extraWorkNoPOVF) {
        this.extraWorkNoPOVF = extraWorkNoPOVF;
    }

    public Double getUMP() {
        return UMP;
    }

    public void setUMP(Double UMP) {
        this.UMP = UMP;
    }

    public Double getRemainingFromPO() {
//         if(asp!=null){
//             getRemainingFromPOService();
//             getRemainingFromPOExtrawork();
//         }
        return remainingFromPO;
    }

    public void setRemainingFromPO(Double remainingFromPO) {
        this.remainingFromPO = remainingFromPO;
    }

    public Double getGrnReceived() {
//         if(asp!=null){
//             getGrnReceivedService();
//             getGrnReceivedExtrawork();
//         }
        return grnReceived;
    }

    public void setGrnReceived(Double grnReceived) {
        this.grnReceived = grnReceived;
    }

    public Double getGrndeserved() {
//        if(asp!=null){
//            getGrnDeservedService();
//            getGrnDeservedExtrawork();
//         }
        return grndeserved;
    }

    public void setGrndeserved(Double grndeserved) {
        this.grndeserved = grndeserved;
    }

    public Double getPoReceived() {
//        if(asp!=null){
//            getPoReceivedService();
//            getPoReceivedExtrawork();
//        }
        return poReceived;
    }

    public void setPoReceived(Double poReceived) {
        this.poReceived = poReceived;
    }

    public Double getCreditNotes() {
        return creditNotes;
    }

    public void setCreditNotes(Double creditNotes) {
        this.creditNotes = creditNotes;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    public List<CreditNote> getCreditNotesDetails() {
        if(asp!=null){
          creditNotesDetails = em.createNativeQuery("  select * \n" +
                  " from credit_note  \n" +
                  " where cn_owner='"+asp.getSubcontractorName()+"' and settled=false" , CreditNote.class).getResultList();  
          for (CreditNote x : creditNotesDetails) {
                creditNotes+=x.getCnValue();
            }
        }
        return creditNotesDetails;
    }

    public List<Penalty> getPenaltyDetails() {
        if(asp!=null){
          penaltyDetails = em.createNativeQuery("  select * \n" +
                  " from penalty \n" +
                  " where pn_owner='"+asp.getSubcontractorName()+"' and settled=false" , Penalty.class).getResultList();  
          for (Penalty x : penaltyDetails) {
                penalty+=x.getPnValue();
            }
        }
        return penaltyDetails;
    }

    public Double getOverall() {
        return overall;
    }

    public List<String[]> getNetworkBreakDown() {
         networkBreakDown = em.createNativeQuery(" select network_name,sum(val) val " +
                                                " from ( " +
                                                " select network_name,sum(po_value) val " +
                                                " from asp_service_po " +
                                                " group by network_name " +
                                                " union " +
                                                " select network_name,sum(po_value) val " +
                                                " from asp_extrawork_po " +
                                                " group by network_name) a " +
                                                " group by network_name ").getResultList();    
        return networkBreakDown;
    }
    
    
    public void clearValues(){
        committedCostFig=0.0;
        extraWorkNoPOVF=0.0;
        remainingFromPO=0.0;
        grnReceived=0.0;
        grndeserved=0.0;
        poReceived=0.0;
        UMP=1.0;
        creditNotes = 0.0;
        penalty=0.0;
        overall =0.0;
    }
    
    public void callValues(){
        getCommittedCost();
        getGrnDeservedService();
        getGrnDeservedExtrawork();
        getGrnReceivedService();
        getGrnReceivedExtrawork();
        getPoReceivedService();
        getPoReceivedExtrawork();
        getRemainingFromPOService();
        getRemainingFromPOExtrawork();
        getCreditNotesDetails();
        getPenaltyDetails();
        overall = (committedCostFig+grndeserved)-(creditNotes+penalty);
    }
     
}
