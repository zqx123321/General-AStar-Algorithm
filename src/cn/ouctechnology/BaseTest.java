package cn.ouctechnology;

import cn.ouctechnology.core.Algorithm;
import cn.ouctechnology.core.AlgorithmTest;
import cn.ouctechnology.core.Status;
import cn.ouctechnology.example.Block;

/**
 * 测试类
 */
public class BaseTest {

    public static void main(String[] args) {
        /**滑动积木块问题**/

        Status status = Block.setInitialStatus();
        Long start = System.currentTimeMillis();
        new AlgorithmTest(status).A();
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
//       /**野人和传教士问题**/
//       new Algorithm(new MC()).A();
//       /**N皇后问题**/
//       new Algorithm(new Queen()).A();
//       /**N数码问题**/
        //new Algorithm(new Figure()).A();
    }
}
