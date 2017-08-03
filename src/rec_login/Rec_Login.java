
package rec_login;

import burp.IBurpExtenderCallbacks;
import burp.ICookie;
import burp.IExtensionHelpers;
import burp.IHttpListener;
import burp.IHttpRequestResponse;
import burp.IParameter;
import burp.ITab;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author xyzt
 */
public class Rec_Login implements ITab, IHttpListener{

    private IBurpExtenderCallbacks callbacks;
    private String login;
    private int on_off = 0;
    private JList<String> listview;
    private DefaultListModel lmodel;
    private List<IHttpRequestResponse> action_login;
    private PrintWriter Sout;
    private IExtensionHelpers helper;
    private JLabel requestlabelview; 
    private JLabel responselabelview;
    private int select = 0;
    private IHttpRequestResponse lastresponse;
    private java.util.HashMap<String, String> hashcookie;
    private MyButton inizio_login = new MyButton("/Image/play.png","/Image/play_down.png","/Image/play_down.png",0,0);
    private MyButton fine_login = new MyButton("/Image/stop.png","/Image/stop_down.png","/Image/stop_down.png",0,0);
    private MyButton reset_login= new MyButton("/Image/reset.png","/Image/reset_down.png","/Image/reset_down.png",0,0);
    private JButton go = new JButton("go");
    private JButton buttonOnOff = new JButton("On");
    
    public Rec_Login(IBurpExtenderCallbacks callbacks, PrintWriter Sout) {
        
        this.Sout = Sout;
        this.callbacks = callbacks;
        this.login = "off";
        this.action_login = new ArrayList<IHttpRequestResponse>();
        this.helper = callbacks.getHelpers();
        this.hashcookie = new  java.util.HashMap<String, String>();
        
        go.setEnabled(false);
        fine_login.setEnabled(false);
        inizio_login.setEnabled(false);
    }

    @Override
    public String getTabCaption() {
        return "Rec Login";
    }

