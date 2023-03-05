package org.assimbly.util;

import dev.jeka.core.api.depmanagement.*;
import dev.jeka.core.api.depmanagement.resolution.*;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            URLClassLoader child = new URLClassLoader(new URL[] {url}, this.getClass().getClassLoader());

            ArrayList<String> classNames = getClassNamesFromJar(path.toString());

            for (String className : classNames) {
                Class classToLoad = Class.forName(className, true, child);
                classes.add(classToLoad);
            }
        }

        return classes;

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

    public enum PredefinedBlocks {

        AGGREGATE("aggregate"),
        AMAZON("amazon"),
        APPENDTOBODY("appendtobody"),
        BASE64TOTEXT("base64totext"),
        CONTINUEFLOW("continueflow"),
        CSVTOXML("csvtoxml"),
        CONNECT("CONNECT"),
        DELAY("delay"),
        DELETE("delete"),
        DYNAMIC("dynamic"),
        EDITOXML("editoxml"),
        EDIFACTTOXML("edifacttoxml"),
        EDIFACTSTANDARDSTOXML("edifactstandardstoxml"),
        ENRICH("enrich"),
        EXCELTOXML("exceltoxml"),
        FILTER("filter"),
        FMUTA("fmuta"),
        FORMTOXML("formtoxml"),
        GET("get"),
        GOOGLEDRIVE("googledrive"),
        GROOVY("groovy"),
        HEAD("head"),
        HL7ER7("hl7er7"),
        HL7XML("hl7xml"),
        HTTP("http"),
        HTTPS("https"),
        JAVA("java"),
        JOOR("joor"),
        JSONTOXML("jsontoxml"),
        LOG("log"),
        MANAGEFLOW("manageflow"),
        MLLP("mllp"),
        MULTIPART("multipart"),
        OAUTH2TOKEN("oauth2token"),
        OPTIONS("OPTIONS"),
        PATCH("PATCH"),
        PAUSEFLOW("pauseflow"),
        PDF("pdf"),
        POLLENRICH("pollenrich"),
        POST("POST"),
        PREPENDTOBODY("prependtobody"),
        PRINT("print"),
        PUT("PUT"),
        PYTHON("python"),
        QUEUETHROTTLE("queuethrottle"),
        RABBITMQ("rabbitmq"),
        REMOVEHEADERS("removeheaders"),
        REMOVECOOKIE("removecookie"),
        REPLACE("replace"),
        RESUMEFLOW("resumeflow"),
        SETBODY("setbody"),
        SETBODYBYHEADER("setbodybyheader"),
        SETCOOKIE("setcookie"),
        SETHEADER("setheader"),
        SETHEADERS("setheaders"),
        SETMESSAGE("setmessage"),
        SETPATTERN("setpattern"),
        SETPROPERTY("setproperty"),
        SETUUID("setuuid"),
        SIMPLE("simple"),
        SIMPLEREPLACE("simplereplace"),
        STARTFLOW("startflow"),
        STOPFLOW("stopflow"),
        SUSPENDFLOW("suspendflow"),
        SPLIT("split"),
        TEXTTOBASE64("texttobase64"),
        TRACE("TRACE"),
        THROTTLE("throttle"),
        UNIVOCITYCSV("univocity-csv"),
        UNZIP("unzip"),
        VELOCITY("velocity"),
        WIRETAP("wiretap"),
        XMLTOEDI("xmltoedi"),
        XMLTOEDIFACT("xmltoedifact"),
        XMLTOEDIFACTSTANDARDS("xmltoedifactstandards"),
        XMLTOCSV("xmltocsv"),
        XMLTOEXCEL("xmltoexcel"),
        XMLTOJSON("xmltojson"),
        XSLT("xslt"),
        ZIP("zip"),
        ;

        private static Map<String, PredefinedBlocks> BY_LABEL = new HashMap<>();

        static {
            for (PredefinedBlocks cd : values()) {
                BY_LABEL.put(cd.label, cd);
            }
        }

        public final String label;

        PredefinedBlocks(final String label) {
            this.label = label;
        }

        public static boolean hasBlock(String label){
            return BY_LABEL.containsKey(label);
        }
    }


    //These components are part of baseComponentsModule
    public enum CompiledDependency {

        ACTIVEMQ("activemq"),
        AGGREGATE("aggregate"),
        ALERIS("aleris"),
        AMAZON("amazon"),
        AMAZONMQ("amazonmq"),
        AMQP("amqp"),
        AMQPS("amqps"),
        ARCHIVE("archive"),
        AS2("as2"),
        AWS2S3("aws2-s3"),
        BASE64TOTEXT("base64totext"),
        BEAN("bean"),
        CONTROLBUS("controlbus"),
        CSVTOXML("csvtoxml"),
        DATAFORMAT("dataformat"),
        DELAY("delay"),
        DIRECT("direct"),
        DIRECTVM("direct-vm"),
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
        FTP("ftp"),
        FTPS("ftps"),
        GOOGLEDRIVE("googledrive"),
        GROOVY("groovy"),
        HL7XML("hl7xml"),
        HTTP("http"),
        HTTPS("https"),
        IBMMQ("ibmmq"),
        IMAP("imap"),
        IMAPS("imaps"),
        JAVA("java"),
        JETTY("jetty"),
        JETTYNOSSL("jetty-nossl"),
        JMS("jms"),
        JSONTOXML("jsontoxml"),
        KAFKA("kafka"),
        KAMELET("kamelet"),
        LOG("log"),
        MLLP("mllp"),
        MULTIPART("multipart"),
        NETTY("netty"),
        NETTYHTTP("netty-http"),
        PDF("pdf"),
        PDFTOTEXT("pdftotext"),
        POLLENRICH("pollenrich"),
        POP3("pop3"),
        PRINT("print"),
        QUARTZ("quartz"),
        QUEUETHROTTLE("queuethrottle"),
        RABBITMQ("rabbitmq"),
        REMOVECOOKIE("removecookie"),
        REMOVEHEADERS("removeheaders"),
        REPLACE("replace"),
        REST("rest"),
        RESTSOPENAPI("rest-openapi"),
        RESTSWAGGER("rest-swagger"),
        SANDBOX("sandbox"),
        SCHEDULER("scheduler"),
        SEDA("seda"),
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
        SFTP("sftp"),
        SIMPLEREPLACE("simplereplace"),
        SJMS("sjms"),
        SJMS2("sjms2"),
        SLACK("slack"),
        SMTP("smtp"),
        SOAP("soap"),
        SONICMQ("sonicmq"),
        SPLIT("split"),
        SPRING("spring"),
        SQL("sql"),
        SQLCUSTOM("sql-custom"),
        STREAM("stream"),
        STUB("stub"),
        TEXTTOBASE64("texttobase64"),
        THROTTLE("throttle"),
        TIMER("timer"),
        UNDERTOW("undertow"),
        UNIVOCITYCSV("univocity-csv"),
        UNZIP("unzip"),
        VELOCITY("velocity"),
        VERTXHTTP("vertx-http"),
        VM("vm"),
        WASTEBIN("wastebin"),
        WEBSOCKET("websocket"),
        WIRETAP("wiretap"),
        XMLTOCSV("xmltocsv"),
        XMLTOEDI("xmltoedi"),
        XMLTOEDIFACT("xmltoedifact"),
        XMLTOEDIFACTSTANDARDS("xmltoedifactstandards"),
        XMLTOEXCEL("xmltoexcel"),
        XMLTOJSON("xmltojson"),
        XSLT("xslt"),
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