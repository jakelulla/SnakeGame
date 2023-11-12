import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

//Jake Lulla
//Program description:
//Oct 13, 2021

public class Apple
{
   private int x, y, w, h, xMax, xMin, yMax, yMin;
   private Color color;

   public Apple(int x, int y, int w, int h, int xMax, int xMin, int yMax, int yMin, Color color)
   {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
      this.xMax = xMax;
      this.xMin = xMin;
      this.yMin = yMin;
      this.yMax = yMax;
      this.color = color;
   }

   public int getX()
   {
      return x;
   }

   public void setX(int x)
   {
      this.x = x;
   }

   public int getY()
   {
      return y;
   }

   public void setY(int y)
   {
      this.y = y;
   }

   public int getW()
   {
      return w;
   }

   public void setW(int w)
   {
      this.w = w;
   }

   public int getH()
   {
      return h;
   }

   public void setH(int h)
   {
      this.h = h;
   }

   public Color getColor()
   {
      return color;
   }
   public int getyMax()
   {
      return yMax;
   }
   public int getyMin()
   {
      return yMin;
   }

   public void setColor(Color color)
   {
      this.color = color;
   }
   

   public Rectangle getBounds()
   {
      return new Rectangle(x, y, w, h);
   }

   public void draw(Graphics2D g2)
   {
      g2.setColor(color);
      g2.fillRect(x, y, w, h);
   }


   public boolean foodSnakeOverlap(ArrayList<SnakeBodyPart> snake)
   {
      for (SnakeBodyPart s : snake)
      {
         if (getBounds().intersects(s.getBounds()))
         {
            return true;
         }
      }
      return false;
   }

   public void move()
   {
      ArrayList<Integer> xVals = new ArrayList<Integer>();
      for (int i = xMin; i < xMax; i += w)
      {
         xVals.add(i);
      }
      x = xVals.get((int) (Math.random() * xVals.size()));
      ArrayList<Integer> yVals = new ArrayList<Integer>();
      for (int i = yMin; i < yMax; i += h)
      {
         yVals.add(i);
      }
      y = yVals.get((int) (Math.random() * yVals.size()));

   }

}
