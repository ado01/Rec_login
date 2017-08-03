
package rec_login;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import java.io.PrintWriter;

/**
 *
 * @author xyzt
 */
public class msg_request implements Runnable {

    private PrintWriter Sout; 
    private IBurpExtenderCallbacks callbacks;
    private IHttpRequestResponse req;
    private IExtensionHelpers helper;
    public IHttpRequestResponse lastresponse;
    public int flagLastReq;
    
    public msg_request(IBurpExtenderCallbacks callbacks, PrintWriter Sout, IHttpRequestResponse req, IHttpRequestResponse lastresponse,int flagLastReq){
        this.Sout = Sout;
        this.callbacks = callbacks;
        this.req = req;
        this.lastresponse = lastresponse;
        this.flagLastReq = flagLastReq;
    }
    
    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try{
            if(flagLastReq == 0){
                lastresponse = callbacks.makeHttpRequest(req.getHttpService(), req.getRequest());
                //Sout.println(helper.bytesToString(response.getResponse()));
            }else {
                lastresponse = callbacks.makeHttpRequest(req.getHttpService(), req.getRequest());
                Sout.println("ULTIMA RICHIESTA");
            }
        }catch(Exception e){
            Sout.println("Generata un eccezione all'interno del thread");
            System.exit(0);
        }
    }
    
}
