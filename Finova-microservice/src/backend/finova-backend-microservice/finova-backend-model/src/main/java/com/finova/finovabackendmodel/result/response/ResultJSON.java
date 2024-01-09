package com.finova.finovabackendmodel.result.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultJSON {
    private Integer code;
    private Object data;
    private String msg;

    // 使用Code的消息
    public ResultJSON(Code code, Object data) {
        this.code = code.code;
        this.data = data;
        this.msg = code.znMsg;
    }

    // 自定义消息
    public ResultJSON(Code code, Object data, String msg) {
        this.code = code.code;
        this.data = data;
        this.msg = msg;
    }

}
