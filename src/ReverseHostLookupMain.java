import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class ReverseHostLookupMain extends JFrame implements ActionListener {
	
	JPanel p1;
    JButton b1;
    JLabel l1;
    final JTextArea result;
    final JTextField host;
    
	public ReverseHostLookupMain() {
        
	       setTitle("Reverse Host Lookup Tool");
	       setSize(300, 300);
	       setLocationRelativeTo(null);
	       setDefaultCloseOperation(EXIT_ON_CLOSE); 
	       setResizable(false);
	       
	       JPanel panel = new JPanel();
	       getContentPane().add(panel);
	      /// panel.setLayout(new GridBagLayout());

	       
	       JButton scanButton = new JButton("Lookup");
	       scanButton.setBounds(200, 30, 80, 30);
	       
	       l1= new JLabel("Host");
	       host = new JTextField(15);
	       result = new JTextArea(10, 25);
	       result.setEditable(false);
	       
	       result.setBackground(Color.black);
	       result.setForeground(Color.GREEN);
	       
	       JScrollPane scroll = new JScrollPane (result, 
		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

			
	       JButton about = new JButton("About");
	       about.setBounds(230,30,80,30);
	       
	       scanButton.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent event) {
	    	        
	    	    	getInfo(host.getText());
	    	    	
	    	    }
	    	});
	       
	       about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Powered by AkhlD\nCodeBreach.in for more stuffs!", "Reverse Host Lookup Tool ", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	       
	       panel.add(l1);
	       panel.add(host);
	       panel.add(scanButton);
	       panel.add(scroll);
	       panel.add(about);
	      
                               
           
	    }
	
	private static String url ="http://domains.yougetsignal.com/domains.php";

	
	public static void main(String[] arg){
		
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ReverseHostLookupMain ex = new ReverseHostLookupMain();
                ex.setVisible(true);
            }
        });
		
		//String host="codebreach.in";
		
		//getInfo(host);
		
	}

	private  void getInfo(String shost) {
		
		try{
			
			/*Configure Proxy if needed
			 * 
			System.setProperty("http.proxyHost", "212.7.4.201");
			System.setProperty("http.proxyPort", "3128");
			
			*/
			
			Connection.Response res = Jsoup.connect(url)
				    .data("remoteAddress", shost)
				    .method(Method.POST)
				    .execute();

				Document doc = res.parse();
				
				//System.out.println(doc.text());
				
				String json = doc.text();
				
				JSONParser parser = new JSONParser();
				
				Object obj = parser.parse(json);
				JSONObject jsonObject = (JSONObject) obj;
				
				JSONArray msg = (JSONArray) jsonObject.get("domainArray");
				
				//System.out.println(jsonObject.get("remoteIpAddress"));

			//	System.out.println(msg);
				
				result.setText("");
				
				result.setText(host.getText() + " is hosted on :" + jsonObject.get("remoteIpAddress") + "\n");
				
				for(Object row : msg.toArray()){
					
					//System.out.println(row.toString().split("\"")[1]);
					result.append("\n" + row.toString().split("\"")[1]);
					
				}
		}catch(Exception e){ System.out.println("Error :" + e); }
		
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
