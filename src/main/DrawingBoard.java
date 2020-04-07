package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
	}
	
	public void groupAll() {
		CompositeGObject cgObject = new CompositeGObject();
		for(GObject obj : gObjects){
			cgObject.add(obj);
		}
		gObjects.clear();
		gObjects.add(cgObject);
	}

	public void deleteSelected() {
		if (target != null) gObjects.remove(target);
	}
	
	public void clear() {
		gObjects.clear();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		public int mouseX = 0;
		public int mouseY = 0;
		
		private void deselectAll() {
			for (GObject obj : gObjects){
				obj.deselected();
				target = null;
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			deselectAll();
			mouseX = e.getX();
			mouseY = e.getY();
			for (GObject obj : gObjects){
				if (obj.pointerHit(mouseX, mouseY)) {
					target = obj;
					target.selected();
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			try {
				target.move(e.getX() - mouseX, e.getY() - mouseY);
				mouseX = e.getX();
				mouseY = e.getY();
			}
			catch (Exception ignored) {
				;
			}
		}
	}
	
}