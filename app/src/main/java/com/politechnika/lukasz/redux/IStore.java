package com.politechnika.lukasz.redux;

/**
 * Created by Lukasz on 18.10.2017.
 */

public interface IStore<TState> {
    TState getState();
    void subscribe();
    void addSubscription(ISubscriber subscriber);
    void removeSubscription(ISubscriber subscriber);
    Object dispatch(Object action);
}
