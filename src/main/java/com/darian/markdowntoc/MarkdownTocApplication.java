package com.darian.markdowntoc;

import com.darian.markdowntoc.files.GeneratorFromFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * -DFILE_PATH=D:\GitHub_Repositories\docs -DWRITE_TO_FILE=true
 *
 *
 */
@SpringBootApplication
public class MarkdownTocApplication {

    public static void main(String[] args) {
//        GeneratorFromFile.FILE_PATH = "D:\\GitHub_Repositories\\docs";
//        GeneratorFromFile.WRITE_TO_FILE = true;
//        GeneratorFromFile.README_DIRECTORY_DOC =
//                "D:\\GitHub_Repositories\\darian1996.github.io\\docs\\README.md";
//        GeneratorFromFile.PATTERN_STRING = "\\\\*\\.md|\\\\*\\.bat";
        SpringApplication.run(MarkdownTocApplication.class, args);
        GeneratorFromFile.getGitHubUrl();
    }

}
