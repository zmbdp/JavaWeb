package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommandUtil {
    // 这个类呢主要是用来创建新的进程，
    // 然后这个进程是把一群 fw 写的代码放到一个文件夹里面，
    // 然后看看 javac 运行的对不对，返回给客户端

    /**
     * @param cmd        执行文件的路径
     * @param stdoutFile 标准输入的文件路径
     * @param stderrFile 标准输出的文件路径
     * @return 进程的状态
     */
    public static int run(String cmd, String stdoutFile, String stderrFile) {
        try {
            // 直接就运行这个指令了，这个指令是 javac..... 就直接转换成 .class 文件了
            Process process = Runtime.getRuntime().exec(cmd);
            // 获取标准输出，写到指定文件中
            if (stdoutFile != null) {
                InputStream stdoutFrom = process.getInputStream();
                FileOutputStream stdoutTo = new FileOutputStream(stdoutFile);
                while (true) {
                    int ch = stdoutFrom.read();
                    if (ch == -1) {
                        break;
                    }
                    // 读取到标准输入
                    stdoutTo.write(ch);
                }
                // 关闭文件
                stdoutFrom.close();
                stdoutTo.close();
            }

            // 获取标准错误，写道指定文件中
            if (stderrFile != null) {
                // 开始读取！！！
                InputStream stderrFrom = process.getErrorStream();
                FileOutputStream stderrTo = new FileOutputStream(stderrFile);
                while (true) {
                    int ch = stderrFrom.read();
                    if (ch == -1) {
                        break;
                    }
                    // 那就读取到文件中
                    stderrTo.write(ch);
                }
                // 关闭文件
                stderrFrom.close();
                stderrTo.close();
            }

            // 开始进程等待，看看是否正确
            int exitCode = process.waitFor();
            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}