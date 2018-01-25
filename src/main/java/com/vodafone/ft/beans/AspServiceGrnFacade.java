/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.beans;

import com.vodafone.ft.entities.AspServiceGrn;
import com.vodafone.ft.entities.CostOfSales;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eamrela
 */
@Stateless
public class AspServiceGrnFacade extends AbstractFacade<AspServiceGrn> {

    @PersistenceContext(unitName = "com.vodafone_FT_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AspServiceGrnFacade() {
        super(AspServiceGrn.class);
    }

    public AspServiceGrn merge(AspServiceGrn selected) {
        return em.merge(selected);
    }

    public List<AspServiceGrn> findSettelmentGRNs(CostOfSales selectedUserCos) {
        return em.createNativeQuery(" select * from asp_service_grn " +
                                    " where id not in (select grn_id "
                                    + " from cost_of_sales_j_asp_service_grn where cos_id = "+selectedUserCos.getCostId()+" ) ", AspServiceGrn.class).getResultList();
    }
    
}
