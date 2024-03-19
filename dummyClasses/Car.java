package org.vehicles;

class Car {

    int wheels;

    int doors;

    String model;

    String ownerName;
    Bird b;
    double milesDriven;

    public Car() {

    }

    public void drive()
    {
        System.out.println("Vroom");
    }

    public void park()
    {
        System.out.println("Stopped vrooming");
    }

    public void turbo()
    {
        System.out.println("SUPER VROOM");
    }

    public void hyper()
    {
        System.out.println("HYPER VROOM");
    }

    public void lightspeed()
    {
        System.out.println("ENTERING HYPERSPACE");
    }
    
}