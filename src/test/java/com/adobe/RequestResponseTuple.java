package com.adobe;

public class RequestResponseTuple<T,K> {
    private T request;
    private K response;

    public T getRequest() {
        return request;
    }

    public void setRequest(final T request) {
        this.request = request;
    }

    public K getResponse() {
        return response;
    }

    public void setResponse(final K response) {
        this.response = response;
    }
}