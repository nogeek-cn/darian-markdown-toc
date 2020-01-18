package com.darian.markdowntoc.files;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/***
 * 通过 windows 的目录文件夹，给总的目录文件夹生成，
 * 一个总览的带目录跳转的， MD 文件，
 * 生成的目的是为了给私有文档仓库生成目录文档。 README_PATH_DOC.md
 *
 * @author <a href="1934849492@qq.com">Darian</a>
 * @date 2020/1/18  11:46
 */
@Slf4j
public class GeneratorFromFile {
    /**
     * GitHub 的作者的名字，作者的名字，需要拼接在 URL 中
     * 优先级 ， GitHub / Gitee 优先于 Git
     */
    static String GIT_AUTHOR_NAME = "Darian1996";
    static String GITHUB_AUTHOR_NAME = null;
    static String GITEE_AUTHOR_NAME = null;
    /**
     * 生成的分支的名称，如果要生成其他分支，需要把 对应的文件夹，切换成对应的分支
     */
    static String GIT_BRANCH = "master";

    static String README_DIRECTORY_DOC = null;
    static String README_DIRECTORY_DOC_FILENAME = "README_DIRECTORY_DOC.md";

    static String FILE_PATH = "D:\\GitHub_Repositories\\docs\\other_video\\23_设计模式";
    static String GITHUB_URL_PRE = null;
    static String GITEE_URL_PRE = null;
    static String OTHER_URL_PRE = "";

    /**
     * 需要匹配规则的文件  例子 .md 结尾的文件
     */
    static String PATTERN_STRING = "\\\\*\\.md";

    static boolean WRITE_TO_FILE = false;

    static String GIT_PATH_FILE_NAME = ".git";


    /**
     * 可以跳过的文件夹的名称
     */
    static List<String> INGORE_DIRECTORIES = Arrays.asList(".git", ".idea", "TopNtest.class", "旧",
            "assets", "photo", "viso", "图片", "LoveHistory", "胜天半子");


    static List<String> RESULT_STRING_LIST = new ArrayList<>();

    static {
        RESULT_STRING_LIST.add("# Darian: 自动生成目录");
    }


    public static void main(String[] args) {
        FILE_PATH = "D:\\GitHub_Repositories\\docs";
        WRITE_TO_FILE = true;
        getGitHubUrl();
    }

    public static void initConfig() {
        GIT_AUTHOR_NAME = System.getProperty("GIT_AUTHOR_NAME", "Darian1996");
        GITHUB_AUTHOR_NAME = System.getProperty("GITHUB_AUTHOR_NAME", GIT_AUTHOR_NAME);
        GITEE_AUTHOR_NAME = System.getProperty("GITEE_AUTHOR_NAME", GIT_AUTHOR_NAME);

        GIT_BRANCH = System.getProperty("GIT_BRANCH", "master");
        README_DIRECTORY_DOC = System.getProperty("README_DIRECTORY_DOC");
        FILE_PATH = System.getProperty("FILE_PATH");
        if (FILE_PATH == null || FILE_PATH.isBlank()) {
            throw new RuntimeException("FILE_PATH is Blank");
        }

        README_DIRECTORY_DOC_FILENAME = System.getProperty("README_DIRECTORY_DOC_FILENAME", "README_DIRECTORY_DOC.md");
        OTHER_URL_PRE = System.getProperty("OTHER_URL_PRE");
        PATTERN_STRING = System.getProperty("PATTERN_STRING", "\\\\*\\.md");
        String WRITE_TO_FILE_STRING = System.getProperty("WRITE_TO_FILE", "false");
        WRITE_TO_FILE = "true".equalsIgnoreCase(WRITE_TO_FILE_STRING);

        log.info(this_InfoMsg());
    }

