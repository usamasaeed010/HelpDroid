package com.tpxsofts.helpdroidx.Model;

public class HospitalDataForPopullation
{
     public String Name;
    public String Adress;
    public String number;


    public HospitalDataForPopullation(String name, String adress, String number) {
        Name = name;
        Adress = adress;
        this.number = number;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
