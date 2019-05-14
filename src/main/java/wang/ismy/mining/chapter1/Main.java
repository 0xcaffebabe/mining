package wang.ismy.mining.chapter1;

import wang.ismy.mining.chapter1.Dataset;

import java.io.*;


public class Main {


    public static void main(String[] args) throws IOException {

        Dataset dataset = new Dataset();
        dataset.init();
        System.out.println("准备计算用户1与前1000名用户之间的兴趣爱好相似度：");
        long time = System.currentTimeMillis();
        var map = dataset.calcBatch(1,1000);
        System.out.println("计算完成，耗时:"+(System.currentTimeMillis()-time));

        for (var i : map.keySet()){
            System.out.println("用户"+i+"："+map.get(i));
        }

    }
}
