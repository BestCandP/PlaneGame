package game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * @Auther: yinw_puzh
 * @Date: 2019/2/13
 * @Description: com.ywzf.game
 * @version: 1.0
 */
public class MyPlaneGameFrame extends Frame {

    Image planeImg = GameUtil.getImage("images/plane.png");
    Image bgImg = GameUtil.getImage("images/bg.jpg");

    Plane plane = new Plane(planeImg,250,250);
    Shell[] shells = new Shell[50];

    Explode bao;
    Date startTime = new Date();
    Date endTime;
    int period;//游戏持续时间

    @Override
    public void paint(Graphics g) { //自动调用 g变量相当于一只画笔
        super.paint(g);
        Color c = g.getColor();

//        Color c = g.getColor();
//        Font f = g.getFont();
//
//        g.drawLine(100,100,300,300);
//        g.drawLine(100,100,300,300);
//        g.drawOval(100,100,300,300);
//        g.fillRect(100,100,40,40);
//        g.setColor(Color.red);
//        g.setFont(new Font("仿宋",Font.BOLD,20));
//        g.drawString("who i am",200,200);
//
//        g.setColor(c);
//        g.setFont(f);

        g.drawImage(bgImg, 0, 0, null);//画出背景
        plane.drawSelf(g);//画出飞机
        for (int i = 0; i < shells.length; i++) {
            shells[i].draw(g);

            //碰撞检测
            boolean peng = shells[i].getRect().intersects(plane.getRect());
            if (peng) {
                //System.out.println("碰撞");
                plane.live = false;
                if(bao==null){
                    bao = new Explode(plane.x,plane.y);

                    endTime = new Date();
                    period = (int)(endTime.getTime() - startTime.getTime())/1000;
                }
                bao.draw(g);
            }

            if(!plane.live){
                g.setColor(Color.red);
                Font f = new Font("宋体", Font.BOLD,40);
                g.setFont(f);
                g.drawString("时间:" + period + "秒",(int)plane.x,(int)plane.y);
            }



        }//画出炮弹 并且检测碰撞
        g.setColor(c);
    }



    /**
     *  帮助我们反复重画窗口
     */

    class PaintThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(true){
                //System.out.println("窗口画一次");
                repaint(); //重画

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 功能描述:键盘监听
     */

    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            plane.minusDirection(e);
        }
    }

    /**
     *  功能描述: 初始化窗口
     */

    public void launchFrame(){
        this.setTitle("飞机小游戏");
        this.setVisible(true);
        this.setSize(Constant.GAME_WITDH,Constant.GAME_HEIGHT);
        this.setLocation(300,300);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        new PaintThread().start();//启动重画窗口的线程
        addKeyListener(new KeyMonitor());//给窗口增加键盘监听

        for(int i = 0;i < shells.length;i++){
            shells[i] = new Shell();
        }

    }


    public static void main(String[] args){
        MyPlaneGameFrame f = new MyPlaneGameFrame();
        f.launchFrame();
    }

    //使用双缓冲技术 解决闪烁问题
    private Image offScreenImage = null;

    public void update(Graphics g){
        if(offScreenImage == null){
            offScreenImage = this.createImage(Constant.GAME_WITDH,Constant.GAME_HEIGHT);
        }

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage,0,0,null);
    }
}
