package cn.ouctechnology.example;

import cn.ouctechnology.core.Status;

import java.util.*;

/**
 * N数码问题
 */
public class Figure extends Status {
    private int index;
    private static int N;

    public static void setN(int n) {
        N = n;
    }

    public Figure(Status pre, int gn, int[] state, int index, int step) {
        super(pre, gn, step, state);
        this.index = index;
    }

    @Override
    protected int getHn() {
        int sum = 0;
        for (int i = 1; i < N * N; i++) {
            sum += Math.abs(state[i] - i);
        }
        return sum;
    }

    @Override
    public List<Status> moveNext(Status status) {
        Figure pre = (Figure) status;
        List<Status> figures = new ArrayList<>();
        if (index + 1 < N * N) {
            Figure newFig = new Figure(pre, pre.gn + 1, pre.state, index + 1, status.step + 1);
            newFig.setZobristValue();
            newFig.setHn();
            newFig.state[0] = index + 1;
            figures.add(newFig);
        }
        if (index - 1 > 0) {
            Figure newFig = new Figure(pre, pre.gn + 1, pre.state, index - 1, status.step + 1);
            newFig.setZobristValue();
            newFig.setHn();
            newFig.state[0] = index - 1;
            figures.add(newFig);
        }
        if (index + 3 < N * N) {
            int[] newlist = Arrays.copyOf(state, state.length);
            int temp = newlist[index + 2];
            for (int i = index + 2; i > index; i--) {
                newlist[i] = newlist[i - 1];
            }
            newlist[index] = temp;
            Figure newFig = new Figure(pre, pre.gn + 1, newlist, index + 3, status.step + 1);
            newFig.setHn();
            newFig.setZobristValue();
            newFig.state[0] = index + 3;
            figures.add(newFig);
        }
        if (index - 3 > 0) {
            int[] newlist = Arrays.copyOf(state, state.length);
            int temp = newlist[index - 3];
            for (int i = index - 3; i < index; i++) {
                newlist[i] = newlist[i + 1];
            }
            newlist[index] = temp;
            Figure newFig = new Figure(pre, pre.gn + 1, newlist, index - 3, status.step + 1);
            newFig.setHn();
            newFig.setZobristValue();
            newFig.state[0] = index - 3;
            figures.add(newFig);
        }
        return figures;
    }

    public static Status setInitialStatus() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            int N = sc.nextInt();
            int index = sc.nextInt();
            Figure.setN(N);
            int[] state = new int[N * N];
            for (int i = 1; i < N * N; i++) {
                state[i] = i;
            }
            Random random = new Random();
            for (int i = N * N - 1; i > 0; i--) {
                int num = random.nextInt(i) + 1;
                int temp = state[num];
                state[num] = state[i];
                state[i] = temp;
            }

            Status.setZobrist(N * N, N * N + 1);
            Figure figure = new Figure(null, 0, state, index, 0);
            figure.setZobristValue();
            figure.setHn();
            return figure;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "gn=" + gn +
                ", hn=" + hn +
                ", index=" + index +
                ", state=" + Arrays.toString(state) +
                '}';
    }
}
