//上方工具欄
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class Toolbar extends JPanel {
	
	//背景顏色設定
	private Color backgroundColor = new Color(227,216,167);// R,G,B
	private JPanel ToolbarBtn;
	private Canvas canvas;
	private Attribute attribute;		//屬性表
	
	//存檔需要canvas的畫布大小使用
	int A,B;
	
    public Toolbar(DreamHouse parent,Canvas canvas,int a, int b) {
    	
    	this.canvas = canvas;
    	setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1100, 110));
        setLayout(new BorderLayout());   
        
        A = a;
        B = b;
      //按鈕JPanel區域
      ToolbarBtn = new JPanel(new FlowLayout(FlowLayout.LEADING,10,10));
      ToolbarBtn.setBackground(Color.WHITE);
      FlowLayout flowLayout = (FlowLayout) ToolbarBtn.getLayout();
      flowLayout.setAlignment(FlowLayout.LEFT); // 設定對齊方式       
      
      JButton saveJPGBtn = new JButton(new ImageIcon("images/savefile_size.png"));
      saveJPGBtn.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		String FileName = getFileName();
      		if(FileName != null && !FileName.isEmpty()) {
      			if(!isFileNameExists(FileName)) {
      				if(saveCanvasToFile(canvas,FileName)) {
      					JOptionPane.showMessageDialog(null, "儲存成功!");
      				}else {
      					JOptionPane.showMessageDialog(null, "儲存失敗，請重新輸入!");
      				}
      			}else {
      				JOptionPane.showMessageDialog(null, "檔名已存在，請使用其他檔案名稱!");
      			}
      		}
      		
      	}
      });
      JPanel saveJPGJPanel = new JPanel();
      saveJPGJPanel.setBackground(backgroundColor);
      saveJPGJPanel.setLayout(new BorderLayout());
      JLabel saveJPG_label = new JLabel("儲存圖片");
      saveJPG_label.setHorizontalAlignment(JLabel.CENTER);
      saveJPGJPanel.add(saveJPG_label,BorderLayout.SOUTH);
      saveJPGJPanel.add(saveJPGBtn,BorderLayout.CENTER);
      ToolbarBtn.add(saveJPGJPanel);

      JButton PreviousBtn = new JButton(new ImageIcon("images/prestp_size.png"));
      JPanel PreviousJPanel = new JPanel();
      PreviousJPanel.setBackground(backgroundColor);
      PreviousJPanel.setLayout(new BorderLayout());
      JLabel Previous_label = new JLabel("上一步");
      Previous_label.setHorizontalAlignment(JLabel.CENTER);
      PreviousJPanel.add(Previous_label,BorderLayout.SOUTH);
      PreviousJPanel.add(PreviousBtn,BorderLayout.CENTER);
      ToolbarBtn.add(PreviousJPanel);
      
      JButton NextBtn = new JButton(new ImageIcon("images/nextstp_size.png"));
      JPanel NextJPanel = new JPanel();
      NextJPanel.setBackground(backgroundColor);
      NextJPanel.setLayout(new BorderLayout());
      JLabel Next_label = new JLabel("下一步");
      Next_label.setHorizontalAlignment(JLabel.CENTER);
      NextJPanel.add(Next_label,BorderLayout.SOUTH);
      NextJPanel.add(NextBtn,BorderLayout.CENTER);
      ToolbarBtn.add(NextJPanel);
      
      JButton DeleteBtn = new JButton(new ImageIcon("images/delete_size.png"));
      JPanel DeleteJPanel = new JPanel();
      DeleteJPanel.setBackground(backgroundColor);
      DeleteJPanel.setLayout(new BorderLayout());
      JLabel Delete_label = new JLabel("刪除傢俱");
      Delete_label.setHorizontalAlignment(JLabel.CENTER);
      DeleteJPanel.add(Delete_label,BorderLayout.SOUTH);
      DeleteJPanel.add(DeleteBtn,BorderLayout.CENTER);
      ToolbarBtn.add(DeleteJPanel);
      
      JButton BtnL = new JButton(new ImageIcon("images/pre90.png"));
      JPanel preStepJPanel = new JPanel();
      preStepJPanel.setBackground(backgroundColor);
      preStepJPanel.setLayout(new BorderLayout());
      JLabel preStep_label = new JLabel("向左旋轉90°");
      preStep_label.setHorizontalAlignment(JLabel.CENTER);
      preStepJPanel.add(preStep_label,BorderLayout.SOUTH);
      preStepJPanel.add(BtnL,BorderLayout.CENTER);
      ToolbarBtn.add(preStepJPanel);
      
      JButton BtnR = new JButton(new ImageIcon("images/next90.png"));
      JPanel nextStepJPanel = new JPanel();
      nextStepJPanel.setBackground(backgroundColor);
      nextStepJPanel.setLayout(new BorderLayout());
      JLabel nextStep_label = new JLabel("向右旋轉90°");
      nextStep_label.setHorizontalAlignment(JLabel.CENTER);
      nextStepJPanel.add(nextStep_label,BorderLayout.SOUTH);
      nextStepJPanel.add(BtnR,BorderLayout.CENTER);
      ToolbarBtn.add(nextStepJPanel);
      
