package com.example.bucket4j.controller;

import com.example.bucket4j.dto.BucketDto;
import com.example.bucket4j.dto.Message;
import com.example.bucket4j.dto.StatusEnum;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BucketController {

    @Autowired
    private Bucket bucket;

    @GetMapping("/bucket")
    public ResponseEntity<Message> getBoardList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        if (bucket.tryConsume(1)) {
            message.setStatus(StatusEnum.OK);
            // 예제 데이터
            BucketDto bucketDto = new BucketDto();
            bucketDto.setName("Poppy");
            bucketDto.setColor("RED");
            bucketDto.setPetals("9");
            message.setData(bucketDto);
            message.setMessage("조회 성공");
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.TOO_MANY_REQUESTS);
            message.setMessage("조회 실패");
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
