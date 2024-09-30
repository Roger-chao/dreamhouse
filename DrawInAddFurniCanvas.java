import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DrawInAddFurniCanvas extends JPanel {
	String name;
	int ang,w,h;
	Point op,lp,cp;//移動的起點紀錄
	double scale;
	Status status;
	boolean visible;
	Cursor currentCursor;
	
	AddFurniCanvas parent=null;
	AddFurniToolbar toolbar;
	JLabel label=new JLabel();
	BufferedImage temp;
	ImageIcon imageIcon;
	
	DrawInAddFurniCanvas(AddFurniCanvas p,int setX,int setY,AddFurniToolbar tool,int angel,double sc){
		super();
		parent=p;
		status=Status.selected;
		ang=angel;
		scale=sc;
		
		
		parent.add(label);
		label.setSize(w, h);
		label.setVisible(false);
		parent.initw=w;
		parent.inith=h;
		label.setLocation(setX-w/2, setY-h/2);
		toolbar = tool;		
		
		//移動
		label.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				DrawInAddFurniCanvas.this.currentCursor = DrawInAddFurniCanvas.this.label.getCursor();
				DrawInAddFurniCanvas.this.label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				DrawInAddFurniCanvas.this.label.setCursor(DrawInAddFurniCanvas.this.currentCursor);
			
			
            
            	
            }
            
            public void mouseReleased(MouseEvent e) {
            	
            	//檢查家具是否重疊
				if(DrawInAddFurniCanvas.this.status==Status.Moving) {
					if(parent.furnitures.size()>=2) {
						for (int i = 0; i < parent.furnitures.size(); i++) {
							JLabel label1 = parent.actFurniture.label;
			            	JLabel label2 = parent.furnitures.get(i).label;

			            	Rectangle bounds1 = label1.getBounds();
			            	Rectangle bounds2 = label2.getBounds();
			            
			            	//家具如果重疊做復位
			            	if ((bounds1.intersects(bounds2))&&(parent.actFurniture!=parent.furnitures.get(i))) {
			                	parent.actFurniture.label.setLocation(op);			                    
			            	}
						}
					}
				
					Previous prevtemp=new Previous("move",DrawInAddFurniCanvas.this,op);
				}
				DrawInAddFurniCanvas.this.label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				DrawInAddFurniCanvas.this.status=Status.selected;
				
			}
        });
		label.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
            	if(DrawInAddFurniCanvas.this.status==Status.ready2Move) {
            		DrawInAddFurniCanvas.this.status=Status.Moving;
            		cp=e.getLocationOnScreen();
    				
            		Point loc=DrawInAddFurniCanvas.this.label.getLocation();
				
            		int newX=(loc.x+cp.x-lp.x);
            		int newY=(loc.y+cp.y-lp.y);
				
            		//檢查邊界，確保標籤不會超出容器
            		newX = Math.min(Math.max(newX, 91), (int)(p.rectWidth)+90 - label.getWidth());
            		newY = Math.min(Math.max(newY, 101), (int)(p.rectHeight)+100 - label.getHeight());
                
            		DrawInAddFurniCanvas.this.label.setLocation(newX, newY);
            		DrawInAddFurniCanvas.this.parent.repaint();
            		lp=cp;

            	}
            	else if(DrawInAddFurniCanvas.this.status==Status.Moving) {
            		cp=e.getLocationOnScreen();
    				
            		Point loc=DrawInAddFurniCanvas.this.label.getLocation();
				
            		int newX=(loc.x+cp.x-lp.x);
            		int newY=(loc.y+cp.y-lp.y);
				
            		//檢查邊界，確保標籤不會超出容器
            		newX = Math.min(Math.max(newX, 91), (int)(p.rectWidth)+90 - label.getWidth());
            		newY = Math.min(Math.max(newY, 101), (int)(p.rectHeight)+100 - label.getHeight());
                
            		DrawInAddFurniCanvas.this.label.setLocation(newX, newY);
            		DrawInAddFurniCanvas.this.parent.repaint();
            		lp=cp;
            	}
            }
        });
		
		//拖曳控制點改變大小
				label.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent e) {
						if((DrawInAddFurniCanvas.this.status==Status.ready2Resize)||(DrawInAddFurniCanvas.this.status==Status.Resizing)) {
							if (imageIcon != null) {
								
								// 取得標籤新大小
								int width = label.getWidth();
								int height = label.getHeight();
								
								if((ang==0)||(ang%180==0)) {
									w=width;		//更新物件長寬
									h=height;
								}
								else {
									w=height;		//更新物件長寬
									h=width;
								}
//								loadImageToLabel(icon);		// 調整圖片大小
//								toolbar.removeattr();
//								toolbar.showattr(name,w,h,ang,'o');
							}
						}
						else if(parent.status==Status.resize) {
							if (imageIcon != null) {
								
								// 取得標籤新大小
								int width = label.getWidth();
								int height = label.getHeight();
								
								if((ang==0)||(ang%180==0)) {
									w=width;		//更新物件長寬
									h=height;
								}
								else {
									w=height;		//更新物件長寬
									h=width;
								}
//								loadImageToLabel(icon);		// 調整圖片大小
//								toolbar.removeattr();
//								toolbar.showattr(name,w,h,ang,'o');
							}
						}
			        }
				});
	}
	
	private static String removeExtension(String fileName) {		//移除副檔名
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        } 
        else {
            return fileName;
        }
    }
	
    private BufferedImage rotateImage(BufferedImage originalImage, double radians) {
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - width) / 2, (newHeight - height) / 2);
        at.rotate(radians, width / 2, height / 2);

        g2d.setTransform(at);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

	void zoomNoOutline(int a,int b,int s) {
//		double A = a;
//	    double B = b;
	    int cx = label.getX();
	    int cy = label.getY();
	    int newX,newY;
		if((ang==0)||(ang%180==0)) {
			label.setSize((int)(w/this.scale), (int)(h/this.scale));
			Image newImg = temp.getScaledInstance((int)(w/this.scale), (int)(h/this.scale), Image.SCALE_SMOOTH);
	    	label.setIcon(new ImageIcon(newImg));
	    	System.out.println("set1");
		}
		else {
			label.setSize((int)(h/this.scale), (int)(w/this.scale));
			Image newImg = temp.getScaledInstance((int)(h/this.scale), (int)(w/this.scale), Image.SCALE_SMOOTH);
	    	label.setIcon(new ImageIcon(newImg));
	    	System.out.println("set2");
		}
//		if( s ==0 && parent.scale >=0.1) {
//		    // 計算新的物件位置
//		    newX = (int)(91 + ((cx - 91.0)/(parent.scale+0.1) * (parent.scale)));
//	    	newY = (int)(101 + ((cy - 101.0)/(parent.scale+0.1) * (parent.scale)));
//		    
//		    // 調整 label 的位置
//		    label.setLocation(newX, newY);
//	    }else if(s == 1 && parent.scale<=2.0){
//	    	 // 計算新的物件位置
//		    newX = (int)(91 + ((cx - 91.0)/(parent.scale-0.1) * (parent.scale)));
//	    	newY = (int)(101 + ((cy - 101.0)/(parent.scale-0.1) * (parent.scale)));
//		    // 調整 label 的位置
//		    label.setLocation(newX, newY);
//	    }

		parent.repaint();
	}
	public void paint(Graphics g)
	{
		g.setColor(parent.selectedColor1);
		
		g.drawRect(1, 1, this.getWidth()-2, this.getHeight()-2);
		
	}
}
