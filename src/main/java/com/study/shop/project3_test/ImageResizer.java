package com.study.shop.project3_test;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ImageResizer extends JPanel implements MouseMotionListener
{
    public static void main(String[] args) 
    {
        JFrame f = new JFrame();
        f.setResizable(true);
        f.setTitle("Image Resizer");
        f.getContentPane().setBackground(Color.WHITE);
        launch(f, new ImageResizer(), 640, 480);
    }

    ///////////////////////////////////////////////////////////////////////////
    
    public ImageResizer()
    {
        super();
        this.img = image(ImageResizer.class.getResource("atech_logo.png"));
    }
    
    public void paint(Graphics o)
    {
        super.paint(o);
        Graphics2D g = (Graphics2D) o;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (s_w != this.getSize().width || s_h != this.getSize().height)
        {
            s_w = this.getSize().width;
            s_h = this.getSize().height;
            
            
            System.out.println("[INF] screen resized ... regenerate a rectangle ...");
            regenerate();
        }
        
        g.drawImage(img, i_x, i_y, i_w, i_h, null);
    }
    
    int[] pix;
    Image img;
    int   i_x;  // image position x
    int   i_y;  // image position y
    int   i_w;  // image width
    int   i_h;  // image height
    int   s_w;  // screen dimension width
    int   s_h;  // screen dimension height
    
    public void regenerate()
    {
    	double ratio = ((double)img.getHeight(null)/(double)img.getWidth(null)) * 100;
    	//스크린 창 사이즈에 따라 이미지 사이즈 변경
    	//이미지의 폭과 너비 "비율"에 맞게 변경되도록
    	//if(s_h < img.getHeight(null)/4 || s_h > img.getHeight(null)/4) {
    	if(s_h * 100 >= s_w * ratio) {
            System.out.println(1);
    		i_w = s_w;
    		i_h = (int)((double)s_w * ratio / 100);
    	}
    	else {
            System.out.println(2);
			i_h = s_h;
			i_w = (int)((double)s_h * 100 / ratio);
		}
    	i_x = 0;
    	i_y = 0;
    	
    	
    	
    	System.out.println(ratio);
        //(getWidth() - image.getWidth(this)) / 2
        System.out.println(i_w);
        System.out.println(i_h);
        //중점 맞추기
        i_x = (s_w - i_w) / 2;
        i_y = (s_h - i_h) / 2;

        
       // i_w = img.getWidth(null)/4;
       // i_h = img.getHeight(null)/4; //s_h * s_h/img.getHeight(null)
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    public static BufferedImage image(URL url)
    {
        try
        {
            return ImageIO.read(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void launch(final JFrame f, final Component c, final int w, final int h)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                if (false) JFrame.setDefaultLookAndFeelDecorated(true);
                f.setSize(new Dimension(w, h));

                f.getContentPane().setLayout(new BorderLayout());
                f.getContentPane().add(c, BorderLayout.CENTER);

                c.addMouseMotionListener((MouseMotionListener) c);
                
                Toolkit tk = Toolkit.getDefaultToolkit();
                final int w = ((int) tk.getScreenSize().getWidth());
                final int h = ((int) tk.getScreenSize().getHeight());
                f.setLocation((w - f.getSize().width) / 2, (h - f.getSize().height) / 2);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        f.setVisible(true);
                        f.setFocusable(true);

                    }
                });
            }
        });
    }

    public void mouseDragged(MouseEvent e)
    {
        this.repaint();
    }

    public void mouseMoved(MouseEvent e)
    {
        this.repaint();
    }
}
