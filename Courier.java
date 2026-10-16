package com.courier.model;

import java.time.LocalDateTime;

public class Courier {
    private int courierId;
    private String trackingId;
    private String senderName;
    private String receiverName;
    private String senderContact;
    private String receiverContact;
    private String fromCity;
    private String toCity;
    private String fromAddress;
    private String toAddress;
    private double weight;
    private String packageType;
    private String serviceType;
    private int originalDistance;
    private int optimizedDistance;
    private double baseCost;
    private double optimizedCost;
    private double discount;
    private String status;
    private LocalDateTime bookingTime;
    private LocalDateTime estimatedDelivery;
    private String currentLocation;
    private String deliveryBoy;

    public Courier(int courierId, String senderName, String receiverName, 
                   String fromCity, String toCity, double weight, 
                   int originalDistance, int optimizedDistance, double cost) {
        this.courierId = courierId;
        this.trackingId = "CR" + courierId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.weight = weight;
        this.originalDistance = originalDistance;
        this.optimizedDistance = optimizedDistance;
        this.baseCost = originalDistance * 2.5 + weight * 10;
        this.optimizedCost = cost;
        this.discount = this.baseCost - this.optimizedCost;
        this.status = "BOOKED";
        this.bookingTime = LocalDateTime.now();
        this.estimatedDelivery = bookingTime.plusDays(2);
        this.currentLocation = fromCity + " Warehouse";
        this.deliveryBoy = "Not Assigned";
        this.packageType = "Standard";
        this.serviceType = "Standard Delivery";
    }

    // Getters
    public int getCourierId() { return courierId; }
    public String getTrackingId() { return trackingId; }
    public String getSenderName() { return senderName; }
    public String getReceiverName() { return receiverName; }
    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
    public String getRoute() { return fromCity + " -> " + toCity; }
    public double getWeight() { return weight; }
    public int getOriginalDistance() { return originalDistance; }
    public int getOptimizedDistance() { return optimizedDistance; }
    public double getOptimizedCost() { return optimizedCost; }
    public double getBaseCost() { return baseCost; }
    public double getDiscount() { return discount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String loc) { this.currentLocation = loc; }

    public void displayCourier() {
        System.out.println("=====================================");
        System.out.println("Courier ID: " + trackingId);
        System.out.println("Sender: " + senderName + " | Receiver: " + receiverName);
        System.out.println("Route: " + getRoute());
        System.out.println("Weight: " + weight + " KG");
        System.out.println("Original Distance: " + originalDistance + " KM");
        System.out.println("Optimized Distance (Dijkstra): " + optimizedDistance + " KM");
        System.out.println("Base Cost: Rs." + baseCost);
        System.out.println("Discount (Optimization): Rs." + discount);
        System.out.println("Final Cost: Rs." + optimizedCost);
        System.out.println("Status: " + status);
        System.out.println("Current Location: " + currentLocation);
        System.out.println("Estimated Delivery: " + estimatedDelivery);
        System.out.println("=====================================");
    }
}