    @Override
    public Component getUiComponent() {
        
        JPanel Rec = new JPanel();
   
        GridBagConstraints lay = new GridBagConstraints();
        
        Rec.setLayout(new GridBagLayout());
        
        //JButton inizio_login = new JButton("inizio_login");
        inizio_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = "on";
                fine_login.setEnabled(true);
                go.setEnabled(false);
                Sout.println(login);
            }
        });
        lay.weightx = 0.5;
        lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 0;
        lay.gridy = 0;
        lay.insets = new Insets(10,10,10,10);
        Rec.add(inizio_login, lay);
        
        //JButton fine_login = new JButton("fine_login");
        fine_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = "off";
                go.setEnabled(true);
                inizio_login.setEnabled(true);
                Sout.println(login);
            }
        });
        lay.weightx = 0.5;
        lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 1;
        lay.gridy = 0;
        lay.insets = new Insets(10,10,10,10);
        Rec.add(fine_login, lay);
        
       //JButton reset_login = new JButton("reset_login");
        reset_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = "reset";
                lmodel.removeAllElements();
                action_login.clear();
                requestlabelview.setText("");
                responselabelview.setText("");
                hashcookie.clear();
                go.setEnabled(false);
                fine_login.setEnabled(false);
            }
        });
        lay.weightx = 0.5;
        lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 2;
        lay.gridy = 0;
        lay.insets = new Insets(10,10,10,10);
        Rec.add(reset_login, lay);
        
        lmodel = new DefaultListModel();
        listview = new JList(lmodel);
        listview.addListSelectionListener(new ListSelectionListener(){    
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    select = listview.getSelectedIndex();
                    String param = "";
                    action_login.get(select);
                    for(IParameter s: helper.analyzeRequest(action_login.get(select)).getParameters()){
                        param = param +"<span>"+ s.getName()+ "       :        " + helper.urlDecode(s.getValue())+"</span><br>";
                    }
                    requestlabelview.setText( "<html>"+ param + "</html>");
                    //responselabelview.setText(helper.bytesToString(action_login.get(select).getRequest()));
                    for(byte b:action_login.get(select).getRequest()){
                        Sout.write(Byte.toString(b));
                    }
                }   
            }

        });
        
        JScrollPane scroll = new JScrollPane(listview);
        scroll.setPreferredSize(new Dimension(800,200));
        listview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listview.setSelectedIndex(0);
        lay.weightx = 1.0;
        //lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 0;
        lay.gridy = 1;
        lay.insets = new Insets(10,10,10,10);  
        lay.gridheight = 3;
        lay.gridwidth = 3;
        Rec.add(scroll, lay);
        
        requestlabelview = new JLabel();
        requestlabelview.setVerticalAlignment(SwingConstants.NORTH);
        requestlabelview.setHorizontalAlignment(SwingConstants.LEFT);
        JScrollPane scrolllabelrequst = new JScrollPane(requestlabelview);
        scrolllabelrequst.setPreferredSize(new Dimension(200,300));
        lay.weightx = 0.5;
        lay.weighty = 0.5;
        //lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 0;
        lay.gridy = 4;
        lay.insets = new Insets(10,10,10,10);  
        lay.gridheight = 3;
        lay.gridwidth = 1;
        Rec.add(scrolllabelrequst, lay);
        
        responselabelview = new JLabel();
        responselabelview.setVerticalAlignment(SwingConstants.NORTH);
        responselabelview.setHorizontalAlignment(SwingConstants.LEFT);
        JScrollPane scrolllabelresponse = new JScrollPane(responselabelview);
        scrolllabelresponse.setPreferredSize(new Dimension(200,300));
        lay.weightx = 0.5;
        lay.weighty = 0.5;
        //lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 2;
        lay.gridy = 4;
        lay.insets = new Insets(10,10,10,10);  
        lay.gridheight = 3;
        lay.gridwidth = 1;
        Rec.add(scrolllabelresponse, lay);
        
        //MyButton go = new MyButton("/Image/play.png","/Image/play_down.png","",0,0);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                inizio_login.setEnabled(false);
                fine_login.setEnabled(false);
                reset_login.setEnabled(false);
                int islast=0;
                for(IHttpRequestResponse req: action_login){  
                    msg_request threq;
                    if(action_login.size() == islast ){
                        threq = new msg_request(callbacks, Sout, req, lastresponse,1);
                    }else{
                        threq = new msg_request(callbacks, Sout, req, lastresponse,0);
                        //islast++;
                    }
                    Thread t_last=new Thread(threq);
                    t_last.start();
                    try {
                        t_last.join();
                        lastresponse = threq.lastresponse;
                        if(threq.lastresponse.getResponse()!=null){
                            for(ICookie lc : helper.analyzeResponse(threq.lastresponse.getResponse()).getCookies()){
                                boolean trovato = false;
                                Iterator it = hashcookie.entrySet().iterator();
                                while(it.hasNext()){
                                    Map.Entry pair = (Map.Entry<String, String>) it.next();
                                    if(lc.getName().equals((String)pair.getKey())){
                                        pair.setValue(lc.getValue());
                                        trovato = true;
                                    }
                                }
                                if(!trovato){
                                    hashcookie.put(lc.getName(), lc.getValue());
                                }
                            }
                        }       
                    } catch (InterruptedException ex) {
                        Sout.println(Rec_Login.class.getName());
                    }       
                }
                //stampa per verifica
                String viewcookie = "";
                Iterator it = hashcookie.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry pair = (Map.Entry<String, String>) it.next();
                    viewcookie = viewcookie + "<span>" + pair.getKey() + ":" + pair.getValue() + "</span><br>";
                    Sout.println("-Chiave:"+pair.getKey()+" -Valore:"+pair.getValue());
                }
                responselabelview.setText("<html>"+viewcookie+"</html>");
                
                inizio_login.setEnabled(true);
                fine_login.setEnabled(true);
                reset_login.setEnabled(true);
                        
                Sout.println(hashcookie.size());
                Sout.println("fine login");
            }
        });
        lay.weightx = 0.5;
        lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 3;
        lay.gridy = 0;
        lay.insets = new Insets(10,10,10,10);
        Rec.add(go, lay);
        
        buttonOnOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(on_off==1){
                    on_off=0;
                    buttonOnOff.setText("On");
                    inizio_login.setEnabled(false);
                    fine_login.setEnabled(false);
                    reset_login.setEnabled(false);
                    go.setEnabled(false);
                }else{
                    on_off=1;
                    buttonOnOff.setText("Off");
                    inizio_login.setEnabled(true);
                }
            }
        });
        lay.weightx = 1;
        lay.fill = GridBagConstraints.HORIZONTAL;
        lay.gridx = 3;
        lay.gridy = 1;
        lay.insets = new Insets(10,10,10,10);
        Rec.add(buttonOnOff, lay);
 
        return Rec;
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
        if(on_off == 1){
            if (login.equals("on")) { 
            if(messageIsRequest){//è una richiesta
                action_login.add(messageInfo);
                lmodel.addElement(helper.analyzeRequest(messageInfo).getUrl().getHost()+helper.urlDecode(helper.analyzeRequest(messageInfo).getUrl().getFile()));
            }else{// è una risposta
                for(ICookie lc : helper.analyzeResponse(messageInfo.getResponse()).getCookies()){
                    boolean trovato = false;
                    Iterator it = hashcookie.entrySet().iterator();
                    while(it.hasNext()){
                        Map.Entry pair = (Map.Entry<String, String>) it.next();
                        Sout.println("Chiave:"+pair.getKey()+" Valore:"+pair.getValue());
                        if(lc.getName().equals((String)pair.getKey())){
                            pair.setValue(lc.getValue());
                            trovato = true;
                        }        
                    }
                    if(!trovato){
                        hashcookie.put(lc.getName(), lc.getValue());
                    }
                }
            }  
        } else if (login.equals("off")) {
            //TODO non sto registrando
            //action_login_response.add(messageInfo);
        }
        
        if(toolFlag == callbacks.TOOL_PROXY){
            Sout.println("----------------Provieni da PROXY-----------------");
            if(messageIsRequest){
                Iterator it = hashcookie.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry pair = (Map.Entry<String, String>) it.next();
                    boolean cookiePresente = false;
                    for(IParameter par: helper.analyzeRequest(messageInfo).getParameters()){
                        if(par.getName().equals(pair.getKey())){
                            IParameter newParam = helper.buildParameter((String)pair.getKey(),(String)pair.getValue(),IParameter.PARAM_COOKIE);
                            messageInfo.setRequest(helper.updateParameter(messageInfo.getRequest(), newParam));
                            cookiePresente = true; 
                        }
                    }
                    if(!cookiePresente){
                        IParameter newParam = helper.buildParameter((String)pair.getKey(), (String)pair.getValue(), IParameter.PARAM_COOKIE);      
                        messageInfo.setRequest(helper.addParameter(messageInfo.getRequest(), newParam));
                    }
                }
                //stampa per verifica
                for(IParameter par: helper.analyzeRequest(messageInfo).getParameters()){
                    Sout.println("Nome Parametro:"+par.getName()+"Valore Parametro: "+par.getValue());
                }
            }
            }else if(toolFlag == callbacks.TOOL_REPEATER){
                Sout.println("----------------Provieni da REPEATER--------------");
            }else if(toolFlag == callbacks.TOOL_SCANNER && messageIsRequest){
                Sout.println("----------------Provieni da SCANNER---------------");
                if(messageIsRequest){
                    Iterator it = hashcookie.entrySet().iterator();
                    while(it.hasNext()){
                        Map.Entry pair = (Map.Entry<String, String>) it.next();
                        boolean cookiePresente = false;
                        for(IParameter par: helper.analyzeRequest(messageInfo).getParameters()){
                            if(par.getName().equals(pair.getKey())){
                                IParameter newParam = helper.buildParameter((String)pair.getKey(),(String)pair.getValue(),IParameter.PARAM_COOKIE);
                                messageInfo.setRequest(helper.updateParameter(messageInfo.getRequest(), newParam));
                                cookiePresente = true; 
                                //Sout.println("Chiave:"+pair.getKey()+" Valore hash:"+pair.getValue()+"     Valore richiesta:"+par.getValue());
                            }
                        }
                        if(!cookiePresente){
                            IParameter newParam = helper.buildParameter((String)pair.getValue(), (String)pair.getKey(), IParameter.PARAM_COOKIE);
                            messageInfo.setRequest(helper.addParameter(messageInfo.getRequest(), newParam));
                        }
                    }
                    //stampa per verifica
                    for(IParameter par: helper.analyzeRequest(messageInfo).getParameters()){
                        Sout.println("Nome Parametro:"+par.getName()+"Valore Parametro: "+par.getValue());
                    }
                }
            }else if(toolFlag == callbacks.TOOL_INTRUDER){
                Sout.println("----------------Provieni da INTRUDER--------------");
            }
        }else{
            //TODO in caso in cui l'estenzione è spenta (posso anche non fare nulla)
        }
    }
}
