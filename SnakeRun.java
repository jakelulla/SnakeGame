import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//Jake Lulla
//Program description:
//Oct 5, 2021

public class SnakeRun extends JPanel implements KeyListener
{
   public static final int PREF_W = 800;
   public static final int PREF_H = 600;
   private Snake snake, snake2, snake3, snake4;
   private int score;
   private String message;
   private boolean isPlaying;
   private boolean gameOver;
   private boolean collision;
   private boolean mirror = false;
   private String gameMode;
   private Apple food;
   private boolean collide = true;
   private Timer timer;

   public SnakeRun()
   {
      this.addKeyListener(this);
      this.setFocusable(true);
      score = 0;
      food = new Apple(300, 400, 25, 25, PREF_W, 0, PREF_H, 125, Color.RED);
      message = "Press <S> to start";
      snake = new Snake(25, 125 + 50, 25, 25, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 0,
            PREF_W, 125, PREF_H, 4, Color.GREEN);
      snake2 = new Snake(PREF_W - 50, 125 + 50, 25, 25, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT,
            KeyEvent.VK_LEFT, 0, PREF_W, 125, PREF_H, 3, Color.YELLOW);
      snake3 = new Snake(25, PREF_H - 75, 25, 25, KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
            0, PREF_W, 125, PREF_H, 4, Color.PINK);
      snake4 = new Snake(PREF_W - 50, PREF_H - 75, 25, 25, KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_RIGHT,
            KeyEvent.VK_LEFT, 0, PREF_W, 125, PREF_H, 3, Color.WHITE);

      gameOver = true;
      isPlaying = true;
      timer = new Timer(100, new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            update();
         }

      });
      timer.start();
   }

   public void moveFood()
   {
      do
      {
         food.move();
      } while (food.foodSnakeOverlap(snake.getSnake()));
   }

   public void update()
   {
      // game code goes here
      if (!isPlaying)
      {
         message = "Press <S> to start";
      }
      if (isPlaying && !gameOver)
      {
         if (snake.getReset() || snake2.getReset() || snake3.getReset() || snake4.getReset())
         {
            gameOver = true;
            message = "Press <R> to reset";
         }
         snake.update();
         if (mirror)
         {
            if (snake.oppSnakeCollision(snake2) || snake.oppSnakeCollision(snake3) || snake.oppSnakeCollision(snake4))
            {
               collision = true;
            }
            snake2.update();
            if (snake.oppSnakeCollision(snake2) || snake.oppSnakeCollision(snake3) || snake.oppSnakeCollision(snake4))
            {
               collision = true;
            }
            snake3.update();
            if (snake.oppSnakeCollision(snake2) || snake.oppSnakeCollision(snake3) || snake.oppSnakeCollision(snake4))
            {
               collision = true;
            }
            snake4.update();
            if (snake.oppSnakeCollision(snake2) || snake.oppSnakeCollision(snake3) || snake.oppSnakeCollision(snake4))
            {
               collision = true;
            }
         }
         if (collision && mirror && collide)
         {
            snake.setReset(true);
            snake2.setReset(true);
            snake3.setReset(true);
            snake4.setReset(true);
            collision = false;
         }

         if (mirror)
            if (food.getBounds().intersects(snake.getSnake().get(0).getBounds())
                  || food.getBounds().intersects(snake2.getSnake().get(0).getBounds())
                  || food.getBounds().intersects(snake3.getSnake().get(0).getBounds())
                  || food.getBounds().intersects(snake4.getSnake().get(0).getBounds()))
            {
               for (int i = 0; i < 4; i++)
               {
                  snake.addBodyPart();
                  snake2.addBodyPart();
                  snake3.addBodyPart();
                  snake4.addBodyPart();
               }
               moveFood();
            }
         if (!mirror && food.getBounds().intersects(snake.getSnake().get(0).getBounds()))
         {
            snake.addBodyPart();
            moveFood();
         }
         if (mirror)
            score = snake.getSnake().size() - 1 + snake2.getSnake().size() - 1 + snake3.getSnake().size() - 1
                  + snake4.getSnake().size() - 1;
         else
            score = snake.getSnake().size() - 1;
         repaint();
      }
   }

   public Dimension getPreferredSize()
   {
      return new Dimension(PREF_W, PREF_H);
   }

   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Snake!");
      JPanel gamePanel = new SnakeRun();
      frame.add(gamePanel);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.setResizable(false);
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      // Rectangle for the background
      g2.setColor(new Color(40, 150, 239));
      g2.fillRect(0, 0, this.getWidth(), this.getHeight());
      food.draw(g2);
      if (mirror)
      {
         snake2.draw(g2);
         snake3.draw(g2);
         snake4.draw(g2);
      }
      snake.draw(g2);
      g2.setColor(Color.BLACK);
      for (int i = 0; i <= PREF_W; i += snake.getSnake().get(0).getW())
      {
         g2.drawLine(i, PREF_H, i, 125);
      }
      for (int i = 125; i < PREF_H; i += snake.getSnake().get(0).getH())
      {
         g2.drawLine(0, i, PREF_W, i);
      }
      Font stringFont = new Font("SansSerif", Font.PLAIN, 55);
      g2.setFont(stringFont);
      int width = g.getFontMetrics().stringWidth(message);
      g2.drawString(message, PREF_W / 2 - (width / 2), 75);
      String points = Integer.toString(score);
      int width1 = g.getFontMetrics().stringWidth(points);
      g2.drawString(points, 50 - (width1 / 2), 75);
      if (!mirror)
         gameMode = "Press <M> for a mirrored snake game mode!";
      else if (mirror && collide == true)
         gameMode = "Press <M> for a normal snake game or press <C> to disable collisions for this mode.";
      else
         gameMode = "Press <M> for a normal snake game or press <C> to enable collisions for this mode";
      Font f = new Font("SansSerif", Font.PLAIN, 17);
      g2.setFont(f);
      int width2 = g.getFontMetrics().stringWidth(gameMode);
      g2.drawString(gameMode, PREF_W / 2 - width2 / 2, 30);
      repaint();
   }

   @Override
   public void keyTyped(KeyEvent e)
   {

   }

   @Override
   public void keyPressed(KeyEvent e)
   {
      if (isPlaying)
      {
         snake.thisKeyWasPressed(e.getKeyCode());
         snake2.thisKeyWasPressed(e.getKeyCode());
         snake3.thisKeyWasPressed(e.getKeyCode());
         snake4.thisKeyWasPressed(e.getKeyCode());
      }
      if (e.getKeyCode() == KeyEvent.VK_S
            && !(snake.getReset() || snake2.getReset() || snake3.getReset() || snake4.getReset()))
      {
         gameOver = false;
         if (mirror)
            message = "Mirror Snake Game";
         else
            message = "Snake Game";
      }
      if (e.getKeyCode() == KeyEvent.VK_SPACE)
      {
         int i = 1;
         while (i < 300)
         {
            snake.addBodyPart();
            i++;
         }
      }
      if (e.getKeyCode() == KeyEvent.VK_R)
      {
         if (mirror && snake.getReset() || snake2.getReset() || snake3.getReset() || snake4.getReset())
         {
            snake.setReset(true);
            snake2.setReset(true);
            snake3.setReset(true);
            snake4.setReset(true);
            snake.resetGame(25, 125 + 50, 4);
            snake2.resetGame(PREF_W - 50, 125 + 50, 3);
            snake3.resetGame(25, PREF_H - 75, 4);
            snake4.resetGame(PREF_W - 50, PREF_H - 75, 3);
            food.move();
            score = 0;
            message = "Press <S> to start";
         }
         if (!mirror && snake.getReset())
         {
            snake.setReset(true);
            snake.resetGame(25, 125 + 50, 4);
            food.move();
            score = 0;
            message = "Press <S> to start";
         }

      }
      if (e.getKeyCode() == KeyEvent.VK_M)
      {
         snake.setReset(true);
         snake2.setReset(true);
         snake3.setReset(true);
         snake4.setReset(true);
         snake.resetGame(25, 125 + 50, 4);
         snake2.resetGame(PREF_W - 50, 125 + 50, 3);
         snake3.resetGame(25, PREF_H - 75, 4);
         snake4.resetGame(PREF_W - 50, PREF_H - 75, 3);
         food.move();
         score = 0;
         if (!mirror)
            mirror = true;
         else
            mirror = false;
         gameOver = true;
         message = "Press <S> to start";
         snake.setReset(false);
         snake2.setReset(false);
         snake3.setReset(false);
         snake4.setReset(false);
      }
      if (e.getKeyCode() == KeyEvent.VK_C)
      {
         if (collide)
            collide = false;
         else
            collide = true;
      }
      repaint();
   }

   @Override
   public void keyReleased(KeyEvent e)
   {
   }
}
