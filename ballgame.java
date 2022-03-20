import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ballgame{
   
   
    Frame frame=new Frame();
    //球大小
    private final int ballX=10;
    private final int ballY=10;
    //球位置
    private int blocalX=250;
    private int blocalY=250;
    //球速度
    private int speedX=10;
    private int speedY=2;
    //遊戲結束
    private boolean isover=false;
    //畫布大小
    private final int x=500;
    private final int y=500;
	//所有平台
	private Brick brick[]=new Brick[10];
	//平台速度
	private int bspeed=1;
    //timer
    private Timer timer;
	//記時
	private int timecount=0;
	//計分
	private float score=0;
    //重寫 canvas中paint
    private class mycanvas extends Canvas{
        @Override
		
        public void paint(Graphics g) {
            super.paint(g);
			//遊戲結束時
            if (isover){
				Font font = g.getFont().deriveFont( 20.0f );
				g.setFont( font );
                g.drawString("GmaeOver!",220,250);
				g.drawString("level: "+bspeed,220,270);
				g.drawString("score: "+(int)(score),220,290);
				if(JOptionPane.showConfirmDialog(null, "Play again?",null, JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
				{
					new ballgame().init();
					isover = false;
				}
				else
					System.exit(0);
			}
            //遊戲進行時
            else {
                g.setColor(Color.red);
                g.fillOval(blocalX,blocalY,ballX,ballY);
				g.setColor(Color.blue);
				for(int i=0;i<10;i++){
					g.fillRect(brick[i].brick_x,brick[i].brick_y,brick[i].brick_width,brick[i].brick_height);
				}
				g.setColor(Color.black);
				
				Font font = g.getFont().deriveFont( 20.0f );
				g.setFont( font );
				g.drawString("level: "+bspeed,10,20);
				g.drawString("score: "+(int)(score),10,40);
            }
			
        }
		
    }
    //建立畫板
    mycanvas drawarea=new mycanvas();


    public void init() {

        KeyListener keyListener = new KeyAdapter() {
			@Override
			
            public void keyPressed(KeyEvent e) {
                int code=e.getKeyCode();
                if(code==KeyEvent.VK_LEFT)
                    blocalX-=speedX;
                else if(code==KeyEvent.VK_RIGHT)
                    blocalX+=speedX;
            }
			
        };
		
        frame.addKeyListener(keyListener);
        drawarea.addKeyListener(keyListener);

		//平台座標
		brick[0]=new Brick(50,45,70,10);
		brick[1]=new Brick(430,120,70,10);
		brick[2]=new Brick(300,120,70,10);
		brick[3]=new Brick(240,195,70,10);
		brick[4]=new Brick(140,260,70,10);
		brick[5]=new Brick(220,260,70,10);
		brick[6]=new Brick(320,340,70,10);
		brick[7]=new Brick(240,400,70,10);
		brick[8]=new Brick(50,480,70,10);
		brick[9]=new Brick(400,480,70,10);
		
		
        ActionListener actionListener=new AbstractAction() {
            @Override
			
            public void actionPerformed(ActionEvent e) {
				//levelup
				if(bspeed<=4)
					timecount+=1;
				if(timecount>=1000){
					bspeed+=1;
					timecount=0;
				}
				//計分
				score+=0.1;
				//球下墜
				blocalY+=speedY;
				//球事件
				if(blocalX>x)
					blocalX=0;
				if(blocalX<0)
					blocalX=x;
				//平台事件
				for(int i=0;i<10;i++){
					brick[i].brick_y-=bspeed;
					if(brick[i].brick_y<=0){
						brick[i].brick_y=500;
						brick[i].brick_x=(int)(Math.random()*430);
					}
					
					if(blocalY+ballY>=brick[i].brick_y-speedY-1&&blocalY+ballY<=brick[i].brick_y+speedY+1&&blocalX+ballX>=brick[i].brick_x&&blocalX<=brick[i].brick_x+70){
						blocalY-=(bspeed+speedY);
					}
				}
				//輸判斷
				if(blocalY>500||blocalY<0) {
                    timer.stop();
                    isover=true;
                    drawarea.repaint();
                }
				drawarea.repaint();
			}
			
        };
		
        drawarea.setPreferredSize(new Dimension(x,y));
        frame.add(drawarea);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
		
        timer=new Timer(1,actionListener);
        timer.start();
        frame.pack();
        frame.setVisible(true);

    }
	
    public static void main(String[] args){
        new ballgame().init();
    }
	
	class Brick{
		Rectangle rect=null;
		//長方形物件，磚塊按鈕的位置和寬高
		int brick_x,brick_y;
		//按扭的左上角座標
		int brick_width,brick_height;
		//按扭的寬和高
		Boolean visible;
		public Brick(int x,int y,int w,int h)
		{
			brick_x=x;
			brick_y=y;
			brick_width=w;
			brick_height=h;
			visible=true;
			rect=new Rectangle(x,y,w,h);
			//建立長方形物件---磚塊按鈕的位置和寬高。
		}
	}
}