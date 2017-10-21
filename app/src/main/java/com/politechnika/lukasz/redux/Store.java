package com.politechnika.lukasz.redux;

/*
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Store<TState> implements IStore<TState> {
    private TState lastState;
    private List<ISubscriber> listOfSubscribers = new ArrayList<>();
    private BiFunction<TState, Object, TState> reducer;

    public Store(BiFunction<TState, Object, TState> reducer, TState initialState){
        this.lastState = initialState;
        this.reducer = reducer;
    }

    public synchronized TState Dispatch(Object action){
        innerDispatcher(action);
        subscribe();
        return lastState;
    }

    private synchronized TState innerDispatcher(Object action){
        lastState = reducer.apply(lastState, action);
        return lastState;
    }

    @Override
    public TState getState() {
        return null;
    }

    @Override
    public void subscribe() {
        for(ISubscriber sub : listOfSubscribers)
            sub.subscribe();
    }

    @Override
    public void addSubscription(ISubscriber subscriber) {
        listOfSubscribers.add(subscriber);
    }

    @Override
    public void removeSubscription(ISubscriber subscriber) {
        listOfSubscribers.remove(subscriber);
    }

    @Override
    public Object dispatch(Object action) {
        return null;
    }
}
*/
