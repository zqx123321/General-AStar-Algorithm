package cn.ouctechnology.example;

import cn.ouctechnology.core.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 滑动积木块问题
 */
public class Block extends Status {
    private int index;


    public Block(Status pre, int gn, int[] state, int index, int step) {
        super(pre, gn, step, state);
        this.index = index;
        this.step = step;
    }

    /**
     * 重载getHn
     * @return
     */
    @Override
    protected int getHn() {
        int count = 0;
        int black = 0;
        for (int k = 0; k < state.length; k++) {
            if (state[k] == 1) {
                black++;
            } else if (state[k] == 2) {
                count += black;
            }
        }
        return count;
    }

    /**
     * 按照规则移动木块
     * @param status：当前状态
     * @return下一个状态集合
     */
    @Override
    public List<Status> moveNext(Status status) {
        List<Status> res = new ArrayList<>();
        Block block = (Block) status;
        int index = block.index;
        int[] state = block.state;
        //按照规则移动积木
        for (int i = -3; i <= 3; i++) {
            if (i == 0) continue;
            if (index + i >= state.length || index + i < 0) continue;
            //在新数组中操作
            int[] nowState = Arrays.copyOf(state, state.length);
            int temp = nowState[index];
            nowState[index] = nowState[index + i];
            nowState[index + i] = temp;
            //获取耗散值
            int value;
            if (Math.abs(i) == 3) value = 2;
            else value = 1;
            value += block.gn;
            //New出新的结点
            Block newBlock = new Block(status, value, nowState, index + i, ((Block) status).step + 1);
            //由当前状态的zobrist值得到下一个状态的zobrist值
            int[][] zobrist = getZobrist();
            int tempValue = status.getZobristValue() ^ zobrist[index][state[index]] ^ zobrist[index + i][state[index + i]];
            tempValue ^= zobrist[index][nowState[index]] ^ zobrist[index + i][nowState[index + i]];
            newBlock.setZobristValue(tempValue);
            newBlock.setHn();
            res.add(newBlock);
        }
        return res;
    }


    @Override
    public String toString() {
        return "Block{" +
                "state=" + Arrays.toString(state) +
                ", step=" + step +
                ", gn=" + gn +
                ", hn=" + hn +
                '}';
    }

    @Override
    public Boolean isOver() {
        return hn == 0 && state[state.length - 1] == 0;
    }

    public static Status setInitialStatus() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            int N = sc.nextInt();
            int[] temp = new int[2 * N + 1];
            for (int i = 0; i < N; i++) {
                temp[i] = 1;
            }
            for (int i = 0; i < N; i++) {
                temp[N + i] = 2;
            }
            temp[2 * N] = 0;
            Status.setZobrist(2 * N + 1, 3);
            Block block = new Block(null, 0, temp, 2 * N, 0);
            block.setHn();
            block.setZobristValue();
            return block;
        }
        return null;
    }
}
