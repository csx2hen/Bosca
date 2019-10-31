package com.bosca.file.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class FileController {

    //just for test
    @GetMapping
    public String getFiles() {
        return "files";
    }
}
