package com.portal.consumer.core.controller;

import com.google.gson.Gson;
import com.portal.consumer.core.common.PageObject;
import com.portal.consumer.util.WriterFileCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rabbit-consumer")
public class LoggerController {

    @Autowired
    private WriterFileCSV writerFileCSV;

    @Value("${path.file.csv}")
    private String pathFolder;

    @GetMapping("/download-log")
    public ResponseEntity<Resource> downloadCsv(@RequestParam(value = "pathFile", defaultValue = "") String pathFile) throws IOException {
        if (pathFile.trim().length() == 0) {
            return ResponseEntity.notFound().build();
        }
        String pathFileLogActive = pathFolder + pathFile;

        File file = new File(pathFileLogActive);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/read-log/all")
    public ResponseEntity<List<Map<String, String>>> readFileLogAll(@RequestBody Map<String, String> filterData,
                                                @RequestParam(name = "orderBy", defaultValue = "0") Long sortBy
                                                                 ) {
        List<Map<String, String>> datas = writerFileCSV.readFileCSVAll(filterData, sortBy);
        return ResponseEntity.ok()
                .body(datas);
    }

    @PostMapping("/read-log/page")
    public ResponseEntity<String> readFileLogPage(@RequestBody Map<String, String> filterData,
                                                                 @RequestParam(name = "orderBy", defaultValue = "0") Long sortBy,
                                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "50") int size
    ) {
        PageObject datas = writerFileCSV.readFileCSVAllPage(filterData, sortBy, page, size);
        Gson gson = new Gson();
        return ResponseEntity.ok()
                .body(gson.toJson(datas));
    }

}
