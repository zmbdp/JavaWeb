package utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    /**
     * 负责把 filePath 对映文件的内容给读取出来，放到返回值中
     *
     * @param filePath 传过来的文件名字
     * @return 返回读取到的文件内容
     */
    public static String readFile(String filePath) {
        StringBuilder result = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath);) {
            // 然后循环加入到 result 里面就行
            while (true) {
                int ch = fileReader.read();
                if (ch == -1) {
                    break;
                }
                result.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    /**
     * 这个方法负责把 content 写入到 filePath 文件中
     *
     * @param filePath 写入到这个文件中
     * @param content 要写的数据
     */
    public static void writeFile(String filePath, String content) {
        // 先打开 endFile 文件再说
        try (FileWriter fileWriter = new FileWriter(filePath);) {
            // 然后再把起始文件写到 end 文件中
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
