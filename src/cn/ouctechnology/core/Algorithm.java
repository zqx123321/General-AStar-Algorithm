package cn.ouctechnology.core;

import java.util.*;

/**
 * 策略模式，利用类的组合
 */
@SuppressWarnings("all")
public class Algorithm {
    //策略模式
    Status initStatus;
    /**
     * CLOSED表使用HashMap，Zobrist作为key，随机寻址
     */
    private Map<Integer, Status> CLOSED;
    private Queue<Status> OPENED;

    /**
     * 构造方法，传入initStatus
     *
     * @param initStatus
     */
    public Algorithm(Status initStatus) {
        this.initStatus = initStatus;
        CLOSED = new HashMap<>();
        OPENED = new PriorityQueue<>(Status.getComparator());
    }

    /**
     * setter装配，完全解耦
     *
     * @param initStatus
     */
    public void setInitStatus(Status initStatus) {
        this.initStatus = initStatus;
        CLOSED = new HashMap<>();
        OPENED = new PriorityQueue<>(Status.getComparator());
    }

    /**
     * A*算法核心
     */
    public void A() {
        OPENED.add(initStatus);
        while (!OPENED.isEmpty()) {
            Status status = OPENED.poll();
            /**
             * 改进的A*算法
             */
            Status statusInClose = CLOSED.get(status.ZobristValue);
            //不在，直接加入
            if (statusInClose == null) {
                CLOSED.put(status.ZobristValue, status);
            }
            //在，判断Fn值大小
            else if (statusInClose.getFn() > status.getFn()) {
                CLOSED.replace(status.ZobristValue, statusInClose, status);
                continue;
            } else {
                continue;
            }
            //移动下一个状态
            List<Status> sts = status.moveNext(status);
            for (Status st : sts) {
                if (st.isOver()) {
                    st.output();
                    return;
                }
                OPENED.add(st);
            }
        }
        System.out.println("NO");
        return;
    }
}
