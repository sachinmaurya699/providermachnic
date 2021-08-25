package com.sossolution.serviceonwayustad.MyInterface;

import com.sossolution.serviceonwayustad.Model_class.Price_Update;

public interface My_price_interface
{
    void my_price_update(Price_Update price_update);
    void on_item_price_check(String s1);
    void on_item_price_uncheck(String s2);
    void my_price_store(String s3);
    void  my_price_remove(String s4);
    void my_delete_list(String s5);
    void on_item_final_price(String s6);
}
