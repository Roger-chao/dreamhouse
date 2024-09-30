import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;

public class Rect {

	Point sp, ep;
	AddFurniCanvas AddFurniCanvas;
	
	Rect(Point s, Point e)
	{
		sp=s;
		ep=e;
		//g.setColor(AddFurniCanvas.selectedColor1);
		//g.drawRect(e.x, s.y, s.x-e.x, e.y-s.y);
	}
}