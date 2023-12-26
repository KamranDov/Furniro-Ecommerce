package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product")
    public void generateExcelReportProduct(HttpServletResponse response)throws Exception{
    response.setContentType("application/octet-stream");
    String headerKey="Content-Disposition";
    String headerValue="attachment;filename=products.xls";
    response.setHeader(headerKey,headerValue);
    reportService.generateProductWithVariationExcel(response);
    }
}
