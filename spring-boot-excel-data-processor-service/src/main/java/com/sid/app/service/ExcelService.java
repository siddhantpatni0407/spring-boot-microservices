package com.sid.app.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("PMD")
@Service
@Slf4j
public class ExcelService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void processExcelFile(MultipartFile file) {
        log.info("Processing Excel file - START");

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                createTableFromSheet(sheet);
                insertDataIntoTable(sheet);
            }
        } catch (IOException e) {
            log.error("Error processing Excel file: {}", e.getMessage());
        }

        log.info("Processing Excel file - END");
    }

    // Helper method to check if the content type corresponds to an Excel file
    public static boolean isExcelFile(String contentType) {
        return contentType != null && (contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    private void createTableFromSheet(Sheet sheet) {
        log.info("Creating table from sheet - START");
        String tableName = sheet.getSheetName().replaceAll("\\s+", "_");
        StringBuilder createTableQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (");

        Row headerRow = sheet.getRow(0);
        Iterator<Cell> cellIterator = headerRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String columnName = cell.getStringCellValue().replaceAll("\\s+", "_");

            switch (cell.getCellType()) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        createTableQuery.append(columnName).append(" DATE, ");
                    } else {
                        createTableQuery.append(columnName).append(" NUMERIC, ");
                    }
                    break;
                case BOOLEAN:
                    createTableQuery.append(columnName).append(" BOOLEAN, ");
                    break;
                default:
                    createTableQuery.append(columnName).append(" VARCHAR(255), ");
                    break;
            }
        }

        createTableQuery.delete(createTableQuery.length() - 2, createTableQuery.length());
        createTableQuery.append(")");

        try {
            jdbcTemplate.execute(createTableQuery.toString());
        } catch (DataAccessException e) {
            log.error("Error creating table: {}", e.getMessage());
        }
        log.info("Creating table from sheet - END");
    }


    public void insertDataIntoTable(Sheet sheet) {
        log.info("Inserting data into table - START");
        String tableName = sheet.getSheetName().replaceAll("\\s+", "_");
        log.info("Inserting data into table: {}", tableName);

        Row headerRow = sheet.getRow(0);
        int numberOfColumns = headerRow.getLastCellNum();
        List<String> columnNames = new ArrayList<>();
        for (int i = 0; i < numberOfColumns; i++) {
            Cell cell = headerRow.getCell(i);
            columnNames.add(cell.getStringCellValue());
        }

        StringBuilder insertQuery = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" (")
                .append(String.join(",", columnNames))
                .append(") VALUES (");

        for (int i = 0; i < numberOfColumns; i++) {
            insertQuery.append("?,");
        }
        insertQuery.deleteCharAt(insertQuery.length() - 1);
        insertQuery.append(")");

        try {
            jdbcTemplate.batchUpdate(insertQuery.toString(), new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Row row = sheet.getRow(i + 1);
                    for (int j = 0; j < numberOfColumns; j++) {
                        Cell cell = row.getCell(j);
                        if (cell.getCellType() == CellType.NUMERIC) {
                            ps.setObject(j + 1, cell.getNumericCellValue());
                        } else {
                            ps.setString(j + 1, cell.getStringCellValue());
                        }
                    }
                }

                @Override
                public int getBatchSize() {
                    return sheet.getLastRowNum();
                }
            });
            log.info("Data inserted successfully");
        } catch (DataAccessException e) {
            log.error("Error inserting data: {}", e.getMessage());
        }
        log.info("Inserting data into table - END");
    }

    public byte[] generateExcelFile(String tableName) throws IOException {
        log.info("Generating Excel file for table '{}'", tableName);

        try (Workbook workbook = new XSSFWorkbook()) {
            List<List<Object>> data = fetchDataFromDatabase(tableName);
            Sheet sheet = workbook.createSheet(tableName);

            int rowNum = 0;
            for (List<Object> rowData : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object cellData : rowData) {
                    Cell cell = row.createCell(colNum++);
                    if (cellData instanceof String) {
                        cell.setCellValue((String) cellData);
                    } else if (cellData instanceof Double) {
                        cell.setCellValue((Double) cellData);
                    } else if (cellData instanceof Integer) {
                        cell.setCellValue((Integer) cellData);
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            log.info("Excel file generated successfully for table '{}'", tableName);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error generating Excel file: {}", e.getMessage());
            throw e;
        }
    }

    private List<List<Object>> fetchDataFromDatabase(String tableName) {
        log.info("Fetching data from table '{}'", tableName);

        List<List<Object>> data = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        jdbcTemplate.query(query, (ResultSet rs) -> {
            try {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    List<Object> rowData = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        rowData.add(value);
                    }
                    data.add(rowData);
                }
            } catch (SQLException e) {
                log.error("Error fetching data: {}", e.getMessage());
            }
        });

        log.info("Fetched {} rows from table '{}'", data.size(), tableName);
        return data;
    }

}