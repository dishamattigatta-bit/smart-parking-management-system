# 🅿️ Smart Parking Management System

A Java Spring Boot web application to manage parking slots, vehicle entry/exit, and automatic fee calculation using DSA concepts.

## ✨ Features
- Park vehicle with automatic slot allocation (Min-Heap)
- Exit vehicle with automatic fee calculation
- Real-time available slots tracking
- Total collection tracking
- Complete parking history

## 🛠️ Tech Stack
- Java 17, Spring Boot 3.2.0
- Spring Data JPA, Thymeleaf, HTML/CSS
- H2 In-Memory Database
- Maven

## 📊 DSA Used
- **Min-Heap (PriorityQueue)** - To always give the lowest available slot number first
- **HashMap** - To quickly find parked vehicle by number O(1)
- **ArrayList** - To store all parking records

## 💰 Pricing
- CAR - Rs 50 / hour
- BIKE - Rs 20 / hour
- Minimum 1 hour charged

## 🚀 How to Run
1. Open in GitHub Codespaces
2. Run: `mvn spring-boot:run`
3. Open Port 8080 in browser
