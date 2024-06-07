package io.softwarestrategies.eventmgr.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
public class ApiController {

    @GetMapping
    public ResponseEntity<Object> getHeartBeat() {
        return ResponseEntity.ok("hi");
    }
}
