//中間畫布
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;
import java.awt.event.*;

public class Canvas extends JPanel {
	int rectWidth = 0;
    int rectHeight = 0;
    Status status;
    Vector<FurnitureInCanvas> furnitures = null;
    Vector<FurnitureInCanvas> temstore = null;
    Vector<Previous> prevSteps = null;
    Vector<Next> nextSteps = null;
    int setX=0,setY=0;
	String icon;
	int newX,newY;
	FurnitureInCanvas actFurniture=null;
	int index; 
	Toolbar toolbar;
	
	
	//儲存檔案-抓畫布位置
	int scaledX =0;
	int scaledY =0;
	int scaledWidth =0;
	int scaledHeight =0;
	
	int initw, inith;
	//背景顏色設定
	private Color backgroundColor = new Color(227,216,167);// R,G,B(227,216,167)
    public Canvas(int a,int b) {
    	rectWidth = a;
        rectHeight = b;
        setBackground(backgroundColor);
        
        this.status=Status.active;
        //解決滾動圖片不見問題
        this.setLayout(null);
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(Canvas.this.status==Status.creatingFurniture) {
                	setX = e.getX();
                	setY = e.getY();

                	FurnitureInCanvas temp=new FurnitureInCanvas(icon,Canvas.this,setX,setY,toolbar,0,scale);
                	
                	if(temstore==null)
                		temstore=new Vector<FurnitureInCanvas>();
                	
             		if(furnitures==null)
            			furnitures=new Vector<FurnitureInCanvas>();
             		else {
             			if(nextSteps!=null) {
             				nextSteps.clear();
             				System.out.println(nextSteps.size());
             			}
             		}
             		
            		furnitures.add(temp);
            		temp.label.setSize((int)(temp.label.getWidth()*scale), (int)(temp.label.getHeight()*scale));
            		newX=temp.label.getX();
            		newY=temp.label.getY();
            		newX = Math.min(Math.max(newX, 91), (int)(rectWidth*scale)+90 - temp.label.getWidth());
            		newY = Math.min(Math.max(newY, 101), (int)(rectHeight*scale)+100 - temp.label.getHeight());
            		temp.label.setLocation(newX, newY);
            		temp.label.setVisible(true);
            		if (temp.imageIcon != null) {
						
						// 取得標籤新大小
						int width = temp.label.getWidth();
						int height = temp.label.getHeight();
						
						if((temp.ang==0)||(temp.ang%180==0)) {
							temp.w=width;		//更新物件長寬
							temp.h=height;
						}
						else {
							temp.w=height;		//更新物件長寬
							temp.h=width;
						}
						temp.loadImageToLabel(icon);		// 調整圖片大小
						toolbar.removeattr();
						toolbar.showattr(temp.name,temp.w,temp.h,temp.ang,'o');
					}
            		temp.doSelected();
            		
            		
            		if(actFurniture!=null)
            			actFurniture.doInSelected();
            		actFurniture=temp;   //將目前家具設為活動家具
            		
            		if(prevSteps==null)
            			prevSteps=new Vector<Previous>();
            		
            		
            		Previous prevtemp=new Previous("create",actFurniture);
            		
            		prevSteps.add(prevtemp);
            		
            		icon=null;
                    
                }
                else {
                	if(actFurniture!=null) {
                		actFurniture.doInSelected();
                		actFurniture=null;
                	}
            		toolbar.removeattr();
            		toolbar.showattr(null,a,b,0,'c');	//顯示畫布屬性表
                }
                Canvas.this.status=Status.active;
            }
        });

    }
    
    public void setToolbar(Toolbar tool) {
        this.toolbar = tool;
    }
    
    //處理圖片匯入
    private Image image;
    public void setImage(Image image) {
        this.image = image;
    }
    
    //捲動軸範圍
  	public Dimension getPreferredSize() {
  		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
  		int t = 0;
  		if(scale==1)
  			t = (int)(scale*100);
  		else if(scale>1)
  			t = (int)(scale*100)*5;
  		if((Canvas.this.rectHeight>2000)&&(Canvas.this.rectWidth>2000))									//畫布太大	
  			return new Dimension(Canvas.this.rectWidth+t*20,Canvas.this.rectHeight+t*20);
  		else if((Canvas.this.rectHeight>screen.height-220)&&(Canvas.this.rectWidth>screen.width-260)) 	//畫布大於螢幕長寬
  			return new Dimension(Canvas.this.rectWidth+t*6,Canvas.this.rectHeight+t*6);
  		else if(Canvas.this.rectHeight>screen.height-220)												//畫布大於螢幕寬
  			return new Dimension(screen.width-260+t*4,Canvas.this.rectHeight+t*10);
  		else if(Canvas.this.rectWidth>screen.width-260)													//畫布大於螢幕長
  			return new Dimension(Canvas.this.rectWidth+t*10,screen.height-220+t*4);
        return new Dimension(screen.width-260+t*5,screen.height-220+t*3);   							//畫布不大於螢幕長寬 
        
    }
  	
  	double scale=1.0;	//畫布的縮放比例
    public void zoomscale(int s) {
    	repaint();
    	updateUI();			//更新調整捲動軸範圍
    	int si=0;
    	if(s==0) { 			//s=0，縮小10%
    		scale-=0.1;
    		si=0;
    	}
    	else{ 				//s=1，放大10%
    		scale+=0.1;
    		si=0;
    	}
    	if(scale>=2) {		//若超過200%則為200%
    		scale=2;
    		si=1;
    	}
    	else if(scale<=0.1){	//若低於10%則為10%
    		scale=0.1;
    		si=1;
    	}
    	if(si==0 && this.furnitures!=null) {
			for (FurnitureInCanvas furniture : furnitures) {
			
				if(furniture.label != null) {
	        		if(actFurniture == furniture) {
	        			furniture.zoom(rectWidth,rectHeight,s);
	        		}
	        		else {
	        			furniture.zoomNoOutline(rectWidth,rectHeight,s);
	        		}
	        	}
	        	else {
	        		System.out.println("label == null");
	        	}
			}
        }
	   
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	super.paintComponent(g2);
    	
//        int rectWidth = 1100;
//        int rectHeight = 500;
        
        // 繪製白色的長方形
        g2.setColor(Color.WHITE);
        g2.fillRect(90, 100, (int)(rectWidth*scale), (int)(rectHeight*scale)); //(寬、高)
        
        // 繪製黑色的框線
        g2.setColor(Color.BLACK);
        g2.drawRect(90, 100, (int)(rectWidth*scale), (int)(rectHeight*scale));
        
        if (image != null) {
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
	void createAFurniture(String s) {
		if(this.status==Status.ready2CreateAFurniture) {
			icon=s;
			this.status=Status.creatingFurniture;
		}
	}
	
	void changebyattr(String n,int w,int h) {
		if(actFurniture!=null) {
			Previous prevtemp=new Previous("resize",actFurniture,actFurniture.label.getWidth(),actFurniture.label.getHeight(),actFurniture.label.getLocation());
			prevSteps.add(prevtemp);
			actFurniture.changeImagebyattr(n,w,h);
			
		}
	}
	
	void actionTurnLeft() {
		if(actFurniture!=null) {
			actFurniture.turnLeft();
			Previous prevtemp=new Previous("turnLeft",actFurniture);
			prevSteps.add(prevtemp);
		}
	}
	
	void actionTurnRight() {
		if(actFurniture!=null) {
			actFurniture.turnRight();
			Previous prevtemp=new Previous("turnRight",actFurniture);
			prevSteps.add(prevtemp);
		}
	}
	
	//刪除家具
		void actionDeleteAFurniture() {
			if(actFurniture!=null) {
				index=furnitures.indexOf(actFurniture);
				this.remove(actFurniture.label);
				this.remove(actFurniture.cps.N);
				this.remove(actFurniture.cps.S);
				this.remove(actFurniture.cps.E);
				this.remove(actFurniture.cps.W);
				this.remove(actFurniture.cps.NE);
				this.remove(actFurniture.cps.NW);
				this.remove(actFurniture.cps.SE);
				this.remove(actFurniture.cps.SW);
				
				Previous prevtemp=new Previous("delete",actFurniture);
	
        		prevSteps.add(prevtemp);
				actFurniture.doInSelected();
				furnitures.remove(actFurniture);
				furnitures.trimToSize();
				temstore.add(actFurniture);
				actFurniture=null;

				this.repaint();
				toolbar.removeattr();
        		toolbar.showattr(null,rectWidth,rectHeight,0,'c');	//顯示畫布屬性表
				System.out.println(furnitures.size());
			}
		}
		
		void actionPrev() {
			if(nextSteps==null)
    			nextSteps=new Vector<Next>();
			
			if((prevSteps!=null)&&(prevSteps.size()!=0)) {
				index=prevSteps.size()-1;
				
				//創造->刪除
				if(prevSteps.get(index).state=="create") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=prevSteps.get(index).fparent;
					this.remove(actFurniture.label);
					this.remove(actFurniture.cps.N);
					this.remove(actFurniture.cps.S);
					this.remove(actFurniture.cps.E);
					this.remove(actFurniture.cps.W);
					this.remove(actFurniture.cps.NE);
					this.remove(actFurniture.cps.NW);
					this.remove(actFurniture.cps.SE);
					this.remove(actFurniture.cps.SW);
					
					Next nexttemp=new Next("delete",actFurniture);
					nextSteps.add(nexttemp);
					actFurniture.doInSelected();
					furnitures.remove(actFurniture);
					furnitures.trimToSize();
					temstore.add(actFurniture);
					actFurniture=null;

				}
				
				//刪除->創造
				if(prevSteps.get(index).state=="delete") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=prevSteps.get(index).fparent;
					this.add(actFurniture.label);
					this.add(actFurniture.cps.N);
					this.add(actFurniture.cps.S);
					this.add(actFurniture.cps.E);
					this.add(actFurniture.cps.W);
					this.add(actFurniture.cps.NE);
					this.add(actFurniture.cps.NW);
					this.add(actFurniture.cps.SE);
					this.add(actFurniture.cps.SW);
            		actFurniture.doSelected();
            		
            		furnitures.add(actFurniture);
            		Next nexttemp=new Next("create",actFurniture);
					nextSteps.add(nexttemp);

				}

				
				//右轉->左轉
				if(prevSteps.get(index).state=="turnRight") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=prevSteps.get(index).fparent;
					actFurniture.doSelected();
					actFurniture.turnLeft();
					
					Next nexttemp=new Next("turnLeft",actFurniture);
					nextSteps.add(nexttemp);
				}
				
				//左轉->右轉
				if(prevSteps.get(index).state=="turnLeft") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=prevSteps.get(index).fparent;
					actFurniture.doSelected();
					actFurniture.turnRight();
					
					Next nexttemp=new Next("turnRight",actFurniture);
					nextSteps.add(nexttemp);
				}
				
				//移動
				if(prevSteps.get(index).state=="move") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=prevSteps.get(index).fparent;
					
					Next nexttemp=new Next("move",actFurniture,actFurniture.label.getLocation());
					nextSteps.add(nexttemp);
					
					prevSteps.get(index).fparent.label.setLocation(prevSteps.get(index).op);
					prevSteps.get(index).fparent.doSelected();
				}
				
				//改變大小
				if(prevSteps.get(index).state=="resize") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=prevSteps.get(index).fparent;
					
					Next nexttemp=new Next("resize",actFurniture,actFurniture.label.getLocation(),actFurniture.label.getWidth(),actFurniture.label.getHeight());
					nextSteps.add(nexttemp);
					
					prevSteps.get(index).fparent.label.setLocation(prevSteps.get(index).op);
					prevSteps.get(index).fparent.label.setSize(prevSteps.get(index).width,prevSteps.get(index).height);
					
					if (prevSteps.get(index).fparent.imageIcon != null) {			
						// 取得標籤新大小
						int width = prevSteps.get(index).fparent.label.getWidth();
						int height = prevSteps.get(index).fparent.label.getHeight();
						
						if((prevSteps.get(index).fparent.ang==0)||(prevSteps.get(index).fparent.ang%180==0)) {
							prevSteps.get(index).fparent.w=width;		//更新物件長寬
							prevSteps.get(index).fparent.h=height;
						}
						else {
							prevSteps.get(index).fparent.w=height;		//更新物件長寬
							prevSteps.get(index).fparent.h=width;
						}
						prevSteps.get(index).fparent.loadImageToLabel(prevSteps.get(index).fparent.icon);		// 調整圖片大小
						toolbar.removeattr();
						toolbar.showattr(prevSteps.get(index).fparent.name,prevSteps.get(index).fparent.w,prevSteps.get(index).fparent.h,prevSteps.get(index).fparent.ang,'o');
					}
					
					prevSteps.get(index).fparent.doSelected();
				}
				
				prevSteps.remove(index);
				prevSteps.trimToSize();
				this.repaint();
				System.out.print("prevSteps: ");
				System.out.println(prevSteps.size());
	
			}
		}
		
		void actionNext() {
			if((nextSteps!=null)&&(nextSteps.size()!=0)) {
				index=nextSteps.size()-1;
				
				//創造->刪除
				if(nextSteps.get(index).state=="create") {
					actFurniture=nextSteps.get(index).fparent;
					this.remove(actFurniture.label);
					this.remove(actFurniture.cps.N);
					this.remove(actFurniture.cps.S);
					this.remove(actFurniture.cps.E);
					this.remove(actFurniture.cps.W);
					this.remove(actFurniture.cps.NE);
					this.remove(actFurniture.cps.NW);
					this.remove(actFurniture.cps.SE);
					this.remove(actFurniture.cps.SW);
					
					Previous prevtemp=new Previous("delete",actFurniture);
					prevSteps.add(prevtemp);
					furnitures.remove(actFurniture);
					furnitures.trimToSize();
					temstore.add(actFurniture);
					actFurniture=null;
				}
				
				//刪除->創造
				if(nextSteps.get(index).state=="delete") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=nextSteps.get(index).fparent;
					actFurniture.doSelected();
					this.add(actFurniture.label);
					this.add(actFurniture.cps.N);
					this.add(actFurniture.cps.S);
					this.add(actFurniture.cps.E);
					this.add(actFurniture.cps.W);
					this.add(actFurniture.cps.NE);
					this.add(actFurniture.cps.NW);
					this.add(actFurniture.cps.SE);
					this.add(actFurniture.cps.SW);
					
					furnitures.add(actFurniture);
					Previous prevtemp=new Previous("create",actFurniture);
					prevSteps.add(prevtemp);
					
				}
				
				//右轉->左轉
				if(nextSteps.get(index).state=="turnRight") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=nextSteps.get(index).fparent;
					actFurniture.doSelected();
					actFurniture.turnLeft();
					
					Previous prevtemp=new Previous("turnLeft",actFurniture);
					prevSteps.add(prevtemp);
				}
			
				//左轉->右轉
				if(nextSteps.get(index).state=="turnLeft") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=nextSteps.get(index).fparent;
					actFurniture.doSelected();
					actFurniture.turnRight();
					
					Previous prevtemp=new Previous("turnRight",actFurniture);
					prevSteps.add(prevtemp);
				}
				
				//移動
				if(nextSteps.get(index).state=="move") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=nextSteps.get(index).fparent;
					
					Previous prevtemp=new Previous("move",actFurniture,actFurniture.label.getLocation());
					prevSteps.add(prevtemp);
					
					nextSteps.get(index).fparent.label.setLocation(nextSteps.get(index).op);
					nextSteps.get(index).fparent.doSelected();
					
					
				}
				
				//改變大小
				if(nextSteps.get(index).state=="resize") {
					if(actFurniture!=null)
						actFurniture.doInSelected();
					actFurniture=nextSteps.get(index).fparent;
					
					Previous prevtemp=new Previous("resize",actFurniture,actFurniture.label.getWidth(),actFurniture.label.getHeight(),actFurniture.label.getLocation());
					prevSteps.add(prevtemp);
					
					nextSteps.get(index).fparent.label.setLocation(nextSteps.get(index).op);
					nextSteps.get(index).fparent.label.setSize(nextSteps.get(index).width,nextSteps.get(index).height);
					
					if (nextSteps.get(index).fparent.imageIcon != null) {			
						// 取得標籤新大小
						int width = nextSteps.get(index).fparent.label.getWidth();
						int height = nextSteps.get(index).fparent.label.getHeight();
						
						if((nextSteps.get(index).fparent.ang==0)||(nextSteps.get(index).fparent.ang%180==0)) {
							nextSteps.get(index).fparent.w=width;		//更新物件長寬
							nextSteps.get(index).fparent.h=height;
						}
						else {
							nextSteps.get(index).fparent.w=height;		//更新物件長寬
							nextSteps.get(index).fparent.h=width;
						}
						nextSteps.get(index).fparent.loadImageToLabel(nextSteps.get(index).fparent.icon);		// 調整圖片大小
						toolbar.removeattr();
						toolbar.showattr(nextSteps.get(index).fparent.name,nextSteps.get(index).fparent.w,nextSteps.get(index).fparent.h,nextSteps.get(index).fparent.ang,'o');
					}
					
					actFurniture.doSelected();
				}
				
				
				nextSteps.remove(index);
				nextSteps.trimToSize();
				this.repaint();
				System.out.print("nextSteps: ");
				System.out.println(nextSteps.size());
		
			}
		}
		
		//畫外框
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.gray);
			if(actFurniture!=null) {
				if(actFurniture.visible)
				{
					g.setColor(Color.gray);
					Point l=actFurniture.label.getLocation();
					Dimension s=actFurniture.label.getSize();
					g.drawRect(l.x-6, l.y-6, s.width+12, s.height+12);
				}
			}
		}
	
	//給toolbar使用-給畫布相對於canvas的位置+大小
	public void setScaledPosition(int scaledX, int scaledY, int scaledWidth, int scaledHeight) {
        this.scaledX = scaledX;
        this.scaledY = scaledY;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
    }
	
	
}
