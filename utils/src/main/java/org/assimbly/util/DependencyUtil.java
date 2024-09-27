package org.assimbly.util;

import dev.jeka.core.api.depmanagement.*;
import dev.jeka.core.api.depmanagement.resolution.*;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

//import static dev.jeka.core.api.depmanagement.JkJavaDepScopes.*;

public class DependencyUtil {

	public List<Path> resolveDependency(String groupId, String artifactId, String version) throws Exception {
    	
        String dependency = groupId + ":" + artifactId + ":" + version;
        
        JkDependencySet deps = JkDependencySet.of()
                .and(dependency);

        JkDependencyResolver resolver = JkDependencyResolver.of().addRepos(JkRepo.ofMavenCentral());

        List<Path> paths = resolver.resolve(deps).getFiles().getEntries();

        return paths;

	}

    public List<Class> loadDependency(List<Path> paths) throws Exception {

        List<Class> classes = new ArrayList<>();

        for(Path path: paths){

            URL url = path.toUri().toURL();

            AccessController.doPrivileged((PrivilegedAction) () -> {

                URLClassLoader child = new URLClassLoader(new URL[] {url}, this.getClass().getClassLoader());

                ArrayList<String> classNames = null;

                try {
                    classNames = getClassNamesFromJar(path.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                for (String className : classNames) {
                    Class classToLoad = null;

                    try {
                        classToLoad = Class.forName(className, true, child);
                        classes.add(classToLoad);
                    } catch(NoClassDefFoundError e) {
                        //ignore
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return classes;
            });

        }

        return classes;

    }

    private boolean classExists(String moduleName, String className) {
        Optional<Module> module = ModuleLayer.boot().findModule(moduleName);
        if(module.isPresent()) {
            return Class.forName(module.get(), className) != null;
        }
        return false;
    }

    /**
     * Checks if a class can be found with the classloader.
     * @param name the fully qualified name of the class in question.
     * @return true if the class can be found, otherwise false.
     */
    private boolean classExists(String name) {
        int lastDot = name.lastIndexOf('.');
        String moduleName = name.substring(0, lastDot);
        String className = name.substring(lastDot+1);
        return classExists(moduleName, className);
    }


    // Returns an arraylist of class names in a JarInputStream
    private ArrayList<String> getClassNamesFromJar(JarInputStream jarFile) throws Exception {
        ArrayList<String> classNames = new ArrayList<>();
        try {
            //JarInputStream jarFile = new JarInputStream(jarFileStream);
            JarEntry jar;

            //Iterate through the contents of the jar file
            while (true) {
                jar = jarFile.getNextJarEntry();
                if (jar == null) {
                    break;
                }
                //Pick file that has the extension of .class
                if ((jar.getName().endsWith(".class"))) {
                    String className = jar.getName().replaceAll("/", "\\.");
                    String myClass = className.substring(0, className.lastIndexOf('.'));
                    classNames.add(myClass);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error while getting class names from jar", e);
        }
        return classNames;
    }

    // Returns an arraylist of class names in a JarInputStream
    // Calls the above function by converting the jar path to a stream
    private ArrayList<String> getClassNamesFromJar(String jarPath) throws Exception {
        return getClassNamesFromJar(new JarInputStream(new FileInputStream(jarPath)));
    }

    //These components are part of baseComponentsModule
    public enum CompiledDependency {

        AGGREGATE("aggregate"),
        ALERIS("aleris"),
        AMAZON("amazon"),
        AMAZONMQ("amazonmq"),
        AMQP("amqp"),
        AMQPS("amqps"),
        ARCHIVE("archive"),
        BASE64TOTEXT("base64totext"),
        BEAN("bean"),
        CONTROLBUS("controlbus"),
        CSVTOXML("csvtoxml"),
        DATAFORMAT("dataformat"),
        DELAY("delay"),
        DOCCONVERTER("docconverter"),
        EDIFACTDOTWEB("edifact-dotweb"),
        EDIFACTSTANDARDS("edifact-standards"),
        EDIFACTSTANDARDSTOXML("edifactstandardstoxml"),
        EDIFACTTOXML("edifacttoxml"),
        EDITOXML("editoxml"),
        ELASTICSEARCHREST("elasticsearch-rest"),
        ENCODER("encoder"),
        ENRICH("enrich"),
        EXCELTOXML("exceltoxml"),
        FILE("file"),
        FILTER("filter"),
        FLV("flv"),
        FMUTA("fmuta"),
        FORM2XML("form2xml"),
        FORMTOXML("formtoxml"),
        GOOGLEDRIVE("googledrive"),
        GROOVY("groovy"),
        HTTP("http"),
        HTTPS("https"),
        IBMMQ("ibmmq"),
        IMAP("imap"),
        IMAPS("imaps"),
        JETTYNOSSL("jetty-nossl"),
        JSONTOXML("jsontoxml"),
        LOG("log"),
        MLLP("mllp"),
        MULTIPART("multipart"),
        NETTY("netty"),
        NETTYHTTP("netty-http"),
        ORIFLAME("oriflame"),
        PDFTOTEXT("pdftotext"),
        POLLENRICH("pollenrich"),
        PRINT("print"),
        QUEUETHROTTLE("queuethrottle"),
        REMOVECOOKIE("removecookie"),
        REMOVEHEADERS("removeheaders"),
        REPLACE("replace"),
        REPLACEINPDF("replaceinpdf"),
        SANDBOX("sandbox"),
        SERVLET("servlet"),
        SETBODY("setbody"),
        SETCOOKIE("setcookie"),
        SETHEADER("setheader"),
        SETHEADERS("setheaders"),
        SETMESSAGE("setmessage"),
        SETOAUTH2TOKEN("setoauth2token"),
        SETPATTERN("setpattern"),
        SETPROPERTY("setproperty"),
        SETUUID("setuuid"),
        SIMPLEREPLACE("simplereplace"),
        SONICMQ("sonicmq"),
        SQLCUSTOM("sql-custom"),
        TEXTTOBASE64("texttobase64"),
        THROTTLE("throttle"),
        UNIVOCITYCSV("univocity-csv"),
        UNZIP("unzip"),
        WASTEBIN("wastebin"),
        WIRETAP("wiretap"),
        XMLTOCSV("xmltocsv"),
        XMLTOEDI("xmltoedi"),
        XMLTOEDIFACT("xmltoedifact"),
        XMLTOEDIFACTSTANDARDS("xmltoedifactstandards"),
        XMLTOEXCEL("xmltoexcel"),
        XMLTOJSON("xmltojson"),
        XMLTOJSONLEGACY("xmltojsonlegacy"),
        XSLTSAXON("xslt-saxon"),
        ZIP("zip"),
        ;

        private static Map<String, CompiledDependency> BY_LABEL = new HashMap<>();

        static {
            for (CompiledDependency cd : values()) {
                BY_LABEL.put(cd.label, cd);
            }
        }

        public final String label;

        CompiledDependency(final String label) {
            this.label = label;
        }

        public static boolean hasCompiledDependency(String label){
            return BY_LABEL.containsKey(label);
        }
    }

}