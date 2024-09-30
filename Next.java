import java.awt.Point;

public class Next {
	Canvas cparent;
	FurnitureInCanvas fparent;
	DrawInAddFurniCanvas fparent2;
	String imgpath;
	String state;
	int x,y;
	Toolbar bar;
	int ang;
	Point op;
	int width,height;
	
	//create, delete, turnLeft, turnRight函式
	Next(String s,FurnitureInCanvas p){
		state=s;
		fparent=p;
	}
	
	Next(String s,DrawInAddFurniCanvas p){
		state=s;
		fparent2=p;
	}
	
	
	//move函式
	Next(String s,FurnitureInCanvas p, Point a){
		state=s;
		fparent=p;
		op=a;
	}
	
	Next(String s,DrawInAddFurniCanvas p, Point a){
		state=s;
		fparent2=p;
		op=a;
	}
	
	//resize函式
	Next(String s,FurnitureInCanvas p,Point a,int w,int h){
		state=s;
		fparent=p;
		width=w;
		height=h;
		op=a;
	}
	
	Next(String s,DrawInAddFurniCanvas p,Point a,int w,int h){
		state=s;
		fparent2=p;
		width=w;
		height=h;
		op=a;
	}
}
