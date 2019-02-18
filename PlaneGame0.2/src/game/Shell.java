package game;

import java.awt.*;

/**
 * @Auther: yinw_puzh
 * @Date: 2019/2/13
 * @Description: game
 * @version: 1.0
 */
public class Shell extends GameObject{
    double degree;

    public Shell(){
        x = 200;
        y = 200;
        width = 10;
        height = 10;
        speed = 3;

        degree = Math.random() * Math.PI * 2;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.fillOval((int)x,(int)y,width,height);

        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        if(x < 0 || x > Constant.GAME_WITDH){
            degree = Math.PI - degree;
        }

        if(y < 30 || y > Constant.GAME_HEIGHT){
            degree = - degree;
        }
    }
}
