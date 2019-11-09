package com.fileloader;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @RequestMapping(method = RequestMethod.GET, name = "/test")
    public String test() {
        return "test";
    }

    @RequestMapping(method = RequestMethod.POST, name = "/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        Resource resource = file.getResource();
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

        parts.add("file", resource);
        HttpEntity<MultiValueMap<String, Object>> httpEntity =
                new HttpEntity<>(parts, httpHeaders);

        return  restTemplate.postForEntity("http://localhost:8090/uploadMultipart", httpEntity, String.class);
    }
}
