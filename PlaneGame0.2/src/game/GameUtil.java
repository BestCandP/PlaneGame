package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @Auther: yinw_puzh
 * @Date: 2019/2/13
 * @Description: com.ywzf.game
 * @version: 1.0
 */
public class GameUtil {
    private GameUtil(){
    }

    /**
     * 返回指定路径文件的图片对象
     * @param path
     * @return
     */
    public static Image getImage(String path){
        BufferedImage bi = null;
        try {
            URL u = GameUtil.class.getClassLoader().getResource(path);
            bi = ImageIO.read(u);
        }catch (IOException e){
            e.printStackTrace();
        }
        return bi;
    }
}
