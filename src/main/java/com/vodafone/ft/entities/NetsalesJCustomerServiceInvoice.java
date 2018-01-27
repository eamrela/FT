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
@Table(name = "netsales_j_customer_service_invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetsalesJCustomerServiceInvoice.findAll", query = "SELECT n FROM NetsalesJCustomerServiceInvoice n")
    , @NamedQuery(name = "NetsalesJCustomerServiceInvoice.findByNsId", query = "SELECT n FROM NetsalesJCustomerServiceInvoice n WHERE n.netsalesJCustomerServiceInvoicePK.nsId = :nsId")
    , @NamedQuery(name = "NetsalesJCustomerServiceInvoice.findByInvoiceId", query = "SELECT n FROM NetsalesJCustomerServiceInvoice n WHERE n.netsalesJCustomerServiceInvoicePK.invoiceId = :invoiceId")
    , @NamedQuery(name = "NetsalesJCustomerServiceInvoice.findBySettlePercentage", query = "SELECT n FROM NetsalesJCustomerServiceInvoice n WHERE n.settlePercentage = :settlePercentage")})
public class NetsalesJCustomerServiceInvoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NetsalesJCustomerServiceInvoicePK netsalesJCustomerServiceInvoicePK;
    @Column(name = "settle_percentage")
    private Double settlePercentage;
    @JoinColumn(name = "invoice_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerServiceInvoice customerServiceInvoice;
    @JoinColumn(name = "ns_id", referencedColumnName = "netsales_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NetSales netSales;

    public NetsalesJCustomerServiceInvoice() {
    }

    public NetsalesJCustomerServiceInvoice(NetsalesJCustomerServiceInvoicePK netsalesJCustomerServiceInvoicePK) {
        this.netsalesJCustomerServiceInvoicePK = netsalesJCustomerServiceInvoicePK;
    }

    public NetsalesJCustomerServiceInvoice(long nsId, long invoiceId) {
        this.netsalesJCustomerServiceInvoicePK = new NetsalesJCustomerServiceInvoicePK(nsId, invoiceId);
    }

    public NetsalesJCustomerServiceInvoicePK getNetsalesJCustomerServiceInvoicePK() {
        return netsalesJCustomerServiceInvoicePK;
    }

    public void setNetsalesJCustomerServiceInvoicePK(NetsalesJCustomerServiceInvoicePK netsalesJCustomerServiceInvoicePK) {
        this.netsalesJCustomerServiceInvoicePK = netsalesJCustomerServiceInvoicePK;
    }

    public Double getSettlePercentage() {
        return settlePercentage;
    }

    public void setSettlePercentage(Double settlePercentage) {
        this.settlePercentage = settlePercentage;
    }

    public CustomerServiceInvoice getCustomerServiceInvoice() {
        return customerServiceInvoice;
    }

    public void setCustomerServiceInvoice(CustomerServiceInvoice customerServiceInvoice) {
        this.customerServiceInvoice = customerServiceInvoice;
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
        hash += (netsalesJCustomerServiceInvoicePK != null ? netsalesJCustomerServiceInvoicePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetsalesJCustomerServiceInvoice)) {
            return false;
        }
        NetsalesJCustomerServiceInvoice other = (NetsalesJCustomerServiceInvoice) object;
        if ((this.netsalesJCustomerServiceInvoicePK == null && other.netsalesJCustomerServiceInvoicePK != null) || (this.netsalesJCustomerServiceInvoicePK != null && !this.netsalesJCustomerServiceInvoicePK.equals(other.netsalesJCustomerServiceInvoicePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.NetsalesJCustomerServiceInvoice[ netsalesJCustomerServiceInvoicePK=" + netsalesJCustomerServiceInvoicePK + " ]";
    }
    
}
