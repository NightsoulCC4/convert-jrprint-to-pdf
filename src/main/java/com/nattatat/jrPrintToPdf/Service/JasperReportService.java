package com.nattatat.jrPrintToPdf.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.pdfa.PdfADocument;

import java.io.*;

@Service
public class JasperReportService {

    protected final static Logger log = LogManager.getLogger(JasperReportService.class);

    public void convertJrprintToPdf(String jrPrintFilePath, String pdfOutputPath) {
        log.info("\noutput: " + pdfOutputPath);
        try {

            // read jasper file.
            JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(jrPrintFilePath);

            // convert file output to stream for exporting (Optional for exporter parameter).
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Set the exporter (Optional).
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");

            // Export file with custom exporter.
            exporter.exportReport();

            // Convert jasper file to pdf and export.
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfOutputPath);

            System.out.println("PDF exported successfully.");
        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("Error exporting to PDF.");
        }
    }

    public void convertJrprintToPdfa(String jrPrintFilePath, String pdfOutputPath) throws FileNotFoundException {
        convertJrprintToPdf(jrPrintFilePath, pdfOutputPath);
        try {
            String file_path = pdfOutputPath;
            File file = new File(file_path);
            // FileOutputStream fileOutputStream = new FileOutputStream(file_path);

            // Create PdfAWriter
            PdfWriter pdfWriter = new PdfWriter(file);
            PdfADocument pdfADocument = new PdfADocument(pdfWriter, PdfAConformanceLevel.PDF_A_1B,
                    new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", null));

            // Close the PdfADocument
            pdfADocument.close();
            pdfWriter.close();

            System.out.println("PDF exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
