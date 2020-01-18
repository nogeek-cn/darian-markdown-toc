package com.darian.markdowntoc;



import java.util.regex.Pattern;

import static com.darian.markdowntoc.files.GeneratorFromFile.*;

/***
 *
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/19  6:43
 */
public class AllTest {
    public static void main(String[] args) {
        // 正则表达式
        PATTERN_STRING = "\\\\*\\.md";
        Pattern pattern = Pattern.compile(PATTERN_STRING);
        assertTrue(pattern.matcher("asdfasfda.md").find(), "EL表达式不对");
        assertTrue(!pattern.matcher("asdfasfdamdmd").find(), "EL表达式不对");

        PATTERN_STRING="\\\\*\\.md|\\\\*\\.bat";
        pattern = Pattern.compile(PATTERN_STRING);
        assertTrue(pattern.matcher("asdfasfda.md").find(), "EL表达式不对");
        assertTrue(!pattern.matcher("asdfasfdamdmd").find(), "EL表达式不对");
        assertTrue(pattern.matcher("asdfasfda.bat").find(), "EL表达式不对");
        assertTrue(!pattern.matcher("asdfasfdamdbat").find(), "EL表达式不对");

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
