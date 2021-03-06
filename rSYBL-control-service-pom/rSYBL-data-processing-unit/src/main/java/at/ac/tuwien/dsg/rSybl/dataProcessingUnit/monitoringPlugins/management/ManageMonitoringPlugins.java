
/** 
   Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup E184.                 This work was partially supported by the European Commission in terms of the CELAR FP7 project (FP7-ICT-2011-8 #317790).

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package at.ac.tuwien.dsg.rSybl.dataProcessingUnit.monitoringPlugins.management;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import at.ac.tuwien.dsg.csdg.Node;
import at.ac.tuwien.dsg.rSybl.dataProcessingUnit.monitoringPlugins.interfaces.MonitoringInterface;



public class ManageMonitoringPlugins {
	private File directory=null;
	public ManageMonitoringPlugins(){
		    URL folderURL = this.getClass().getClassLoader().getResource("pluginsMonitoring");
		    String folderPath = folderURL.getPath();
		   // File directory = new File(folderPath); 
			try {
				directory = new File(folderURL.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		    
		    try {
				ClasspathUtils.addDirToClasspath(directory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private  Collection<Class<?>> getAllClasses() throws IOException
    {
   
		 Collection<Class<?>> classes = new ArrayList<Class<?>>();

	 if (directory.exists())
    {
         File[] files = directory.listFiles();
for (File f:files){
    JarFile jar = new JarFile(f.getAbsolutePath());
    for (Enumeration<JarEntry> entries = jar.entries() ; entries.hasMoreElements() ;)
    {
        JarEntry entry = entries.nextElement();
        String file = entry.getName();
     
        if (file.endsWith(".class"))
        {
        	String classname = file.replace('/', '.').substring(0, file.length() - 6);
            
                Class<?> c =null;
				try {
					c = Class.forName(classname);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                classes.add(c);
            
            
        }
    }}}

//    for (Class<?> c : classes)
//        System.out.println(c);
    return classes;

    }
	public  Collection<MonitoringInterface> getAllPlugins(Node cloudService){
	    Collection<Class<?>> classes = null;
	    Collection<MonitoringInterface> plugins = new ArrayList<MonitoringInterface>();

		try {
			classes = getAllClasses();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(Class<?> clazz : classes)
		{
			for (Class<?>c:clazz.getInterfaces()){
	        System.out.println(c+" " +MonitoringInterface.class);

		    if(c== MonitoringInterface.class) //Need something in this if statement
		    {
		        try {
					plugins.add((MonitoringInterface) Class.forName(clazz.getName()).newInstance());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					try {
						Constructor<?> cons = clazz.getConstructor(Node.class);
						plugins.add((MonitoringInterface) cons.newInstance(cloudService));
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		    }
			}
		}
		return plugins;
	}
	public Map<Method,MonitoringInterface> getMethods(Collection<MonitoringInterface> plugins){
		Map<Method,MonitoringInterface> methods = new HashMap<Method,MonitoringInterface>();
		for (MonitoringInterface plugin : plugins){
			Method [] meths=plugin.getClass().getMethods();
			for (Method m : meths){
				methods.put(m, plugin);
			}
		}
		return methods;
	}
}
