/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.beans;


import com.vodafone.ft.entities.CustomerPoChasers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author eamrela
 */
@Stateless
public class CustomerPoChasersFacade extends AbstractFacade<CustomerPoChasers> {

    @PersistenceContext(unitName = "com.vodafone_FT_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerPoChasersFacade() {
        super(CustomerPoChasers.class);
    }
    
}
