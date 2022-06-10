package ldap.learn;

import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
//import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
//import javax.naming.ldap.InitialLdapContext;
import com.genexus.*;
//import com.unboundid.ldap.sdk.LDAPConnection; 

public class App {

	private DirContext connection;
	
	private String ldapTrustStore; // = "/security/ldapscert.p12";
	private String ldapTrustStorePass; // = "123456*";
	private String ldapURL; // = "ldaps://adpinfrw01.tufi.local:636";
	private String ldapUsr; // = "ldap_service";
	private String ldapPass; // = "********";
	private String ldapProtocol; // = "ssl";
	
	public String getLdapTrustStore() {
		return ldapTrustStore;
	}

	public void setLdapTrustStore(String ldapTrustStore) {
		this.ldapTrustStore = ldapTrustStore.trim();
	}

	public String getLdapTrustStorePass() {
		return ldapTrustStorePass;
	}

	public void setLdapTrustStorePass(String ldapTrustStorePass) {
		this.ldapTrustStorePass = ldapTrustStorePass.trim();
	}

	public String getLdapURL() {
		return ldapURL;
	}

	public void setLdapURL(String ldapURL) {
		this.ldapURL = ldapURL.trim();
	}

	public String getLdapUsr() {
		return ldapUsr;
	}

	public void setLdapUsr(String ldapUsr) {
		this.ldapUsr = ldapUsr.trim();
	}

	public String getLdapPass() {
		return ldapPass;
	}

	public void setLdapPass(String ldapPass) {
		this.ldapPass = ldapPass.trim();
	}

	public String getLdapProtocol() {
		return ldapProtocol;
	}

	public void setLdapProtocol(String ldapProtocol) {
		this.ldapProtocol = ldapProtocol.trim();
	}


	
	public App(int remoteHandle, ModelContext context){}

	public App(){}
	
	public void execute(String[] param)
    {
          //System.out.println("I am in the external class" + param[0]);
    }
	
	
	/* create connection during object creation */
	private String newConnection() {
		//System.setProperty("javax.net.ssl.trustStore","/security/ldapscert.p12");
		//System.setProperty("javax.net.ssl.trustStorePassword","123456*");
		System.setProperty("javax.net.ssl.trustStore", ldapTrustStore);
		System.setProperty("javax.net.ssl.trustStorePassword", ldapTrustStorePass);
		//System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, "ldaps://10.0.51.20:636");
		//env.put(Context.PROVIDER_URL, "ldaps://adpinfrw01.tufi.local:636");
		//env.put(Context.SECURITY_PROTOCOL, "ssl");
		env.put(Context.PROVIDER_URL, ldapURL);
		env.put(Context.SECURITY_PROTOCOL, ldapProtocol);
		//env.put(Context.SECURITY_PRINCIPAL, "CN=Servicios LDAP,OU=Usuarios_Servicios,OU=Tu Financiera,DC=tufi,DC=local");
		//env.put(Context.SECURITY_PRINCIPAL, "ldap_service@tufi.local");
		//env.put(Context.SECURITY_PRINCIPAL, "TUFI\\ldap_service");
		//env.put(Context.SECURITY_CREDENTIALS, "*******");
		env.put(Context.SECURITY_PRINCIPAL, "TUFI\\"+ ldapUsr);
		env.put(Context.SECURITY_CREDENTIALS, ldapPass);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		//System.out.println("Usuario: " + ldapUsr +" - Pass: "+ ldapPass);
		//System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
		try {
			connection = new InitialDirContext(env);
			//connection = new InitialLdapContext(env, null);
			System.err.println("Conectado - " + connection);
			return "SIN_ERROR";
		} catch (AuthenticationException ex) {
			System.out.println("newConnection - AuthenticationException: "+ ex.getMessage());
			//ex.printStackTrace();
			return "01 - AuthenticationException: "+ ex.getMessage();
		} catch (NamingException en) {
			// TODO Auto-generated catch block
			System.out.println("newConnection - NamingException: "+ en.getMessage());
			//en.printStackTrace();
			return "02 - NamingException: "+ en.getMessage(); // +" - '"+ ldapTrustStore +"/"+ ldapTrustStorePass +"'" ; // +" - File.exists() = "+ Files.exists(ldapTrustStore) ;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("newConnection - Exception: "+ e.getMessage());
			//e.printStackTrace();
			return "03 Exception: "+ e.getMessage();
		}
	}
	
