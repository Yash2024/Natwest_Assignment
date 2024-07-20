package com.yash.ReportGen.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReferenceData {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String refkey1;
	private String refdata1;
	private String refkey2;
	private String refdata2;
	private String refdata3;
	private Double refdata4;
	
	public ReferenceData() {
		
	}

	public ReferenceData(String refkey1, String refdata1, String refkey2, String refdata2, String refdata3,
			Double refdata4) {
		super();
		this.refkey1 = refkey1;
		this.refdata1 = refdata1;
		this.refkey2 = refkey2;
		this.refdata2 = refdata2;
		this.refdata3 = refdata3;
		this.refdata4 = refdata4;
	}

	public String getRefkey1() {
		return refkey1;
	}

	public void setRefkey1(String refkey1) {
		this.refkey1 = refkey1;
	}

	public String getRefdata1() {
		return refdata1;
	}

	public void setRefdata1(String refdata1) {
		this.refdata1 = refdata1;
	}

	public String getRefkey2() {
		return refkey2;
	}

	public void setRefkey2(String refkey2) {
		this.refkey2 = refkey2;
	}

	public String getRefdata2() {
		return refdata2;
	}

	public void setRefdata2(String refdata2) {
		this.refdata2 = refdata2;
	}

	public String getRefdata3() {
		return refdata3;
	}

	public void setRefdata3(String refdata3) {
		this.refdata3 = refdata3;
	}

	public Double getRefdata4() {
		return refdata4;
	}

	public void setRefdata4(Double refdata4) {
		this.refdata4 = refdata4;
	}

	@Override
	public String toString() {
		return "referenceData [refkey1=" + refkey1 + ", refdata1=" + refdata1 + ", refkey2=" + refkey2 + ", refdata2="
				+ refdata2 + ", refdata3=" + refdata3 + ", refdata4=" + refdata4 + "]";
	}
	
}
