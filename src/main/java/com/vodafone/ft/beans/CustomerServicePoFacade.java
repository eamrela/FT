/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.beans;

import com.vodafone.ft.entities.CustomerServicePo;
import com.vodafone.ft.entities.NetSales;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eamrela
 */
@Stateless
public class CustomerServicePoFacade extends AbstractFacade<CustomerServicePo> {

    @PersistenceContext(unitName = "com.vodafone_FT_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerServicePoFacade() {
        super(CustomerServicePo.class);
    }

    public List<CustomerServicePo> findSettelmentPOs(NetSales selectedUserNs) {
        return em.createNativeQuery(" select * " +
                                    " from customer_service_po " +
                                    " where po_number not in "
                                    + " (select po_number from netsales_j_customer_po_service where ns_id ="+selectedUserNs.getNetsalesId()+" ) "
                                            + " order by po_date desc ", 
                CustomerServicePo.class).getResultList();
    }
    
}
