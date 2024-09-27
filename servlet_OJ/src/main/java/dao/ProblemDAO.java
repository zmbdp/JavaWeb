package dao;

import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 通过这个类封装了针对 ProblemInfo 的增删改查.
// 1. 新增题目
// 2. 删除题目
// 3. 查询题目列表
// 4. 查询题目详情
public class ProblemDAO {
    public void insert(ProblemInfo problemInfo) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1. 和数据库建立连接
            connection = DBUtil.getConnection();
            // 2. 构造 SQL 语句
            String sql = "insert into oj_table values(null, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, problemInfo.getTitle());
            statement.setString(2, problemInfo.getLevel());
            statement.setString(3, problemInfo.getDescription());
            statement.setString(4, problemInfo.getTemplateCode());
            statement.setString(5, problemInfo.getTestCode());
            // 3. 执行 SQL
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("题目新增失败!");
            } else {
                System.out.println("题目新增成功!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1. 和数据库建立连接
            connection = DBUtil.getConnection();
            // 2. 拼装 SQL 语句
            String sql = "delete from oj_table where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            // 3. 执行 SQL
            int ret = statement.executeUpdate();
            if (ret != 1) {
                System.out.println("删除题目失败!");
            } else {
                System.out.println("删除题目成功!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }

    // 这个操作是把当前题目列表中的所有题都查出来了
    // 万一数据库中的题目特别多, 咋办? 只要实现 "分页查询" 即可. 后台实现分页查询, 非常容易.
    // 前端传过来一个当前的 "页码" , 根据页码算一下, 依据 sql limit offset 语句, 要算出来 offset 是 几
    // 但是前端这里实现一个分页器稍微麻烦一些(比后端要麻烦很多). 此处暂时不考虑分页功能.
    public List<ProblemInfo> selectAll() {
        List<ProblemInfo> problemInfos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 1. 和数据库建立连接
            connection = DBUtil.getConnection();
            // 2. 拼装 SQL
            String sql = "select id, title, level from oj_table";
            statement = connection.prepareStatement(sql);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历 resultSet
            while (resultSet.next()) {
                // 每一行都是一个 ProblemInfo 对象
                ProblemInfo problemInfo = new ProblemInfo();
                problemInfo.setId(resultSet.getInt("id"));
                problemInfo.setTitle(resultSet.getString("title"));
                problemInfo.setLevel(resultSet.getString("level"));
                problemInfos.add(problemInfo);
            }
            return problemInfos;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    public ProblemInfo selectOne(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 1. 和数据库建立连接
            connection = DBUtil.getConnection();
            // 2. 拼接 SQL 语句
            String sql = "select * from oj_table where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历查询结果. (由于 id 是主键, 按照 id 查找的结果一定是唯一的)
            if (resultSet.next()) {
                ProblemInfo problemInfo = new ProblemInfo();
                problemInfo.setId(resultSet.getInt("id"));
                problemInfo.setTitle(resultSet.getString("title"));
                problemInfo.setLevel(resultSet.getString("level"));
                problemInfo.setDescription(resultSet.getString("description"));
                problemInfo.setTemplateCode(resultSet.getString("templateCode"));
                problemInfo.setTestCode(resultSet.getString("testCode"));
                return problemInfo;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    public static void main(String[] args) {
        ProblemDAO problemDAO = new ProblemDAO();
        ProblemInfo problem = new ProblemInfo();
        // problem.setId();
        problem.setTitle("两数之和");
        problem.setLevel("简单");
        problem.setDescription("给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。\n" +
                "\n" +
                "你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n" +
                "\n" +
                "你可以按任意顺序返回答案。\n" +
                "\n" +
                " \n" +
                "\n" +
                "示例 1：\n" +
                "\n" +
                "输入：nums = [2,7,11,15], target = 9\n" +
                "输出：[0,1]\n" +
                "解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。\n" +
                "示例 2：\n" +
                "\n" +
                "输入：nums = [3,2,4], target = 6\n" +
                "输出：[1,2]\n" +
                "示例 3：\n" +
                "\n" +
                "输入：nums = [3,3], target = 6\n" +
                "输出：[0,1]\n" +
                " \n" +
                "\n" +
                "提示：\n" +
                "\n" +
                "2 <= nums.length <= 104\n" +
                "-109 <= nums[i] <= 109\n" +
                "-109 <= target <= 109\n" +
                "只会存在一个有效答案\n" +
                "进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？\n" +
                "\n" +
                "来源：力扣（LeetCode）\n" +
                "链接：https://leetcode-cn.com/problems/two-sum\n" +
                "著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。");
        problem.setTemplateCode("class Solution {\n" +
                "    public int[] twoSum(int[] nums, int target) {\n" +
                "\n" +
                "    }\n" +
                "}");
        problem.setTestCode("    public static void main(String[] args) {\n" +
                "        Solution solution = new Solution();\n" +
                "        // testcase1\n" +
                "        int[] nums = {2,7,11,15};\n" +
                "        int target = 9;\n" +
                "        int[] result = solution.twoSum(nums, target);\n" +
                "        if (result.length == 2 && result[0] == 0 && result[1] == 1) {\n" +
                "            System.out.println(\"testcase1 OK\");\n" +
                "        } else {\n" +
                "            System.out.println(\"testcase1 failed!\");\n" +
                "        }\n" +
                "\n" +
                "        // testcase2\n" +
                "        int[] nums2 = {3,2,4};\n" +
                "        int target2 = 6;\n" +
                "        int[] result2 = solution.twoSum(nums2, target2);\n" +
                "        if (result2.length == 2 && result2[0] == 1 && result2[1] == 2) {\n" +
                "            System.out.println(\"testcase2 OK\");\n" +
                "        } else {\n" +
                "            System.out.println(\"testcase2 failed!\");\n" +
                "        }\n" +
                "    }\n");
        problemDAO.insert(problem);
        System.out.println("插入成功!");
    }
}