    private static String this_InfoMsg() {
        StringBuilder sb = new StringBuilder();
        sb.append("generatorInfo: {\n");
        sb.append("  GIT_AUTHOR_NAME : " + GIT_AUTHOR_NAME + "\n");
        sb.append("  GITHUB_AUTHOR_NAME : " + GITHUB_AUTHOR_NAME + "\n");
        sb.append("  GITEE_AUTHOR_NAME : " + GITEE_AUTHOR_NAME + "\n");
        sb.append("  GIT_BRANCH : " + GIT_BRANCH + "\n");
        sb.append("  README_DIRECTORY_DOC : " + README_DIRECTORY_DOC + "\n");
        sb.append("  FILE_PATH : " + FILE_PATH + "\n");
        if (README_DIRECTORY_DOC == null || README_DIRECTORY_DOC.isBlank()) {
            sb.append("    {\n");
            sb.append("      while generator to file ["
                    + FILE_PATH + GIT_PATH_FILE_NAME + "\\" + README_DIRECTORY_DOC_FILENAME
                    + "]\n");
            sb.append("    }\n");
        }
        sb.append("  OTHER_URL_PRE : " + OTHER_URL_PRE + "\n");
        if (OTHER_URL_PRE == null || OTHER_URL_PRE.isBlank()) {
            sb.append("    {\n");
            sb.append("      OTHER_URL_PRE is Blank\n");

            sb.append("    }\n");
        }
        sb.append("  PATTERN_STRING : " + PATTERN_STRING + "\n");
        sb.append("  WRITE_TO_FILE : " + WRITE_TO_FILE + "\n");
        if (WRITE_TO_FILE) {
            sb.append("    {\n");
            sb.append("      while write to File\n");
            sb.append("    }\n");
        } else {
            sb.append("    {\n");
            sb.append("      while not write to File\n");
            sb.append("    }\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public static void getGitHubUrl() {
        initConfig();
        assertTrue(isNotBlank(FILE_PATH), "FILE_PATH 不能为空");
        try {
            File file = new File(FILE_PATH);
            assertTrue(file.isDirectory(), AssertionException.PATH_MUST_BE_DIRECTORY);
            File[] tempList = file.listFiles();
            assertTrue(tempList != null, AssertionException.GIT_REPOSITORY_NAME_NOT_FOUND);

            filePathToUrlPre(FILE_PATH);

            getFileFullNameList(FILE_PATH);
            if (WRITE_TO_FILE) {
                if (isBlank(README_DIRECTORY_DOC)) {
                    README_DIRECTORY_DOC = FILE_PATH + "\\" + README_DIRECTORY_DOC_FILENAME;
                }
                Path path = Paths.get(README_DIRECTORY_DOC);
                Files.write(path, RESULT_STRING_LIST, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            log.error("exception: ", e);
        }
    }

    static List<String> files = new ArrayList<>();

    public static List<String> getFileFullNameList(String path) {
        return getFileFullNameList(path, 0);
    }

    /**
     * 文件夹，需要递归以后，根据目录的深度生成对应的目录，需要层次往后边依次增加
     *
     * @param path
     * @param deep 深度，需要拼接前边的空格
     * @return
     */
    public static List<String> getFileFullNameList(String path, int deep) {
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                // 完整的路径, 从盘符开始一直计算
                String fileFullName = tempList[i].toString();
                files.add(fileFullName);
                // 文件名，不包含路径
                String fileName = tempList[i].getName();
                if (Pattern.compile(PATTERN_STRING).matcher(fileName).find()) {
                    String pathPreAndFile = fileFullName.substring(FILE_PATH.length())
                            .replaceAll("\\\\", "/").replaceAll(" ", "%20");

                    String outLineString = "- " + fileName;
                    if (isNotBlank(GITHUB_URL_PRE)) {
                        outLineString += "  [GitHub](" + GITHUB_URL_PRE + pathPreAndFile + ") ";
                    }
                    if (isNotBlank(GITEE_URL_PRE)) {
                        outLineString += "  [Gitee](" + GITEE_URL_PRE + pathPreAndFile + ") ";
                    }
                    if (isNotBlank(OTHER_URL_PRE)) {
                        outLineString += "  [Other](" + OTHER_URL_PRE + pathPreAndFile + ") ";
                    }

                    for (int deepi = 0; deepi < deep; deepi++) {
                        outLineString = "  " + outLineString;
                    }

                    System.out.println(outLineString);
                    RESULT_STRING_LIST.add(outLineString);
                }

            }
            // 文件夹，需要递归以后，根据目录的深度生成对应的目录，需要层次往后边依次增加
            if (tempList[i].isDirectory()) {
                // 过滤的文件夹
                if (INGORE_DIRECTORIES.contains(tempList[i].getName())) {
                    continue;
                }
                String outString = "- " + tempList[i].getName();
                for (int deepi = 0; deepi < deep; deepi++) {
                    outString = "  " + outString;
                }

                System.out.println(outString);
                RESULT_STRING_LIST.add(outString);
                getFileFullNameList(tempList[i].toString(), deep + 1);
            }
        }
        return files;
    }

    /**
     * 根据 传进来的 filePath 生成这个 filePath 对应的 GitHub / Gitee 的 URl 的路径，
     * 这个将解析为整个文件夹在 网络上的 URL
     *
     * @param filePath
     */
    public static void filePathToUrlPre(String filePath) {
        String findGitRepositoryName = findGitRepositoryName(filePath);
        if (findGitRepositoryName == null) {
            log.error(AssertionException.GIT_REPOSITORY_NAME_NOT_FOUND);
            return;
        }

        String git_file_pre = filePath.substring(filePath.indexOf(findGitRepositoryName) + findGitRepositoryName.length())
                .replaceAll("\\\\", "/");

        GITHUB_AUTHOR_NAME = GITHUB_AUTHOR_NAME != null ? GITHUB_AUTHOR_NAME : GIT_AUTHOR_NAME;
        GITEE_AUTHOR_NAME = GITEE_AUTHOR_NAME != null ? GITEE_AUTHOR_NAME : GIT_AUTHOR_NAME;

        String github_repository_Url = "https://github.com/" + GITHUB_AUTHOR_NAME + "/" + findGitRepositoryName + "/tree/" + GIT_BRANCH;
        String gitee_repository_Url = "https://gitee.com/" + GITEE_AUTHOR_NAME + "/" + findGitRepositoryName + "/tree/" + GIT_BRANCH;

        GITHUB_URL_PRE = github_repository_Url + git_file_pre;
        GITEE_URL_PRE = gitee_repository_Url + git_file_pre;

        GITHUB_URL_PRE = GITHUB_URL_PRE.replaceAll("\\\\", "/").replaceAll(" ", "%20");
        GITEE_URL_PRE = GITEE_URL_PRE.replaceAll("\\\\", "/").replaceAll(" ", "%20");
    }

    /**
     * 根据 filePath 找到包含这个 filePath 的最近的 Git 仓库名
     *
     * @param filePath
     * @return
     */
    public static String findGitRepositoryName(String filePath) {
        String findGitRepositoryName = null;
        if (filePath.endsWith(":\\")) {
            log.error("已经找到了盘符：" + filePath);
            return null;
        }
        File file = new File(filePath);
        for (File fileItem : file.listFiles()) {
            if (fileItem.isDirectory()) {
                if (fileItem.getName().equals(GIT_PATH_FILE_NAME)) {
                    findGitRepositoryName = file.getName();
                    break;
                }
            }
        }
        if (findGitRepositoryName == null) {
            return findGitRepositoryName(file.getParent());
        }
        return findGitRepositoryName;
    }

    private static void assertTrue(boolean expresion, String errorMsg) {
        if (expresion != true)
            throw new AssertionException(errorMsg);
    }

    private static void assertTrue(boolean expresion) {
        if (expresion != true)
            throw new AssertionException();
    }

    private static boolean isNotBlank(String str) {
        return str != null && str.length() > 0;
    }

    private static boolean isBlank(String str) {
        return !isNotBlank(str);
    }

    static class AssertionException extends RuntimeException {
        public static String GIT_REPOSITORY_NAME_NOT_FOUND = "没有找到Git仓库";
        public static String PATH_MUST_BE_DIRECTORY = "path 必须是文件夹！";

        public AssertionException() {
        }

        public AssertionException(String msg) {
            super(msg);
        }
    }


    private static void test() {
        // 正则表达式
        PATTERN_STRING = "\\\\*\\.md";
        Pattern.compile(PATTERN_STRING);
        Pattern pattern = Pattern.compile(PATTERN_STRING);
        assertTrue(pattern.matcher("asdfasfda.md").find(), "EL表达式不对");
        assertTrue(!pattern.matcher("asdfasfdamdmd").find(), "EL表达式不对");

        // 找仓库
        assertTrue(findGitRepositoryName("D:\\GitHub_Repositories") == null);
        assertTrue("docs".equals(findGitRepositoryName("D:\\GitHub_Repositories\\docs\\other_video\\23_设计模式")));
        assertTrue("docs".equals(findGitRepositoryName("D:\\GitHub_Repositories\\docs\\other_video")));

        // URL 的前缀
        filePathToUrlPre("D:\\GitHub_Repositories");
        assertTrue(GITEE_URL_PRE == null && GITHUB_URL_PRE == null);
        filePathToUrlPre("D:\\GitHub_Repositories\\docs\\other_video\\23_设计模式");

        assertTrue(GITEE_URL_PRE.equals("https://gitee.com/Darian1996/docs/tree/master/other_video/23_设计模式"));
        assertTrue(GITHUB_URL_PRE.equals("https://github.com/Darian1996/docs/tree/master/other_video/23_设计模式"));

        filePathToUrlPre("D:\\GitHub_Repositories\\docs\\other_video");
        assertTrue(GITEE_URL_PRE.equals("https://gitee.com/Darian1996/docs/tree/master/other_video"));
        assertTrue(GITHUB_URL_PRE.equals("https://github.com/Darian1996/docs/tree/master/other_video"));

        getGitHubUrl();
        getFileFullNameList("D:\\GitHub_Repositories\\docs\\other_video");
        System.exit(1);
    }
}

