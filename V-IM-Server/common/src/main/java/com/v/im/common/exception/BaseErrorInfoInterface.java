package com.v.im.common.exception;

/**
 * @author 乐天
 */
public interface BaseErrorInfoInterface {

    /**
     * 错误码
     * @return str
     * */
    String getResultCode();

    /**
     * 错误描述
     * @return str
     * */
    String getResultMsg();
}
