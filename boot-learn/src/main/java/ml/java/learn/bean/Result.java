package ml.java.learn.bean;

/**
 * @Auther: jiml
 * @Date: 2024/7/31 20:53
 * @Description:
 */


public class Result<T> {

    public static final String OK = "0";

    private String code;

    private String msg;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result<Void> ok() {
        Result<Void> r = new Result<Void>();
        r.code = ResultEnum.OK.getCode();
        r.msg = ResultEnum.OK.getMsg();
        return r;
    }

    public static <T>  Result<T> ok(T data) {
        Result<T> r = new Result<T>();
        r.code = ResultEnum.OK.getCode();
        r.msg = ResultEnum.OK.getMsg();
        r.setData(data);
        return r;
    }

    public static Result<Void> fail() {
        Result<Void> r = new Result<Void>();
        r.code = ResultEnum.FAIL.getCode();
        r.msg = ResultEnum.FAIL.getMsg();
        return r;
    }

}
