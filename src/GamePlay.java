import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.zombies.Zombie;
import core.zombies.Zombie0;
import core.zombies.Zombie1;
import core.zombies.Zombie2;
import core.zombies.Zombie3;

public class GamePlay extends JPanel{
	
	private static final long serialVersionUID = -996061716951210254L;
	// 游戏窗口大小，游戏状态
	public static final int WIDTH = 1400;
	public static final int HEIGHT = 600;	
	private Background start = new Background(WIDTH,HEIGHT,0,0);
	
	// 游戏对象
	// 僵尸集合
	private List<Zombie> zombies = new ArrayList<Zombie>();

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		GamePlay game= new GamePlay();
		frame.add(game);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		
		game.action();
		
		// 启动线程加载音乐
		Runnable r = new zombieAubio("bgm.wav");
		Thread t = new Thread(r);
		t.start();	

	}
	
	public void action() {
		// 定时器
		Timer timer = new Timer();
		int interval = 10;
		timer.schedule(new TimerTask() {
			public void run() {
				zombieEnterAction();
				zombieStepAction();
				repaint();
			}
		},10,interval*10);
	}
	
	// 画
	public void paint(Graphics g) {
		// 画背景
		start.paintObject(g);
		
		// 画僵尸
		for(Zombie z:zombies) {
			z.paintObject(g);
		}
	}
	
	// 生成僵尸
	public Zombie nextOneZombie() {
		Random rand = new Random();
		// 控制不同种类僵尸出现的概率
		int type = rand.nextInt(20);
		if(type<5) {
			return new Zombie0();
		}else if(type<10) {
			return new Zombie1();
		}else if(type<15) {
			return new Zombie2();
		}else {
			return new Zombie3();
		}
	}

	// 僵尸入场
	// 设置进场间隔
	int zombieEnterTime = 0;
	public void zombieEnterAction() {
		zombieEnterTime++;
		if(zombieEnterTime%30==0) {
			zombies.add(nextOneZombie());
		}
	}

	//僵尸移动
	//设置移动间隔
	public void zombieStepAction() {
		for(Zombie z:zombies) {
			//只有活着的僵尸会移动
			if(z.isLife()) {
				z.step();
			}
		}
	}

}
