import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

//Jake Lulla
//Program description:
//Oct 6, 2021

public class Snake
{
   public enum Direction
   {
      STOPPED, UP, DOWN, LEFT, RIGHT
   }

   private int dx, dy, upKey, downKey, leftKey, rightKey, xMin, yMin, xMax, yMax;
   // private boolean up, down, left, right;
   private Direction direction;
   private Direction nextDirection;
//   private Color color;
   private ArrayList<SnakeBodyPart> snake = new ArrayList<SnakeBodyPart>();
   private boolean reset;

   public Snake(int x, int y, int w, int h, int upKey, int downKey, int leftKey, int rightKey, int xMin, int xMax,
         int yMin, int yMax, int direction, Color color)
   {
      super();
      snake.add(new SnakeBodyPart(x, y, w, h, color));
      this.dx = w;
      this.dy = h;
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
      this.upKey = upKey;
      this.downKey = downKey;
      this.rightKey = rightKey;
      this.leftKey = leftKey;
      if(direction == 1) {
         this.direction = this.nextDirection = Direction.UP;
      }
      if(direction == 2) {
         this.direction = this.nextDirection = Direction.DOWN;
      }
      if(direction == 3) {
         this.direction = this.nextDirection = Direction.LEFT;
      }
      if(direction == 4) {
         this.direction = this.nextDirection = Direction.RIGHT;
      }
   }

   public void update()
   {
      if (direction == Direction.STOPPED)
      {
         return;
      }

      SnakeBodyPart head = snake.get(0).clone();
      direction = nextDirection;
      switch (direction)
      {
      case UP:
         head.setY(head.getY() - dy);
         break;
      case DOWN:
         head.setY(head.getY() + dy);
         break;
      case LEFT:
         head.setX(head.getX() - dx);
         break;
      case RIGHT:
         head.setX(head.getX() + dx);
         break;
      }

      if (head.getX() < xMin)
      {
         reset = true;
      }
      if (head.getX() + snake.get(0).getW() > xMax)
      {
         reset = true;
      }
      if (head.getY() + snake.get(0).getH() > yMax)
      {
         reset = true;
      }
      if (head.getY() < yMin)
      {
         reset = true;
      }
      if (reset)
      {
         return;
      }
      snake.remove(snake.size() - 1);
      snake.add(0, head);

      if (snakeCollision())
         reset = true;
   }

   public void draw(Graphics2D g2)
   {
      for (SnakeBodyPart s : snake)
      {
         s.draw(g2);
      }

   }

   public boolean snakeCollision()
   {
      SnakeBodyPart head = snake.get(0);
      for (int i = 1; i < snake.size(); i++)
      {
         if (head.getBounds().intersects(snake.get(i).getBounds()))
         {
            return true;
         }
      }
      return false;
   }
   public boolean oppSnakeCollision(Snake s)
   {
      SnakeBodyPart head = snake.get(0);
      for (int i = 0; i < s.getSnake().size(); i++)
      {
         if (head.getBounds().intersects(s.getSnake().get(i).getBounds()))
         {
            return true;
         }
      }
      return false;
   }


   public ArrayList<SnakeBodyPart> getSnake()
   {
      return snake;
   }

   public void setSnake(ArrayList<SnakeBodyPart> snake)
   {
      this.snake = snake;
   }

   public void setxMin(int xMin)
   {
      this.xMin = xMin;
   }

   public void setyMin(int yMin)
   {
      this.yMin = yMin;
   }

   public void setxMax(int xMax)
   {
      this.xMax = xMax;
   }

   public void setyMax(int yMax)
   {
      this.yMax = yMax;
   }
   public int getyMax()
   {
      return yMax;
   }

   public boolean getReset()
   {
      return reset;
   }

   public void setReset(boolean reset)
   {
      this.reset = reset;
   }
   public int getDirection() {
      if(direction == Direction.RIGHT)
         return 1;
      if(direction == Direction.LEFT)
         return 2;
      if(direction == Direction.UP)
         return 3;
      if(direction == Direction.DOWN)
         return 4;
      return 0;
      
   }

   public void addBodyPart()
   {
      snake.add(new SnakeBodyPart(snake.get(0).getX(), snake.get(0).getY(), snake.get(0).getW(), snake.get(0).getH(),
            snake.get(0).getColor()));
   }

   public void resetGame(int x, int y, int direction)
   {
      if (reset)
      {
         while (snake.size() > 1)
         {
            snake.remove(1);
         }
         snake.get(0).setX(x);
         snake.get(0).setY(y);
         if(direction == 1) {
            this.direction = this.nextDirection = Direction.UP;
         }
         if(direction == 2) {
            this.direction = this.nextDirection = Direction.DOWN;
         }
         if(direction == 3) {
            this.direction = this.nextDirection = Direction.LEFT;
         }
         if(direction == 4) {
            this.direction = this.nextDirection = Direction.RIGHT;
         }         
         reset = false;
      }
   }

   public void thisKeyWasPressed(int theKeyThatWasPressed)
   {
      if (theKeyThatWasPressed == upKey && direction != Direction.DOWN)
      {
         nextDirection = Direction.UP;
      } else if (theKeyThatWasPressed == downKey && direction != Direction.UP)
      {
         nextDirection = Direction.DOWN;
      } else if (theKeyThatWasPressed == leftKey && direction != Direction.RIGHT)
      {
         nextDirection = Direction.LEFT;
      } else if (theKeyThatWasPressed == rightKey && direction != Direction.LEFT)
      {
         nextDirection = Direction.RIGHT;
      }
      
   }


   @Override
   public String toString()
   {
      return "Snake [dx=" + dx + ", dy=" + dy + ", upKey=" + upKey + ", downKey=" + downKey + ", leftKey=" + leftKey
            + ", rightKey=" + rightKey + ", xMin=" + xMin + ", yMin=" + yMin + ", xMax=" + xMax + ", yMax=" + yMax
            + ", direction=" + direction + ", snake=" + snake + "]";
   }

 

  

}
