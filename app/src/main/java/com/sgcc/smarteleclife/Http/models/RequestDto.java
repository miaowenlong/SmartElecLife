package com.sgcc.smarteleclife.Http.models;

/**
 * Created by miao_wenlong on 2017/8/15.
 */

public class RequestDto {
    private int requestCode;
    private String obj;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
