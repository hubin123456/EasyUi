package com.jandar.pojo;

public class warehouse extends Page{
       private String warehouseName; //仓库名
       private String warehouseAddress; //仓库地址
       private String warehouseSize; //仓库大小
       private String warehouseCompany; //所属公司
       private String createTime;
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getWarehouseAddress() {
		return warehouseAddress;
	}
	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}
	public String getWarehouseSize() {
		return warehouseSize;
	}
	public void setWarehouseSize(String warehouseSize) {
		this.warehouseSize = warehouseSize;
	}
	public String getWarehouseCompany() {
		return warehouseCompany;
	}
	public void setWarehouseCompany(String warehouseCompany) {
		this.warehouseCompany = warehouseCompany;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
