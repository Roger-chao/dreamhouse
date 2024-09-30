import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

public class AddFurniToolbar extends JPanel{
	
	Point lp=null;
	Point cp=null;
	Point fp=null;
	Status status;
	Vector<Line> lines=null;
	DrawInAddFurniCanvas activeShape =null;
	AddFurniCanvas parent2=null;
	private DreamHouse parent;
	private JPanel AddFurniToolbarBtn;
	private AddFurniCanvas addfurnicanvas;
	
	public AddFurniToolbar(DreamHouse parent,AddFurniCanvas addfurnicanvas){
		
		status=Status.active;
		this.addfurnicanvas = addfurnicanvas;
		this.parent = parent;
		AddFurniToolbarBtn = new JPanel(new FlowLayout(FlowLayout.LEADING,10,10));
		AddFurniToolbarBtn.setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1000,100));
		setLayout(new BorderLayout());   
		FlowLayout flowLayout = (FlowLayout) AddFurniToolbarBtn.getLayout();
	    flowLayout.setAlignment(FlowLayout.CENTER);
	    
/*		JButton ChooseBtn = new JButton(new ImageIcon("images/choosething.png"));
	    JPanel ChooseJPanel = new JPanel();
	    ChooseJPanel.setLayout(new BorderLayout());
	    ChooseJPanel.add(ChooseBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(ChooseJPanel);
	    
	    JButton prestpBtn = new JButton(new ImageIcon("images/prestp_size.png"));
	    JPanel prestpJPanel = new JPanel();
	    prestpJPanel.setLayout(new BorderLayout());
	    prestpJPanel.add(prestpBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(prestpJPanel);
	    
	    JButton nextstpBtn = new JButton(new ImageIcon("images/nextstp_size.png"));
	    JPanel nextstpJPanel = new JPanel();
	    nextstpJPanel.setLayout(new BorderLayout());
	    nextstpJPanel.add(nextstpBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(nextstpJPanel);*/
	    
	    JButton PenBtn = new JButton(new ImageIcon("images/pen.png"));
	    JPanel PenJPanel = new JPanel();
	    PenJPanel.setLayout(new BorderLayout());
	    PenJPanel.add(PenBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(PenJPanel);
	    
/*	    JButton fillupBtn = new JButton(new ImageIcon("images/fillup.png"));
	    JPanel fillupJPanel = new JPanel();
	    fillupJPanel.setLayout(new BorderLayout());
	    fillupJPanel.add(fillupBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(fillupJPanel);*/
	    
	    JButton eraserBtn = new JButton(new ImageIcon("images/eraser.png"));
	    JPanel eraserJPanel = new JPanel();
	    eraserJPanel.setLayout(new BorderLayout());
	    eraserJPanel.add(eraserBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(eraserJPanel);
	    
/*	    JButton wordBtn = new JButton(new ImageIcon("images/word.png"));
	    JPanel wordJPanel = new JPanel();
	    wordJPanel.setLayout(new BorderLayout());
	    wordJPanel.add(wordBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(wordJPanel);*/
	    
	    JButton lineBtn = new JButton(new ImageIcon("images/line.png"));
	    JPanel lineJPanel = new JPanel();
	    lineJPanel.setLayout(new BorderLayout());
	    lineJPanel.add(lineBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(lineJPanel);
	    
	    JButton triangleBtn = new JButton(new ImageIcon("images/triangle.png"));
	    JPanel triangleJPanel = new JPanel();
	    triangleJPanel.setLayout(new BorderLayout());
	    triangleJPanel.add(triangleBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(triangleJPanel);
	    
	    JButton circleBtn = new JButton(new ImageIcon("images/circle.png"));
	    JPanel circleJPanel = new JPanel();
	    circleJPanel.setLayout(new BorderLayout());
	    circleJPanel.add(circleBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(circleJPanel);
	    
	    JButton rectangleBtn = new JButton(new ImageIcon("images/rectangle.png"));
	    JPanel rectangleJPanel = new JPanel();
	    rectangleJPanel.setLayout(new BorderLayout());
	    rectangleJPanel.add(rectangleBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(rectangleJPanel);
	    
/*	    JButton colorButton1=new JButton();
	    colorButton1.setBackground(addfurnicanvas.selectedColor1);
	    colorButton1.setPreferredSize(new Dimension(60,60));
	    colorButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 顯示顏色選擇對話方塊
            	addfurnicanvas.selectedColor1 = JColorChooser.showDialog(null, "選擇顏色", addfurnicanvas.selectedColor1);

                // 更新按鈕背景顏色
                colorButton1.setBackground(addfurnicanvas.selectedColor1);
            }
        });
	    AddFurniToolbarBtn.add(colorButton1);
	    
	    JButton colorButton2=new JButton();
	    colorButton2.setBackground(addfurnicanvas.selectedColor2);
	    colorButton2.setPreferredSize(new Dimension(60,60));
	    colorButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Color selectedColor3 = addfurnicanvas.selectedColor1;
            	addfurnicanvas.selectedColor1=addfurnicanvas.selectedColor2;
           	colorButton1.setBackground(addfurnicanvas.selectedColor1);
            	addfurnicanvas.selectedColor2=selectedColor3;
            	colorButton2.setBackground(addfurnicanvas.selectedColor2);
            }
        });
	    AddFurniToolbarBtn.add(colorButton2);
	    
	    JButton pre90Btn = new JButton(new ImageIcon("images/pre90.png"));
	    JPanel pre90JPanel = new JPanel();
	    pre90JPanel.setLayout(new BorderLayout());
	    pre90JPanel.add(pre90Btn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(pre90JPanel);
	    
	    JButton next90Btn = new JButton(new ImageIcon("images/next90.png"));
	    JPanel next90JPanel = new JPanel();
	    next90JPanel.setLayout(new BorderLayout());
	    next90JPanel.add(next90Btn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(next90JPanel);*/
	    
	    JButton savefurnitureBtn = new JButton(new ImageIcon("images/savefurniture.png"));
	    JPanel savefurnitureJPanel = new JPanel();
	    savefurnitureJPanel.setLayout(new BorderLayout());
	    savefurnitureJPanel.add(savefurnitureBtn,BorderLayout.CENTER);
	    AddFurniToolbarBtn.add(savefurnitureJPanel);
	    
	    add(AddFurniToolbarBtn, BorderLayout.CENTER);
	    
/*	    JColorChooser colorChooser1 = new JColorChooser(addfurnicanvas.selectedColor1);
        colorChooser1.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // 當顏色改變時，獲取新的顏色
            	AddFurniToolbar.this.addfurnicanvas.selectedColor1 = colorChooser1.getColor();
	    		addfurnicanvas.addtoorbar.addfurnicanvas.selectedColor1=AddFurniToolbar.this.addfurnicanvas.selectedColor1;
                // 更新按鈕背景顏色
                colorButton1.setBackground(addfurnicanvas.selectedColor1);
           }
        });
        
        ChooseBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.ready2choosething;
				
			}
		});
        prestpBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				//addfurnicanvas.actionPrev();
				
			}
		});
        nextstpBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				//addfurnicanvas.actionPrev();
			}
		});*/
        PenBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				actionFreeDrawBtn();
				
			}
		});
/*        fillupBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.ready2fillup;
				
			}
		});*/
        eraserBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				actionEraserBtn();
				
			}
		});
        
/*        wordBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.ready2writeword;
				
			}
		});*/
        lineBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.ready2Createline;
				
			}
		});
        triangleBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.ready2Createtriangle;
				
			}
		});
        circleBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.ready2Createcircle;
				
			}
		});
        rectangleBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.ready2Createrectangle;
				
			}
		});
/*        pre90Btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.active;
				
			}
		});
        next90Btn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.status=Status.active;
				
			}
		});*/
        savefurnitureBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				addfurnicanvas.saveImage(parent);
			}
		});
        
        
	}
		
	public void paint1(Graphics g)
	{
		g.setColor(addfurnicanvas.selectedColor1);
		
		g.drawRect(1, 1, this.getWidth()-2, this.getHeight()-2);
		
	}
	void actionFreeDrawBtn()
	{
		if((this.addfurnicanvas!=null))
			this.addfurnicanvas.status=status.ready4Drawing;
		
	}
	void actionEraserBtn()
	{
		if((this.addfurnicanvas!=null))
			this.addfurnicanvas.status=status.ready2eraser;
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 1, getWidth(), getHeight()-2);
	}
}
