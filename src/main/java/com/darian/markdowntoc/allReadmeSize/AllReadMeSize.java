package com.darian.markdowntoc.allReadmeSize;

import com.darian.markdowntoc.files.GeneratorFromFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/***
 *
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/18  20:30
 */
public class AllReadMeSize {
    public static void main(String[] args) throws Exception {
        String fileListPath = "D:\\GitHub_Repositories\\docs";

        getAllReadMeGenerator(fileListPath);

        Long fileSize = 0L;

        Path path = Paths.get(fileListPath + "\\ALL_README.md");
        Files.write(path, RESULT_STRING_LIST, Charset.forName("UTF-8"));
    }


    public static List<String> getAllReadMeGenerator(String path) throws Exception {

        String GITHUB_URL_PRE = GeneratorFromFile.getGitHubUrlPre(path);
        String GITEE_URL_PRE = GeneratorFromFile.getGiteeUrlPre(path);

        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            File itemFile = tempList[i];
            if (itemFile.isFile()) {

                // 完整的路径, 从盘符开始一直计算
                String fileFullName = tempList[i].toString();
                // 文件名，不包含路径
                FILEList.add(fileFullName);
                String fileName = itemFile.getName();
                if (Pattern.compile(PATTERN_STRING).matcher(fileName).find()) {
                    String pathPreAndFile = fileFullName.substring(path.length())
                            .replaceAll("\\\\", "/").replaceAll(" ", "%20");

                    String outLineString = "- " + fileName;
                    if (GITHUB_URL_PRE != null && !GITHUB_URL_PRE.isBlank()) {
                        outLineString += "  [GitHub](" + GITHUB_URL_PRE + pathPreAndFile + ") ";
                    }
                    if (GITEE_URL_PRE != null && !GITEE_URL_PRE.isBlank()) {
                        outLineString += "  [Gitee](" + GITEE_URL_PRE + pathPreAndFile + ") ";
                    }
//                    System.out.println(outLineString);
                    RESULT_STRING_LIST.add(outLineString);
                    StringBuilder fileVaule_builder = new StringBuilder();
                    FileInputStream fileInputStream = new FileInputStream(fileFullName);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    String temp;
                    while ((temp = bufferedReader.readLine()) != null) {//一次读一行
                        fileVaule_builder.append(temp);
                    }
                    String fileValue = fileVaule_builder.toString();
                    fileValue = fileValue.replaceAll(" ", "");
                    fileValue = fileValue.replaceAll("	", "");
                    fileValue = "  -  > " + fileValue;
                    RESULT_STRING_LIST.add(fileValue);
                }

            }
            //
            if (tempList[i].isDirectory()) {
                // 过滤的文件夹
                if (GeneratorFromFile.INGORE_DIRECTORIES.contains(tempList[i].getName())) {
                    continue;
                }

                getAllReadMeGenerator(tempList[i].toString());
            }
        }
        return FILEList;
    }

    /**
     * 需要匹配规则的文件  例子 .md 结尾的文件
     */
    static String PATTERN_STRING = "\\\\*\\.md";

    static List<String> RESULT_STRING_LIST = new ArrayList<>();

    static List<String> FILEList = new ArrayList<>();
}
