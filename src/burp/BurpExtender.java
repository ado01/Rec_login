
package burp;

import java.io.PrintWriter;
import javax.swing.SwingUtilities;
import rec_login.Rec_Login;

/**
 *
 * @author xyzt
 */
public class BurpExtender implements IBurpExtender{

    private PrintWriter Sout;
    private PrintWriter Serr;
    private Rec_Login rec;
    
    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        
        Sout = new PrintWriter(callbacks.getStdout(), true);
        Serr = new PrintWriter(callbacks.getStderr(), true);
        
        SwingUtilities.invokeLater(new Runnable (){
            @Override
            public void run() {
                rec = new Rec_Login(callbacks, Sout);
                callbacks.registerHttpListener(rec);
                callbacks.addSuiteTab(rec);
            }     
        }); 
        Sout.println("Estensione Ok");
    } 
}
