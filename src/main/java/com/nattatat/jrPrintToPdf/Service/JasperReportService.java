package com.nattatat.jrPrintToPdf.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lowagie.text.Font;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class JasperReportService {

    protected final static Logger log = LogManager.getLogger(JasperReportService.class);

    public void convertJrprintToPdf(String jrPrintFilePath, String pdfOutputPath) {
        log.info("\noutput: " + pdfOutputPath);
        try {
            JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(jrPrintFilePath);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            /* Map<String, String> fontMap = new HashMap<>(); */

            // Set font settings for PDF export
            /* String pdfFont = FontFamily.getExportFont("Arial");
            fontMap.put("Arial", pdfFont); */

            // Set the export font map for PDF
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
            /* exporter.setParameter(JRPdfExporterParameter.FONT_MAP, fontMap); */

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

    public void exportToPdf(JasperPrint jasperPrint, String outputFilePath) throws Exception {
        // Export JasperPrint to PDF.
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePath);
    }
}
