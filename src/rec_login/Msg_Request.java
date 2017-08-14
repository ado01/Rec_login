
package rec_login;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import java.io.PrintWriter;

/**
 *
 * @author xyzt
 */
public class Msg_Request implements Runnable {

    private PrintWriter Sout; 
    private IBurpExtenderCallbacks callbacks;
    private IHttpRequestResponse req;
    private IExtensionHelpers helper;
    public IHttpRequestResponse lastresponse;
    public int flagLastReq;
    
    public Msg_Request(IBurpExtenderCallbacks callbacks, PrintWriter Sout, IHttpRequestResponse req, IHttpRequestResponse lastresponse,int flagLastReq){
        this.Sout = Sout;
        this.callbacks = callbacks;
        this.req = req;
        this.lastresponse = lastresponse;
        this.flagLastReq = flagLastReq;
    }
    
    @Override
    public void run() {
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
            //System.exit(0);
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    Sout.println("Generata un eccezione all'interno del thread");
                }
            });
        }
    }
    
}
