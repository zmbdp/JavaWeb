package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProblemDAO;
import dao.ProblemInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/problem")
public class ProblemServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("application/json;charset=utf8");
        ProblemDAO problemDAO = new ProblemDAO();

        // 尝试获取 id 参数. 如果能获取到, 说明是获取题目详情; 如果不能获取到, 说明是获取题目列表.
        String idString = req.getParameter("id");
        if (idString == null || idString.isEmpty()) {
            List<ProblemInfo> problemInfoList = problemDAO.selectAll();
            String respStr = objectMapper.writeValueAsString(problemInfoList);
            resp.getWriter().write(respStr);
        } else {
            // 那就说明是详情页
            // 那就直接返回题目的详情就好了
            ProblemInfo problemInfo = problemDAO.selectOne(Integer.parseInt(idString));
            String respStr = objectMapper.writeValueAsString(problemInfo);
            resp.getWriter().write(respStr);
        }
    }
}
