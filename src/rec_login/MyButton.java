
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
    public boolean disable=false;      /*utilizato per attivare e disattivare il pulsante*/
    private boolean def=true;

    private String imageDefault;
    private String imagePressed;        /*nome del immagine da inserire nel button*/
    private String imageDisabilitato;
    
    private Image imageiconDefault;
    private Image imageiconPressed;     /*immagine da inserire all'interno del Graphics*/
    private Image imageiconDisabilitato;
    
    private Image image;    /*utilizato per cambiare il Graphics*/
    
    private String lettera;
    public String data;
    /*le due stringhe indicano il percorso dell'immagine del bottone mentre gli interi mi posizionano
    * il bottone in un posto x y all'interno del componente del bottone.
    */
    public MyButton(String imageDefault, String imagePressed, String imageDisabilitato,int x , int y){
        super();

        this.imageDefault=imageDefault;
        this.imagePressed=imagePressed;
        this.imageDisabilitato=imageDisabilitato;
        
        //recupero le immagini ( cliccato e non cliccato) dalla stringa relativa passata
        this.imageiconDefault = Toolkit.getDefaultToolkit () .getImage(getClass().getResource(imageDefault));
        this.imageiconPressed  = Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imagePressed));
        this.imageiconDisabilitato=Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imageDisabilitato));
        
        //elimino il contenuto grafico ed il contorno di default del pulsante
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        
        //controllo se il pulsante deve essere abbilitato o disabbilitato e a seconda dei casi inserisco l'image del pulsante
        controllo();
        
        //imposto l'immagine visualizzata quando il mouse passa sopra il pulsante o quando esso è selezionato
        this.setRolloverEnabled(true);
        
        //imposto il puntatore del mouse appropriato per gli oggetti attivi su cui cliccare
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    }
    
    public MyButton(String imageDefault, String imagePressed, String imageDisabilitato,int x , int y,String lettera){
        super();
        
        this.lettera=lettera;
        
        this.imageDefault=imageDefault;
        this.imagePressed=imagePressed;
        this.imageDisabilitato=imageDisabilitato;
        
        //recupero le immagini ( cliccato e non cliccato) dalla stringa relativa passata
        this.imageiconDefault = Toolkit.getDefaultToolkit () .getImage(getClass().getResource(imageDefault));
        this.imageiconPressed  = Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imagePressed));
        this.imageiconDisabilitato=Toolkit.getDefaultToolkit ().getImage(getClass().getResource(imageDisabilitato));
        
        //elimino il contenuto grafico ed il contorno di default del pulsante
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        //controllo se il pulsante deve essere abbilitato o disabbilitato e a seconda dei casi inserisco l'image del pulsante
        controllo();
        
        //imposto l'immagine visualizzata quando il mouse passa sopra il pulsante o quando esso è selezionato
        this.setRolloverEnabled(true);
        //this.setRolloverIcon(new ImageIcon(img_FOCUSED));
        //this.setSelectedIcon(newImageIcon(img_FOCUSED));
        
        //imposto il puntatore del mouse appropriato per gli oggetti attivi su cui cliccare
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    }
    
    public MyButton(String imageDefault, String imagePressed, String imageDisabilitato,int x , int y, int larghezza, int lunghezza){
        super();
        
        this.imageDefault=imageDefault;
        this.imagePressed=imagePressed;
        this.imageDisabilitato=imageDisabilitato;
        
        //recupero le immagini ( cliccato e non cliccato) dalla stringa relativa passata
        /*this.imageiconDefault = Toolkit.getDefaultToolkit () .getImage(imageDefault);
        this.imageiconPressed  = Toolkit.getDefaultToolkit ().getImage(imagePressed);
        this.imageiconDisabilitato=Toolkit.getDefaultToolkit ().getImage(imageDisabilitato);*/
        
        //ridimensiono l'immagine per le dimensioni del buffer 
        final BufferedImage imgDefault = scaleImage(larghezza,lunghezza,imageDefault);
        //creo l'immagine da inserire per il pulsante di default
        this.imageiconDefault = (Image)imgDefault;
        
        //ridimensiono l'immagine per le dimensioni del buffer 
        final BufferedImage imgPressed = scaleImage(larghezza,lunghezza,imagePressed);
        //creo l'immagine da inserire per il pulsante di default
        this.imageiconPressed = (Image)imgPressed;
        
        //ridimensiono l'immagine per le dimensioni del buffer 
        final BufferedImage imgDisabilitato = scaleImage(larghezza,lunghezza,imageDisabilitato);
        //creo l'immagine da inserire per il pulsante di default
        this.imageiconDisabilitato = (Image)imgDisabilitato;
        
        //this.setPreferredSize(new Dimension(larghezza,lunghezza));
        
        //elimino il contenuto grafico ed il contorno di default del pulsante
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        
        //controllo se il pulsante deve essere abbilitato o disabbilitato e a seconda dei casi inserisco l'image del pulsante
        controllo();
        
        //imposto l'immagine visualizzata quando il mouse passa sopra il pulsante o quando esso è selezionato
        this.setRolloverEnabled(true);
        
        //imposto il puntatore del mouse appropriato per gli oggetti attivi su cui cliccare
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    }
    /* il metodo "setBounds" si occupa di posizionare e dimensionare un elemento grafico. Noi creiamo
     * qui una versione "facilitata" di questo metodo che dimensiona AUTOMATICAMENTE il pulsante  se-
     * condo le dimensioni dell'immagine, pertanto in ingresso riceve solo due coordinate          */
    
    public void setBounds(int x, int y)
    {
        super.setBounds(x,y,image.getWidth(this),image.getHeight(this));
    }
    //metodo utilizzato per gestire il pulsante nelle sue fasi (abbilitato e Disabbilitato)
    public void controllo()
    {
       if(this.disable==false){
             //imposto l'immagine di base
            this.setIcon(new ImageIcon(imageiconDefault));
     
            //imposto l'immagine visualizzata quando il mouse clicca sul pulsante
            this.setPressedIcon(new ImageIcon(imageiconPressed ));
            this.setEnabled(true);
        }else{
            this.setEnabled(false);
            //if(){}faccio il controllo per quando c'è il big button
            this.setDisabledIcon(new ImageIcon(imageiconDisabilitato));
            
        }
    }
    //metodo utilizzato per abbilitare il pulsante 
    public void setEnable()
    {
        this.disable=false;
        controllo();
    }
    //metodo utlizzato per disabbilitare il pulsante
    public void setDisable()
    {
        this.disable=true;
        controllo();
    }
    //metodo per recuperare la striga dell'img
    public String getLettera()
    {
        return this.lettera;
    }
    public String getDefaultmage()
    {
        return this.imageDefault;
    }
    
    //metodo per ridefinire la grandezza delle immagini
    public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        try {
            ImageIcon ii = new ImageIcon(filename);//path to image
            //con l'opzione BITMASK non modifica l'immagine di sfondo
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