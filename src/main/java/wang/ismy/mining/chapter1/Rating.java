package wang.ismy.mining.chapter1;

import lombok.Data;



@Data
public class Rating {

    private Integer userId;

    private Integer movieId;

    private Double rating;

    private Long time;
}
