# darian-markdown-doc

- 自动生成文件夹对应 GitHub / Gitee 上的目录

## 简介

- 生成目录结构
- 文件 后拼接 Gitee Github OtherUrl 
- 可以使用 `-D` 进行启动

## 参数说明

- FILE_PATH：需要打印目录的文件
- GIT_AUTHOR_NAME ： Git 的用户名
- GITHUB_AUTHOR_NAME： GitHub  的用户名 （优先于 GIT_AUTHOR_NAME ）
- GITEE_AUTHOR_NAME： Gitee 的用户名 （优先于 GIT_AUTHOR_NAME ）
- GIT_BRANCH：  分支名
- README_DIRECTORY_DOC：总体目录需要生成在哪里？ 默认 `当前文件夹 + README_DIRECTORY_DOC_FILENAME` 
- README_DIRECTORY_DOC_FILENAME：生成目录的文件的名字 （默认：`README_DIRECTORY_DOC.md`）
- OTHER_URL_PRE：其他的可能要生成的 URL （比如： GitPages）
- PATTERN_STRING：需要生成文件名的 正则表达式
- WRITE_TO_FILE：是否写入到文件 （默认 false，生成目录，请打开）；



## 用法

- 下载链接地址  [链接](https://gitee.com/Darian1996/darian-markdown-toc/blob/master/target/markdown-toc-0.0.1-SNAPSHOT.jar) 

- ```c
  java -DFILE_PATH=XXX -jar markdown-toc-0.0.1-SNAPSHOT.jar
  ```

## example

就会生成目录了

---

---

# Darian: 自动生成目录

- 2017
  - 2017-01-工程化专题
    - Maven-James.md  [GitHub](https://github.com/Darian1996/docs/tree/master/2017/2017-01-工程化专题/Maven-James.md)   [Gitee](https://gitee.com/Darian1996/docs/tree/master/2017/2017-01-工程化专题/Maven-James.md) 
  - 2017-05-01-什么是性能优化-james
    - 什么是性能优化.md  [GitHub](https://github.com/Darian1996/docs/tree/master/2017/2017-05-01-什么是性能优化-james/什么是性能优化.md)   [Gitee](https://gitee.com/Darian1996/docs/tree/master/2017/2017-05-01-什么是性能优化-james/什么是性能优化.md) 
  - 2017-05-02-性能优化-性能测试与优化-testOps云层
    - 2017-05-02-性能测试与优化-testOps云层.md  [GitHub](https://github.com/Darian1996/docs/tree/master/2017/2017-05-02-性能优化-性能测试与优化-testOps云层/2017-05-02-性能测试与优化-testOps云层.md)   [Gitee](https://gitee.com/Darian1996/docs/tree/master/2017/2017-05-02-性能优化-性能测试与优化-testOps云层/2017-05-02-性能测试与优化-testOps云层.md) 
  - 2017-05-03-性能优化JVM篇-james
    - 2017-05-03-01-JVM介绍.md  [GitHub](https://github.com/Darian1996/docs/tree/master/2017/2017-05-03-性能优化JVM篇-james/2017-05-03-01-JVM介绍.md)   [Gitee](https://gitee.com/Darian1996/docs/tree/master/2017/2017-05-03-性能优化JVM篇-james/2017-05-03-01-JVM介绍.md) 

---