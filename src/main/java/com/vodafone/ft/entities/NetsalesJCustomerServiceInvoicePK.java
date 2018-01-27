/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author eamrela
 */
@Embeddable
public class NetsalesJCustomerServiceInvoicePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ns_id")
    private long nsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "invoice_id")
    private long invoiceId;

    public NetsalesJCustomerServiceInvoicePK() {
    }

    public NetsalesJCustomerServiceInvoicePK(long nsId, long invoiceId) {
        this.nsId = nsId;
        this.invoiceId = invoiceId;
    }

    public long getNsId() {
        return nsId;
    }

    public void setNsId(long nsId) {
        this.nsId = nsId;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nsId;
        hash += (int) invoiceId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetsalesJCustomerServiceInvoicePK)) {
            return false;
        }
        NetsalesJCustomerServiceInvoicePK other = (NetsalesJCustomerServiceInvoicePK) object;
        if (this.nsId != other.nsId) {
            return false;
        }
        if (this.invoiceId != other.invoiceId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.NetsalesJCustomerServiceInvoicePK[ nsId=" + nsId + ", invoiceId=" + invoiceId + " ]";
    }
    
}
