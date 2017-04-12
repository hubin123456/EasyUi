package com.jandar.pojo;

public class kucun extends Page{
    private int kucunId; //货物id
    private String goodsName;
    private String goodsImage;
    private String goodsLei;
    private String goodsUnit;
    private String goodsPrice;
    private String goodsbeizhu;
    private String supplierName;
    private String warehouseName; //仓库名
   
    private String createTime; //入库时间
    private String kucunNumber; //货物数量
    private String kucunyujing1;
    private String kucunyujing;
    public int getKucunId() {
        return kucunId;
    }
    public void setKucunId(int kucunId) {
        this.kucunId = kucunId;
    }
    
    public String getWarehouseName() {
        return warehouseName;
    }
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getKucunNumber() {
        return kucunNumber;
    }
    public void setKucunNumber(String kucunNumber) {
        this.kucunNumber = kucunNumber;
    }
    public String getKucunyujing1() {
        return kucunyujing1;
    }
    public void setKucunyujing1(String kucunyujing1) {
        this.kucunyujing1 = kucunyujing1;
    }
    public String getKucunyujing() {
        return kucunyujing;
    }
    public void setKucunyujing(String kucunyujing) {
        this.kucunyujing = kucunyujing;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsImage() {
        return goodsImage;
    }
    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }
    public String getGoodsLei() {
        return goodsLei;
    }
    public void setGoodsLei(String goodsLei) {
        this.goodsLei = goodsLei;
    }
    public String getGoodsUnit() {
        return goodsUnit;
    }
    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }
    public String getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public String getGoodsbeizhu() {
        return goodsbeizhu;
    }
    public void setGoodsbeizhu(String goodsbeizhu) {
        this.goodsbeizhu = goodsbeizhu;
    }
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
 
}