	//public void newLdapConection(String username, String password) {
	//	 try {
	//		 LDAPConnection ldap = new LDAPConnection();
	//	     return true;
	//	    } catch (LDAPException e) {
	//	        if (e.getResultCode() == ResultCode.INVALID_CREDENTIALS)
	//	            return false;
	//	        throw e;
	//	    }
	//}

	//public void getAllUsers() throws NamingException {
	//	String searchFilter = "(objectClass=inetOrgPerson)";
	//	String[] reqAtt = { "cn", "sn" };
	//	SearchControls controls = new SearchControls();
	//	controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	//	controls.setReturningAttributes(reqAtt);
	//
	//	NamingEnumeration users = connection.search("OU=Tu Financiera,DC=tufi,DC=local", searchFilter, controls);
	//
	//	SearchResult result = null;
	//	while (users.hasMore()) {
	//		result = (SearchResult) users.next();
	//		Attributes attr = result.getAttributes();
	//		//String name = attr.get("cn").get(0).toString();
	//		//deleteUserFromGroup(name,"Administrators");
	//		System.out.println(attr.get("cn"));
	//		System.out.println(attr.get("sn"));
	//	}
	//
	//}

	//public void addUser() {
	//	Attributes attributes = new BasicAttributes();
	//	Attribute attribute = new BasicAttribute("objectClass");
	//	attribute.add("inetOrgPerson");
	//
	//	attributes.put(attribute);
	//	// user details
	//	attributes.put("sn", "Ricky");
	//	try {
	//		connection.createSubcontext("cn=Tommy,ou=users,ou=system", attributes);
	//		System.out.println("success");
	//	} catch (NamingException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//
	//}

	//public void addUserToGroup(String username, String groupName)
	//{
	//	ModificationItem[] mods = new ModificationItem[1];
	//	Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
	//	mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
	//	try {
	//		connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
	//		System.out.println("success");
	//	} catch (NamingException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//	
	//}
	
	//public void deleteUser()
	//{
	//	try {
	//		connection.destroySubcontext("cn=Tommy,ou=users,ou=system");
	//		System.out.println("success");
	//	} catch (NamingException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//}
	
	//public void deleteUserFromGroup(String username, String groupName)
	//{
	//	ModificationItem[] mods = new ModificationItem[1];
	//	Attribute attribute = new BasicAttribute("uniqueMember","cn="+username+",ou=users,ou=system");
	//	mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
	//	try {
	//		connection.modifyAttributes("cn="+groupName+",ou=groups,ou=system", mods);
	//		System.out.println("success");
	//	} catch (NamingException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
	//	
	//}

	//public void searchUsers() throws NamingException {
	//	String searchFilter = "(sAMAccountName=pruebas_ad)"; //  for one user
	//	//String searchFilter = "(&(uid=1)(cn=Smith))"; // and condition 
	//	//String searchFilter = "(|(uid=1)(uid=2)(cn=Smith))"; // or condition
	//	String[] reqAtt = { "cn", "sn", "uid", "distinguishedName" };
	//	SearchControls controls = new SearchControls();
	//	controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	//	controls.setReturningAttributes(reqAtt);
	//
	//	NamingEnumeration users = connection.search("OU=Tu Financiera,DC=tufi,DC=local", searchFilter, controls);
	//
	//	SearchResult result = null;
	//	while (users.hasMore()) {
	//		result = (SearchResult) users.next();
	//		Attributes attr = result.getAttributes();
	//		String name = attr.get("cn").get(0).toString();
	//		//deleteUserFromGroup(name,"Administrators");
	//		System.out.println(attr.get("cn"));
	//		System.out.println(attr.get("sn"));
	//		System.out.println(attr.get("distinguishedName"));
	//		System.out.println(attr.get("uid"));
	//	}
	//
	//}
	
