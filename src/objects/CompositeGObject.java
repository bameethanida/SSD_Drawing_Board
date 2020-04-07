package objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		gObjects.add(gObject);
		recalculateRegion();
	}

	public void remove(GObject gObject) {
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		for (GObject g : gObjects) {
			g.move(dX, dY);
		}
	}
	
	public void recalculateRegion() {
		int xMax = 0;
		int xMin = gObjects.get(0).x;
		int yMax = 0;
		int yMin = gObjects.get(0).y;

		for (GObject g: gObjects){
			if (g.x + g.width > xMax)
				xMax = g.x + g.width;
			if (g.x < xMin)
				xMin = g.x;
			if (g.y + g.height > yMax)
				yMax = g.y + g.height;
			if (g.y < yMin)
				yMin = g.y;
		}
		super.x = xMin;
		super.y = yMin;
		super.width = xMax - xMin;
		super.height = yMax - yMin;
	}

	@Override
	public void paintObject(Graphics g) {
		for (GObject gOb : gObjects){
			gOb.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		g.drawString("Group", x, y + height + 10);
	}
	
}
