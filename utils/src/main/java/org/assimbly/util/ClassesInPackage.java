package org.assimbly.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;

@SuppressWarnings("rawtypes")
public class ClassesInPackage {

        protected static final Logger log = LoggerFactory.getLogger("org.assimbly.util.ClassesInPackage");

        public Set<Class> findClasses(String packageName) {

            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toSet());
        }

        private Class getClass(String className, String packageName) {
            try {
                return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
            } catch (ClassNotFoundException e) {
                log.error("Class not found: " + className + "Reason: " + e.getMessage());
            }
            return null;
        }

    public Set<Class> findAllClasses(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName()
                        .equalsIgnoreCase(packageName))
                .map(clazz -> clazz.load())
                .collect(Collectors.toSet());
    }

}