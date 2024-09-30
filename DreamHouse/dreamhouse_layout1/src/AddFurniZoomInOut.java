//import java.awt.*;
//import java.awt.event.*;
//
//public class AddFurniZoomInOut extends Panel{
//	private Color backgroundColor = new Color(220,220,220);
//	
//	int num=100;
//	AddFurniCanvas parent=null;
//	
//	Button smallBtn = null;
//	Button bigBtn = null;
//	
//	public AddFurniZoomInOut(AddFurniCanvas p) {
//		parent = p;
//		setBackground(backgroundColor);
//        smallBtn = new Button("-");
//        add(smallBtn);
//        Label label = new Label(num+"%",Label.CENTER);
//        add(label);
//        bigBtn = new Button("+");
//        add(bigBtn);
//        
//        smallBtn.addMouseListener(new MouseAdapter() {
//  			public void mouseClicked(MouseEvent e){
//  					num-=10;
//  					if(num<10)
//  						num=10;
//  					label.setText(num+"%");
//  					actzoomout();
//        	}
//        });
//        bigBtn.addMouseListener(new MouseAdapter() {
//  			public void mouseClicked(MouseEvent e){
//        		num+=10;
//        		if(num>200)
//        			num=200;
//        		label.setText(num+"%");
//        		actzoomin();
//        	}
//        });
//        
//	}
//	public void actzoomin() {
//		parent.zoomscale(1);
//    }
//	public void actzoomout() {
//		parent.zoomscale(0);
//    }
//
//}