	/* use this to authenticate any existing user */
	//public static boolean authUser(String username, String password)
	//{
	//	try {
	//		Properties env = new Properties();
	//		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	//		env.put(Context.PROVIDER_URL, "ldaps://10.0.51.20:636");
	//		env.put(Context.SECURITY_AUTHENTICATION, "simple");
	//		//env.put(Context.SECURITY_AUTHENTICATION, "EXTERNAL");
	//		env.put(Context.SECURITY_PROTOCOL, "ssl");
	//		//env.put(Context.SECURITY_PRINCIPAL, "CN="+username+",OU=Testing,OU=Tu%20Financiera,DC=tufi,DC=local");  //check the DN correctly
	//		env.put(Context.SECURITY_PRINCIPAL, "CN="+username+",CN=Usuario%20Pruebas%20AD,OU=Testing,OU=Tu%20Financiera,DC=tufi,DC=local");  //check the DN correctly
	//		//env.put(Context.SECURITY_PRINCIPAL, username+"@10.0.51.20");
	//		env.put(Context.SECURITY_CREDENTIALS, password);
	//		//env.put(Context.SECURITY_AUTHENTICATION, "Credential" ); // "Login");
	//		//env.put(Context.SECURITY_AUTHENTICATION, "Login");
	//		
	//		//env.put("USE_SERVICE_NAME", "true");
	//		//Context ic = new InitialContext(env);
	//		
	//		DirContext con = new InitialDirContext(env);
	//
	//		System.out.println("success");
	//		con.close();
	//		return true;
	//	}catch (AuthenticationException ex) {
	//		System.out.println("authUser AuthenticationException: "+ ex.getMessage());
	//		ex.printStackTrace();
	//		return false;
	//	} catch (NamingException en) {
	//		// TODO Auto-generated catch block
	//		System.out.println("authUser NamingException: "+ en.getMessage());
	//		///System.out.println(""+ LdapCtxFactory.class.getName())
	//		en.printStackTrace();
	//		return false;
	//	}catch (Exception e) {
	//		System.out.println("failed: "+e.getMessage());
	//		return false;
	//	}
	//}
	
	
	private String searchUser(String username) throws NamingException {
		try {
			if(username == "") {
				System.out.println("searchUser -  1 - Debe especificar el nombre de usuario");
				return "Debe especificar el nombre de usuario";
			}
			String searchFilter = "(sAMAccountName="+ username +")"; //  for one user
			//String searchFilter = "(&(uid=1)(cn=Smith))"; // and condition 
			//String searchFilter = "(|(uid=1)(uid=2)(cn=Smith))"; // or condition
			//String[] reqAtt = { "cn", "sn", "uid", "distinguishedName" };
			String[] reqAtt = { "distinguishedName" };
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			controls.setReturningAttributes(reqAtt);
			
			System.out.println("searchUser -  1.1");
	
			NamingEnumeration users = connection.search("OU=Tu Financiera,DC=tufi,DC=local", searchFilter, controls);
	
			System.out.println("searchUser -  1.2 users: " + connection);
			
			SearchResult result = null;
			while (users.hasMore()) {
				result = (SearchResult) users.next();
				Attributes attr = result.getAttributes();
				//String name = attr.get("cn").get(0).toString();
				//deleteUserFromGroup(name,"Administrators");
				//System.out.println(attr.get("cn"));
				//System.out.println(attr.get("sn"));
				//System.out.println(attr.get("distinguishedName"));
				//System.out.println(attr.get("uid"));
				return attr.get("distinguishedName").toString();
			}
			System.out.println("searchUser -  2 - Usuario no encontrado");
			return "Usuario no encontrado";
		}catch(InvalidSearchFilterException e){
			System.out.println("searchUser -  3 - "+ e.toString());
			return "searchUser - Error: "+ e.toString();
		}catch(InvalidSearchControlsException e){
			System.out.println("searchUser -  4 - "+ e.toString());
			return "searchUser - Error: "+ e.toString();
		}catch(NamingException e){
			System.out.println("searchUser -  5 - "+ e.toString());
			return "searchUser - Error: "+ e.toString();
		}catch(Exception e) {
			System.out.println("searchUser -  6 Exception - "+ e.toString());
			return "searchUser - Error: "+ e.toString();
		}
	}
	
