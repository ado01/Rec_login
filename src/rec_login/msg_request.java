/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rec_login;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import java.io.PrintWriter;

/**
 *
 * @author caluisi
 */
public class msg_request implements Runnable {

    PrintWriter Sout; 
    IBurpExtenderCallbacks callbacks;
    IHttpRequestResponse req;
    IExtensionHelpers helper;
    IHttpRequestResponse lastrequest;
    int flagLastReq;
    
    public msg_request(IBurpExtenderCallbacks callbacks, PrintWriter Sout, IHttpRequestResponse req, IHttpRequestResponse lastrequest,int flagLastReq){
        this.Sout = Sout;
        this.callbacks = callbacks;
        this.req = req;
        this.lastrequest = lastrequest;
        this.flagLastReq = flagLastReq;
    }
    
    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try{
            if(flagLastReq == 0){
                lastrequest = callbacks.makeHttpRequest(req.getHttpService(), req.getRequest());
                //Sout.println(helper.bytesToString(response.getResponse()));
            }else {
                lastrequest = callbacks.makeHttpRequest(req.getHttpService(), req.getRequest());
                Sout.println("ULTIMA RICHIESTA");
            }
        }catch(Exception e){
            Sout.println("Generata un eccezione all'interno del thread");
            System.exit(0);
        }
    }
    
}
