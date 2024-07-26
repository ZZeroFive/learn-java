package ml.java.learn.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: jiml
 * @Date: 2024/7/26 08:26
 * @Description: 票务实体类，模拟多线程安全问题
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private String srcName;
    private String dstName;
    private int wholeNum;

}
