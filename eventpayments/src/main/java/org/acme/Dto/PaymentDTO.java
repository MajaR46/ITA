package org.acme.Dto;

import org.acme.Vao.Payment;

import java.time.LocalDateTime;

public class PaymentDTO {
    private String id;
    private String userId;

    private String ticketId;
    private double amount;
    private LocalDateTime paymentDate;
    private Payment.PaymentType paymentType;

    public PaymentDTO() {
        // Default constructor
    }

    public PaymentDTO(String id, String userId, String ticketId, double amount, LocalDateTime paymentDate, Payment.PaymentType paymentType) {
        this.id = id;
        this.userId = userId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Payment.PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Payment.PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    // Convert Payment entity to PaymentResponseDTO
    public static PaymentDTO fromPayment(Payment payment) {
        return new PaymentDTO(
                payment.id.toString(),
                payment.getUserId(),
                payment.getTicketId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentType()
        );
    }
}
