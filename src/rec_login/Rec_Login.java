/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rec_login;

import burp.IBurpExtenderCallbacks;
import burp.ICookie;
import burp.IExtensionHelpers;
import burp.IHttpListener;
import burp.IHttpRequestResponse;
import burp.IParameter;
import burp.IScanIssue;
import burp.IScannerCheck;
import burp.IScannerInsertionPoint;
import burp.ITab;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author caluisi
 */
public class Rec_Login implements ITab, IHttpListener{

    private IBurpExtenderCallbacks callbacks;
    private String login;
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
    private MyButton inizio_login;
    private MyButton fine_login;
    private MyButton reset_login;
    private JButton go;
    
    public Rec_Login(IBurpExtenderCallbacks callbacks, PrintWriter Sout) {
        
        this.Sout = Sout;
        this.callbacks = callbacks;
        this.login = "off";
        this.action_login = new ArrayList<IHttpRequestResponse>();
        this.helper = callbacks.getHelpers();
        this.hashcookie = new  java.util.HashMap<String, String>();
        
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
        
        inizio_login = new MyButton("/Image/play.png","/Image/play_down.png","",0,0);
        //JButton inizio_login = new JButton("inizio_login");
        inizio_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = "on";
                Sout.println(login);
            }
        });
        lay.weightx = 0.5;
        lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 0;
        lay.gridy = 0;
        lay.insets = new Insets(10,10,10,10);
        Rec.add(inizio_login, lay);
        
        fine_login = new MyButton("/Image/stop.png","/Image/stop_down.png","",0,0);
        //JButton fine_login = new JButton("fine_login");
        fine_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = "off";
            }
        });
        lay.weightx = 0.5;
        lay.fill = GridBagConstraints.BOTH;
        lay.gridx = 1;
        lay.gridy = 0;
        lay.insets = new Insets(10,10,10,10);
        Rec.add(fine_login, lay);
        
        reset_login = new MyButton("/Image/reset.png","/Image/reset_down.png","",0,0);
        //JButton reset_login = new JButton("reset_login");
        reset_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = "reset";
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
        go = new JButton("go");
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
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
                        lastresponse = threq.lastrequest;
                        if(threq.lastrequest.getResponse()!=null){
                            for(ICookie lc : helper.analyzeResponse(threq.lastrequest.getResponse()).getCookies()){
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
 
        return Rec;
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
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
            /*Sout.println(helper.analyzeResponse(lastrequest.getRequest()).getHeaders().get(1));
            List<ICookie> cookie = helper.analyzeResponse(lastrequest.getResponse()).getCookies();
            List<String> header = helper.analyzeResponse(lastrequest.getResponse()).getHeaders();
            for(String h: header){
                Sout.println("VALUE : "+ h);
            }
            Sout.println(hashcookie.size());
            for(ICookie c: cookie){
                Sout.println("NAME :"+c.getName()+" VALORE:"+ c.getValue());
            }*/
            
            if(messageIsRequest){
                Iterator it = hashcookie.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry pair = (Map.Entry<String, String>) it.next();
                    boolean cookiePresente = false;
                    for(IParameter par: helper.analyzeRequest(messageInfo).getParameters()){
                        if(par.getName().equals(pair.getKey())){
                            IParameter newParam = helper.buildParameter((String)pair.getKey(),(String)pair.getValue(),IParameter.PARAM_COOKIE);
                            helper.updateParameter(messageInfo.getRequest(), newParam);
                            cookiePresente = true; 
                            //Sout.println("Chiave:"+pair.getKey()+" Valore hash:"+pair.getValue()+"     Valore richiesta:"+par.getValue());
                        }
                    }
                    if(!cookiePresente){
                        IParameter newParam = helper.buildParameter((String)pair.getValue(), (String)pair.getKey(), IParameter.PARAM_COOKIE);
                        helper.addParameter(messageInfo.getRequest(), newParam);
                    }
                    //it.remove();
                }
                //stampa per verifica
                for(IParameter par: helper.analyzeRequest(messageInfo).getParameters()){
                    Sout.println("Nome Parametro:"+par.getName()+"Valore Parametro: "+par.getValue());
                }
            }
            
        }else if(toolFlag == callbacks.TOOL_INTRUDER){
            Sout.println("----------------Provieni da INTRUDER--------------");
        }
        //Sout.println(lastrequest.getHttpService().getHost());
    }
}
