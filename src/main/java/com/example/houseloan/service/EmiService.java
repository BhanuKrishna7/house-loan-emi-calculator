package com.example.houseloan.service;

import org.springframework.stereotype.Service;

@Service
public class EmiService {

    // Returns EMI, total payment, total interest
    public EmiResult calculateEmi(double principal, double annualRatePercent, int tenureMonths) {
        double monthlyRate = annualRatePercent / 1200.0; // percent to monthly fraction
        double emi;
        if (monthlyRate == 0) {
            emi = principal / tenureMonths;
        } else {
            double factor = Math.pow(1 + monthlyRate, tenureMonths);
            emi = principal * monthlyRate * factor / (factor - 1);
        }
        double totalPayment = emi * tenureMonths;
        double totalInterest = totalPayment - principal;
        return new EmiResult(emi, totalPayment, totalInterest);
    }

    public static class EmiResult {
        private final double emi;
        private final double totalPayment;
        private final double totalInterest;

        public EmiResult(double emi, double totalPayment, double totalInterest) {
            this.emi = emi;
            this.totalPayment = totalPayment;
            this.totalInterest = totalInterest;
        }

        public double getEmi() { return emi; }
        public double getTotalPayment() { return totalPayment; }
        public double getTotalInterest() { return totalInterest; }
    }
}