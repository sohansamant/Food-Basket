package com.cs442.ssamant4.foodbasket;

/*
 * Created by sohan on 11/13/2016.
 */


public class Handling_items
{
    private String title;
    private String body;
    private String price;

    static double total=0;
    static String totalQuantity="";


    public Handling_items(String title, String body, String price)
    {
        this.title = title;
        this.body = body;
        this.price=price;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getPrice(){return price;}

    @Override
    public String toString() {
        return getTitle();
    }

    public static double getTotal()
    {
        return total;
    }

    public static void setTotal(double i)
    {
        total += i;
    }

    public static String getQuantity()
    {
        return totalQuantity;
    }

    public static void setQuantity(String i)
    {
        totalQuantity += i;
    }

    public static void reset()
    {
        total=0;
        totalQuantity="";
    }

}
