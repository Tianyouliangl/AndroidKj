package com.kj.app.bean;

import java.io.Serializable;

public class BaseCallModel<T> implements Serializable{
    public int status;
    public String errorInfo;
    public T data;
}