//      JPanel saveFileJPanel = new JPanel();
//      saveFileJPanel.setBackground(backgroundColor);
//      saveFileJPanel.setLayout(new BorderLayout());
//      JLabel saveFile_label = new JLabel("儲存檔案");
//      saveFile_label.setHorizontalAlignment(JLabel.CENTER);
//      saveFileJPanel.add(saveFile_label,BorderLayout.SOUTH);
//      //saveFileJPanel.add(saveFileBtn,BorderLayout.CENTER);
//      ToolbarBtn.add(saveFileJPanel);
      
      add(ToolbarBtn, BorderLayout.CENTER);
      
		BtnL.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				canvas.status=Status.active;
				canvas.actionTurnLeft();
			}
		});

		BtnR.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				canvas.status=Status.active;
				canvas.actionTurnRight();
			}
		});
		
		DeleteBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				canvas.actionDeleteAFurniture();
			}
		});
		
		PreviousBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				canvas.actionPrev();
			}
		});
        
		NextBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				canvas.actionNext();
			}
		});
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        ToolbarBtn.setBounds(1,2,getWidth()-401,getHeight()-3);
        g.drawRect(0, 1, getWidth(), getHeight()-2);
    }
    void showattr(String n,int a,int b,int ang,char c) {		//顯示屬性表
        attribute = new Attribute(n,a,b,ang,c);
        add(attribute,BorderLayout.EAST);
        attribute.setCanvas(canvas);
        revalidate();
        repaint();
    }
    void removeattr() {
    	this.remove(attribute);
    }
    
    private String getFileName() {
    	JTextField inputFileName = new JTextField();
    	int option = JOptionPane.showConfirmDialog(null, inputFileName , "請輸入檔名" , JOptionPane.OK_CANCEL_OPTION);
    	if(option == JOptionPane.OK_OPTION) {
    		if (!inputFileName.getText().isEmpty()) {
                return inputFileName.getText();
            } else {
                JOptionPane.showMessageDialog(null, "檔名不可為空!");
                return null;
            }
    	}else {
    		return null;
    	}
    	
    }

    private boolean isFileNameExists(String fileName) {
    	File FileName = new File(fileName + ".jpg");
    	return FileName.exists();
    }
    
    private boolean saveCanvasToFile(Canvas canvas, String name) {
    	try {
    		if(canvas != null) {
    			// 獲取畫布的尺寸
                Dimension canvasSize = canvas.getSize();

                //讓物件變成為選取狀態
                if(canvas.actFurniture !=null) {
                	canvas.actFurniture.doInSelected();
                }
                
                // 創建一個 BufferedImage 去抓畫布canvas尺寸
                BufferedImage bufferedImage = new BufferedImage(canvasSize.width, canvasSize.height, BufferedImage.TYPE_INT_RGB);
                //將畫布內容畫到bufferedImage裡面 then 釋放
                Graphics2D g2d = bufferedImage.createGraphics();
                canvas.paint(g2d);
                g2d.dispose();
                
                // 設定 Canvas 的縮放後的位置值
                int scaledWidth = (int) (A * canvas.scale);
                int scaledHeight = (int) (B * canvas.scale);
                
                //抓到位置+大小後存入resultImage
                BufferedImage resultImage = bufferedImage.getSubimage(90, 100, scaledWidth, scaledHeight);
//                String desktopPath = "C:\\Users\\USER\\Desktop";
//
//                // 指定保存的檔名 -->console會顯示 可以到那裏找存在哪
//                File file = new File(desktopPath+name+".jpg");
                // 指定保存的檔名 -->console會顯示 可以到那裏找存在哪
                File file = new File(name+".jpg");

                // 將圖片保存為 JPEG 檔
                ImageIO.write(resultImage, "jpg", file);

                System.out.println("截圖已保存為: " + file.getAbsolutePath());
                return true;
    		} else {
                System.out.println("Canvas is null");
                return false;
            }
        } catch (IOException ex) {
            System.err.println(ex);
            return false;
        }
    }
}

