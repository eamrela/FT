/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.beans;


import com.vodafone.ft.entities.AspExtraworkPo;
import com.vodafone.ft.entities.CostOfSales;
import com.vodafone.ft.entities.CustomerExtraworkPo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eamrela
 */
@Stateless
public class AspExtraworkPoFacade extends AbstractFacade<AspExtraworkPo> {

    @PersistenceContext(unitName = "com.vodafone_FT_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AspExtraworkPoFacade() {
        super(AspExtraworkPo.class);
    }

    public List<AspExtraworkPo> findMatchingPOs(CustomerExtraworkPo selectedUserPo) {
        return em.createNativeQuery(" select * " +
                                    " from asp_extrawork_po asppo " +
                                    " where  po_number not in " +
                                    " (select asp_extrawork_po_id from customer_extrawork_po_j_asp_extrawork_po) " +
                                    " and  " +
                                    " COALESCE(((select COALESCE((sum(total_price_vendor)-sum(total_price_asp))/sum(total_price_vendor),null) " +
                                    " from extra_work " +
                                    " where id in (select  extrawork_id from asp_extrawork_po_j_extrawork "
                                    + "where asp_extrawork_po_id = asppo.po_number))*po_value)+po_value,po_value) <= "
                                    +selectedUserPo.getRemainingFromPo()
                                    , AspExtraworkPo.class).getResultList();
    }

    public AspExtraworkPo merge(AspExtraworkPo selected) {
        return em.merge(selected);
    }

    public List<AspExtraworkPo> findSettelmentItems(CostOfSales selectedUserCos) {
        return em.createNativeQuery(" select * " +
                                    " from asp_extrawork_po " +
                                    " where po_number not in "
                                    + " (select po_number from cost_of_sales_j_asp_extrawork_po where cos_id ="+selectedUserCos.getCostId()+" ) "
                                            + " order by po_date desc ", 
                AspExtraworkPo.class).getResultList();
    }
    
}
