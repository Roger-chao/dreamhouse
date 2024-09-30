import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;

public class Oval {

	Point fp, cp;
	AddFurniCanvas AddFurniCanvas;
	
	Oval(Point s, Point e)
	{
		super();
		fp=s;
		cp=e;
		//g.setColor(AddFurniCanvas.selectedColor1);
		//g.drawOval(e.x, s.y, s.x-e.x, e.y-s.y);
		//System.out.println("TEST:"+AddFurniCanvas.selectedColor1);
	}
}