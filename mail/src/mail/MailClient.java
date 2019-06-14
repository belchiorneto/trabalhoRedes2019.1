package mail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MailClient {
	private int POP3Port = 110;
	private String POP3url = "";
	private String mailAddress = "";
	private String mailPass = "";
	
	private Socket client;
	private InputStream is;
	private BufferedReader sockin;
	private OutputStream os;
	private PrintWriter sockout;
	private String reply;
	private String cmd;
	private Email email;
    
	public MailClient(int port, String url, String pass, String login) throws Exception {
		this.POP3Port = port;
		this.POP3url = url;
		this.mailAddress = login;
		this.mailPass = pass;
		
		client = new Socket(POP3url, POP3Port);
		is = client.getInputStream();
		sockin = new BufferedReader(new InputStreamReader(is));
		os = client.getOutputStream();
		sockout = new PrintWriter(os, true);
		reply = sockin.readLine();
		email = new Email();
	}
	
	public Email getEmail() {
		return email;
	}
	public void connect() throws Exception{
		cmd = "user ";
	    sockout.println(cmd + mailAddress);
	    reply = sockin.readLine();
	    cmd = "pass ";
	    sockout.println(cmd + mailPass);
	}
	public int getcount() throws Exception {
		int emails = 0;
		cmd = "stat";
	    sockout.println(cmd);
	    reply = sockin.readLine();
	    cmd = "stat";
	    sockout.println(cmd);
	    reply = sockin.readLine();
	    String qtMail[] = reply.split(" ");
	    emails = Integer.parseInt(qtMail[1]);
	 	return emails;
	}
	
	public void getMessage(int n)  throws Exception{
		String emailcompleto = "";
		String body = "";
		String origem = "";
		String destino = "";
		String subject = "";
		
		cmd = "retr " + n;
	    sockout.println(cmd);
	    reply = sockin.readLine();
	    
	    do {
	    	reply = sockin.readLine();
	    	emailcompleto += reply + "\n";
	    	String from[] = reply.split("From: ");
	    	if(from.length > 1) {
	    		origem = from[1];
	    	}
	    	
	    	String to[] = reply.split("To: ");
	    	if(to.length > 1) {
	    		destino = to[1];
	    	}
	    	String sub[] = reply.split("Subject: ");
	    	if(sub.length > 1) {
	    		subject = sub[1];
	    	}
	    	if(reply.length() > 0) {
	    		if (reply.charAt(0) == '.') {
	    			break;
	    		}
	    	}
	    }while (reply != ".");
	    
	     
	    
	    String boundary = "";
	    String getBoundary[] = emailcompleto.split("boundary=\"");
	    boundary = getBoundary[1].split("\"")[0];
	    String parteEmail[] = emailcompleto.split(boundary);
	    
	    body = parteEmail[3];
	    body.replace("Content-Type: text/html; charset=\"UTF-8\"", "<html>");
	    email.setId(n);
	    email.setDestino(destino);
	    email.setSubject(subject);
	    email.setBody(body);
	    email.setOrigem(origem);
	}
	
	
	public void closeConnection() throws Exception {
		String cmd = "quit";
	    sockout.println(cmd);
	    client.close();
	}

	public int getPOP3Port() {
		return POP3Port;
	}

	public void setPOP3Port(int pOP3Port) {
		POP3Port = pOP3Port;
	}

	public String getPOP3url() {
		return POP3url;
	}

	public void setPOP3url(String pOP3url) {
		POP3url = pOP3url;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMailPass() {
		return mailPass;
	}

	public void setMailPass(String mailPass) {
		this.mailPass = mailPass;
	}

}