	/* use this to update user password */
	private String updateUserPassword(String username, String password, String tipoUsuario) {
		try {
			String distinguishedName = searchUser(username);
			distinguishedName = distinguishedName.substring(19);
			String newQuotedPassword = "\""+ password +"\"";
			byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
			//System.out.println("updateUserPassword: '"+ distinguishedName +"'");
			//String dnBase=",CN=Usuario Pruebas AD,OU=Testing,OU=Tu Financiera,DC=tufi,DC=local";
			//System.out.println("getAttributes: "+ connection.getAttributes("CN=dsantacruzidtk").toString());
			ModificationItem[] mods= new ModificationItem[1];
			//mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));// if you want, then you can delete the old password and after that you can replace with new password
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));
			connection.modifyAttributes(distinguishedName, mods);//try to form DN dynamically
			System.out.println("success - tipoUsuario: "+ tipoUsuario);
			if(tipoUsuario == "L") {
				mods= new ModificationItem[1];
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("pwdLastSet", "0"));// if you want, then you can delete the old password and after that you can replace with new password 
				connection.modifyAttributes(distinguishedName, mods);//try to form DN dynamically
				System.out.println("success - tipoUsuario: "+ tipoUsuario);
			}
			connection.close();
			return "SIN_ERROR";
		}catch (Exception e) {
			System.out.println("ERROR "+e.getMessage());
			return "ERROR "+e.getMessage();
		}
	}
	
	
	private String unlockUser(String username) {
		try {
			String distinguishedName = searchUser(username);
			System.out.println("unlockUser: distinguishedName = "+ distinguishedName);
			distinguishedName = distinguishedName.substring(19);
			//String newQuotedPassword = "\""+ password +"\"";
			//byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
			//System.out.println("updateUserPassword: '"+ distinguishedName +"'");
			//String dnBase=",CN=Usuario Pruebas AD,OU=Testing,OU=Tu Financiera,DC=tufi,DC=local";
			//System.out.println("getAttributes: "+ connection.getAttributes("CN=dsantacruzidtk").toString());
			ModificationItem[] mods= new ModificationItem[1];
			//mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));// if you want, then you can delete the old password and after that you can replace with new password
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("lockoutTime", "0"));// if you want, then you can delete the old password and after that you can replace with new password 
			connection.modifyAttributes(distinguishedName, mods);//try to form DN dynamically
			System.err.println("Unlock success");
			connection.close();
			return "SIN_ERROR";
		}catch (Exception e) {
			System.out.println("fallido: "+e.getMessage());
			return "ERROR "+e.getMessage();
		}
	}
	
	private String userDN(String username) {
		try {
			String distinguishedName = searchUser(username);
			System.out.println("userDN: distinguishedName = "+ distinguishedName);
			//System.out.println("userDN success");
			connection.close();
			return distinguishedName;
		}catch (Exception e) {
			System.out.println("fallido: "+e.getMessage());
			return "ERROR "+e.getMessage();
		}
	}
	
	
	//public static void main(String[] args) throws NamingException {
	
		//App app = new App();
		//app.newConnection();
		// app.addUser();
		// app.getAllUsers();
		// app.deleteUser();
		// app.searchUsers();
		 
		//System.out.println(authUser("ldap_service", "7uf1.AD.2"));
		//app.updateUserPassword("pruebas_ad", "123456.a");
		//app.updateUserPassword("alvillalba", "123456.a", "R");
		//app.unlockUser("pruebas_ad");
		//try {
			//String usuario = "pruebas_ad";
			//String respuesta;
			//try {
			//	respuesta = searchUser(usuario);
			//} catch (NamingException e) {
			//	// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			//System.out.println("buscarUsuario: "+ respuesta);
		//} catch (Exception e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//this.reseteoClaveR();
		//}
	//		
	//}
	
	/**/
	public String reseteoClave(String username, String password) {
		App app = new App();
		app.newConnection();
		String tipoUsuario = "L";
		System.out.println("reseteoClave - tipoUsuario: "+ tipoUsuario);
		return app.updateUserPassword(username, password, tipoUsuario);
	}
	
	/**/
	public String reseteoClaveR(String username, String password) {
		App app = new App();
		app.newConnection();
		String tipoUsuario = "R";
		System.out.println("reseteoClaveR - tipoUsuario: "+ tipoUsuario);
		return app.updateUserPassword(username, password, tipoUsuario);
	}
	
	/**/
	public String desbloqueoUsr(String username) {
		App app = new App();
		app.newConnection();
		return app.unlockUser(username);
	}
	
	/**/
	public String obtenerDN(String username) {
		App app = new App();
		app.newConnection();
		String dn = app.userDN(username);
		dn = dn.replace("distinguishedName: ", "");
		return dn;
	}
	
	/**/
	public String reseteoClave(String username, String password, String trustStore, String trustStorePass, String URL, String Usr, String Pass, String Protocol) {
		App app = new App();
		app.setLdapTrustStore(trustStore);
		app.setLdapTrustStorePass(trustStorePass);
		app.setLdapURL(URL);
		app.setLdapUsr(Usr);
		app.setLdapPass(Pass);
		app.setLdapProtocol(Protocol);
		System.out.println("reseteoClave");
		String errCon = app.newConnection();
		if (!errCon.endsWith("SIN_ERROR")) {
			return "ERROR de coneccion " + errCon;
		}
		String tipoUsuario = "L";
		System.out.println("reseteoClave - tipoUsuario: "+ tipoUsuario);
		return app.updateUserPassword(username, password, tipoUsuario);
		//return reseteoClave(username, password);
	}
	
	/**/
	public String reseteoClaveR(String username, String password, String trustStore, String trustStorePass, String URL, String Usr, String Pass, String Protocol) {
		App app = new App();
		app.setLdapTrustStore(trustStore);
		app.setLdapTrustStorePass(trustStorePass);
		app.setLdapURL(URL);
		app.setLdapUsr(Usr);
		app.setLdapPass(Pass);
		app.setLdapProtocol(Protocol);
		System.out.println("reseteoClaveR");
		String errCon = app.newConnection();
		if (!errCon.endsWith("SIN_ERROR")) {
			return "ERROR de coneccion " + errCon;
		}
		String tipoUsuario = "R";
		System.out.println("reseteoClaveR - tipoUsuario: "+ tipoUsuario);
		return app.updateUserPassword(username, password, tipoUsuario);
		//return reseteoClaveR(username, password);
	}
	
	/**/
	public String desbloqueoUsr(String username, String trustStore, String trustStorePass, String URL, String Usr, String Pass, String Protocol) {
		App app = new App();
		app.setLdapTrustStore(trustStore);
		app.setLdapTrustStorePass(trustStorePass);
		app.setLdapURL(URL);
		app.setLdapUsr(Usr);
		app.setLdapPass(Pass);
		app.setLdapProtocol(Protocol);
		String errCon = app.newConnection();
		if (!errCon.endsWith("SIN_ERROR")) {
			return "ERROR de coneccion " + errCon;
		}
		return app.unlockUser(username);
		//return desbloqueoUsr(username);
	}
	
	/**/
	public String obtenerDN(String username, String trustStore, String trustStorePass, String URL, String Usr, String Pass, String Protocol) {
		App app = new App();
		app.setLdapTrustStore(trustStore);
		app.setLdapTrustStorePass(trustStorePass);
		app.setLdapURL(URL);
		app.setLdapUsr(Usr);
		app.setLdapPass(Pass);
		app.setLdapProtocol(Protocol);
		String errCon = app.newConnection();
		if (!errCon.endsWith("SIN_ERROR")) {
			return "ERROR de coneccion " + errCon;
		}
		String dn = app.userDN(username);
		dn = dn.replace("distinguishedName: ", "");
		return dn;
		//return obtenerDN(username);
	}
}
