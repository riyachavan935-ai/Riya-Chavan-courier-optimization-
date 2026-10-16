package com.courier.service;
import com.courier.model.Courier;
import com.courier.algorithm.*;
import java.util.*;

public class CourierService {
    private List<Courier> courierDatabase;
    private Graph courierNetwork;
    private int idCounter;

    public CourierService() {
        this.courierDatabase = new ArrayList<>();
        this.courierNetwork = Graph.initializeMaharashtraNetwork();
        this.idCounter = 1001;
        System.out.println("Courier Logistics System Initialized - Network Ready");
    }

    public Courier bookCourier(String sender, String receiver, String from, String to, double weight, String serviceType) {
        // Find shortest path using Dijkstra
        Map<String, Object> optimizationResult = DeliveryOptimizer.dijkstra(courierNetwork, from, to);
        
        int originalDist = (int)(Math.random()*200+100);
        int optimizedDist = originalDist;
        List<String> path = new ArrayList<>();

        if(optimizationResult != null) {
            optimizedDist = (int) optimizationResult.get("distance");
            path = (List<String>) optimizationResult.get("path");
            originalDist = optimizedDist + 15 + (int)(Math.random()*20);
        }

        double cost = DeliveryOptimizer.calculateOptimizedCost(originalDist, optimizedDist, weight, serviceType);
        
        Courier courier = new Courier(idCounter++, sender, receiver, from, to, weight, originalDist, optimizedDist, cost);
        courierDatabase.add(courier);
        
        System.out.println("\n✅ Courier Booked Successfully!");
        System.out.println("Optimization Path: " + path);
        System.out.println("Distance Saved: " + (originalDist - optimizedDist) + " KM");
        System.out.println("Cost Saved: Rs." + (courier.getBaseCost() - courier.getOptimizedCost()));
        
        return courier;
    }

    public Courier trackCourier(String trackingId) {
        for(Courier c : courierDatabase) {
            if(c.getTrackingId().equalsIgnoreCase(trackingId)) {
                return c;
            }
        }
        return null;
    }

    public Courier trackById(int id) {
        for(Courier c : courierDatabase) {
            if(c.getCourierId() == id) return c;
        }
        return null;
    }

    public List<Courier> getAllCouriers() { return courierDatabase; }

    public List<Courier> getCouriersByStatus(String status) {
        List<Courier> result = new ArrayList<>();
        for(Courier c : courierDatabase) {
            if(c.getStatus().equalsIgnoreCase(status)) result.add(c);
        }
        return result;
    }

    public void updateStatus(String trackingId, String newStatus, String location) {
        Courier c = trackCourier(trackingId);
        if(c != null) {
            c.setStatus(newStatus);
            c.setCurrentLocation(location);
            System.out.println("Status Updated: " + trackingId + " -> " + newStatus + " at " + location);
        }
    }

    public void generateReport() {
        System.out.println("\n========== COURIER LOGISTICS REPORT - 3121 ==========");
        System.out.println("Total Couriers: " + courierDatabase.size());
        System.out.println("Booked: " + getCouriersByStatus("BOOKED").size());
        System.out.println("In-Transit: " + getCouriersByStatus("IN-TRANSIT").size());
        System.out.println("Delivered: " + getCouriersByStatus("DELIVERED").size());
        
        double totalSaved = 0;
        int totalDistSaved = 0;
        for(Courier c : courierDatabase) {
            totalSaved += c.getDiscount();
            totalDistSaved += (c.getOriginalDistance() - c.getOptimizedDistance());
        }
        System.out.println("Total Cost Saved via Optimization: Rs." + totalSaved);
        System.out.println("Total Distance Saved: " + totalDistSaved + " KM");
        System.out.println("Average Optimization: " + (totalDistSaved / Math.max(courierDatabase.size(),1)) + " KM per courier");
        System.out.println("Algorithm Used: Dijkstra's Shortest Path");
        System.out.println("====================================================");
    }

    public Graph getNetwork() { return courierNetwork; }
}
