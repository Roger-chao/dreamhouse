import javax.swing.*;
import javax.swing.text.NumberFormatter;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Vector;



public class Attribute extends JPanel{
	Status status;
	private Canvas canvas;
	
	private GridBagLayout gb = new GridBagLayout();					//宣告 GridBagLayout
	private JPanel name;			//屬性表各容器
	private JPanel height;
	private JPanel width;
	private JPanel angel;
	private JLabel nlb;
	private JTextField ntext;
	private JLabel heilb;
	private JLabel widthlb;
	private JLabel angellb;
	private JFormattedTextField heitext;		//文字框傳入的數值
	private JFormattedTextField widtext;
	//private int count=0;
	int Canvas_W;				//畫布長寬(用以讓輸入物件不超過畫布)
	int Canvas_H;   
	private String ntextinput;
	private Object heitextinput;
	private Object widtextinput;
	private String na;			//傳入的物件數值
	private int hei;
	private int wid;



	
    public Attribute(String n, int w,int h,int ang,char a) {
    	
		setBackground(new Color(179, 179, 179));
        setPreferredSize(new Dimension(400, 110)); //表格寬、高(不變)
        setLayout(gb);  			//設定屬性表內部布局方式
        //this.status=Status.inselected;
        
        if(a=='c') {		//畫布屬性表
        	Canvas_W=w;
        	Canvas_H=h;
        	ang=0;
        	this.status=Status.inselected;
	        //名稱
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.BOTH;		//填充方式
	        gbc.gridx = 0;				//起始位置
	        gbc.gridy = GridBagConstraints.RELATIVE;
	        gbc.weighty = 1.0;
	        gbc.gridwidth = GridBagConstraints.REMAINDER;	//所佔網格長寬
	        gbc.gridheight = 1;
	        gbc.anchor = GridBagConstraints.WEST;			//設定定位點
	        gbc.ipadx = 250;								//增加寬度
	        //gbc.insets = new Insets(0,0,0,0);				//調整間距(上左下右)
	        name = new JPanel(new FlowLayout(FlowLayout.LEFT)); //BorderLayout()
	        nlb = new JLabel("名稱：畫布",JLabel.LEFT);
	        //nlb.setBounds(0,0,10,100);
	        ntext = new JTextField(11);
	        ntext.setVisible(false);
			name.add(nlb);	//,BorderLayout.WEST
	        name.add(ntext);
	        this.add(name,gbc);
	        
	        
			//長度
			GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.fill = GridBagConstraints.BOTH;
	        gbc1.gridx = 0;
	        gbc1.gridy = GridBagConstraints.RELATIVE;
	        gbc1.gridwidth =  7;
	        gbc1.gridheight = 1;
	        gbc1.weighty = 1.0;
	        gbc1.ipadx = 10;
	        height = new JPanel(new FlowLayout(FlowLayout.LEFT));		//將"長度"new成容器
	        heilb = new JLabel("長度：" + h);
	        heitext = new JFormattedTextField(NumberFormat.getIntegerInstance());
	        //heitext.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
	        heitext.setColumns(7);					//文本框寬度
	        heitext.setVisible(false);
	        height.add(heilb);
	        height.add(heitext);
	        height.add(new JLabel("(cm)"));
			this.add(height,gbc1);
			
			//寬度
			GridBagConstraints gbc2 = new GridBagConstraints();
			gbc2.fill = GridBagConstraints.BOTH;
	        gbc2.gridx = 7;
	        gbc2.gridy = GridBagConstraints.RELATIVE;
	        gbc2.gridwidth = 7;
	        gbc2.gridheight = 1;
	        width = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        widthlb = new JLabel("寬度：" + w);
	        widtext = new JFormattedTextField(NumberFormat.getIntegerInstance());
	        widtext.setColumns(7);
	        widtext.setVisible(false);
	        width.add(widthlb);
	        width.add(widtext);
	        width.add(new JLabel("(cm)"));
			this.add(width,gbc2);
			
			
			//角度
			GridBagConstraints gbc3 = new GridBagConstraints();
	        gbc3.fill = GridBagConstraints.BOTH;		//填充方式
	        gbc3.gridx = 0;				//位置
	        gbc3.gridy = GridBagConstraints.RELATIVE;
	        gbc3.gridwidth = GridBagConstraints.REMAINDER;	//網格長寬
	        gbc3.gridheight = 1;
	        gbc3.anchor = GridBagConstraints.WEST;			//設定定位點
	        gbc3.weighty = 1.0;
	        gbc3.insets = new Insets(0,0,0,0);				//調整間距(上下左右)
	        angel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        angellb = new JLabel("角度： 0°");
	        angel.add(angellb);
	        this.add(angel,gbc3);

    	}
    	else if(a=='o'){			//物件屬性表
        	this.status=Status.selected;
	        //名稱
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.fill = GridBagConstraints.BOTH;		//填充方式
	        gbc.gridx = 0;				//起始位置
	        gbc.gridy = GridBagConstraints.RELATIVE;
	        gbc.weighty = 1.0;
	        gbc.gridwidth = GridBagConstraints.REMAINDER;	//所佔網格長寬
	        gbc.gridheight = 1;
	        gbc.anchor = GridBagConstraints.WEST;			//設定定位點
	        gbc.ipadx = 200;								//增加寬度
	        name = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        nlb = new JLabel("名稱：" + ntextinput ,JLabel.LEFT);
	        ntext = new JTextField(11);
	        ntext.setVisible(false);
			name.add(nlb);
	        name.add(ntext);
	        this.add(name,gbc);
	        
	        
			//長度
	        GridBagConstraints gbc1 = new GridBagConstraints();
			gbc1.fill = GridBagConstraints.BOTH;
	        gbc1.gridx = 0;
	        gbc1.gridy = GridBagConstraints.RELATIVE;
	        gbc1.gridwidth =  7;
	        gbc1.gridheight = 1;
	        gbc1.weighty = 1.0;
	        gbc1.ipadx = 10;
	        height = new JPanel(new FlowLayout(FlowLayout.LEFT));		//將"長度"new成容器
	        heilb = new JLabel("長度：" + h);
	        heitext = new JFormattedTextField(NumberFormat.getIntegerInstance());
	        //heitext.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
	        heitext.setColumns(7);					//文本框寬度
	        heitext.setVisible(false);
	        height.add(heilb);
	        height.add(heitext);
	        height.add(new JLabel("(cm)"));
			this.add(height,gbc1);
			
			//寬度
			GridBagConstraints gbc2 = new GridBagConstraints();
			gbc2.fill = GridBagConstraints.BOTH;
	        gbc2.gridx = 7;
	        gbc2.gridy = GridBagConstraints.RELATIVE;
	        gbc2.gridwidth = 7;
	        gbc2.gridheight = 1;
	        width = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        widthlb = new JLabel("寬度：" + w);
	        widtext = new JFormattedTextField(NumberFormat.getIntegerInstance());
	        widtext.setColumns(7);
	        widtext.setVisible(false);
	        width.add(widthlb);
	        width.add(widtext);
	        width.add(new JLabel("(cm)"));
			this.add(width,gbc2);
			
			
			//角度
			GridBagConstraints gbc3 = new GridBagConstraints();
	        gbc3.fill = GridBagConstraints.BOTH;		//填充方式
	        gbc3.gridx = 0;				//位置
	        gbc3.gridy = GridBagConstraints.RELATIVE;
	        gbc3.gridwidth = GridBagConstraints.REMAINDER;	//網格長寬
	        gbc3.gridheight = 1;
	        gbc3.anchor = GridBagConstraints.WEST;			//設定定位點
	        gbc3.weighty = 1.0;
	        angel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        angellb = new JLabel("角度： " + ang + "°");
	        angel.add(angellb);
	        this.add(angel,gbc3);
	        showOBJattr(n,w,h,ang);
    	}

    }	
    
