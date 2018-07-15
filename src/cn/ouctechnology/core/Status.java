package cn.ouctechnology.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * 状态抽象类
 */
public abstract class Status {

    /**
     * 公共属性
     */
    protected Status pre;
    protected int gn;
    protected int hn;
    protected int[] state;//状态序列
    public int step;
    protected int ZobristValue;//当前状态的Zobrist
    protected static int[][] Zobrist;

    /**
     * 构造方法
     *
     * @param pre
     * @param gn
     * @param state
     */
    public Status(Status pre, int gn, int step, int[] state) {
        this.pre = pre;
        this.gn = gn;
        this.state = state;
    }

    /**
     * 生成随机数，设置Zobrist数组初始值
     *
     * @param length
     * @param count
     */
    public static void setZobrist(int length, int count) {
        Zobrist = new int[length][count];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < count; j++) {
                Zobrist[i][j] = random.nextInt();
            }
        }
    }

    /**
     * 得到当前状态的Zobrist值
     */
    public void setZobristValue() {
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            h ^= Zobrist[i][state[i]];
        }
        this.ZobristValue = h;
    }

    /**
     * 到当前状态的Zobrist值+1重载
     *
     * @param zobristValue
     */
    public void setZobristValue(int zobristValue) {
        ZobristValue = zobristValue;
    }

    /**
     * 得到当前状态的zobrist值
     *
     * @return
     */
    public int getZobristValue() {
        return ZobristValue;
    }

    /**
     * 得到zobrist数组
     *
     * @return
     */
    public static int[][] getZobrist() {
        return Zobrist;
    }

    /**
     * 设置Hn
     */
    protected void setHn() {
        this.hn = getHn();
    }


    /**
     * 判断是否结束
     *
     * @return
     */
    public Boolean isOver() {
        return hn == 0;
    }

    /**
     * 得到启发函数的值
     *
     * @return
     */
    public int getFn() {
        return hn + gn;
    }


    /**
     * 得到前一个状态
     *
     * @return
     */
    public Status getPre() {
        return pre;
    }

    /**
     * 重载equals方法
     * 判读两个状态的ZobristValue是否相同即可
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        return this.ZobristValue == ((Status) o).ZobristValue;
    }

    /**
     * 输出路径
     */
    public void output() {
        //得到前一个状态
        Status status = this.getPre();
        List<Status> list = new ArrayList<>();
        while (status != null) {
            list.add(status);
            //此状态的前一个状态
            status = status.getPre();
        }
        //状态对象必须覆盖equals方法
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.println(list.get(i));
        }
        System.out.println(this);
    }


    /**
     * 返回比较器，用于优先队列排序
     *
     * @return
     */
    public static Comparator<Status> getComparator() {
        Comparator<Status> comparator = (o1, o2) -> {
            if (o1.getFn() == o2.getFn()) {
                return o1.step - o2.step;
            } else {
                return o1.getFn() - o2.getFn();
            }
        };
        return comparator;
    }

    /********************************************************************
     * 以下两个函数需要推迟到子类中才能知道具体的行为，需要子类重写
     *
     */

    /**
     * 转移状态
     *
     * @param status：当前状态
     * @return 下一个状态集合
     */
    public abstract List<Status> moveNext(Status status);

    /**
     * 得到某一状态的Hn值
     *
     * @return
     */
    protected abstract int getHn();

}
