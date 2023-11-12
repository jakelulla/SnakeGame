import java.awt.Dimension;
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

public class PanelTemplate extends JPanel
{
   public static final int PREF_W = 800;
   public static final int PREF_H = 600;
   
   private Timer timer;
   public PanelTemplate() {
      this.setFocusable(true);
      timer = new Timer(100, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            
         }
         
      });
      timer.start();
   }
   public void update() {
      //game code goes here
      
      
      
      repaint();
   }
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }
   
   
   public static void main (String [] args) {
      JFrame frame = new JFrame("Snake!");
      JPanel gamePanel = new PanelTemplate();
      frame.add(gamePanel);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.fillArc(10, 10, 250, 250, 360, 360);   
      repaint();
   }
}
