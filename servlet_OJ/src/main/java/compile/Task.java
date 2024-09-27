package compile;

import utils.CommandUtil;
import utils.FileUtil;

import java.io.File;
import java.util.UUID;

public class Task {
    // 约定所有临时文件存放的目录
    private String WORK_DIR = null;
    // 约定代码的类名
    private String CLASS_NAME = null;
    // 约定要编译的代码文件名.
    private String CODE_FILE = null;
    // 约定存放编译错误信息的文件名
    private String COMPILE_ERROR = null;
    // 约定存放运行时的标准输出的文件名
    private String STDOUT = null;
    // 约定存放运行时的标准错误的文件名
    private String STDERR = null;


    public Task() {
        // 在 Java 中使用 UUID 这个类就能生成一个 UUID 了
        WORK_DIR = "./tmp/" + UUID.randomUUID().toString() + "/";// 代码啊，编译结果这些东西存放的目录
        System.out.println(WORK_DIR);
        CLASS_NAME = "Solution";// 代码的类名
        CODE_FILE = WORK_DIR + "Solution.java";// 前端抛过来的代码放到这个文件里面
        COMPILE_ERROR = WORK_DIR + "compileError.txt";// 错误信息的文件名
        STDOUT = WORK_DIR + "stdout.txt";// 标准输出的文件名
        STDERR = WORK_DIR + "stderr.txt";// 标准错误的文件名
    }

    // 这个类表示运行的代码 编译+运行
    // 主要是做运行 Java 源代码的任务
    // 返回值: 表示编译运行的结果，编译出错，运行出错，运行正确这些....
    public Answer compileAndRun(Question question) {
        Answer answer = new Answer();
        // 0. 准备好用来存放临时文件的目录
        File workDir = new File(WORK_DIR);
        if (!workDir.exists()) {
            // 创建多级目录.
            workDir.mkdirs();
        }
        // 1. 把 compile.Question 的 code 写入到 Solution.java 文件中。
        // 2. 然后创建子进程，调用 javac 进行编译，注意！ 编译的时候得有个 .java 的文件。
        //      如果编译出错，javac 就会把错误信息写入到 stderr 里面，然后专门搞一个文件来保存，这个文件就叫 compileError.txt
        // 3. 创建子进程，调用 javac 命令执行，
        //      运行程序的时候，会获取到标准输出和标准错误，然后会写入到 stdout.txt 和 stderr.txt
        // 4. 父进程获取刚才编译执行的结果，并打包成 compile.Answer 对象返回给 controller

        // 然后再把这个 compile.Question 的 code 给写入到 CODE_POW 这个文件中
        FileUtil.writeFile(CODE_FILE, question.getCode());

        // 然后创建子进程，进行编译这些操作
        // 先设置一个命令
        String compileCmd = String.format("javac -encoding utf8 %s -d %s", CODE_FILE, WORK_DIR);
        System.out.println("编译命令: " + compileCmd);// 打印一下日志
        // 然后再执行这个命令
        // 对于 javac 这个命令来说，我们不关心标准输出，只关心标准错误，
        // 一旦编译出错，就会通过标准错误给反馈出来
        CommandUtil.run(compileCmd, null, COMPILE_ERROR);
        String compileError = FileUtil.readFile(COMPILE_ERROR);
        // 然后直接看看这个编译出错的文件空不空，就知道有没有出错了
        if (!compileError.isEmpty()) {
            System.err.println("编译出错!!!");
            // 说明编译出错了
            answer.setError(1);
            answer.setReason(compileError);
            return answer;
        }
        // 编译完成之后得开始运行了吧？？
        String runCmd = String.format("java -classpath %s %s", WORK_DIR, CLASS_NAME);
        System.out.println("运行命令: " + runCmd);
        CommandUtil.run(runCmd, STDOUT, STDERR);
        String runError = FileUtil.readFile(STDERR);
        if (!runError.isEmpty()) {
            // 说明运行出错了
            System.err.println("运行错误!!!");
            answer.setError(2);
            answer.setReason(runError);
            return answer;
        }
        answer.setError(0);
        answer.setStdout(FileUtil.readFile(STDOUT));
        return answer;
    }
}
