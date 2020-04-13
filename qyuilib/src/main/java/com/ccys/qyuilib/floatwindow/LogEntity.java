package com.ccys.qyuilib.floatwindow;

import java.io.Serializable;

public class LogEntity implements Serializable {
    private String content;//请求头
    private String url;
    private String params;
    private String headers;
    private boolean isOpen;//内容是否折叠
    private String action;//消息的类型

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    private Type type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type implements Serializable{
        REQUEST,SUCCESS,ERROR
    }
}
