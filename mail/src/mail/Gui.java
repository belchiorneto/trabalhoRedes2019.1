package mail;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Gui extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6672594319721820776L;
	TitledBorder brd = new TitledBorder("Inbox");
	TitledBorder brd1 = new TitledBorder("Mensagem");
	JFrame jfrm;
	JPanel topPanel;
	JToolBar tb;
	JPanel lp;
	JPanel rp;
	JPanel irp;
	JPanel Inboxirp;
	
	JButton btnListar;
	
	JList maillist;
	DefaultListModel lmodel;
	
	JMenuBar mnbr;
	
	JLabel frm;
	JLabel to;
	JLabel pw;
	JLabel sub;
	JTextArea msg;
	
	JLabel port;
	JLabel url;
	JLabel pass;
	JLabel login;
	JTextField  POPport;
	JTextField  POPurl;
	JPasswordField POPpass;
	JTextField  POPlogin;
	
	public Gui()
	{
		
        jfrm = new JFrame("Mail Client");
        tb = new JToolBar();
		mnbr = new JMenuBar();
		maillist = new JList();
		lmodel = new DefaultListModel();
		maillist.setModel(lmodel);
		maillist.setBounds(10,40, 200,500);  
		
		JSplitPane sp = new JSplitPane();
		
		lp = new JPanel(null);
		lp.add(maillist);
		rp = new JPanel(null);
		topPanel = new JPanel(null);
		
		sp.setTopComponent(topPanel);
		sp.setLeftComponent(lp);
		sp.setRightComponent(rp);
		sp.setDividerLocation(200);
		
		
		btnListar = new JButton("Listar");
		btnListar.addActionListener(this);
		
		lp.add(btnListar);
		btnListar.setBounds(10,10,100,30);
		
		
		jfrm.add(sp);
		jfrm.setJMenuBar(mnbr);
		jfrm.setSize(900,600);
		jfrm.setVisible(true);
		jfrm.add(tb, BorderLayout.NORTH);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		maillist.addListSelectionListener(new ListSelectionListener() {

	        @Override
	        public void valueChanged(ListSelectionEvent evt) {
	            carregaEmal(evt);
	        }
	    });
		
	}
	public void top() {
		
		port = new JLabel("Porta: ");
		port.setBounds(15,15,50,50);
		POPport = new JTextField (10);
		POPport.setText("110");
		url = new JLabel("URL: ");
		url.setBounds(65,15,50,50);
		POPurl = new JTextField (30);
		POPurl.setText("mail.loja.pro.br");
		pass = new JLabel("senha: ");
		pass.setBounds(120,15,50,50);
		POPpass = new JPasswordField(10);
		login = new JLabel("login: ");
		login.setBounds(15,180,50,50);
		POPlogin = new JTextField (20);
		POPlogin.setText("redes2019.1@loja.pro.br");
		
		tb.add(port);
		tb.add(POPport);
        tb.add(url);
        tb.add(POPurl);
        tb.add(login);
        tb.add(POPlogin);
        tb.add(pass);
        tb.add(POPpass);
        
        tb.setFloatable(false);
		
	}
	public void RightPanel()
	{
		irp = new JPanel(null);
		rp.add(irp,BorderLayout.CENTER);
		brd1.setTitleJustification(TitledBorder.CENTER);
	    brd1.setTitlePosition(TitledBorder.TOP);
	    irp.setBorder(brd1);
		irp.setBounds(10,10,700,500);
		irp.setVisible(false);
		
		frm = new JLabel("");
		frm.setBounds(15,15,400,50);
		to = new JLabel("");
		to.setBounds(15,50,400,50);
		sub = new JLabel("");
		sub.setBounds(15,85,400,50);
		msg = new JTextArea("");
		msg.setBounds(15,130,400,400);
		
		
		irp.add(frm);
		irp.add(to);
		irp.add(sub);
		irp.add(msg);
		
		
		brd.setTitleJustification(TitledBorder.CENTER);
	    brd.setTitlePosition(TitledBorder.TOP);
		Inboxirp = new JPanel();
		Inboxirp.setBorder(brd);
		rp.add(Inboxirp,BorderLayout.EAST);
		Inboxirp.setBounds(850,10,850,700);
		Inboxirp.setVisible(false);
		
	}
	
	public void carregaEmal(javax.swing.event.ListSelectionEvent evt) {
		MailClient mailclient;
		try {
			mailclient = new MailClient(Integer.parseInt(POPport.getText()), POPurl.getText(), POPpass.getText(), POPlogin.getText());
			mailclient.connect();
			System.out.println(maillist.getSelectedIndex());
			mailclient.getMessage(maillist.getSelectedIndex()+1);
			Email email = mailclient.getEmail();
			frm.setText("De: " + email.getOrigem());
			to.setText("Para: " + email.getDestino());
			sub.setText("Assunto: " + email.getSubject());
			msg.setText("Body: " + email.getBody());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		irp.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnListar)
		{
			irp.setVisible(true);
			
		}
		if(e.getSource()==btnListar)
		{
			
			MailClient mailclient;
			try {
				mailclient = new MailClient(Integer.parseInt(POPport.getText()), POPurl.getText(), POPpass.getText(), POPlogin.getText());
				mailclient.connect();
				int qtEmail = mailclient.getcount();
				for(int i = 1; i <= qtEmail; i++) {
					lmodel.addElement("Email " + i);
				}
				System.out.print(lmodel);
				maillist.setModel(lmodel);
			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
			
		
	}

}
