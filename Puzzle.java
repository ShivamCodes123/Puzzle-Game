import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Puzzle implements MouseListener{
	JFrame frame;
	JPanel panel;
	JPanel subPanel;
	ArrayList<BufferedImage> bImg;
	ArrayList<JLabel> images = new ArrayList<JLabel>();
	ArrayList<JLabel> finPuzzle = new ArrayList<JLabel>();
	ImageIcon img = new ImageIcon();
	int blankX = 0;
	int blankY = 0;
	JLabel blank;
	int randNum;
	int x;
	JLabel missing;
	
	public Puzzle(int xPos){
		x = xPos;
		blank = new JLabel();
		bImg = new ArrayList<BufferedImage>();
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		ImageIcon datta = new ImageIcon("/Users/shiva/eclipse-workspace/Puzzle/src/datta.png");
		panel.add(new JLabel(datta));
		subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(x, x));
		subPanel.addMouseListener(this);
		splitDatta(datta, x);
		frame.setSize(1000, 510);
		int jump = 0;
		for (int i = 0; i < bImg.size(); i+=x) {
			System.out.println(i);
			finPuzzle.add(new JLabel(new ImageIcon(bImg.get(i))));
			if(i+x >= bImg.size()) {
				
				jump++;
				if(jump < x) {
					i = jump - x;
					System.out.println(bImg.size());
				}
				else {
					break;
				}
			}
		}
		finPuzzle.remove(finPuzzle.size()-1);
		
		for (int i = 0; i < x*x - 1; i++) {
			randNum = (int)(Math.random() * finPuzzle.size());
			if(!images.contains(finPuzzle.get(randNum))) {
				images.add(finPuzzle.get(randNum));
				subPanel.add(finPuzzle.get(randNum));
			}
			else {
				i--;
			}
		}
		missing = new JLabel(new ImageIcon(bImg.get(bImg.size()-1)));
		subPanel.add(blank);
		images.add(blank);
		finPuzzle.add(blank);
		panel.add(subPanel);
		frame.add(panel);
		frame.setVisible(true);
	}
	public void drawer() {
		subPanel.removeAll();
		for (int i = 0; i < images.size(); i++) {
			if(images.get(i).equals(blank) == false) {
				subPanel.add(images.get(i));
			}
			else {
				subPanel.add(blank);
			}
		}
		panel.add(subPanel);
		frame.add(panel);
		frame.setVisible(true);
		int count = 0;
		for (int i = 0; i < images.size(); i++) {
			if(images.get(i).equals(finPuzzle.get(i))) {
				System.out.println("t");
				count++;
			}
			else {
				System.out.println("f");
			}
		}
		System.out.println(count);
		if(count >= images.size() - 1) {
			System.out.println("yay");
			System.out.println(finPuzzle.indexOf(blank));
			finPuzzle.set(finPuzzle.indexOf(blank), missing);
			subPanel.removeAll();

			for (int i = 0; i < finPuzzle.size(); i++) {
				System.out.println("i " + i);
				subPanel.add(finPuzzle.get(i));
			}
			System.out.println(((JLabel) subPanel.getComponent(3)).getWidth());
			images = finPuzzle;
			panel.add(subPanel);
			frame.add(panel);
			frame.setVisible(true);
		}
		else {
			count = 0;
		}
		
		frame.repaint();
	}
	private void splitDatta(ImageIcon datta, int x) {
		BufferedImage bi = new BufferedImage(
			    datta.getIconWidth(),
			    datta.getIconHeight(),
			    BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.createGraphics();
			// paint the Icon to the BufferedImage.
			datta.paintIcon(null, g, 0,0);
			g.dispose();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				BufferedImage subimage = bi.getSubimage(datta.getIconWidth()/x*i, datta.getIconHeight()/x*j, datta.getIconWidth()/x, datta.getIconHeight()/x);
				bImg.add(subimage);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel p = (JLabel) subPanel.getComponentAt(e.getPoint());
		System.out.println(returnIndex(p));
		System.out.println("hello");
		if((returnIndex(p)+1)%x != 0) {
			if(images.get(returnIndex(p) + 1).equals(blank)) {
				swap(e, p);
				drawer();
				return;
			}
		}
		if((returnIndex(p)+1)%x != 1) {
			if((subPanel.findComponentAt((int)(e.getPoint().getX() - p.getWidth()), (int)(e.getPoint().getY()))).equals(blank)) {
				swap(e, p);
				drawer();
				return;
			}
		}
		if(returnIndex(p) < x*x-x) {
			System.out.println((int)(e.getPoint().getX()) + " " + (int)(e.getPoint().getY() + p.getHeight()));
			System.out.println(blank.getX() + " " + blank.getY());
			if((subPanel.findComponentAt((int)(e.getPoint().getX()), (int)(e.getPoint().getY() + p.getHeight()))).equals(blank)) {

				swap(e, p);
				drawer();
				return;
			}
		}
		System.out.println(returnIndex(p));
		if(returnIndex(p)+1 > x) {
			System.out.println("hey");
			if((subPanel.findComponentAt((int)(e.getPoint().getX()), (int)(e.getPoint().getY() - p.getHeight()))).equals(blank)) {
				swap(e, p);
				drawer();
				return;
			}
		}
	}
	public void swap(MouseEvent e, JLabel p) {
		int index1 = 0;
		int index2 = 0;
		for (int i = 0; i < images.size(); i++) {
			if(images.get(i).equals(blank)) {
				index2 = i;
			}
			else if(images.get(i).getX() == p.getX() && images.get(i).getY() == p.getY()) {
				index1 = i;
			}
			
		}
		JLabel temp = images.get(index1);
		images.set(index1, images.get(index2));
		images.set(index2, temp);
	}
	public int returnIndex(JLabel p) {
		for (int i = 0; i < images.size(); i++) {
			if(images.get(i).getLocation().equals(p.getLocation())) {
				return i;
			}
		}
		
		return -1;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