    @Override
    protected void paintComponent(Graphics g) {
    	//黑色外框線條設定
        super.paintComponent(g);
        g.setColor(Color.BLACK);        
        g.drawRect(0, 1, getWidth()-2, getHeight()-2);
    }
    
    
    class actionListener implements ActionListener {		//監測輸入文字框事件
        public void actionPerformed(ActionEvent e) {
        	if(ntext.getText().length() == 0) {
            	nlb.setText("名稱：" + na);
            	ntext.setVisible(false);
            }
        	else if(ntext.getText() == null){
        		ntextinput=na;
        	}
        	else
        		ntextinput = ntext.getText();				//取得輸入名稱
        	
            heitextinput = heitext.getValue();
            widtextinput = widtext.getValue();

            if (ntextinput != null) {
            	na=ntextinput;
                nlb.setText("名稱：" + na);
                ntext.setVisible(false);
                System.out.print("name：" + ntextinput);
            }
            if (heitextinput != null) {
            	//hei = ((Number) heitextinput).doubleValue();            	
            	hei = heitextinput.hashCode();
            	if(hei > Canvas_H) {
            		
            	}
            	//System.out.print("height:" +hei);
	            
            	heilb.setText("長度：" + hei);
	            heitext.setVisible(false);
	            //設定物件長度為輸入值
            }
            
            if (widtextinput != null) {
            	wid = widtextinput.hashCode();
            	if(wid > Canvas_W) {
            		
            	}
            	//System.out.print("width:" + wid);
            	widthlb.setText("寬度：" + wid);
                widtext.setVisible(false);
            }
            //改變圖片大小
            canvas.changebyattr(ntextinput,wid,hei);
        }
    }
    
