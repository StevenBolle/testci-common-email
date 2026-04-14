package org.apache.commons.mail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

public class EmailConcrete extends Email {

	@Override
	public Email setMsg(String msg) throws EmailException {
		this.content = msg;
		this.contentType = "text/plain";
		return this;
	}
	
// todo: test each of the following methods ###################################################
	
// todo: Email addBcc(String... email)*********************************************************
	
	// method write-up (all similar across addBcc)
	
	// WILL THROW EmailException if invalid email address given as any of its arguments
	// getter method retrieves a list
	
	// /**
    //  * Add a blind BCC recipient to the email. The email
    //  * address will also be used as the personal name.
    //  * The name will be encoded by the charset of {@link #setCharset(java.lang.String) setCharset()}.
    //  * If it is not set, it will be encoded using
    //  * the Java platform's default charset (UTF-16) if it contains
    //  * non-ASCII characters; otherwise, it is used as is.
    //  *
    //  * @param email A String.
    //  * @return An Email.
    //  * @throws EmailException Indicates an invalid email address
    //  * @since 1.0
    //  */
	
	/*
	 * public Email addBcc(String email) throws EmailException { return
	 * this.addBcc(email, null); }
	 * 
	 * public Email addBcc(String... emails) throws EmailException { if (emails ==
	 * null || emails.length == 0) { throw new
	 * EmailException("Address List provided was invalid"); }
	 * 
	 * for (String email : emails) { addBcc(email, null); }
	 * 
	 * return this; }
	 * 
	 * public Email addBcc(String email, String name) throws EmailException { return
	 * addBcc(email, name, this.charset); }
	 * 
	 * public Email addBcc(String email, String name, String charset) throws
	 * EmailException { this.bccList.add(createInternetAddress(email, name,
	 * charset)); return this; }
	 */
	
	// public List<InternetAddress> getBccAddresses()
    // {
    //     return this.bccList;
    // }


// todo: Email addCc(String email) ------------------------------------------------------------
	
		// at top of email class we find: 
		//** List of "cc" email addresses. */
		// protected List<InternetAddress> ccList = new ArrayList<InternetAddress>();
		
		// There is a getter method for ccAddresses
		// * Get the list of "CC" addresses.
	    // *
	    // * @return List addresses
	    // */
	    // public List<InternetAddress> getCcAddresses()
	    // {
	    //     return this.ccList;
	    // }


// todo: void    addHeader(String name, String value) --------------------------------------------
		
		// /**  DECLARATION from top of Email class
	
     	// * Used to specify the mail headers.  Example:
        // *
     	// * X-Mailer: Sendmail, X-Priority: 1( highest )
     	// * or  2( high ) 3( normal ) 4( low ) and 5( lowest )
     	// * Disposition-Notification-To: user@domain.net
     	//*/
        // protected Map<String, String> headers = new HashMap<String, String>();
		
		// /** --- METHOD DECLARATION
	    // * Adds a header ( name, value ) to the headers Map.
	    // *
	    // * @param name A String with the name.
	    // * @param value A String with the value.
	    // * @since 1.0
	    // * @throws IllegalArgumentException if either {@code name} or {@code value} is null or empty
	    // */
	    // public void addHeader(String name, String value)
	    // {
	    //    if (EmailUtils.isEmpty(name))
	    //    {
	    //        throw new IllegalArgumentException("name can not be null or empty");
	    //    }
	    //    if (EmailUtils.isEmpty(value))
	    //    {
	    //        throw new IllegalArgumentException("value can not be null or empty");
	    //    }
	
	    //    this.headers.put(name, value);
	    // }
		
		// /** --- HEADER SETTER 
	    //  * Used to specify the mail headers.  Example:
	    //  *
	    //  * X-Mailer: Sendmail, X-Priority: 1( highest )
	    //  * or  2( high ) 3( normal ) 4( low ) and 5( lowest )
	    //  * Disposition-Notification-To: user@domain.net
	    //  *
	    //  * @param map A Map.
	    //  * @throws IllegalArgumentException if either of the provided header / value is null or empty
	    //  * @since 1.0
	    //  */
	    // public void setHeaders(Map<String, String> map)
	    // {
	    //     this.headers.clear();
		//
	    //     for (Map.Entry<String, String> entry : map.entrySet())
	    //     {
	    //         addHeader(entry.getKey(), entry.getValue());
	    //     }
	    // }
	    
	    
// todo: Email   addReplyTo(String email, String name) ---------------------------------------------
	
	// simplified notes: EmailException Indicates an invalid email address
	// expects a list returned List<InternetAddress>
	
// todo: void    buildMimeMessage() - Done (71%) ---------------------------------------------------
	
	// passing condition requires all fields of a message to be populated
	// failure conditions exist when any one field is missing
	// has a getter method, getMimeMessage. good for assert test.
	
	
// todo: String  getHostName() ---------------------------------------------------------------------
	
	// interacts with setHostName, checkSessionAlreadyInitialized
	// retrieves HostName from SMTP uses EmailConstants.MAIL_HOST, original method
	// has deprecated method chain.
	// must check hostname, null for hostname, authenticator not equal to null,
	// must check SSL, TLS, bounce address != null, socketTimeout
	
	
// todo: Session getMailSession() ------------------------------------------------------------------
	
	// throws exception when hostname not set. object returned is a session. is a session created
	// is it stored and is it returned. 
	// mocking not an option with jdk 17
	
// todo: Date    getSentDate() ---------------------------------------------------------------------
	
	// a Date object checks and stores the time to the nearest millisecond, 
	// this method retrieves that time stamp.
	// uses internal java getDate() method.
	// also pairs with setSentDate method()
	
// todo: int     getSocketConnectionTimeout() ------------------------------------------------------
	
	// has a setter function, setter function checks whether checkSessionAlreadyInitialized
	// will need to test a default value and setter
	// default value found in EmailConstants.java
	//  public static final int SOCKET_TIMEOUT_MS = 60000;
	
// todo: Email   setFrom(String email) -------------------------------------------------------------
	// simplified: test for valid email address or throws Exception
	// has getter method that returns the from address

}
