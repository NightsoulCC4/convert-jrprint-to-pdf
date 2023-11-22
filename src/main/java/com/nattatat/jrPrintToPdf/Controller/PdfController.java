package com.nattatat.jrPrintToPdf.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nattatat.jrPrintToPdf.Service.JasperReportService;


@RestController
@CrossOrigin
@RequestMapping("/api/jasper")
public class PdfController {

    protected final static Logger log = LogManager.getLogger(PdfController.class);
    
    @Autowired
    JasperReportService jasperReportService;

    @Value("${file_input_path}")
    private String file_input_path;

    @Value("${file_output_path}")
    private String file_output_path;

    @Value("${jrprint_file_name}")
    private String jrprint_file_name;

    @Value("{output_file_name}")
    private String output_file_name;

    @PostMapping("/convert-to-pdf")
    public String convertToPdf() {

        jasperReportService.convertJrprintToPdf(file_input_path + jrprint_file_name, file_output_path + output_file_name);

        return "Conversion completed.";
    }
}
