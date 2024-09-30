import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AddFurniCanvas extends JPanel{
	
	Point lp,cp,op;
	int rectWidth = 0;
    int rectHeight = 0;
    Status status;
    boolean visible;
    Vector<DrawInAddFurniCanvas> furnitures = null;
    Vector<DrawInAddFurniCanvas> temstore = null;
    Vector<Line> lines=null;
    Vector<Eraser> eraser=null;
    Vector<Rect> rect=null;
    Vector<Oval> oval=null;
    Vector<Draw> draw=null;
    Vector<Triangle> triangle=null;
    int setX=0,setY=0,newX,newY,ang;
	double scale;
	DrawInAddFurniCanvas actFurniture=null;
	int index; 
    AddFurniToolbar addtoorbar;
    int initw, inith;
    private int canvasWidth;
    private int canvasHeight;
    public Color selectedColor1 = Color.BLACK;
	public Color selectedColor2 = Color.WHITE;
    
	
	//背景顏色
	private Color backgroundColor = new Color(235,235,235);// R,G,B
	
    public AddFurniCanvas(DrawInAddFurniCanvas actFurniture,DreamHouse parent,AddFurniToolbar AddFurniToolbar , int canvasWidth , int canvasHeight,int setx,int sety,int angel,double sc) {
    	this.canvasWidth = canvasWidth;
    	this.canvasHeight = canvasHeight;
    	this.actFurniture= actFurniture;
    	//this.furniture = furniture;
    	this.addtoorbar=AddFurniToolbar;
        setBackground(backgroundColor);
        ang=angel;
        scale=sc;
        this.setLayout(null);
        /*this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint(); // 視窗大小變化時重新繪製
            }
        });*/
        
        this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				
				
				if(AddFurniCanvas.this.status==Status.ready4Drawing)
				{
				
					lp=e.getPoint();
					AddFurniCanvas.this.status=Status.freeDrawing;
				} 
				else if(AddFurniCanvas.this.status==Status.active)
				{
					if(AddFurniCanvas.this.actFurniture!=null)
					{
						AddFurniCanvas.this.actFurniture.status=Status.inselected;
						AddFurniCanvas.this.actFurniture=null;
					}
				}
				else if(AddFurniCanvas.this.status==Status.ready2eraser)
				{
					lp=e.getPoint();
					AddFurniCanvas.this.status=Status.caneraser;
				}
				else if(AddFurniCanvas.this.status==Status.ready2Createline)
				{
					lp=e.getPoint();
					AddFurniCanvas.this.status=Status.Createline;
				}
				else if(AddFurniCanvas.this.status==Status.ready2Createtriangle)
				{
					lp=e.getPoint();
					AddFurniCanvas.this.status=Status.Createtriangle;
				}
				else if(AddFurniCanvas.this.status==Status.ready2Createcircle)
				{
					lp=e.getPoint();
					AddFurniCanvas.this.status=Status.Createcircle;
				}
				else if(AddFurniCanvas.this.status==Status.ready2Createrectangle)
				{
					lp=e.getPoint();
					AddFurniCanvas.this.status=Status.Createrectangle;
				}
				
				
				
			}
			public void mouseReleased(MouseEvent e)
			{
				if(AddFurniCanvas.this.status==Status.freeDrawing)
				{
					lp=null;
					cp=null;
					AddFurniCanvas.this.status=Status.active;
				}
				else if(AddFurniCanvas.this.status==Status.caneraser)
				{
					lp=null;
					cp=null;
					AddFurniCanvas.this.status=Status.active;
				}
				else if(AddFurniCanvas.this.status==Status.Createline) 
				{
					cp=e.getPoint();
					repaint();
					if(lines==null)
						lines=new Vector<Line>();
					lines.add(new Line(lp, cp));
					AddFurniCanvas.this.status=Status.active;
				}
				else if(AddFurniCanvas.this.status==Status.Createtriangle) 
				{
					/*cp=e.getPoint();
					repaint();
					if(triangle==null)
						triangle=new Vector<Triangle>();
					triangle.add(new Triangle(ap, bp));
					triangle.add(new Triangle(ap, cp));
					triangle.add(new Triangle(bp, cp));
					AddFurniCanvas.this.status=Status.active;*/
					Graphics g=AddFurniCanvas.this.getGraphics();
					g.setXORMode(selectedColor1);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					smartDrawTriangle(g, lp, cp);
					repaint();
					if(triangle==null)
						triangle=new Vector<Triangle>();
					triangle.add(new Triangle(lp, cp));
				}
				else if(AddFurniCanvas.this.status==Status.Createcircle)
				{
					/*Graphics g=AddFurniCanvas.this.getGraphics();
					smartDrawOval(g, lp, cp);
					repaint();
					if(oval==null)
						oval=new Vector<Oval>();
					oval.add(new Oval(lp, cp));
					AddFurniCanvas.this.status=Status.active;
					lp=null;
					cp=null;*/
					Graphics g=AddFurniCanvas.this.getGraphics();
					g.setXORMode(selectedColor1);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					smartDrawOval(g, lp, cp);
					repaint();
					if(oval==null)
						oval=new Vector<Oval>();
					oval.add(new Oval(lp, cp));
				}
				else if(AddFurniCanvas.this.status==Status.Createrectangle) 
				{
					/*Graphics g=AddFurniCanvas.this.getGraphics();
					cp=e.getPoint();
					repaint();
					if(rect==null)
						rect=new Vector<Rect>();
					rect.add(new Rect(g,lp, cp));
					AddFurniCanvas.this.status=Status.active;*/
					Graphics g=AddFurniCanvas.this.getGraphics();
					g.setXORMode(selectedColor1);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					smartDrawRect(g, lp, cp);
					repaint();
					if(rect==null)
						rect=new Vector<Rect>();
					rect.add(new Rect(lp, cp));
					//System.out.println(rect);
				}
				
			}

		});
	
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e)
			{
				if(AddFurniCanvas.this.status==Status.freeDrawing)
				{
					cp=e.getPoint();
            		Graphics g=AddFurniCanvas.this.getGraphics();
					Graphics2D g2=(Graphics2D)g;
					g2.setColor(selectedColor1);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(lp.x, lp.y, cp.x, cp.y);
					if(draw==null)
						draw=new Vector<Draw>();
					draw.add(new Draw(lp, cp));
					lp=cp;
				}
				else if(AddFurniCanvas.this.status==Status.caneraser)
				{
					cp=e.getPoint();
					
            		Graphics g=AddFurniCanvas.this.getGraphics();
					Graphics2D g2=(Graphics2D)g;
					g2.setColor(Color.WHITE);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(lp.x, lp.y, cp.x, cp.y);
					if(eraser==null)
						eraser=new Vector<Eraser>();
					eraser.add(new Eraser(lp,cp));
					lp=cp;
				}
				else if(AddFurniCanvas.this.status==Status.Createline) {
					cp=e.getPoint();
					repaint();
            		Graphics g=AddFurniCanvas.this.getGraphics();
					Graphics2D g2=(Graphics2D)g;
					g2.setColor(selectedColor1);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(lp.x, lp.y, cp.x, cp.y);
				}
				else if(AddFurniCanvas.this.status==Status.Createtriangle) {
					/*cp=e.getPoint();
					repaint();
            		Graphics g=AddFurniCanvas.this.getGraphics();
					Graphics2D g2=(Graphics2D)g;
					g2.setColor(selectedColor1);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine((lp.x+cp.x)/2, lp.y, lp.x, cp.y);
					g2.drawLine((lp.x+cp.x)/2, lp.y, cp.x, cp.y);
					g2.drawLine(lp.x, cp.y, cp.x, cp.y);*/
					cp=e.getPoint();
					Graphics g = AddFurniCanvas.this.getGraphics();
					g.setColor(selectedColor1);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					if(cp!=null)
					{
						repaint();
						smartDrawTriangle(g, lp, cp);
					}
					cp=e.getPoint();
					AddFurniCanvas.this.validate();
					smartDrawTriangle(g, lp, cp);
				}
				else if(AddFurniCanvas.this.status==Status.Createcircle) {
					
					cp=e.getPoint();
					Graphics g = AddFurniCanvas.this.getGraphics();
					g.setColor(selectedColor1);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					if(cp!=null)
					{
						repaint();
						smartDrawOval(g, lp, cp);
					}
					cp=e.getPoint();
					AddFurniCanvas.this.validate();
					smartDrawOval(g, lp, cp);
				}
				else if(AddFurniCanvas.this.status==Status.Createrectangle) {
					/*Graphics g=AddFurniCanvas.this.getGraphics();
					if(cp!=null)
					{
						smartDrawRect(g, lp, cp);
					}
					cp=e.getPoint();
					repaint();*/
					cp=e.getPoint();
					Graphics g = AddFurniCanvas.this.getGraphics();
					g.setColor(selectedColor1);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					if(cp!=null)
					{
						repaint();
						smartDrawRect(g, lp, cp);
					}
					cp=e.getPoint();
					AddFurniCanvas.this.validate();
					smartDrawRect(g, lp, cp);
				}
			}
		});
    }
    
    void smartDrawTriangle(Graphics g,Point fp,Point cp){
		if((fp.x <= cp.x)&&(fp.y <= cp.y) )
		{
			g.drawLine(fp.x, cp.y, cp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, fp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, cp.x, cp.y);
		}
		else if((fp.x > cp.x)&&(fp.y > cp.y))
		{
			g.drawLine(fp.x, cp.y, cp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, fp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, cp.x, cp.y);
		}
		else if((fp.x <= cp.x)&&(fp.y > cp.y))
		{	
			g.drawLine(fp.x, cp.y, cp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, fp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, cp.x, cp.y);
		}
		else 
		{
			g.drawLine(fp.x, cp.y, cp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, fp.x, cp.y);
			g.drawLine((cp.x+fp.x)/2,fp.y, cp.x, cp.y);
		}
	}
    
    void smartDrawRect(Graphics g, Point fp, Point cp )
	{
    	Graphics2D g2=(Graphics2D)g;
    	g2.setColor(selectedColor1);
    	g2.setStroke(new BasicStroke(5));
    	if((fp.x <= cp.x)&&(fp.y <= cp.y))
			g2.drawRect(fp.x, fp.y, cp.x-fp.x, cp.y-fp.y);
		else if((fp.x > cp.x)&&(fp.y > cp.y))
			g2.drawRect(cp.x, cp.y, fp.x-cp.x, fp.y-cp.y);
		else if((fp.x <= cp.x)&&(fp.y > cp.y))
			g2.drawRect(fp.x, cp.y, cp.x-fp.x, fp.y-cp.y);
		else 
			g2.drawRect(cp.x, fp.y, fp.x-cp.x, cp.y-fp.y);	
	}
    
    void smartDrawOval(Graphics g, Point fp, Point cp )
	{
    	Graphics2D g2=(Graphics2D)g;
    	g2.setColor(selectedColor1);
    	g2.setStroke(new BasicStroke(5));
    	if((fp.x <= cp.x)&&(fp.y <= cp.y))
			g2.drawOval(fp.x, fp.y, cp.x-fp.x, cp.y-fp.y);
		else if((fp.x > cp.x)&&(fp.y > cp.y))
			g2.drawOval(cp.x, cp.y, fp.x-cp.x, fp.y-cp.y);
		else if((fp.x <= cp.x)&&(fp.y > cp.y))
			g2.drawOval(fp.x, cp.y, cp.x-fp.x, fp.y-cp.y);
		else 
			g2.drawOval(cp.x, fp.y, fp.x-cp.x, cp.y-fp.y);	
	}
    
    void smartSetSizeLocation(DrawInAddFurniCanvas s, Point fp, Point cp)
	{

		if((fp.x <= cp.x)&&(fp.y <= cp.y))
		{
			s.setSize(cp.x-fp.x, cp.y-fp.y);
			s.setLocation(fp);
		}
		else if((fp.x > cp.x)&&(fp.y > cp.y))
		{
			s.setSize(fp.x-cp.x, fp.y-cp.y);
			s.setLocation(cp);

		}
		else if((fp.x <= cp.x)&&(fp.y > cp.y))
		{
			s.setSize(cp.x-fp.x, fp.y-cp.y);
			s.setLocation(fp.x, cp.y);
		}
		else 
		{
			s.setSize(fp.x-cp.x, cp.y-fp.y);
			s.setLocation(cp.x, fp.y);
		}	
	}
    
    //捲動軸範圍
  	public Dimension getPreferredSize() {
  		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
  		return new Dimension(screen.width-5,screen.height-220);
    }
  	
    public void setCanvasSize(int width, int height) {
		rectWidth = width;
		rectHeight= height;
		repaint();
	}
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		
//        int rectWidth = 1100;
//        int rectHeight = 500;

        // 繪製白色的長方形
        g.setColor(Color.WHITE);
        g.fillRect(230, 110, rectWidth, rectHeight); //(寬、高)

        // 繪製黑色的框線
        g.setColor(Color.BLACK);
        g.drawRect(230, 110, rectWidth, rectHeight);
        if(draw!=null)
        {
        	for(Draw l:draw)
			{
        		g2.setColor(selectedColor1);
        		g2.setStroke(new BasicStroke(5));
				g.drawLine(l.sp.x, l.sp.y, l.ep.x, l.ep.y);
			}
		}
        if(eraser!=null)
        {
        	g2.setColor(Color.WHITE);
        	for(Eraser l:eraser)
			{
				g2.setStroke(new BasicStroke(5));
				g.drawLine(l.sp.x, l.sp.y, l.ep.x, l.ep.y);
			}
		}
        if(lines!=null)
        {
        	for(Line l:lines)
			{
        		g2.setColor(selectedColor1);
        		g2.setStroke(new BasicStroke(5));
				g.drawLine(l.sp.x, l.sp.y, l.ep.x, l.ep.y);
			}
		}
        if(triangle!=null)
        {
        	for(Triangle l:triangle)
			{
        		g2.setColor(selectedColor1);
        		g2.setStroke(new BasicStroke(5));
        		int dx=l.sp.x/2+l.ep.x/2;
        		g2.drawLine(dx, l.sp.y, l.sp.x, l.ep.y);
        		g2.drawLine(dx, l.sp.y, l.ep.x, l.ep.y);
        		g2.drawLine(l.sp.x, l.ep.y, l.ep.x, l.ep.y);
			}
		}
        if(rect!=null)
        {
        	for(Rect l:rect)
			{
        		g2.setColor(selectedColor1);
        		g2.setStroke(new BasicStroke(5));
        		g2.drawRect(l.sp.x, l.sp.y, l.ep.x-l.sp.x, l.ep.y-l.sp.y);
        		System.out.println(l.sp.x);
			}
		}//l.ep.x-l.sp.x, l.sp.y-l.ep.y
        if(oval!=null)
        {
        	for(Oval l:oval)
			{
        		g2.setColor(selectedColor1);
        		g2.setStroke(new BasicStroke(5));
        		g2.drawOval(l.fp.x, l.fp.y, l.cp.x-l.fp.x, l.cp.y-l.fp.y);
			}
		}
	}
	public void saveImage(DreamHouse parent) {
		parent.newWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension CanvasSize =this.getSize();
		
		
		// 設定 Canvas 的縮放後的位置值
        int scaledWidth = (int) (canvasWidth);
        int scaledHeight = (int) (canvasHeight) ;
    	//System.out.print("w:"+scaledWidth);

    	//System.out.print("h:"+scaledHeight);
        if(scaledWidth<=1302 && scaledWidth >=1 && scaledHeight <=578 && scaledHeight>=1) {
			
			BufferedImage image = new BufferedImage(CanvasSize.width, CanvasSize.height, BufferedImage.TYPE_INT_RGB);
		    Graphics2D g2d = image.createGraphics();
		    paint(g2d);
		    g2d.dispose();
		    
	//	 // 設定 Canvas 的縮放後的位置值
	//        int scaledWidth = (int) (canvasWidth);
	//        int scaledHeight = (int) (canvasHeight) ;
	        
	        //抓到位置+大小後存入resultImage
	        BufferedImage saveimage = image.getSubimage(231, 111, scaledWidth-2, scaledHeight-2);
		    
	        try {
	            // 將圖片保存為PNG格式，這可以保留透明背景
	        	String furniname = JOptionPane.showInputDialog("請輸入傢俱名稱");
	        	File outputFile;
	        	if(furniname != null) {
	        		JOptionPane.showMessageDialog(this, "傢俱儲存完成!");
	        	
		        	switch(parent.furniture.chooseaddfurni) {
		     			case 1:
		     				outputFile = new File("images/windowdoor_img/"+furniname+".png");
		     				ImageIO.write(saveimage, "png", outputFile);
		     				break;
		     			case 2:
		     				outputFile = new File( "images/table_img/"+furniname+".png");
		     				ImageIO.write(saveimage, "png", outputFile);
		     				break;
		     			case 3:
		     				outputFile = new File( "images/chair_img/"+furniname+".png");
		     				ImageIO.write(saveimage, "png", outputFile);
		     				break;
		     			case 4:
		     				outputFile = new File( "images/bed_img/"+furniname+".png");
		     				ImageIO.write(saveimage, "png", outputFile);
		     				break;
		     			case 5:
		     				outputFile = new File( "images/other_img/"+furniname+".png");
		     				ImageIO.write(saveimage, "png", outputFile);
		     				break;
		     			default:
		     				break;
		        	}
		        	
		        	switch(parent.furniture.chooseaddfurni) {
	        		case 1:
	        			parent.furniture.comboBox1.addItem(createButton( "images/windowdoor_img/"+furniname+".png"));
	        			break;
	        		case 2:
	        			parent.furniture.comboBox2.addItem(createButton( "images/table_img/"+furniname+".png"));
	        			break;
	        		case 3:
	        			parent.furniture.comboBox3.addItem(createButton( "images/chair_img/"+furniname+".png"));
	        			break;
	        		case 4:
	        			parent.furniture.comboBox4.addItem(createButton( "images/bed_img/"+furniname+".png"));
	        			break;
	        		case 5:
	        			parent.furniture.comboBox5.addItem(createButton( "images/other_img/"+furniname+".png"));
	        			break;
	        		default:
	        			break;
		        	}
		        	parent.newWindow.setVisible(false);
		        	System.out.println("size="+parent.newWindow.getSize());
	        	}else {
	        		JOptionPane.showMessageDialog(this, "儲存失敗!");
	        	}
	
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
        }else {
        	JOptionPane.showMessageDialog(this, "你的傢俱太大了!");
        	parent.newWindow.setVisible(false);
        }

	}

	private static JButton createButton( String imagePath) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(imagePath); 
        button.setIcon(icon);
        return button;
    }
	
	
}