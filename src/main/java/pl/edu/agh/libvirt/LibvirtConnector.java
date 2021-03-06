package pl.edu.agh.libvirt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.libvirt.Connect;
import org.libvirt.ConnectAuth;
import org.libvirt.ConnectAuthDefault;
import org.libvirt.Domain;
import org.libvirt.LibvirtException;
import org.libvirt.NodeInfo;
import org.springframework.jmx.support.ConnectorServerFactoryBean;

import com.sun.jna.*;

public class LibvirtConnector {
	
    ConnectAuth ca;
    Connect conn;
	
	public LibvirtConnector(String address){
	  ca = new ConnectAuthDefault();
	  try {
		conn = new Connect("qemu+tcp://"+address+"/system", ca, 0);
		} catch (LibvirtException e) {
			e.printStackTrace();
		}
	}
	
	public void printProperties(){
		try{
	      NodeInfo ni = conn.nodeInfo();
	      	 	 
	      System.out.println("model: " + ni.model + " mem(kb):" + ni.memory);
	 
	      int numOfVMs = conn.numOfDomains();
	      
	      System.out.println(numOfVMs);
	      
	      for(int i : conn.listDomains())
	      {
	        Domain vm = conn.domainLookupByID(i);
	        System.out.println("vm name: " + vm.getName() + "  type: " + vm.getOSType()
		    	+ " max mem: " + vm.getMaxMemory() + " max cpu: " + vm.getMaxVcpus());
	      }
  
		}
		catch(LibvirtException le)
		{
			le.printStackTrace();
		}
	}
	
	public Map<String,String> serverInfo(){
		Map<String,String> info = new HashMap<String,String>();
		try {
			NodeInfo ni = conn.nodeInfo();
			info.put("Hostname", conn.getHostName() + " [" + ni.model+"]");
			info.put("Cores", Integer.toString(ni.cores));
			info.put("Memory", Long.toString(ni.memory));
			info.put("Running / defined domains", Integer.toString(conn.numOfDomains()) + "/" + Integer.toString(conn.numOfDefinedDomains()));
			
		} catch (LibvirtException e) {
			e.printStackTrace();
		}
		return info;

	}
	
	public void startVm(Domain domain){
        try {
			domain.create();
			
		} catch (LibvirtException e) {
			e.printStackTrace();
		}

	}
	
	public void stopVm(Domain domain){
		try {
			domain.destroy();
		} catch (LibvirtException e) {
			e.printStackTrace();
		}
	}
	
	public Domain domainFromName(String name){
		try {
			return conn.domainLookupByName(name);
		} catch (LibvirtException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Domain domainFromID(int id){
		try {
			return conn.domainLookupByID(id);
		} catch (LibvirtException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] getDefinedDomains(){
		try {
			return conn.listDefinedDomains();
		} catch (LibvirtException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Domain> getAllDomains(){
		List<Domain> d = new ArrayList<Domain>();
	      try {
			for(int i : conn.listDomains())
			  {
			    d.add(conn.domainLookupByID(i));
			   
			  }
		} catch (LibvirtException e) {
			e.printStackTrace();
		}
	    return d;
	}
	

	
	public String getDomainXML(Domain d){
		try {
		
			return d.getXMLDesc(0);
		} catch (LibvirtException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String [] args){
		LibvirtConnector c = new LibvirtConnector("192.168.56.101");
		c.printProperties();
		
		String[] domains = c.getDefinedDomains();
		for (String d : domains){
			System.out.println(d);
			System.out.println(c.getDomainXML(c.domainFromName(d)));
			c.startVm(c.domainFromName(d));
		}
		
	}


	
	

}
