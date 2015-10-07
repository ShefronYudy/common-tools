package org.shefron.fc.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.Descriptor;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

public class AppMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//ø… π”√Spring IOC
		FileReplicator documentReplicator = new FileReplicatorImpl();
		documentReplicator.setSrcDir("");
		documentReplicator.setDestDir("");
		documentReplicator.setFileCopier(new FileCopierImpl());
		
		try{
			MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName objectName = new ObjectName("bean:name=documentReplicator");
			
			RequiredModelMBean mbean = new RequiredModelMBean();
			mbean.setManagedResource(documentReplicator, "objectReference");
			
			Descriptor srcDirDescriptor = new DescriptorSupport(new String[]{"name=SrcDir","descriptorType=attribute","getMethod=getSrcDir","setMethod=setSrcDir"});
			ModelMBeanAttributeInfo srcDirInfo = new ModelMBeanAttributeInfo("SrcDir","java.lang.String","Source directory",true,true,false,srcDirDescriptor);
			
			Descriptor destDirDescriptor = new DescriptorSupport(new String[]{"name=DestDir","descriptorType=attribute","getMethod=getDestDir","setMethod=setDestDir"});
			ModelMBeanAttributeInfo destDirInfo = new ModelMBeanAttributeInfo("DestDir","java.lang.String","Destination directory",true,true,false,destDirDescriptor);
			
			ModelMBeanOperationInfo getSrcDirInfo = new ModelMBeanOperationInfo("Get Source directory",FileReplicator.class.getMethod("getSrcDir"));
			ModelMBeanOperationInfo setSrcDirInfo = new ModelMBeanOperationInfo("Set Source directory",FileReplicator.class.getMethod("setSrcDir",String.class));
			
			ModelMBeanOperationInfo getDestDirInfo = new ModelMBeanOperationInfo("Get Destination directory",FileReplicator.class.getMethod("getDestDir"));
			ModelMBeanOperationInfo setDestDirInfo = new ModelMBeanOperationInfo("Set Destination directory",FileReplicator.class.getMethod("setDestDir",String.class));
			
			ModelMBeanOperationInfo replicateInfo = new ModelMBeanOperationInfo("replicate files",FileReplicator.class.getMethod("replicate"));
			
			ModelMBeanInfo mbeanInfo = new ModelMBeanInfoSupport("FileReplicator","File replicator",new ModelMBeanAttributeInfo[]{srcDirInfo,destDirInfo},null,
					new ModelMBeanOperationInfo[]{getSrcDirInfo,setSrcDirInfo,getDestDirInfo,setDestDirInfo,replicateInfo},null);
			
			mbean.setModelMBeanInfo(mbeanInfo);
			mbeanServer.registerMBean(mbean, objectName);

		}catch(Exception e){
			
		}finally{
			
		}
		
		System.in.read();
		
		
	}

}
