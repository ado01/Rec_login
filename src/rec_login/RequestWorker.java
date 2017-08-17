
package rec_login;

import burp.IBurpExtenderCallbacks;
import burp.ICookie;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author xyzt
 */
public class RequestWorker extends SwingWorker<HashMap<String,String>,Void>{
    
    private IBurpExtenderCallbacks callbacks;
    private PrintWriter Sout;
    private List<IHttpRequestResponse> action_login;
    private HashMap hashcookie;
    private HashMap<String, String> temphashcookie;
    private JPanel panel;
    private JLabel responselabelview;
    private List<MyLabelListener> listener;
    private List<MyProgressListener> listenerProg;
    private int progressivo = 0;
    private String messaggio = "";
    public IHttpRequestResponse lastresponse;
    public IExtensionHelpers helper;
        
    public RequestWorker(IBurpExtenderCallbacks callbacks, PrintWriter Sout, List<IHttpRequestResponse> action_login, HashMap hashcookie, JPanel panel, JLabel responselabelview){
        this.callbacks = callbacks;
        this.Sout = Sout;
        this.action_login = action_login;
        this.hashcookie = hashcookie;
        this.panel = panel;
        this.helper = callbacks.getHelpers();
        this.listener = new ArrayList<MyLabelListener>();
        this.listenerProg = new ArrayList<MyProgressListener>();
    }
    
    public void addLabelEnvListener(MyLabelListener envLabelListener){
        this.listener.add(envLabelListener);
    }
    
    public void notifyLabel(){
        for(MyLabelListener list:listener){
            list.actionLabel(this, messaggio);
        }
    }
    
    public void addProgressEvnListener(MyProgressListener envProgressListener){
        this.listenerProg.add(envProgressListener);
    }
    
    public void notifyProgress(){
        for(MyProgressListener listProg: listenerProg){
            listProg.actionProgress(this, progressivo);
        }
    }
    
    @Override
    protected HashMap<String, String> doInBackground() throws Exception {
        temphashcookie = new HashMap<String, String>();
        int islast=0;
        int num_request=0;
        for(IHttpRequestResponse req: action_login){  
            Msg_Request threq;
            if(action_login.size() == islast ){
                threq = new Msg_Request(callbacks, Sout, req, lastresponse,1);
            }else{
                threq = new Msg_Request(callbacks, Sout, req, lastresponse,0);
            }
            num_request++;
            Thread t_last=new Thread(threq);
            t_last.start();
            try {
                t_last.join();
                progressivo = (num_request*100)/(action_login.size());
                notifyProgress();
                lastresponse = threq.lastresponse;
                if(threq.lastresponse.getResponse()!=null){
                    for(ICookie lc : helper.analyzeResponse(threq.lastresponse.getResponse()).getCookies()){
                        boolean trovato = false;
                        Iterator it = temphashcookie.entrySet().iterator();
                        while(it.hasNext()){
                            Map.Entry pair = (Map.Entry<String, String>) it.next();
                            if(lc.getName().equals((String)pair.getKey())){
                                pair.setValue(lc.getValue());
                                trovato = true;
                            }
                        }
                        if(!trovato){
                            temphashcookie.put(lc.getName(), lc.getValue());
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Sout.println(Rec_Login.class.getName());
            }       
        }
        return temphashcookie;
    }
    
    @Override
    protected void done(){
        try{
            this.hashcookie.clear();
            hashcookie.putAll(temphashcookie);
            //stampa per verifica
            String viewcookie = "";
            Iterator it = temphashcookie.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pair = (Map.Entry<String, String>) it.next();
                viewcookie = viewcookie + "<span>" + pair.getKey() + ":" + pair.getValue() + "</span><br>";
                Sout.println("-Chiave:"+pair.getKey()+" -Valore:"+pair.getValue());
            }
            //responselabelview.setText("<html>"+viewcookie+"</html>");
            messaggio = "<html>"+viewcookie+"</html>";
            //notifico l'evento a tutti gli ascoltatori
            notifyLabel();
            Sout.println("lunghezza hashmap cookie"+hashcookie.size());
            JOptionPane.showMessageDialog(panel, "Operazione di login terminata");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
