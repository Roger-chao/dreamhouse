import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FurnitureInCanvas{
	ControlPoints cps=null;
	String name; 
	int ang;
	int w,h;
	Point op,lp,cp;//移動的起點紀錄
	double scale;
	Status status;
	boolean visible;
	Cursor currentCursor;
	
	Canvas parent=null;
	Toolbar toolbar;
	JLabel label=new JLabel();
	String icon;
	BufferedImage temp;
	ImageIcon imageIcon;
	
	FurnitureInCanvas(String s,Canvas p,int setX,int setY,Toolbar tool,int angel,double sc){
		super();
		parent=p;
		status=Status.selected;
		ang=angel;
		scale=sc;
		icon=s;
		imageIcon=new ImageIcon(s);
		sizecheck(s);
		loadImageToLabel(s);
		
		
		parent.add(label);
		label.setSize(w, h);
		label.setVisible(false);
		parent.initw=w;
		parent.inith=h;
		label.setLocation(setX-w/2, setY-h/2);
		
		toolbar = tool;		
		toolbar.removeattr();
		toolbar.showattr(name,w,h,ang,'o');
		
		//移動
		label.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				FurnitureInCanvas.this.currentCursor = FurnitureInCanvas.this.label.getCursor();
				FurnitureInCanvas.this.label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				FurnitureInCanvas.this.label.setCursor(FurnitureInCanvas.this.currentCursor);
			}
			
            public void mousePressed(MouseEvent e) {
            	if(FurnitureInCanvas.this.status==Status.inselected) {
            		if((parent.actFurniture!=null))
            			parent.actFurniture.doInSelected();
            	
            		parent.actFurniture=FurnitureInCanvas.this;	//將點選的家具設為active
            		FurnitureInCanvas.this.doSelected();
            	
            		toolbar = tool;
            		if (toolbar != null) {					//顯示物件屬性表
            			toolbar.removeattr();
            			toolbar.showattr(name,w,h,ang,'o');
            		}
            	}
            	else if(FurnitureInCanvas.this.status==Status.selected) {
            		lp=e.getLocationOnScreen();
            		op=label.getLocation();
            		FurnitureInCanvas.this.label.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            		
            		FurnitureInCanvas.this.parent.setComponentZOrder(FurnitureInCanvas.this.label, 0);//選取的家具圖層移至最上面
            		FurnitureInCanvas.this.status=Status.ready2Move;
            	}
            	
            }
            
            public void mouseReleased(MouseEvent e) {
            	
            	//檢查家具是否重疊
				if(FurnitureInCanvas.this.status==Status.Moving) {
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
				
					Previous prevtemp=new Previous("move",FurnitureInCanvas.this,op);
					parent.prevSteps.add(prevtemp);
				}
				FurnitureInCanvas.this.label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				FurnitureInCanvas.this.showOutline();
				FurnitureInCanvas.this.status=Status.selected;
				
			}
        });
		label.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
            	if(FurnitureInCanvas.this.status==Status.ready2Move) {
            		FurnitureInCanvas.this.hideOutline();
            		FurnitureInCanvas.this.status=Status.Moving;
            		cp=e.getLocationOnScreen();
    				
            		Point loc=FurnitureInCanvas.this.label.getLocation();
            		
            		int newX=(loc.x+cp.x-lp.x);
            		int newY=(loc.y+cp.y-lp.y);
				
            		//檢查邊界，確保標籤不會超出容器
            		newX = Math.min(Math.max(newX, 91), (int)(p.rectWidth*p.scale)+90 - label.getWidth());
            		newY = Math.min(Math.max(newY, 101), (int)(p.rectHeight*p.scale)+100 - label.getHeight());
                
            		FurnitureInCanvas.this.label.setLocation(newX, newY);
            		FurnitureInCanvas.this.parent.repaint();
            		lp=cp;

            	}
            	else if(FurnitureInCanvas.this.status==Status.Moving) {
            		cp=e.getLocationOnScreen();
    				
            		Point loc=FurnitureInCanvas.this.label.getLocation();
				
            		int newX=(loc.x+cp.x-lp.x);
            		int newY=(loc.y+cp.y-lp.y);
				
            		//檢查邊界，確保標籤不會超出容器
            		newX = Math.min(Math.max(newX, 91), (int)(p.rectWidth*p.scale)+90 - label.getWidth());
            		newY = Math.min(Math.max(newY, 101), (int)(p.rectHeight*p.scale)+100 - label.getHeight());
                
            		FurnitureInCanvas.this.label.setLocation(newX, newY);
            		FurnitureInCanvas.this.parent.repaint();
            		lp=cp;
            	}
            }
        });
		
		//拖曳控制點改變大小
				label.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent e) {
						if((FurnitureInCanvas.this.status==Status.ready2Resize)||(FurnitureInCanvas.this.status==Status.Resizing)) {
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
								loadImageToLabel(icon);		// 調整圖片大小
								toolbar.removeattr();
								toolbar.showattr(name,w,h,ang,'o');
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
								loadImageToLabel(icon);		// 調整圖片大小
								toolbar.removeattr();
								toolbar.showattr(name,w,h,ang,'o');
							}
						}
			        }
				});
	}
	//輸入屬性表數值改變圖片大小
	void changeImagebyattr(String n,int width,int height){
		name=n;			//更新物件名稱、長寬
		w=width;		
		h=height;
		loadImageToLabel(icon);
		cps.pointrepaint();		//控制點重繪
	}
	
	//讀取圖片長寬
	void sizecheck(String s) {
		try {
            File file = new File(s);
            BufferedImage image = ImageIO.read(file);
            String temp_name = file.getName();    		//抓取圖片名稱
            name = removeExtension(temp_name);
            
            w = image.getWidth();
            h = image.getHeight();
		} 
		catch (IOException e) {
            e.printStackTrace();
        }
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
	
	//旋轉以及載入圖片
	void loadImageToLabel(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            BufferedImage rotatedImage = rotateImage(img, Math.toRadians(ang)); //旋轉ang度
            temp = rotateImage(img, Math.toRadians(ang));
            
            if((ang==0)||(ang%180==0)) {
            	label.setSize(w,h);
            	Image newImg = rotatedImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            	label.setIcon(new ImageIcon(newImg));
            }
            else {            	
            	label.setSize(h,w);
            	Image newImg = rotatedImage.getScaledInstance(h, w, Image.SCALE_SMOOTH);
            	label.setIcon(new ImageIcon(newImg));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    
    //左轉90度
	void turnLeft() {
		if(this.status==Status.selected) {
			ang-=90;
			if(ang<=-360) {
				ang=0;
			}
			loadImageToLabel(icon);
			this.doSelected();
			toolbar.removeattr();
			toolbar.showattr(name,w,h,ang,'o');
		}
	}
	
	//右轉90度
	void turnRight() {
		if(this.status==Status.selected) {
			ang+=90;
			if(ang>=360) {
				ang=0;
			}
			loadImageToLabel(icon);
			this.doSelected();
			toolbar.removeattr();
			toolbar.showattr(name,w,h,ang,'o');
		}
	}
	
	void turn(int angel) {
			ang=angel%360;
			loadImageToLabel(icon);
			this.doSelected();
			toolbar.removeattr();
			toolbar.showattr(name,w,h,ang,'o');
	}
	
	void doSelected() {
		this.status=Status.selected;
		this.showOutline();
	}
	
	void doInSelected() {
		this.status=Status.inselected;
		this.hideOutline();
	}
	
	//顯示外框
	void showOutline() {
		visible=true;
		if(cps==null)
		{
			cps=new ControlPoints(this);
		}
		
		cps.setVisible(true);
		parent.repaint();
	}
	
	//隱藏外框
	void hideOutline() {
		visible=false;
		if(cps!=null)
		{
			cps.setVisible(false);
		}
		parent.repaint();
	}
	//放大縮小調整
	void zoom(int a,int b,int s) {
	    int cx = label.getX();
	    int cy = label.getY();
	    int newX,newY;
		if((ang==0)||(ang%180==0)) {
			label.setSize((int)(w/this.scale*parent.scale), (int)(h/this.scale*parent.scale));
			Image newImg = temp.getScaledInstance((int)(w/this.scale*parent.scale), (int)(h/this.scale*parent.scale), Image.SCALE_SMOOTH);
	    	label.setIcon(new ImageIcon(newImg));
		}
		else {
			label.setSize((int)(h/this.scale*parent.scale), (int)(w/this.scale*parent.scale));
			Image newImg = temp.getScaledInstance((int)(h/this.scale*parent.scale), (int)(w/this.scale*parent.scale), Image.SCALE_SMOOTH);
	    	label.setIcon(new ImageIcon(newImg));
		}
		if( s ==0 && parent.scale >=0.1) {
		    // 計算新的物件位置
		    newX = (int)(91 + ((cx - 91.0)/(parent.scale+0.1) * (parent.scale)));
	    	newY = (int)(101 + ((cy - 101.0)/(parent.scale+0.1) * (parent.scale)));
		    
		    // 調整 label 的位置
		    label.setLocation(newX, newY);
	    }else if(s == 1 && parent.scale<=2.0){
	    	 // 計算新的物件位置
		    newX = (int)(91 + ((cx - 91.0)/(parent.scale-0.1) * (parent.scale)));
	    	newY = (int)(101 + ((cy - 101.0)/(parent.scale-0.1) * (parent.scale)));
		    // 調整 label 的位置
		    label.setLocation(newX, newY);
	    }

		cps.setVisible(true);
		parent.repaint();
	}
	void zoomNoOutline(int a,int b,int s) {
//		double A = a;
//	    double B = b;
	    int cx = label.getX();
	    int cy = label.getY();
	    int newX,newY;
		if((ang==0)||(ang%180==0)) {
			label.setSize((int)(w/this.scale*parent.scale), (int)(h/this.scale*parent.scale));
			Image newImg = temp.getScaledInstance((int)(w/this.scale*parent.scale), (int)(h/this.scale*parent.scale), Image.SCALE_SMOOTH);
	    	label.setIcon(new ImageIcon(newImg));
	    	System.out.println("set1");
		}
		else {
			label.setSize((int)(h/this.scale*parent.scale), (int)(w/this.scale*parent.scale));
			Image newImg = temp.getScaledInstance((int)(h/this.scale*parent.scale), (int)(w/this.scale*parent.scale), Image.SCALE_SMOOTH);
	    	label.setIcon(new ImageIcon(newImg));
	    	System.out.println("set2");
		}
		if( s ==0 && parent.scale >=0.1) {
		    // 計算新的物件位置
		    newX = (int)(91 + ((cx - 91.0)/(parent.scale+0.1) * (parent.scale)));
	    	newY = (int)(101 + ((cy - 101.0)/(parent.scale+0.1) * (parent.scale)));
		    
		    // 調整 label 的位置
		    label.setLocation(newX, newY);
	    }else if(s == 1 && parent.scale<=2.0){
	    	 // 計算新的物件位置
		    newX = (int)(91 + ((cx - 91.0)/(parent.scale-0.1) * (parent.scale)));
	    	newY = (int)(101 + ((cy - 101.0)/(parent.scale-0.1) * (parent.scale)));
		    // 調整 label 的位置
		    label.setLocation(newX, newY);
	    }

		parent.repaint();
	}
}
