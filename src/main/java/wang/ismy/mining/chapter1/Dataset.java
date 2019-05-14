package wang.ismy.mining.chapter1;

import com.google.common.collect.Sets;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class Dataset {

    private String uri = "ratings.csv";

    private List<Rating> ratingList = new ArrayList<>();

    public void init() throws IOException {
        Reader in = new FileReader(uri);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        for (CSVRecord record : records) {

            try {

                Integer user = Integer.valueOf(record.get(0));
                Integer movie = Integer.valueOf(record.get(1));
                Double rating = Double.parseDouble(record.get(2));
                Long timestamp = Long.getLong(record.get(3));

                Rating r = new Rating();
                r.setUserId(user);
                r.setMovieId(movie);
                r.setRating(rating);
                r.setTime(timestamp);
                ratingList.add(r);
            } catch (Exception e) {
                System.out.println("转换过程抛出异常:" + e.getMessage());
            }


        }
        System.out.println("初始化完成，数据量:" + ratingList.size());
        in.close();
    }

    public Rating getRating(int index) {
        return ratingList.get(index);
    }

    public double calc(int user1, int user2) {

        var user1Map = getRatingBatch(user1);

        var user2Map = getRatingBatch(user2);

        double ret = 0;

        Set<Integer> keySet = Sets.intersection(user1Map.keySet(),user2Map.keySet());

        for (var i : keySet){
            double x1 = user1Map.get(i);

            double x2 = user2Map.get(i);
            ret +=Math.pow(x1-x2,2);
        }

        ret = Math.sqrt(ret);
        return ret;

    }

    public Map<Integer,Double> calcBatch(int user,int range){

        // 计算用户与前100000个用户的距离
        Map<Integer,Double> map = new HashMap();
        for(int i=1;i<=range;i++){

            map.put(ratingList.get(i).getUserId(),calc(user,ratingList.get(i).getUserId()));
        }
        return map;
    }

    public Map<Integer,Double> getRatingBatch(int userId){

        Map<Integer,Double> map = new HashMap<>();
        for (var i : ratingList){
            if (i.getUserId() == userId){
                map.put(i.getMovieId(),i.getRating());
            }
        }

        return map;
    }
}
