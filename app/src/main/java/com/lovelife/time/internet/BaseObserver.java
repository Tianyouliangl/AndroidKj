package com.lovelife.time.internet;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver <T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    abstract public void onNext(T t);

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {

    }
}
