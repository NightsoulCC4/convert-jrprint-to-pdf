package com.nattatat.jrPrintToPdf.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class JasperReportService {

    protected final static Logger log = LogManager.getLogger(JasperReportService.class);

     public void convertJrprintToPdf(String jrPrintFilePath, String pdfOutputPath) {
        try {
            // Load the .jrprint file
            JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObjectFromFile(jrPrintFilePath);

            // Export JasperPrint to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfOutputPath);

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
