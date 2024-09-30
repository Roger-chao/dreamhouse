import java.awt.*;

public class Previous {
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
	double scale;

	//create, delete, turnLeft, turnRight建構函式
	Previous(String s,FurnitureInCanvas p){
		state=s;
		fparent=p;
	}
	
	Previous(String s,DrawInAddFurniCanvas p){
		state=s;
		fparent2=p;
	}
	
	//move建構函示
	Previous(String s,FurnitureInCanvas p,Point a){
		state=s;
		fparent=p;
		op=a;
	}
	
	Previous(String s,DrawInAddFurniCanvas p,Point a){
		state=s;
		fparent2=p;
		op=a;
	}
	
	//resize建構函示
	Previous(String s,FurnitureInCanvas p,int w,int h,Point a){
		state=s;
		fparent=p;
		width=w;
		height=h;
		op=a;
	}
	
	Previous(String s,DrawInAddFurniCanvas p,int w,int h,Point a){
		state=s;
		fparent2=p;
		width=w;
		height=h;
		op=a;
	}
}
