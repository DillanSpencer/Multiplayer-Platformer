package com.quad.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.quad.net.packets.Packet01Disconnect;

public class Window
{
	private JFrame frame;
	private Canvas canvas;
	private BufferedImage image;
	private Graphics g;
	private BufferStrategy bs;
	private GraphicsEnvironment env;
	private GraphicsDevice device;
	private DisplayMode dm;
	
	public Window(GameContainer gc)
	{
		image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setPreferredSize(s);
		
		frame = new JFrame(gc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		//check if window closes
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Packet01Disconnect packet = new Packet01Disconnect(Settings.USERNAME);
				packet.writeData(gc.getClient());
			}
		});
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public Window(GameContainer gc, boolean fullscreen){
		image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setPreferredSize(s);
		
		frame = new JFrame(gc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.setUndecorated(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		GraphicsDevice device = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if (device.isFullScreenSupported()) {
		    device.setFullScreenWindow(frame);
		    if (device.isDisplayChangeSupported()) {
		        try {
		            List<DisplayMode> matchingModes = new ArrayList<>(25);
		
		            DisplayMode[] modes = device.getDisplayModes();
		            for (DisplayMode mode : modes) {
		                if (mode.getWidth() == 1920 && mode.getHeight() == 1080) {
		                    matchingModes.add(mode);
		                }
		            }
		
		            if (!matchingModes.isEmpty()) {
		                for (DisplayMode mode : matchingModes) {
		                    try {
		                        device.setDisplayMode(mode);
		                        System.out.println(mode.getWidth() + "x" + mode.getHeight() + " " + mode.getBitDepth() + " @ " + mode.getRefreshRate());
		                        break;
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            } else {
		                System.err.println("!! No matching modes available");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    } else {
		        System.err.println("Change display mode not supported");
		    }
		} else {
		    System.err.println("Full screen not supported");
		}

	   frame.createBufferStrategy(2);
	   frame.setResizable(false);

	   try { // 
	     Thread.sleep(1000); 
	   }
	   catch (InterruptedException ex) {}

	  

	   canvas.createBufferStrategy(2);
	   canvas.requestFocus();
	  // canvas.setIgnoreRepaint(true);
	   frame.setVisible(true);
	   
	   bs = canvas.getBufferStrategy(); 
	   g = bs.getDrawGraphics();
	}
	
	public void update()
	{
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
	}
	
	public void cleanUp()
	{
		g.dispose();
		bs.dispose();
		image.flush();
		frame.dispose();
	}

	public Canvas getCanvas()
	{
		return canvas;
	}

	public BufferedImage getImage()
	{
		return image;
	}
	
}