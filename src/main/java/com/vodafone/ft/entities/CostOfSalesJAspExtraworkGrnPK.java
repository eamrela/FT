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
public class CostOfSalesJAspExtraworkGrnPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "cos_id")
    private long cosId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grn_id")
    private long grnId;

    public CostOfSalesJAspExtraworkGrnPK() {
    }

    public CostOfSalesJAspExtraworkGrnPK(long cosId, long grnId) {
        this.cosId = cosId;
        this.grnId = grnId;
    }

    public long getCosId() {
        return cosId;
    }

    public void setCosId(long cosId) {
        this.cosId = cosId;
    }

    public long getGrnId() {
        return grnId;
    }

    public void setGrnId(long grnId) {
        this.grnId = grnId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cosId;
        hash += (int) grnId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostOfSalesJAspExtraworkGrnPK)) {
            return false;
        }
        CostOfSalesJAspExtraworkGrnPK other = (CostOfSalesJAspExtraworkGrnPK) object;
        if (this.cosId != other.cosId) {
            return false;
        }
        if (this.grnId != other.grnId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.CostOfSalesJAspExtraworkGrnPK[ cosId=" + cosId + ", grnId=" + grnId + " ]";
    }
    
}
