package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.ProductVariation;
import az.crocusoft.ecommerce.repository.ProductRepository;
import az.crocusoft.ecommerce.repository.ProductVariationRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ProductRepository productRepository;

    public void generateProductWithVariationExcel(HttpServletResponse httpServletResponse) throws IOException {
        List<Product> products = productRepository.findAllWithVariations();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("Product with Variation Info");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Product Id");
        row.createCell(1).setCellValue("Product Name");
        row.createCell(2).setCellValue("Product Title");
        row.createCell(3).setCellValue("Product Published");
        row.createCell(4).setCellValue("Product New");
        row.createCell(5).setCellValue("Product Description");
        row.createCell(6).setCellValue("Product Long Description");
        row.createCell(7).setCellValue("Product Main Image");
        row.createCell(8).setCellValue("Variation Id");
        row.createCell(9).setCellValue("Variation SKU");
        row.createCell(10).setCellValue("Variation Color");
        row.createCell(11).setCellValue("Variation Size");
        row.createCell(12).setCellValue("Variation Price");
        row.createCell(13).setCellValue("Variation Discount");
        row.createCell(14).setCellValue("Variation Stock Quantity");

        int dataRowIndex = 1;

        for (Product product : products) {
            if (!product.getVariations().isEmpty()) {
                ProductVariation productVariation = product.getVariations().get(0);
                HSSFRow dataRow = sheet.createRow(dataRowIndex);
                dataRow.createCell(0).setCellValue(product.getId());
                dataRow.createCell(1).setCellValue(product.getName());
                dataRow.createCell(2).setCellValue(product.getTitle());
                dataRow.createCell(3).setCellValue(product.isPublished());
                dataRow.createCell(4).setCellValue(product.isNewProduct());
                dataRow.createCell(5).setCellValue(product.getDescription());
                dataRow.createCell(6).setCellValue(product.getLongDescription());
                dataRow.createCell(7).setCellValue(product.getMainImage().getId());
                dataRow.createCell(8).setCellValue(productVariation.getProductVariationiId());
                dataRow.createCell(9).setCellValue(productVariation.getSku());
                dataRow.createCell(10).setCellValue(productVariation.getColor());
                dataRow.createCell(11).setCellValue(productVariation.getSize());
                dataRow.createCell(12).setCellValue(productVariation.getPrice());
                dataRow.createCell(13).setCellValue(productVariation.getDiscount());
                dataRow.createCell(14).setCellValue(productVariation.getStockQuantity());

                dataRowIndex++;
            }
        }

        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        hssfWorkbook.write(outputStream);
        hssfWorkbook.close();
        outputStream.close();
    }



}
