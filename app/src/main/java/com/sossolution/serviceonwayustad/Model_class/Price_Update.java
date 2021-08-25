package com.sossolution.serviceonwayustad.Model_class;

public class Price_Update
{

    String service;
    String Maker;
    String Model;

    public String getMaker()
    {
        return Maker;
    }

    public void setMaker(String maker)
    {
        Maker = maker;
    }

    public String getModel()
    {
        return Model;
    }

    public void setModel(String model)
    {
        Model = model;
    }

    String price;
    String Id;
    Boolean item_selected=false;
    String updated_price;

    public String getUpdated_price() {
        return updated_price;
    }

    public void setUpdated_price(String updated_price) {
        this.updated_price = updated_price;
    }

    public String getPrice_update() {
        return price_update;
    }

    public void setPrice_update(String price_update) {
        this.price_update = price_update;
    }

    String price_update;

    public Boolean getItem_selected()
    {
        return item_selected;
    }

    public void setItem_selected(Boolean item_selected)
    {
        this.item_selected = item_selected;
    }

    public String getId()
    {
        return Id;
    }

    public void setId(String id)
    {
        Id = id;
    }

    public Price_Update()
    {

    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }
}
