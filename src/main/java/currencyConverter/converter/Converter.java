package currencyConverter.converter;
 
import java.awt.*; 
import java.awt.event.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser; 
public class Converter { 
 public static String from;
 public static String to;
 public static double amount;
 public static String result;
    public static void converter() 
    { 
  
        // Creating a new frame using Frame 
        Frame f = new Frame("CONVERTER"); 
  
        // Creating two labels 
        Label l1, l2,l3,l4; 
 
        TextField t1, t2,t3,t4; 
  
        // Creating buttons 
        Button b1,b2; 
  
        // Naming the labels and setting 
        // the bounds for the labels 
        l1 = new Label("From Currency of"); 
        l1.setBounds(70,150,100,50); 
        l2 = new Label("To Currency of"); 
        l2.setBounds(270,150, 100, 50); 
        l3 = new Label("Result");
        l3.setBounds(70,300,250,30);
        l4 = new Label("Amount");
        l4.setBounds(100,100,50,30);
  
        // Initializing the text fields with  
        // bounds for the text fields 
        t1 = new TextField(); 
        t1.setBounds(180, 150, 50, 30); 
        t2 = new TextField(); 
        t2.setBounds(370, 150, 50, 30); 
        t3 = new TextField();
        t3.setBounds(150,300,150,30);
        t4 = new TextField(); 
        t4.setBounds(180, 100,150, 30);
  
        // Creating a button  
        // and setting the bounds 
        b1 = new Button("Calculate"); 
        b1.setBounds(200, 250, 100, 20); 
        
        b2 = new Button("Close");
        b2.setBounds(350, 350, 100, 20);
  
        // Adding action listener 
        b1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                from=t1.getText();
                to=t2.getText();
                amount=Double.parseDouble(t4.getText());
                sendHttpsGETRequest(from,to,amount);
                t3.setText(result);
            } 
        }); 
  
        // Adding action listener 
      b2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) 
            { 
                f.dispose(); 
            } 
        }); 
  
        // Default method for closing the frame 
        f.addWindowListener(new WindowAdapter() { 
            public void windowClosing(WindowEvent e) 
            { 
                System.exit(0); 
            } 
        }); 
  
        // Adding the created objects 
        // to the form 
        f.add(l1); 
        f.add(t1); 
        f.add(l2); 
        f.add(t2);
        f.add(t3);
        f.add(l3);
        f.add(l4);
        f.add(t4);
        f.add(b1); 
        f.add(b2);
  
        f.setLayout(null); 
        f.setSize(500, 500);
        f.setVisible(true);
     
    } 

	protected static void sendHttpsGETRequest(String from2, String to2, double amount2) {

   	 try {
   		 String httpurl ="https://api.frankfurter.app/latest?amount="+amount+"&from="+from+"&to="+to;
   			URL url = new URL(httpurl);
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("GET");
               conn.connect();

               //Getting the response code
               int responsecode = conn.getResponseCode();

               if (responsecode != 200) {
                   throw new RuntimeException("HttpResponseCode: " + responsecode);
               } else {

                   String inline = "";
                   Scanner scanner = new Scanner(url.openStream());

                   //Write all the JSON data into a string using a scanner
                   while (scanner.hasNext()) {
                       inline += scanner.nextLine();
                   }

                   //Close the scanner
                   scanner.close();

                   //Using the JSON simple library parse the string into a json object
                   JSONParser parse = new JSONParser();
                   JSONObject data_obj = (JSONObject) parse.parse(inline);

                   //Get the required object from the above created object
                   JSONObject obj = (JSONObject) data_obj.get("rates");
                   result=obj.get(to).toString();
                  
               }

           } catch (Exception e) {
               e.printStackTrace();
           }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
converter();
}
}