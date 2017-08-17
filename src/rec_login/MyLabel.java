/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rec_login;

import javax.swing.JLabel;

/**
 *
 * @author xyzt
 */
public class MyLabel extends JLabel implements MyLabelListener {

    private Rec_Login hadeler;
    
    public MyLabel(Rec_Login handler){
        super();
        this.hadeler = hadeler;
    }
    
    @Override
    public void actionLabel(RequestWorker sorgente, String messaggio) {
        this.setText(messaggio);
    }
    
}
