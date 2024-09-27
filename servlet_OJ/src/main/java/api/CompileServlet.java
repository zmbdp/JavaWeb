package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import compile.Answer;
import compile.Question;
import compile.Task;
import dao.ProblemDAO;
import dao.ProblemInfo;
import utils.CodeInValidException;
import utils.ProblemNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@WebServlet("/compile")
public class CompileServlet extends HttpServlet {
    static class CompileRequest {
        public int id;
        public String code;
    }

    static class CompileResponse {
        // 约定 error 为 0 表示编译运行 ok, error 为 1 表示编译出错, error 为 2 表示运行异常(用户提交的代码异常了), 3 表示其他错误
        public int error;
        public String reason;
        public String stdout;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    // 这个用来返回代码的运行结果给前端
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 临时加一下这个代码, 来获取到 SmartTomcat 的工作目录
        System.out.println("用户的当前工作目录: "+System.getProperty("user.dir"));

        CompileRequest compileRequest = null;
        CompileResponse compileResponse = new CompileResponse();
        try {
            resp.setStatus(200);
            resp.setContentType("application/json;charset=utf8");
            // 1. 先读取请求的正文. 别按照 JSON 格式进行解析
            String body = readBody(req);
            compileRequest = objectMapper.readValue(body, CompileRequest.class);
            // 2. 根据 id 从数据库中查找到题目的详情 => 得到测试用例代码
            ProblemDAO problemDAO = new ProblemDAO();
            ProblemInfo problemInfo = problemDAO.selectOne(compileRequest.id);
            if (problemInfo == null) {
                // 为了统一处理错误, 在这个地方抛出一个异常.
                throw new ProblemNotFoundException();
            }
            // testCode 是测试用例的代码
            String testCode = problemInfo.getTestCode();
            // requestCode 是用户提交的代码
            String requestCode = compileRequest.code;
            // 3. 把用户提交的代码和测试用例代码, 给拼接成一个完整的代码.
            String finalCode = mergeCode(requestCode, testCode);
            if (finalCode == null) {
                throw new CodeInValidException();
            }
            // System.out.println(finalCode);
            // 4. 创建一个 Task 实例, 调用里面的 compileAndRun 来进行编译运行.
            Task task = new Task();
            Question question = new Question();
            question.setCode(finalCode);
            Answer answer = task.compileAndRun(question);
            // 5. 根据 Task 运行的结果, 包装成一个 HTTP 响应
            compileResponse.error = answer.getError();
            compileResponse.reason = answer.getReason();
            compileResponse.stdout = answer.getStdout();
        } catch (ProblemNotFoundException e) {
            // 处理题目没有找到的异常
            compileResponse.error = 3;
            compileResponse.reason = "没有找到指定的题目! id=" + compileRequest.id;
        } catch (CodeInValidException e) {
            compileResponse.error = 3;
            compileResponse.reason = "提交的代码不符合要求!";
        } finally {
            String respString = objectMapper.writeValueAsString(compileResponse);
            resp.getWriter().write(respString);
        }
    }

    // 这个是用来拼接代码的
    private static String mergeCode(String requestCode, String testCode) {
        // 1. 查找 requestCode 中的最后一个 }
        int pos = requestCode.lastIndexOf("}");
        if (pos == -1) {
            // 说明提交的代码完全没有 } , 显然是非法的代码.
            return null;
        }
        // 2. 根据这个位置进行字符串截取
        String subStr = requestCode.substring(0, pos);
        // 3. 进行拼接
        return subStr + testCode + "\n}";
    }


    // 返回 body 中的数据
    private static String readBody(HttpServletRequest req) throws UnsupportedEncodingException {
        // 1. 先根据 请求头 里面的 ContentLength 获取到 body 的长度(单位是字节)
        int contentLength = req.getContentLength();
        // 2. 按照这个长度准备好一个 byte[] .
        byte[] buffer = new byte[contentLength];
        // 3. 通过 req 里面的 getInputStream 方法, 获取到 body 的流对象.
        try (InputStream inputStream = req.getInputStream()) {
            // 4. 基于这个流对象, 读取内容, 然后把内容放到 byte[] 数组中即可.
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 5. 把这个 byte[] 的内容构造成一个 String
        return new String(buffer, StandardCharsets.UTF_8);// 指定编码格式，别到时候搞错了
    }
}
