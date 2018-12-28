package com.v.common;

/**
 * Created by VLoye on 2018/12/27.
 */
public enum LifeState {
    CREATEED(0),
    INITED(1),
    STARTED(2),
    CLOSED(3);

    int state;

    LifeState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