    class focusListener implements FocusListener {			//監測文字框焦點事件
        private ActionListener actionListener;

        public focusListener(ActionListener actionListener) {
            this.actionListener = actionListener;
        }

        @Override
        public void focusLost(FocusEvent e) {			//文字框失去焦點
            actionListener.actionPerformed(null); 		// 觸發送出動作
        }

        @Override
        public void focusGained(FocusEvent e) {
            // 不需要執行任何動作
        }
    }
    
    
	public void showOBJattr(String n,int w,int h,int ang) {				//改變物件屬性
		
		if(ntextinput == null) {
			na=n;
			ntextinput=n;
		}
		nlb.setText("名稱：" + na);
		
		if(heitextinput == null) {
			hei=h;
		}
		heilb.setText("長度：" + hei);
		
		if(widtextinput == null) {
        	wid=w;
		}
		widthlb.setText("寬度：" + wid);
		
		//判斷輸入
		actionListener action = new actionListener();
		ntext.addActionListener(action);
		heitext.addActionListener(action);
		widtext.addActionListener(action);
		focusListener focus = new focusListener(action);
		ntext.addFocusListener(focus);
		heitext.addFocusListener(focus);
		widtext.addFocusListener(focus);
		LineBorder lineBorder = new LineBorder(new Color(0, 83, 255), 2);    //設定外框顏色、寬度 雯綺(0, 76, 153)
		
		name.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)		//滑鼠進入"名稱"物件
  			{
				name.setBorder(lineBorder);		//顯示外框
  			}
			public void mouseClicked(MouseEvent e)		//滑鼠點擊物件
  			{
				if(Attribute.this.status==Status.selected) {	//讓"名稱"文字框在物件選取的情況下才能改變
					nlb.setText("名稱：");
//					heitext.setVisible(false);
//					widtext.setVisible(false);
					ntext.setVisible(true);					//設置窗體可見			
				}
				
  			}
			public void mouseExited(MouseEvent e)		//滑鼠進入"名稱"物件
  			{
				name.setBorder(null);		//隱藏外框
  			}
  		});
		height.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)
  			{
				height.setBorder(lineBorder);
  			}
			public void mouseClicked(MouseEvent e)
  			{
				if(Attribute.this.status==Status.selected) {
					heilb.setText("長度：");
					heitext.setVisible(true);
					
				}
				
  			}
			public void mouseExited(MouseEvent e)		//滑鼠進入"名稱"物件
  			{
				height.setBorder(null);
  			}
  		});
		width.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e)
  			{
				width.setBorder(lineBorder);
  			}
			public void mouseClicked(MouseEvent e)	
  			{
				if(Attribute.this.status==Status.selected) {
					widthlb.setText("寬度：");
					widtext.setVisible(true);		
				}
				
  			}
			public void mouseExited(MouseEvent e)		//滑鼠進入"名稱"物件
  			{
				width.setBorder(null);
  			}
  		});
	}
    void setCanvas(Canvas can) {
        this.canvas = can;
    }
}
