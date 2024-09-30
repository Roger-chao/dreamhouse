import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                DreamHouse designer = new DreamHouse();
//                designer.setVisible(true);
            }
        });
    }
}

class DreamHouse extends JFrame {
	public JFrame newWindow;
    private Toolbar toolbar;
    private Canvas canvas;
    public Furniture furniture;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private AddFurniToolbar addFurniToolbar;
    private AddFurniCanvas addFurniCanvas;
    private DrawInAddFurniCanvas DrawInAddFurniCanvas;
//	private AddFurniZoomInOut addFurniZoomInOut;
	//放大縮小工具列
	private ZoomInOut zoominout;
	private int Width;
	private int Height;
	private int setx,sety,angel;
	private double sc;
    public DreamHouse(int a,int b) {
    	
        // 設定最上面主題那排
        setTitle("夢幻大家園 - 家居設計");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 設定布局
        setLayout(new BorderLayout()); //使用BorderLayout 去配置版面

        // 初始化主畫面
        canvas = new Canvas(a,b);
        toolbar = new Toolbar(this,canvas,a,b);
        canvas.setToolbar(toolbar);				//將toolbar的值傳入
        furniture = new Furniture(this,canvas);//傳給Furniture.java
        zoominout = new ZoomInOut(canvas);	//新增放大縮小工具列
        toolbar.showattr(null,a,b,0,'c');
        
        addFurniCanvas = new AddFurniCanvas(DrawInAddFurniCanvas,this,addFurniToolbar,Width,Height,setx,sety,angel,sc);
        addFurniToolbar = new AddFurniToolbar(this,addFurniCanvas);
        
        //====================
        //初始化CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        //將主畫面以及AddFurni加到CardLayout裡面管理
        cardPanel.add(createMainPanel(),"MainPanel");
        cardLayout.show(cardPanel, "MainPanel");
        //加入到CardPanel
        add(cardPanel, BorderLayout.CENTER);
           
        
    }
    
    private JPanel createMainPanel() {
    	JPanel mainPanel = new JPanel(new BorderLayout());
    	mainPanel.add(furniture,BorderLayout.WEST);
    	mainPanel.add(toolbar,BorderLayout.NORTH);
    	
    	//新增放大縮小工具列
    	mainPanel.add(zoominout,BorderLayout.SOUTH);
        zoominout.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));	//靠右對齊
        
        //家具列-捲動軸
        JScrollPane scroll1 = new JScrollPane(furniture);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scroll1, BorderLayout.WEST);
        
    	//畫布-捲動軸
        JScrollPane scroll = new JScrollPane(canvas);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scroll, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    
    public void addFurni(int width, int height) {
    	this.Width = width;
    	this.Height = height;
    	AddFurniCanvas newAddCanvas = new AddFurniCanvas(DrawInAddFurniCanvas,this,addFurniToolbar,width,height,setx,sety,angel,sc);
    	AddFurniToolbar newAddToolbar= new AddFurniToolbar(this,newAddCanvas);
//        addFurniZoomInOut = new AddFurniZoomInOut(newAddCanvas);
    	
        //Add Furniture版面配置
        newWindow = new JFrame("自訂傢俱");
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setVisible(true);
        newWindow.setSize(1470, 600);
        newAddCanvas.setCanvasSize(width,height);
        Color backgroundColor = new Color(235,235,235);
        newWindow.setBackground(backgroundColor);
        newWindow.add(newAddToolbar, BorderLayout.NORTH);
        newWindow.add(newAddCanvas, BorderLayout.CENTER);  

		newWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
//        //新增家具-新增放大縮小工具列
//        newWindow.add(addFurniZoomInOut,BorderLayout.SOUTH);
//    	addFurniZoomInOut.setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));	//靠右對齊
        
        //新增家具-捲動軸
        JScrollPane scroll = new JScrollPane(newAddCanvas);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        newWindow.add(scroll, BorderLayout.CENTER);
        
        newWindow.setVisible(true);
        
        
        cardPanel.revalidate();
    	cardPanel.repaint();

    }
    
    public void showMainCanvas() {
    	//顯示主畫面
    	cardLayout.show(cardPanel, "MainPanel");
    	cardPanel.revalidate();
    	cardPanel.repaint();
    	
    }
}