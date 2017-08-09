package com.sgcc.smarteleclife.Http.models;

/**
 * Created by miao_wenlong on 2017/8/15.
 */

public class ReturnDto {
    private int returnFlag;
    private String returnMsg;

    public int getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(int returnFlag) {
        this.returnFlag = returnFlag;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
