package com.sossolution.serviceonwayustad.Model_class;

public class Carprice
{

    String service;
    String price;
    String Maker;
    String MOdel;
    String updated_car_price;

    public String getUpdated_car_price()
    {
        return updated_car_price;
    }

    public void setUpdated_car_price(String updated_car_price)
    {
        this.updated_car_price = updated_car_price;
    }





    public String getMaker() {
        return Maker;
    }

    public void setMaker(String maker) {
        Maker = maker;
    }

    public String getMOdel() {
        return MOdel;
    }

    public void setMOdel(String MOdel) {
        this.MOdel = MOdel;
    }

    String Id;
    Boolean item_selected_car=false;

    public Boolean getItem_selected_car()
    {
        return item_selected_car;
    }

    public void setItem_selected_car(Boolean item_selected)
    {
        this.item_selected_car = item_selected;
    }

    public String getId()
    {
        return Id;
    }

    public void setId(String id)
    {
        Id = id;
    }

    public Carprice()
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
