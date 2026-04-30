package org.assimbly.util.helper;

import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.AutoDetectParser;

import java.io.IOException;
import java.io.InputStream;

public final class MimeTypeHelper {

    private MimeTypeHelper() {}

    public static MediaType detectMimeType(InputStream content) {
        AutoDetectParser parser = new AutoDetectParser();
        Detector detector = parser.getDetector();

        try {
            return detector.detect(content, new Metadata());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MediaType.TEXT_PLAIN;
    }

    public static String findFileExtension(String mimeType) {
        try {
            return MimeTypes.getDefaultMimeTypes().forName(mimeType).getExtension();
        } catch (MimeTypeException e) {
            return null;
        }
    }
}