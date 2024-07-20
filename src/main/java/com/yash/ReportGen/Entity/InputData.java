package com.yash.ReportGen.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InputData {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private Double field5;
	private String refkey1;
	private String refkey2;
	
	public InputData() {
		
	}

	public InputData(String field1, String field2, String field3, String field4, Double field5, String refkey1,
			String refkey2) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
		this.refkey1 = refkey1;
		this.refkey2 = refkey2;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public Double getField5() {
		return field5;
	}

	public void setField5(Double field5) {
		this.field5 = field5;
	}

	public String getRefkey1() {
		return refkey1;
	}

	public void setRefkey1(String refkey1) {
		this.refkey1 = refkey1;
	}

	public String getRefkey2() {
		return refkey2;
	}

	public void setRefkey2(String refkey2) {
		this.refkey2 = refkey2;
	}

	@Override
	public String toString() {
		return "inputData [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + ", field4=" + field4
				+ ", field5=" + field5 + ", refkey1=" + refkey1 + ", refkey2=" + refkey2 + "]";
	}
	
	
}
