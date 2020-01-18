package com.darian.markdowntoc.houbb.test;

import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;
import com.github.houbb.markdown.toc.vo.TocGen;

import java.util.List;

/***
 * 原引用的目录的 test 测试类
 *
 * {@link #fileList(String)} 给某个文件夹下边的所有的 `.md` 文件每一个都添加目录
 * {@link #file(String)}  给某一个 `.md` 文件添加目录
 *
 * @author <a href="1934849492@qq.com">Darian</a>
 * @date 2020/1/18  11:24
 */
public class TestMdAddMuLu {
    public static void main(String[] args) {
        String fileListPath = "D:\\GitHub_Repositories\\docs\\other_video\\23_设计模式";
        fileList(fileListPath);

//        String filepath = "D:\\GitHub_Repositories\\happy-life\\README.md";
//        file(filepath);
    }

    private static void file(String filepath) {
        TocGen tocGen = AtxMarkdownToc.newInstance()
                .genTocFile(filepath);
        System.out.println(tocGen);
    }

    private static void fileList(String fileListPath) {
        List<TocGen> tocGenList = AtxMarkdownToc.newInstance()
                .genTocDir(fileListPath);
//        System.out.println(tocGenList);
        System.out.println(tocGenList.size());
        tocGenList.forEach(System.out::println);
    }
}
