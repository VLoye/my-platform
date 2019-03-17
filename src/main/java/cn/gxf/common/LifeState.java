package cn.gxf.common;

/**
 * Created by VLoye on 2018/12/27.
 */
public enum LifeState {
    CREATEED(0,"create"),
    INITING(1,"initing"),
    INITED(2,"inited"),
    STARTING(3,"starting"),
    STARTED(4,"started"),
    CLOSING(5,"closing"),
    CLOSED(6,"closed");

    int state;
    String description;

    LifeState(int state, String description) {
        this.state = state;
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }
}
