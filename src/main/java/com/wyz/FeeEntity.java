package com.wyz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FeeEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String qishu;
	private Date hkrq;
	
	private BigDecimal hkje;
	
	private String remark;

	public String getQishu() {
		return qishu;
	}

	public void setQishu(String qishu) {
		this.qishu = qishu;
	}

	public Date getHkrq() {
		return hkrq;
	}

	public void setHkrq(Date hkrq) {
		this.hkrq = hkrq;
	}

	public BigDecimal getHkje() {
		return hkje;
	}

	public void setHkje(BigDecimal hkje) {
		this.hkje = hkje;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
