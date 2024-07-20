package com.yash.ReportGen.Entity;

public class OutputData {
	private String outfield1;
	private String outfield2;
	private String outfield3;
	private Double outfield4;
	private Double outfield5;
	
	public OutputData() {
		
	}

	public OutputData(String outfield1, String outfield2, String outfield3, Double outfield4, Double outfield5) {
		super();
		this.outfield1 = outfield1;
		this.outfield2 = outfield2;
		this.outfield3 = outfield3;
		this.outfield4 = outfield4;
		this.outfield5 = outfield5;
	}

	public String getOutfield1() {
		return outfield1;
	}

	public void setOutfield1(String outfield1) {
		this.outfield1 = outfield1;
	}

	public String getOutfield2() {
		return outfield2;
	}

	public void setOutfield2(String outfield2) {
		this.outfield2 = outfield2;
	}

	public String getOutfield3() {
		return outfield3;
	}

	public void setOutfield3(String outfield3) {
		this.outfield3 = outfield3;
	}

	public Double getOutfield4() {
		return outfield4;
	}

	public void setOutfield4(Double outfield4) {
		this.outfield4 = outfield4;
	}

	@Override
	public String toString() {
		return "outputData [outfield1=" + outfield1 + ", outfield2=" + outfield2 + ", outfield3=" + outfield3
				+ ", outfield4=" + outfield4 + ", outfield5=" + outfield5 + "]";
	}

	public Double getOutfield5() {
		return outfield5;
	}

	public void setOutfield5(Double outfield5) {
		this.outfield5 = outfield5;
	}
	
}
