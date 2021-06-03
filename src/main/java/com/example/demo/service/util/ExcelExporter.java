package com.example.demo.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.Client;
import com.example.demo.model.User;
import com.example.demo.service.dto.ClientRespondDto;
import com.example.demo.service.dto.SocialInsuranceRespondDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExporter {

    public static ByteArrayInputStream clientsToExcel(List<ClientRespondDto> clients,
                                                      String province, String district, String commune, String hamlet,
                                                      SocialInsuranceRespondDto.Status status) throws IOException {
        LocalDate d = LocalDate.now();
        String month = d.getMonth() + "/" + d.getYear();
        String[] columns = {"Id", "Name", "Gender", "Date of birth", "Salary", "Address", "Paid money", "Money need to Paid"};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("clients");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            int i = 0;
            Row desRow = sheet.createRow(i);
            Cell des0 = desRow.createCell(i);
            des0.setCellValue("Báo cáo tháng " + month);
            if (province != null) {
                Cell des1 = desRow.createCell(++i);
                des1.setCellValue("Tỉnh/Thành phố: " + province);
            }
            if (district != null) {
                Cell des2 = desRow.createCell(++i);
                des2.setCellValue("Quận/Huyện: " + district);
            }
            if (commune != null) {
                Cell des3 = desRow.createCell(++i);
                des3.setCellValue("Phường/Xã: " + commune);
            }
            if (hamlet != null) {
                Cell des4 = desRow.createCell(++i);
                des4.setCellValue("Thôn/Xóm: " + hamlet);
            }
            if (status != null) {
                Cell des5 = desRow.createCell(++i);
                des5.setCellValue("Trạng thái: " + status);
            }

            Row headerRow = sheet.createRow(1);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(headerCellStyle);
            }

            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 2;
            double totalMoneyNeedToPaid = 0;
            double totalPaidMoney = 0;
            for (ClientRespondDto client : clients) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(client.getIdentityNumber());
                row.createCell(1).setCellValue(client.getName());
                row.createCell(2).setCellValue(client.getGender().toString());
                row.createCell(3).setCellValue(client.getDob().toString());
                row.createCell(4).setCellValue(client.getSalary());
                row.createCell(5).setCellValue(client.getAddress().toString());
                row.createCell(6).setCellValue(client.getMoneyPaidThisMonth());
                row.createCell(7).setCellValue(client.getMoneyNeedToPaidThisMonth());
                totalMoneyNeedToPaid += client.getMoneyNeedToPaidThisMonth();
                totalPaidMoney += client.getMoneyPaidThisMonth();
            }

            Row total = sheet.createRow(rowIdx + 1);
            total.createCell(0).setCellValue("Tổng");
            total.createCell(1).setCellValue(clients.size());
            total.createCell(6).setCellValue(totalPaidMoney);
            total.createCell(7).setCellValue(totalMoneyNeedToPaid);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}