/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.beans;

import com.vodafone.ft.entities.CustomerServiceInvoice;
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
public class CustomerServiceInvoiceFacade extends AbstractFacade<CustomerServiceInvoice> {

    @PersistenceContext(unitName = "com.vodafone_FT_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerServiceInvoiceFacade() {
        super(CustomerServiceInvoice.class);
    }

    public CustomerServiceInvoice merge(CustomerServiceInvoice selected) {
        return em.merge(selected);
    }

    public List<CustomerServiceInvoice> findSettelmentsInvoices(NetSales selectedUserNs) {
        return em.createNativeQuery(" select * from customer_service_invoice " +
                                    " where id not in "
                + " (select invoice_id from netsales_j_customer_service_invoice where ns_id = "+selectedUserNs.getNetsalesId()+" ) ", CustomerServiceInvoice.class).getResultList();
    }
    
}
