package cn.kgc.coolrental.dto;

import cn.kgc.coolrental.constant.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsg {
    private Integer code;
    private ResponseStatus responseStatus;
    private String msg;
    private Object data;

    public static ResponseMsg succ(String op, String word, Object data) {
        return new ResponseMsg(
                ResponseStatus.SUCCESS.getCode(),
                ResponseStatus.SUCCESS,
                op + " " + word + " " + "成功",
                data
        );
    }

    public static ResponseMsg succ(String op, String word) {
        return new ResponseMsg(
                ResponseStatus.SUCCESS.getCode(),
                ResponseStatus.SUCCESS,
                op + " " + word + " " + "成功",
                null
        );
    }

    public static ResponseMsg succ(String msg, Object data) {
        return new ResponseMsg(
                ResponseStatus.SUCCESS.getCode(),
                ResponseStatus.SUCCESS,
                msg,
                data
        );
    }

    public static ResponseMsg succ(String detail) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(ResponseStatus.SUCCESS.getCode());
        responseMsg.setMsg(detail);
        responseMsg.setResponseStatus(ResponseStatus.SUCCESS);
        return responseMsg;
    }

    public static ResponseMsg fail(String op, String word) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(ResponseStatus.FAIL.getCode());
        responseMsg.setMsg(op + " " + word + " " + "失败");
        responseMsg.setResponseStatus(ResponseStatus.FAIL);
        return responseMsg;
    }

    public static ResponseMsg fail(String op, String word, Object data) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(ResponseStatus.FAIL.getCode());
        responseMsg.setMsg(op + " " + word + " " + "失败");
        responseMsg.setData(data);
        responseMsg.setResponseStatus(ResponseStatus.FAIL);
        return responseMsg;
    }

    public static ResponseMsg fail(String detail, Object data) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(ResponseStatus.FAIL.getCode());
        responseMsg.setMsg(detail);
        responseMsg.setData(data);
        responseMsg.setResponseStatus(ResponseStatus.FAIL);
        return responseMsg;
    }

    public static ResponseMsg fail(String detail) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(ResponseStatus.FAIL.getCode());
        responseMsg.setMsg(detail);
        responseMsg.setResponseStatus(ResponseStatus.FAIL);
        return responseMsg;
    }

    public static ResponseMsg data(Object data) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setCode(ResponseStatus.SUCCESS.getCode());
        responseMsg.setData(data);
        responseMsg.setResponseStatus(ResponseStatus.SUCCESS);
        return responseMsg;
    }

    public static ResponseMsg res(boolean flag, String op, String word, Object data) {
        return flag ? succ(op, word, data) : fail(op, word);
    }

    public static ResponseMsg res(boolean flag, String op, String word) {
        return flag ? succ(op, word) : fail(op, word);
    }
}
