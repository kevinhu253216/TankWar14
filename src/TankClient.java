import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

    public static final int GAME_WIDTH =800;//方便改写
    public static final int GAME_HEIGHT =600;//方便改写

    Tank myTank = new Tank(50,50,this);
    List<Missile> missileList= new ArrayList<>();
    Image offScreenImage = null;//刷新率背后图片

    public static void main(String[] args){
        TankClient tc = new TankClient();
        tc.launchFrame();
    }

    public void paint(Graphics g){
        g.drawString("Missiles count:"+missileList.size(), 10, 50);
        for(int i=0;i <missileList.size();i++){
            Missile m =missileList.get(i);
         /*
            if(m.getIsLive() == false){
                missileList.remove(m);
            }
         */
            m.draw(g);
        }
        myTank.draw(g);
    }

    public void update(Graphics g){
        if(offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);//picture
        }
        Graphics gOffScreen = offScreenImage.getGraphics();//背后图片的画笔
        Color c = gOffScreen.getColor();//擦除
        gOffScreen.setColor(Color.GREEN);//擦除
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);//擦除
        gOffScreen.setColor(c);//擦除
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);//java-desktop/awt/grapics/
    }

    public void launchFrame(){
        this.setLocation(300,300);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setTitle("TankWar");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setBackground(Color.GREEN);
        this.setResizable(false);
        this.setVisible(true);
        this.addKeyListener(new KeyMonitor());/////////////////////////////
        new Thread(new PaintThread()).start();
    }

    private class PaintThread implements Runnable{
        public void run(){
            while(true){
                repaint();
                try{
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {//监听键盘 ////////////////////////////
        public void keyPressed(KeyEvent e){
            myTank.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            myTank.keyReleased(e);
        }
    }
}