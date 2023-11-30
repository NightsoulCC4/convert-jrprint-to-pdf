package com.nattatat.jrPrintToPdf.Controller;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nattatat.jrPrintToPdf.Service.JasperReportService;


@RestController
@CrossOrigin
public class PdfController {

    protected final static Logger log = LogManager.getLogger(PdfController.class);
    
    @Autowired
    JasperReportService jasperReportService;

    // These @Value annotation can be able to set at application.property.

    // 1. file_input_path = Path that store jrPrint file.
    @Value("${file_input_path}")
    private String file_input_path;

    // 2. file_output_path = Paht that destination pdf file.
    @Value("${file_output_path}")
    private String file_output_path;

    // 3. jrprint_file_name = .jrPrint file name that store in the file_input_path.
    @Value("${jrprint_file_name}")
    private String jrprint_file_name;

    // 4. output_file_name = .pdf file name that store in the file_output_path.
    @Value("${output_file_name}")
    private String output_file_name;

    @PostMapping("/convertJrprintToPdf")
    public String convertToPdf() {

        // merge path and file name for jasperprint and export file.
        String input = file_input_path + jrprint_file_name;
        String output = file_output_path + output_file_name;

        jasperReportService.convertJrprintToPdf(input, output);

        return "Conversion completed.";
    }

   @PostMapping("/convertJrprintToPdfa")
    public String convertToPdfa() throws FileNotFoundException{

        // merge path and file name for jasperprint and export file.
        String input = file_input_path + jrprint_file_name;
        String output = file_output_path + output_file_name;

        jasperReportService.convertJrprintToPdfa(input, output);

        return "Conversion completed.";
    }
}
