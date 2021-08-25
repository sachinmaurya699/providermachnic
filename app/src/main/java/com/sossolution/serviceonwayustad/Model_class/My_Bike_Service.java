package com.sossolution.serviceonwayustad.Model_class;

public class My_Bike_Service
{
   String id;
   String maker;
   String model;
   String service;
   String charge;
   String logo;
   String date;
   Boolean selected;
    private boolean isSelected;

    public boolean  getSelected1()
    {
        return isSelected;
    }

    public void setSelected1(boolean selected)
    {
        isSelected = selected;
    }


    public Boolean getselected()
    {
        return selected;
    }

    public void setselected(Boolean isselected)
    {
        this.selected = isselected;
    }




    public My_Bike_Service()
    {

    }

  /*  .........................get Mathod..............................*/

    public String getId()
    {
        return id;
    }
    public String getMaker()
    {
        return maker;
    }
    public String getModel()
    {
        return model;
    }
    public  String getService()
    {
        return service;
    }
    public  String getCharge()
    {
        return charge;
    }
    public  String getLogo()
    {
        return logo;
    }
    public  String getDate()
    {
        return date;
    }


   /* ..............................................Post Method.....................................*/

   public void  setId(String id)
   {
       this.id=id;
   }
   public void  setMaker(String maker)
   {
       this.maker=maker;
   }
   public  void setModel(String model)
   {
       this.model=model;
   }
   public void  setService(String service)
   {
       this.service=service;
   }
   public void setCharge(String charge)
   {
       this.charge=charge;
   }
   public void setLogo(String logo)
   {
       this.logo=logo;
   }
   public void setDate(String date)
   {
       this.date=date;
   }





}

