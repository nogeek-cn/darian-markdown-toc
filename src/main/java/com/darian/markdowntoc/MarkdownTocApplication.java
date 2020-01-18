package com.darian.markdowntoc;

import com.darian.markdowntoc.files.GeneratorFromFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarkdownTocApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarkdownTocApplication.class, args);
        GeneratorFromFile.getGitHubUrl();
    }

}
