/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rec_login;

import javax.swing.JProgressBar;

/**
 *
 * @author xyzt
 */
public class MyProgressBar extends JProgressBar implements MyProgressListener{

    public MyProgressBar(){
        super();
    }
    
    @Override
    public void actionProgress(RequestWorker sorgente, int progressivo) {
       setValue(progressivo);
    }
    
}
