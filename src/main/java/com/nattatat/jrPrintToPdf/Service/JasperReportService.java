package com.nattatat.jrPrintToPdf.Service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.pdfa.PdfADocument;

import java.io.*;

@Service
public class JasperReportService{

    protected final static Logger log = LogManager.getLogger(JasperReportService.class);

    public void convertJrprintToPdf(String jrPrintFilePath, String pdfOutputPath) {
        log.info("\noutput: " + pdfOutputPath);
        try {
            JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(jrPrintFilePath);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Set font properties
            JRDesignStyle style = new JRDesignStyle();
            style.setFontName("Arial");
            style.setFontSize(12);
            style.setBold(true);
            style.setItalic(false);

            // Set font to the entire report
            JRDesignTextField textField = new JRDesignTextField();
            textField.setStyle(style);

            // Set the export font map for PDF
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
                        
            exporter.exportReport();

            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfOutputPath);

            byte[] byteArray = outputStream.toByteArray();

            // Save the byte array to a file (optional)
            try (FileOutputStream fileOutputStream = new FileOutputStream(pdfOutputPath)) {
                fileOutputStream.write(byteArray);
                System.out.println("PDF file saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("PDF exported successfully.");
        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("Error exporting to PDF.");
        }
    }

    public void convertJrprintToPdfa(String jrPrintFilePath, String pdfOutputPath) throws FileNotFoundException {
        try {
            // Load .jrprint file
            JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(new File(jrPrintFilePath));
            FileOutputStream fileOutputStream = new FileOutputStream(pdfOutputPath);

            // Create PdfAWriter
            PdfWriter pdfWriter = new PdfWriter(fileOutputStream);
            PdfADocument pdfADocument = new PdfADocument(pdfWriter, PdfAConformanceLevel.PDF_A_1B,
                    new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", null));

            // Create JasperReports exporter
            JRPdfExporter exporter = new JRPdfExporter();

            // Configure exporter
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fileOutputStream);
            exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");

            // Export the report to PDF/A
            exporter.exportReport();

            // Close the PdfADocument
            pdfADocument.close();

            System.out.println("PDF exported successfully.");
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToPdf(JasperPrint jasperPrint, String outputFilePath) throws Exception {
        // Export JasperPrint to PDF.
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);
    }
}
