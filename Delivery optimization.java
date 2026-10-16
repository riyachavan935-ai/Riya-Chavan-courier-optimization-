package com.courier.algorithm;
import java.util.*;

public class DeliveryOptimizer {
    
    public static class Node implements Comparable<Node> {
        String city;
        int distance;
        List<String> path;

        public Node(String city, int distance, List<String> path) {
            this.city = city;
            this.distance = distance;
            this.path = new ArrayList<>(path);
            this.path.add(city);
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    // DIJKSTRA'S SHORTEST PATH ALGORITHM
    public static Map<String, Object> dijkstra(Graph graph, String source, String destination) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, List<String>> paths = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        for(String city : graph.getAdjList().keySet()) {
            dist.put(city, Integer.MAX_VALUE);
        }
        dist.put(source, 0);
        paths.put(source, Arrays.asList(source));
        pq.add(new Node(source, 0, new ArrayList<>()));

        while(!pq.isEmpty()) {
            Node current = pq.poll();
            if(current.distance > dist.get(current.city)) continue;
            if(current.city.equals(destination)) {
                Map<String, Object> result = new HashMap<>();
                result.put("distance", current.distance);
                result.put("path", current.path);
                return result;
            }
            for(Graph.Edge edge : graph.getAdjList().getOrDefault(current.city, new ArrayList<>())) {
                int newDist = current.distance + edge.weight;
                if(newDist < dist.getOrDefault(edge.destination, Integer.MAX_VALUE)) {
                    dist.put(edge.destination, newDist);
                    pq.add(new Node(edge.destination, newDist, current.path));
                }
            }
        }
        return null;
    }

    // COST OPTIMIZATION ALGORITHM
    public static double calculateOptimizedCost(int originalDist, int optimizedDist, double weight, String serviceType) {
        double baseCost = originalDist * 2.5 + weight * 12;
        double optimizedCost = optimizedDist * 2.0 + weight * 10;
        
        // Optimization discounts
        double discount = 0;
        if(optimizedDist < 100) discount += 20;
        if(optimizedDist < originalDist) discount += (originalDist - optimizedDist) * 1.5;
        if(weight < 3) discount += 10;
        
        optimizedCost -= discount;
        
        // Service type charges
        if(serviceType.equals("Express Delivery")) optimizedCost += 50;
        if(serviceType.equals("Same Day")) optimizedCost += 100;
        
        return Math.max(optimizedCost, 50); // Minimum cost
    }

    // TIME OPTIMIZATION
    public static double calculateDeliveryTime(int optimizedDistance, String serviceType) {
        double time = optimizedDistance / 50.0; // 50km/hr avg
        if(serviceType.equals("Express Delivery")) time *= 0.7;
        if(serviceType.equals("Same Day")) time *= 0.5;
        return time;
    }

    // MULTIPLE ROUTE COMPARISON
    public static void compareRoutes(Graph graph, String from, String to) {
        System.out.println("\n=== Route Comparison for " + from + " -> " + to + " ===");
        Map<String, Object> result = dijkstra(graph, from, to);
        if(result != null) {
            System.out.println("Shortest Distance: " + result.get("distance") + " KM");
            System.out.println("Optimized Path: " + result.get("path"));
        }
    }
}
