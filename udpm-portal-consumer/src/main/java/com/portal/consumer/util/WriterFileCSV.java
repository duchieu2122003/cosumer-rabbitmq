package com.portal.consumer.util;

import com.portal.consumer.core.common.PageObject;
import com.portal.consumer.infrastructure.constant.EntityConstant;
import com.portal.consumer.infrastructure.constant.SortByConstant;
import com.portal.consumer.infrastructure.exception.rest.RestApiException;
import com.portal.consumer.infrastructure.rabbit.PropertiesReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WriterFileCSV {

    public String getPropertiesRead(String constant) {
        PropertiesReader po = new PropertiesReader();
        return po.getProperty(constant);
    }

    public void writeFileLog(Map<String, String> data) {
        if (data.isEmpty() || !data.containsKey(EntityConstant.PATH_FILE)) {
            throw new RestApiException("Không tìm thấy thuộc tính pathFile. 😵‍💫😵‍💫😵‍💫");
        }

        String pathFile = getPropertiesRead("path.file.csv") + data.get(EntityConstant.PATH_FILE);

        File file = new File(pathFile);
        File parentDir = file.getParentFile();

        if (!parentDir.exists() && parentDir.mkdirs()) {
            System.out.println("Thư mục đã được tạo: " + parentDir);
        } else if (!parentDir.exists()) {
            System.err.println("Không thể tạo thư mục: " + parentDir);
        }

        List<String> values = data.values()
                .stream()
                .filter(value -> !value.equals(EntityConstant.PATH_FILE))
                .collect(Collectors.toList());

        if (!values.isEmpty()) {
            try (CSVPrinter csvPrinter = CSVFormat.DEFAULT
                    .withSkipHeaderRecord()
                    .print(new FileWriter(pathFile, true))) {
                csvPrinter.printRecord(values);
                System.out.println("======================================================");
                System.out.println("================= Path file: " + pathFile);
                System.out.println("======================================================");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //
//    // tạo file csv
//    public void createFileCSV(String pathFile) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true));
//             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
//            System.out.println("______ File CSV đã được tạo với path: " + pathFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public List<Map<String, String>> readFileCSVAll(Map<String, String> filterData, Long orderByTime) {
        List<Map<String, String>> list = new ArrayList<>();

        if (filterData.isEmpty() || !filterData.containsKey(EntityConstant.PATH_FILE)) {
            return list; // Trả về danh sách trống nếu filterData trống hoặc không chứa đường dẫn tệp.
        }

        String filePath = getPropertiesRead("path.file.csv") + filterData.get(EntityConstant.PATH_FILE);

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            List<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Map<String, String> data = new HashMap<>();
                boolean check = true;

                for (Map.Entry<String, String> entry : filterData.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String dataCsv = csvRecord.get(data.size());

                    if (value == null) {
                        data.put(key, dataCsv);
                    } else if (value != null && (value.trim().isEmpty() || dataCsv.contains(value))) {
                        data.put(key, dataCsv);
                    } else {
                        check = false;
                        break; // Thoát khỏi vòng lặp nếu không khớp
                    }
                }

                if (check) {
                    list.add(data);
                }
            }

            if (orderByTime.equals(SortByConstant.CREATE_TIME_DESC)) {
                Collections.reverse(list);
            }
//            if (isPage) {
//                List<Map<String, String>> listPage = list.stream()
//                        .skip(page * size)
//                        .limit(size)
//                        .collect(Collectors.toList());
//                return listPage;
//            }
        } catch (FileNotFoundException e) {
            throw new RestApiException("Dữ liệu không tồn tại trong hệ thống");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(e.getMessage());
        }
        return list;
    }

    public PageObject readFileCSVAllPage(Map<String, String> filterData, Long orderByTime, int page, int size) {
        PageObject pageObject = new PageObject();
        if (filterData.isEmpty() || !filterData.containsKey(EntityConstant.PATH_FILE)) {
            return pageObject;
        }

        String filePath = getPropertiesRead("path.file.csv") + filterData.get(EntityConstant.PATH_FILE);

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            List<Map<String, String>> list = new ArrayList<>();
            List<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Map<String, String> data = new HashMap<>();
                boolean check = true;

                for (Map.Entry<String, String> entry : filterData.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String dataCsv = csvRecord.get(data.size());

                    if (value == null) {
                        data.put(key, dataCsv);
                    } else if (value != null && (value.trim().isEmpty() || dataCsv.contains(value))) {
                        data.put(key, dataCsv);
                    } else {
                        check = false;
                        break; // Thoát khỏi vòng lặp nếu không khớp
                    }
                }

                if (check) {
                    list.add(data);
                }
            }

            if (orderByTime.equals(SortByConstant.CREATE_TIME_DESC)) {
                Collections.reverse(list);
            }

            int totalPages = (int) Math.ceil((double) list.size() / size);
            List<Map<String, String>> listPage = list.stream()
                    .skip(page * size)
                    .limit(size)
                    .collect(Collectors.toList());

            pageObject.setData(listPage);
            pageObject.setCurrentPage(page);
            pageObject.setTotalPages(totalPages);

        } catch (FileNotFoundException e) {
            throw new RestApiException("Dữ liệu không tồn tại trong hệ thống");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestApiException(e.getMessage());
        }
        return pageObject;
    }

}
