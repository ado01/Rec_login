
package rec_login;

/**
 *
 * @author xyzt
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton{
    public boolean disable=false;     
    private boolean def=true;

    private String imageDefault;
    private String imagePressed;        
    private String imageDisabilitato;
    
    private Image imageiconDefault;
    private Image imageiconPressed;     
    private Image imageiconDisabilitato;
    
    private Image image;    
    
    private String lettera;
    public String data;
   
    public MyButton(String imageDefault, String imagePressed, String imageDisabilitato,int x , int y){
        super();

        this.imageDefault=imageDefault;
        this.imagePressed=imagePressed;
        this.imageDisabilitato=imageDisabilitato;
        
        this.imageiconDefault = Toolkit.getDefaultToolkit () .getImage(getClass().getResource(imageDefault));
        this.imageiconPressed  = Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imagePressed));
        this.imageiconDisabilitato=Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imageDisabilitato));
        
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        
        controllo();
        
        this.setRolloverEnabled(true);

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    }
    
    public MyButton(String imageDefault, String imagePressed, String imageDisabilitato,int x , int y,String lettera){
        super();
        
        this.lettera=lettera;
        
        this.imageDefault=imageDefault;
        this.imagePressed=imagePressed;
        this.imageDisabilitato=imageDisabilitato;
        
        this.imageiconDefault = Toolkit.getDefaultToolkit () .getImage(getClass().getResource(imageDefault));
        this.imageiconPressed  = Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imagePressed));
        this.imageiconDisabilitato=Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imageDisabilitato));
        
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
 
        controllo();

        this.setRolloverEnabled(true);

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    }
    
    public MyButton(String imageDefault, String imagePressed, String imageDisabilitato,int x , int y, int larghezza, int lunghezza){
        super();
        
        this.imageDefault=imageDefault;
        this.imagePressed=imagePressed;
        this.imageDisabilitato=imageDisabilitato;
        
        final BufferedImage imgDefault = scaleImage(larghezza,lunghezza,imageDefault);
        this.imageiconDefault = (Image)imgDefault;
        
        final BufferedImage imgPressed = scaleImage(larghezza,lunghezza,imagePressed);
        this.imageiconPressed = (Image)imgPressed;
        
        final BufferedImage imgDisabilitato = scaleImage(larghezza,lunghezza,imageDisabilitato);
        this.imageiconDisabilitato = (Image)imgDisabilitato;
  
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        
        controllo();
        
        this.setRolloverEnabled(true);

        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    }
    
    public void setBounds(int x, int y)
    {
        super.setBounds(x,y,image.getWidth(this),image.getHeight(this));
    }
    
    public void controllo()
    {
       if(this.disable==false){
            this.setIcon(new ImageIcon(imageiconDefault));
            this.setPressedIcon(new ImageIcon(imageiconPressed ));
            this.setEnabled(true);
        }else{
            this.setEnabled(false);
            this.setDisabledIcon(new ImageIcon(imageiconDisabilitato));
            
        }
    }
    
    public void setEnable()
    {
        this.disable=false;
        controllo();
    }
    
    public void setDisable()
    {
        this.disable=true;
        controllo();
    }
    
    public String getLettera()
    {
        return this.lettera;
    }
    
    public String getDefaultmage()
    {
        return this.imageDefault;
    }
    
    public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        try {
            ImageIcon ii = new ImageIcon(filename);//path to image
            bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.BITMASK);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bi;
    }
}
