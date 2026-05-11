package com.hyperlocal.marketplace.model;

import jakarta.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String serviceName;
    private String date;
    private String timeSlot;
    private String status;
    private String freelancerEmail; // ← NEW FIELD

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFreelancerEmail() { return freelancerEmail; }  // ← NEW
    public void setFreelancerEmail(String freelancerEmail) { this.freelancerEmail = freelancerEmail; } // ← NEW
}