package com.sossolution.serviceonwayustad.Model_class;

public class My_Car_Service
{
    String id;
    String maker;
    String model;
    String service;
    String charge;
    String logo;
    String date;
    private boolean isSelected;

    public Boolean getALl_item_Selected(boolean b)
    {
        return ALl_item_Selected;
    }

    public void setALl_item_Selected(Boolean ALl_item_Selected)
    {
        this.ALl_item_Selected = ALl_item_Selected;
    }

    private  Boolean ALl_item_Selected=false;


    public boolean  getSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }



    public My_Car_Service()
    {

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMaker()
    {
        return maker;
    }

    public void setMaker(String maker)
    {
        this.maker = maker;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getCharge()
    {
        return charge;
    }

    public void setCharge(String charge)
    {
        this.charge = charge;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
    /*............geter.......................*/

}
