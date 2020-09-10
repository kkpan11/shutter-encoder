/*******************************************************************************************
* Copyright (C) 2020 PACIFICO PAUL
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License along
* with this program; if not, write to the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
* 
********************************************************************************************/

package application;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JFrame;

import java.awt.Image;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import library.MEDIAINFO;

	public class Informations {
	public static JFrame frame;
	
	/*
	 * Composants
	 */
	private static JPanel topPanel;
	private boolean drag;
	private JLabel quit;
	private JLabel reduce;
	private JLabel topImage;
	private JLabel bottomImage;
	public static JPanel tabPanel;
	public static JLabel lblWait;
	public static JLabel lblFlecheBas;
	public static JTabbedPane infoTabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public Informations() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(Shutter.language.getProperty("frameInformations"));
		frame.setForeground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setUndecorated(true);
		Area shape1 = new Area(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 15, 15));
        Area shape2 = new Area(new Rectangle(0, frame.getHeight()-15, frame.getWidth(), 15));
        shape1.add(shape2);
		frame.setShape(shape1);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(100,100,100)));
		frame.setIconImage(new ImageIcon((getClass().getClassLoader().getResource("contents/icon.png"))).getImage());
		frame.setLocation(Shutter.frame.getLocation().x - frame.getSize().width -20, Shutter.frame.getLocation().y);	
		
				
		lblWait = new JLabel(Shutter.language.getProperty("lblWait"));
		lblWait.setFont(new Font("FreeSans", Font.PLAIN, 20));
		lblWait.setSize(lblWait.getPreferredSize().width, 40);
		lblWait.setLocation(frame.getSize().width / 2 - lblWait.getSize().width / 2, frame.getSize().height / 2);
		lblWait.setVisible(true);
		frame.getContentPane().add(lblWait);
		
		lblFlecheBas = new JLabel("▲▼");
		lblFlecheBas.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlecheBas.setFont(new Font("FreeSans", Font.PLAIN, 20));
		lblFlecheBas.setSize(new Dimension(frame.getSize().width, 20));
		lblFlecheBas.setLocation(0, frame.getSize().height - lblFlecheBas.getSize().height);
		lblFlecheBas.setVisible(false);
		
		frame.getContentPane().add(lblFlecheBas);
				
		topPanel();
		
		drag = false;		
				
		frame.addMouseMotionListener (new MouseMotionListener(){
 			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (drag && frame.getSize().height > 90 && MEDIAINFO.isRunning == false)
		       	{	
			        frame.setSize(frame.getSize().width, e.getY() + 10);		
			    	lblFlecheBas.setLocation(0, frame.getSize().height - lblFlecheBas.getSize().height);
			    	tabPanel.setBounds(0, topPanel.getSize().height, frame.getSize().width, frame.getSize().height - topPanel.getSize().height - 20);	
			    	infoTabbedPane.setBounds(tabPanel.getBounds());	
		       	}	
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if ((MouseInfo.getPointerInfo().getLocation().y - frame.getLocation().y) > frame.getSize().height - 20 && infoTabbedPane.getTabCount() > 0 && MEDIAINFO.isRunning == false)
					 frame.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				 else 
				{
					if (drag == false && infoTabbedPane.getTabCount() > 0 && MEDIAINFO.isRunning == false)
					 frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}				
			
		});
		
		frame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {				
			}

			@Override
			public void mousePressed(MouseEvent e) {		
				if (frame.getCursor().getType() == Cursor.S_RESIZE_CURSOR)
					drag = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {		
				drag = false;
				if (MEDIAINFO.isRunning == false)
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));	
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (frame.getSize().height <= 90)
				{
					frame.setSize(frame.getSize().width, 100);
		    		lblFlecheBas.setLocation(0, frame.getSize().height - lblFlecheBas.getSize().height);
		    		tabPanel.setBounds(0, topPanel.getSize().height, frame.getSize().width, frame.getSize().height - topPanel.getSize().height - 20);	
		    		infoTabbedPane.setBounds(tabPanel.getBounds());	
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {		
				if (frame.getCursor().getType() == Cursor.S_RESIZE_CURSOR)
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
		});		
			
		KeyListener keyListener = new KeyListener(){

			@Override	
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {	
				
				if (infoTabbedPane.getTabCount() > 1)
				{
					if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
							infoTabbedPane.remove(infoTabbedPane.getSelectedIndex());		
				}	
				else
				{
					if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
					{
						infoTabbedPane.removeAll();
						Utils.changeFrameVisibility(frame, true);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		};

		infoTabbedPane.addKeyListener(keyListener);
		frame.addKeyListener(keyListener);
	
    	frame.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e2)
		    {
				Area shape1 = new Area(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 15, 15));
		        Area shape2 = new Area(new Rectangle(0, frame.getHeight()-15, frame.getWidth(), 15));
		        shape1.add(shape2);
		    	frame.setShape(shape1);
		    }
 		});
		
		frame.addWindowListener(new WindowAdapter(){			
			public void windowDeiconified(WindowEvent we) {
		       
			   frame.toFront();
		    }
		});
        	
	}
	
	public static void addTabControl() {		
		tabPanel = new JPanel();			
		tabPanel.setBackground(new Color(50,50,50));
		tabPanel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		tabPanel.setLayout(null);
		tabPanel.setBounds(0, topPanel.getSize().height, frame.getSize().width, frame.getSize().height - topPanel.getSize().height - 20);				
		
		infoTabbedPane.setBounds(tabPanel.getBounds());	
		infoTabbedPane.setForeground(Color.WHITE);
		
		frame.getContentPane().add(infoTabbedPane);			
		
	}
	
	private static class MousePosition {
		static int mouseX;
		static int mouseY;
	}
	
	private void topPanel() {	
		topPanel	= new JPanel();
		topPanel.setLayout(null);
		topPanel.setBounds(0, 0, frame.getSize().width, 51);
		
		quit = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("contents/quit2.png")));
		quit.setBounds(frame.getSize().width - 24,0,21, 21);
		
		reduce = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("contents/reduce2.png")));
		reduce.setHorizontalAlignment(SwingConstants.CENTER);
		reduce.setBounds(quit.getLocation().x - 21,0,21, 21);
			
		reduce.addMouseListener(new MouseListener(){
			
			private boolean accept = false;

			@Override
			public void mouseClicked(MouseEvent e) {			
			}

			@Override
			public void mousePressed(MouseEvent e) {		
				reduce.setIcon(new ImageIcon((getClass().getClassLoader().getResource("contents/reduce3.png"))));
				accept = true;
			}

			@SuppressWarnings("static-access")
			@Override
			public void mouseReleased(MouseEvent e) {		
				
				if (accept)
				{							
					
					frame.setState(frame.ICONIFIED);	
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {			
				reduce.setIcon(new ImageIcon((getClass().getClassLoader().getResource("contents/reduce.png"))));
			}

			@Override
			public void mouseExited(MouseEvent e) {		
				reduce.setIcon(new ImageIcon((getClass().getClassLoader().getResource("contents/reduce2.png"))));
				accept = false;
			}
			
			
		});
				
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("contents/header.png"));
		Image scaledImage = image.getImage().getScaledInstance(topPanel.getSize().width, topPanel.getSize().height, Image.SCALE_SMOOTH);
		ImageIcon header = new ImageIcon(scaledImage);
		bottomImage = new JLabel(header);
		bottomImage.setBounds(0 ,0, frame.getSize().width, 51);
			
		JLabel title = new JLabel(Shutter.language.getProperty("frameInformations"));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(0, 0, frame.getWidth(), 52);
		title.setFont(new Font("Magneto", Font.PLAIN, 26));
		topPanel.add(title);
		
		topImage = new JLabel();
		ImageIcon imageIcon = new ImageIcon(header.getImage().getScaledInstance(topPanel.getSize().width, topPanel.getSize().height, Image.SCALE_DEFAULT));
		topImage.setIcon(imageIcon);		
		topImage.setBounds(title.getBounds());
				
		topPanel.add(quit);	
		topPanel.add(reduce);	
		topPanel.add(topImage);
		topPanel.add(bottomImage);
		
		quit.addMouseListener(new MouseListener(){

			private boolean accept = false;

			@Override
			public void mouseClicked(MouseEvent e) {			
			}

			@Override
			public void mousePressed(MouseEvent e) {		
				quit.setIcon(new ImageIcon((getClass().getClassLoader().getResource("contents/quit3.png"))));
				accept = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {	
				if (accept)		
				{		  
					infoTabbedPane.removeAll();
					Utils.changeFrameVisibility(frame, true);	
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {			
				quit.setIcon(new ImageIcon((getClass().getClassLoader().getResource("contents/quit.png"))));
			}

			@Override
			public void mouseExited(MouseEvent e) {		
				quit.setIcon(new ImageIcon((getClass().getClassLoader().getResource("contents/quit2.png"))));
				accept = false;
			}

						
		});
		topPanel.setBounds(0, 0, frame.getSize().width, 51);
		frame.getContentPane().add(topPanel);						
		
		bottomImage.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent down) {
			}

			@Override
			public void mousePressed(MouseEvent down) {
				MousePosition.mouseX = down.getPoint().x;
				MousePosition.mouseY = down.getPoint().y;	
				
				frame.toFront();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {					
			}

			@Override
			public void mouseExited(MouseEvent e) {				
			}		

		 });
		 		
		bottomImage.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
					frame.setLocation(MouseInfo.getPointerInfo().getLocation().x - MousePosition.mouseX, MouseInfo.getPointerInfo().getLocation().y - MousePosition.mouseY);	
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
		});
		
	}
}