import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ControlPoints{
	Panel N, S, E, W, NE, NW, SE, SW;
	FurnitureInCanvas parent=null;
	boolean visible=true;
	Cursor currentCursor;
	Point lp,cp,op;
	int ow,oh;
	int newX,newY,newW,newH;
	
	ControlPoints(FurnitureInCanvas p){
		
		parent=p;
		
		N=new Panel();
		S=new Panel();
		E=new Panel();
		W=new Panel();
		NE=new Panel();
		NW=new Panel();
		SE=new Panel();
		SW=new Panel();
		
		N.setSize(7,7);
		S.setSize(7,7);
		E.setSize(7,7);
		W.setSize(7,7);
		NE.setSize(7,7);
		NW.setSize(7,7);
		SE.setSize(7,7);
		SW.setSize(7,7);
		
		N.setBackground(Color.gray);
		S.setBackground(Color.gray);
		E.setBackground(Color.gray);
		W.setBackground(Color.gray);
		NE.setBackground(Color.gray);
		NW.setBackground(Color.gray);
		SE.setBackground(Color.gray);
		SW.setBackground(Color.gray);
		
		N.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.N.getCursor();
				ControlPoints.this.N.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.N.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();				
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            parent.parent.actFurniture.label.setLocation(op);				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		N.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
				{
					ControlPoints.this.parent.status=Status.Resizing;
					cp = e.getLocationOnScreen();
					
					int dy= cp.y - lp.y;
					
					Dimension s = ControlPoints.this.parent.label.getSize();
					Point l = ControlPoints.this.parent.label.getLocation(); 
					
					newH=s.height-dy;
					newH = Math.min(Math.max(newH, 1), oh+op.y-101);
					
					newY=l.y+dy;
					newY = Math.max(Math.min(newY, op.y+oh-1), 101);
					
					ControlPoints.this.parent.label.setLocation(l.x, newY);
					ControlPoints.this.parent.label.setSize(s.width, newH);
					
					ControlPoints.this.parent.parent.repaint();
					ControlPoints.this.setVisible(true);
					
					lp=cp;		
					
					
				}
			}
		});
		
		S.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.S.getCursor();
				ControlPoints.this.S.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.S.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();	
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		S.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
				{
					ControlPoints.this.parent.status=Status.Resizing;
					cp = e.getLocationOnScreen();
					
					int dy= cp.y - lp.y;
					
					Dimension s = ControlPoints.this.parent.label.getSize();
					
					newH=s.height+dy;
					newH = Math.min(Math.max(newH, 1), (int)(parent.parent.rectHeight*parent.parent.scale)+100 - parent.label.getY());
					
					ControlPoints.this.parent.label.setSize(s.width, newH);
					
					ControlPoints.this.parent.parent.repaint();
					ControlPoints.this.setVisible(true);
					
					lp=cp;		
				}
			}
		});
		
		E.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.E.getCursor();
				ControlPoints.this.E.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.E.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();	
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		E.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
						{
							ControlPoints.this.parent.status=Status.Resizing;
							cp = e.getLocationOnScreen();
							
							int dx= cp.x - lp.x;
							
							Dimension s = ControlPoints.this.parent.label.getSize();
							
							newW=s.width+dx;
							newW = Math.min(Math.max(newW, 1), (int)(parent.parent.rectWidth*parent.parent.scale)+90 - parent.label.getX());
							
							ControlPoints.this.parent.label.setSize(newW, s.height);
							
							ControlPoints.this.parent.parent.repaint();
							ControlPoints.this.setVisible(true);
							
							lp=cp;		
						}
			}
		});
		
		W.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.W.getCursor();
				ControlPoints.this.W.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.W.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();	
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            parent.parent.actFurniture.label.setLocation(op);
				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		W.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
				{
					ControlPoints.this.parent.status=Status.Resizing;
					cp = e.getLocationOnScreen();
					
					int dx= cp.x - lp.x;
					
					Dimension s = ControlPoints.this.parent.label.getSize();
					Point l = ControlPoints.this.parent.label.getLocation(); 
					
					newW=s.width-dx;
					newW = Math.min(Math.max(newW, 1), ow+op.x-91);
					
					newX=l.x+dx;
					newX = Math.max(Math.min(newX, op.x+ow-1), 91);
					
					ControlPoints.this.parent.label.setLocation(newX, l.y);
					ControlPoints.this.parent.label.setSize(newW, s.height);
					
					ControlPoints.this.parent.parent.repaint();
					ControlPoints.this.setVisible(true);
					
					lp=cp;		
				}
			}
		});
		
		NE.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.NE.getCursor();
				ControlPoints.this.NE.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.NE.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();	
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            parent.parent.actFurniture.label.setLocation(op);
				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		NE.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
				{
					ControlPoints.this.parent.status=Status.Resizing;
					cp = e.getLocationOnScreen();
					
					int dx= cp.x - lp.x;
					int dy= cp.y - lp.y;
					
					Dimension s = ControlPoints.this.parent.label.getSize();
					Point l = ControlPoints.this.parent.label.getLocation(); 
					
					newW=s.width+dx;
					newW = Math.min(Math.max(newW, 1), (int)(parent.parent.rectWidth*parent.parent.scale)+90 - parent.label.getX());
					
					
					newH=s.height-dy;
					newH = Math.min(Math.max(newH, 1), oh+op.y-101);
					
					newY=l.y+dy;
					newY = Math.max(Math.min(newY, op.y+oh-1), 101);
					
					ControlPoints.this.parent.label.setLocation(l.x, newY);
					ControlPoints.this.parent.label.setSize(newW, newH);
					
					ControlPoints.this.parent.parent.repaint();
					ControlPoints.this.setVisible(true);
					
					lp=cp;		
				}
			}
		});
		
		NW.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.NW.getCursor();
				ControlPoints.this.NW.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.NW.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();	
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            parent.parent.actFurniture.label.setLocation(op);
				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		NW.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
				{
					ControlPoints.this.parent.status=Status.Resizing;
					cp = e.getLocationOnScreen();
					
					int dx= cp.x - lp.x;
					int dy= cp.y - lp.y;
					
					Dimension s = ControlPoints.this.parent.label.getSize();
					Point l = ControlPoints.this.parent.label.getLocation(); 
					
					newW=s.width-dx;
					newW = Math.min(Math.max(newW, 1), ow+op.x-91);
					
					newH=s.height-dy;
					newH = Math.min(Math.max(newH, 1), oh+op.y-101);
					
					newX=l.x+dx;
					newX = Math.max(Math.min(newX, op.x+ow-1), 91);
					
					newY=l.y+dy;
					newY = Math.max(Math.min(newY, op.y+oh-1), 101);
					
					ControlPoints.this.parent.label.setLocation(newX, newY);
					ControlPoints.this.parent.label.setSize(newW, newH);
					
					ControlPoints.this.parent.parent.repaint();
					ControlPoints.this.setVisible(true);
					
					lp=cp;		
				}
			}
		});
		
		SE.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.SE.getCursor();
				ControlPoints.this.SE.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.SE.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();	
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		SE.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
				{
					ControlPoints.this.parent.status=Status.Resizing;
					cp = e.getLocationOnScreen();
					
					int dx= cp.x - lp.x;
					int dy= cp.y - lp.y;
					
					Dimension s = ControlPoints.this.parent.label.getSize();
					
					newW=s.width+dx;
					newW = Math.min(Math.max(newW, 1), (int)(parent.parent.rectWidth*parent.parent.scale)+90 - parent.label.getX());
					
					newH=s.height+dy;
					newH = Math.min(Math.max(newH, 1), (int)(parent.parent.rectHeight*parent.parent.scale)+100 - parent.label.getY());
					
					
					ControlPoints.this.parent.label.setSize(newW, newH);
					
					ControlPoints.this.parent.parent.repaint();
					ControlPoints.this.setVisible(true);
					
					lp=cp;		
				}
			}
		});
		
		SW.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ControlPoints.this.currentCursor = ControlPoints.this.SW.getCursor();
				ControlPoints.this.SW.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				ControlPoints.this.SW.setCursor(ControlPoints.this.currentCursor);
			}
			
			public void mousePressed(MouseEvent e)
			{
				if(ControlPoints.this.parent.status==Status.selected)
				{
					ControlPoints.this.parent.status=Status.ready2Resize;
					parent.parent.setComponentZOrder(parent.label, 0);
					lp=e.getLocationOnScreen();
					ow=parent.label.getWidth();
					oh=parent.label.getHeight();
					op=parent.label.getLocation();	
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if((parent.status==Status.Resizing)&&(parent.parent.furnitures.size()>=2)) {
					for (int i = 0; i < parent.parent.furnitures.size(); i++) {
						JLabel label1 = parent.parent.actFurniture.label;
				        JLabel label2 = parent.parent.furnitures.get(i).label;

				        Rectangle bounds1 = label1.getBounds();
				        Rectangle bounds2 = label2.getBounds();
				            
				        //家具如果重疊做復位
				        if ((bounds1.intersects(bounds2))&&(parent.parent.actFurniture!=parent.parent.furnitures.get(i))) {
				            parent.parent.actFurniture.label.setSize(ow,oh);	
				            parent.parent.actFurniture.label.setLocation(op);
				            
				            if (parent.imageIcon != null) {
								
								// 取得標籤新大小
								int width = parent.label.getWidth();
								int height = parent.label.getHeight();
								
								if((parent.ang==0)||(parent.ang%180==0)) {
									parent.w=width;		//更新物件長寬
									parent.h=height;
								}
								else {
									parent.w=height;		//更新物件長寬
									parent.h=width;
								}
								parent.loadImageToLabel(parent.icon);		// 調整圖片大小
								parent.toolbar.removeattr();
								parent.toolbar.showattr(parent.name,parent.w,parent.h,parent.ang,'o');
				            
				            }
				            
				        }
				    }
					ControlPoints.this.parent.status=Status.selected;
				}
				parent.doSelected();
				Previous prevtemp=new Previous("resize",ControlPoints.this.parent,ow,oh,op);
        		parent.parent.prevSteps.add(prevtemp);
			}
		});
		
		SW.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if((ControlPoints.this.parent.status==Status.ready2Resize)||(ControlPoints.this.parent.status==Status.Resizing))
				{
					ControlPoints.this.parent.status=Status.Resizing;
					cp = e.getLocationOnScreen();
					
					int dx= cp.x - lp.x;
					int dy= cp.y - lp.y;
					
					Dimension s = ControlPoints.this.parent.label.getSize();
					Point l = ControlPoints.this.parent.label.getLocation(); 
					
					newW=s.width-dx;
					newW = Math.min(Math.max(newW, 1), ow+op.x-91);
					
					newH=s.height+dy;
					newH = Math.min(Math.max(newH, 1), (int)(parent.parent.rectHeight*parent.parent.scale)+100 - parent.label.getY());
					
					
					newX=l.x+dx;
					newX = Math.max(Math.min(newX, op.x+ow-1), 91);				
									
					ControlPoints.this.parent.label.setLocation(newX, l.y);
					ControlPoints.this.parent.label.setSize(newW, newH);
					
					ControlPoints.this.parent.parent.repaint();
					ControlPoints.this.setVisible(true);
					
					lp=cp;		
				}
			}
		});
		
		parent.parent.add(N);
		parent.parent.add(S);
		parent.parent.add(E);
		parent.parent.add(W);
		parent.parent.add(NE);
		parent.parent.add(NW);
		parent.parent.add(SE);
		parent.parent.add(SW);
	}
	
	void setVisible(boolean b) {
		if(b) {
			Point loc=parent.label.getLocation();
			Dimension d=parent.label.getSize();
			
			N.setLocation(loc.x+(d.width/2)-3, loc.y-9);
			S.setLocation(loc.x+(d.width/2)-3, loc.y+d.height+3);
			E.setLocation(loc.x+d.width+3, loc.y+d.height/2-3);
			W.setLocation(loc.x-9, loc.y+(d.height/2)-3);
			NE.setLocation(loc.x+d.width+3, loc.y-9);
			NW.setLocation(loc.x-9, loc.y-9);
			SE.setLocation(loc.x+d.width+3, loc.y+d.height+3);
			SW.setLocation(loc.x-9, loc.y+d.height+3);
		}
		
		N.setVisible(b);
		S.setVisible(b);
		E.setVisible(b);
		W.setVisible(b);
		NE.setVisible(b);
		NW.setVisible(b);
		SE.setVisible(b);
		SW.setVisible(b);
	}
	
	void pointrepaint() {	//控制點重繪
		setVisible(true);
		parent.parent.repaint();
	}
}

