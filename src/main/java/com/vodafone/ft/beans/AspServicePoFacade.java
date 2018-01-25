/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.beans;

import com.vodafone.ft.entities.AspServicePo;
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
public class AspServicePoFacade extends AbstractFacade<AspServicePo> {

    @PersistenceContext(unitName = "com.vodafone_FT_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AspServicePoFacade() {
        super(AspServicePo.class);
    }

    public AspServicePo merge(AspServicePo selected) {
        return em.merge(selected);
    }

    public List<AspServicePo> findSettelmentPOs(CostOfSales selectedUserCos) {
        return em.createNativeQuery(" select * " +
                                    " from asp_service_po " +
                                    " where po_number not in "
                                    + " (select po_number from cost_of_sales_j_asp_service_po where cos_id ="+selectedUserCos.getCostId()+" ) "
                                            + " order by po_date desc ", 
                AspServicePo.class).getResultList();
    }
    
}
