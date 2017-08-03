
package burp;

import java.io.PrintWriter;
import rec_login.Rec_Login;

/**
 *
 * @author xyzt
 */
public class BurpExtender implements IBurpExtender{

    PrintWriter Sout;
    PrintWriter Serr;
    
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Sout = new PrintWriter(callbacks.getStdout(), true);
        
        Serr = new PrintWriter(callbacks.getStderr(), true);
        
        Rec_Login rec = new Rec_Login(callbacks, Sout);
        
        callbacks.registerHttpListener(rec);
        
        callbacks.addSuiteTab(rec);
        
        Sout.println("Estensione Ok");
    }
    
}
