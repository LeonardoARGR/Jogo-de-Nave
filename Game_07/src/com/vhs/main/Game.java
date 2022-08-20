package com.vhs.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.vhs.entities.EnemySpawn;
import com.vhs.entities.Entity;
import com.vhs.entities.Player;
import com.vhs.graficos.Spritesheet;
import com.vhs.graficos.UI;
import com.vhs.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 200;
	public static final int HEIGHT = 250;
	public static final int SCALE = 3;
	
	public static String GAME_STATE = "MENU";
	public static int GAME_LEVEL = 1;
	
	private BufferedImage image;
	
	private BufferedImage background;
	private BufferedImage background2;
	
	private int backY = 0;
	private int backY2 = 250;
	private int backSpd = 4;
	
	public static World world;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	public static EnemySpawn spawn;
	public static UI ui;
	public Menu menu;
	public static Level level;
	
	public static int score = 0;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(WIDTH/2-8,HEIGHT-40,16,16,2, Game.spritesheet.getSprite(0, 0, 16, 16));
		world = new World();
		spawn = new EnemySpawn();
		ui = new UI();
		menu = new Menu();
		level = new Level();
		try {
			background = ImageIO.read(getClass().getResource("/background.png"));
			background2 = ImageIO.read(getClass().getResource("/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		entities.add(player);
		
	}
	
	public void initFrame(){
		frame = new JFrame("Space Invaders");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		if(GAME_STATE == "MENU" || GAME_STATE == "NEXT_LEVEL") {
			menu.tick();
		}else if(GAME_STATE == "NORMAL") {
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			spawn.tick();
			ui.tick();
			level.tick();
			
			backY2+=backSpd;
			if(backY2 >= 250) {
				backY2 = -250;
			}
			
			backY+=backSpd;
			if(backY >= 250) {
				backY = -250;
			}
		}
	}
	


	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		g.drawImage(background, 0, backY, null);
		g.drawImage(background2, 0, backY2, null);
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		if(GAME_STATE == "NORMAL") {
			Collections.sort(entities,Entity.nodeSorter);
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
		}
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		if(GAME_STATE == "MENU" || GAME_STATE == "NEXT_LEVEL") {
			menu.render(g);
		}else if(GAME_STATE == "NORMAL") {
			ui.render(g);
			level.render(g);
		}
		bs.show();
	}
	
	public static void nextLevel(){
		Game.GAME_LEVEL++;
		Game.GAME_STATE = "NORMAL";
		
		Game.entities.clear();
		Game.player = new Player(WIDTH/2-8,HEIGHT-40,16,16,2, Game.spritesheet.getSprite(0, 0, 16, 16));
		Game.entities.add(player);
		Game.ui = new UI();
		return;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(GAME_STATE == "MENU") {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.enter = true;
			}
		}else if(GAME_STATE == "NORMAL") {
			if(e.getKeyCode() == KeyEvent.VK_Z) {
				player.isShooting = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = true;
				
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.left = true;
			}
		}else if(GAME_STATE == "NEXT_LEVEL") {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = true;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = true;
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.enter = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(GAME_STATE == "NORMAL") {
			if(e.getKeyCode() == KeyEvent.VK_Z) {
				player.isShooting = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = false;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.left = false;
			}
		}else if(GAME_STATE == "NEXT_LEVEL") {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = false;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = false;
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				menu.enter = false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	

		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
