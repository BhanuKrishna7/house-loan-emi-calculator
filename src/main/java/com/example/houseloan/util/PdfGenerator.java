package com.example.houseloan.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generateEmiReport(String name, double principal, double rate, int months,
                                           double emi, double totalPayment, double totalInterest) throws Exception {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
                cs.newLineAtOffset(50, 750);
                cs.showText("House Loan EMI Report");
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(50, 720);
                cs.showText("Name: " + name);
                cs.newLineAtOffset(0, -18);
                cs.showText(String.format("Principal: %.2f", principal));
                cs.newLineAtOffset(0, -18);
                cs.showText(String.format("Annual Rate (%%): %.2f", rate));
                cs.newLineAtOffset(0, -18);
                cs.showText("Tenure (months): " + months);
                cs.newLineAtOffset(0, -18);
                cs.showText(String.format("EMI: %.2f", emi));
                cs.newLineAtOffset(0, -18);
                cs.showText(String.format("Total Payment: %.2f", totalPayment));
                cs.newLineAtOffset(0, -18);
                cs.showText(String.format("Total Interest: %.2f", totalInterest));
                cs.endText();
            }

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                doc.save(baos);
                return baos.toByteArray();
            }
        }
    }
}