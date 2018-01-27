/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eamrela
 */
@Entity
@Table(name = "netsales_j_customer_extrawork_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetsalesJCustomerExtraworkInvoice.findAll", query = "SELECT n FROM NetsalesJCustomerExtraworkInvoice n")
    , @NamedQuery(name = "NetsalesJCustomerExtraworkInvoice.findByNsId", query = "SELECT n FROM NetsalesJCustomerExtraworkInvoice n WHERE n.netsalesJCustomerExtraworkInvoicePK.nsId = :nsId")
    , @NamedQuery(name = "NetsalesJCustomerExtraworkInvoice.findByInvoiceId", query = "SELECT n FROM NetsalesJCustomerExtraworkInvoice n WHERE n.netsalesJCustomerExtraworkInvoicePK.invoiceId = :invoiceId")
    , @NamedQuery(name = "NetsalesJCustomerExtraworkInvoice.findBySettlePercentage", query = "SELECT n FROM NetsalesJCustomerExtraworkInvoice n WHERE n.settlePercentage = :settlePercentage")})
public class NetsalesJCustomerExtraworkInvoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NetsalesJCustomerExtraworkInvoicePK netsalesJCustomerExtraworkInvoicePK;
    @Column(name = "settle_percentage")
    private Double settlePercentage;
    @JoinColumn(name = "invoice_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerExtraworkInvoice customerExtraworkInvoice;
    @JoinColumn(name = "ns_id", referencedColumnName = "netsales_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NetSales netSales;

    public NetsalesJCustomerExtraworkInvoice() {
    }

    public NetsalesJCustomerExtraworkInvoice(NetsalesJCustomerExtraworkInvoicePK netsalesJCustomerExtraworkInvoicePK) {
        this.netsalesJCustomerExtraworkInvoicePK = netsalesJCustomerExtraworkInvoicePK;
    }

    public NetsalesJCustomerExtraworkInvoice(long nsId, long invoiceId) {
        this.netsalesJCustomerExtraworkInvoicePK = new NetsalesJCustomerExtraworkInvoicePK(nsId, invoiceId);
    }

    public NetsalesJCustomerExtraworkInvoicePK getNetsalesJCustomerExtraworkInvoicePK() {
        return netsalesJCustomerExtraworkInvoicePK;
    }

    public void setNetsalesJCustomerExtraworkInvoicePK(NetsalesJCustomerExtraworkInvoicePK netsalesJCustomerExtraworkInvoicePK) {
        this.netsalesJCustomerExtraworkInvoicePK = netsalesJCustomerExtraworkInvoicePK;
    }

    public Double getSettlePercentage() {
        return settlePercentage;
    }

    public void setSettlePercentage(Double settlePercentage) {
        this.settlePercentage = settlePercentage;
    }

    public CustomerExtraworkInvoice getCustomerExtraworkInvoice() {
        return customerExtraworkInvoice;
    }

    public void setCustomerExtraworkInvoice(CustomerExtraworkInvoice customerExtraworkInvoice) {
        this.customerExtraworkInvoice = customerExtraworkInvoice;
    }

    public NetSales getNetSales() {
        return netSales;
    }

    public void setNetSales(NetSales netSales) {
        this.netSales = netSales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (netsalesJCustomerExtraworkInvoicePK != null ? netsalesJCustomerExtraworkInvoicePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetsalesJCustomerExtraworkInvoice)) {
            return false;
        }
        NetsalesJCustomerExtraworkInvoice other = (NetsalesJCustomerExtraworkInvoice) object;
        if ((this.netsalesJCustomerExtraworkInvoicePK == null && other.netsalesJCustomerExtraworkInvoicePK != null) || (this.netsalesJCustomerExtraworkInvoicePK != null && !this.netsalesJCustomerExtraworkInvoicePK.equals(other.netsalesJCustomerExtraworkInvoicePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.NetsalesJCustomerExtraworkInvoice[ netsalesJCustomerExtraworkInvoicePK=" + netsalesJCustomerExtraworkInvoicePK + " ]";
    }
    
}
