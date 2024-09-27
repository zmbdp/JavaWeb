package compile;

public class Answer {
    // 错误码. 约定 error 为 0 表示编译运行都 ok, 为 1 表示编译出错, 为 2 表示运行出错(抛异常).
    private int error;
    // 出错的提示信息. 如果 error 为 1, 编译出错了, reason 中就放编译的错误信息, 如果 error 为 2, 运行异常了, reason 就放异常信息
    private String reason;
    // 运行程序得到的标准输出的结果.
    private String stdout;
    // 运行程序得到的标准错误的结果.
    private String stderr;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    @Override
    public String toString() {
        return "compile.compile.Answer{" +
                "error=" + error +
                ", reason='" + reason + '\'' +
                ", stdout='" + stdout + '\'' +
                ", stderr='" + stderr + '\'' +
                '}';
    }
}
