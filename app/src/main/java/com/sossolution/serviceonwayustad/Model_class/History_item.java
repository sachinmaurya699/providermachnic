package com.sossolution.serviceonwayustad.Model_class;

public class History_item
{
   String date;
   String id;
   String address;
   String price;
   String Service;

   public void setService(String service)
   {
       this.Service=service;
   }
   public String getService()
   {
       return Service;
   }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
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
