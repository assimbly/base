package org.assimbly.util.helper;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlHelperTest {

    private static final String LINK = "<![CDATA[/transactions?dateFrom=2026-11-20&size=2]]>";

    @Test
    void prettyPrintKeepsCdataSections() {
        String result = XmlHelper.prettyPrint(XmlHelper.newDocument("<HttpData><Link>" + LINK + "</Link></HttpData>"));

        assertTrue(result.contains("<Link>" + LINK + "</Link>"), result);
        assertFalse(result.contains("&amp;"), result);
    }

    @Test
    void mergedDocumentKeepsCdataSections() {
        Document enriched = XmlHelper.newDocument("<Enriched/>");
        XmlHelper.mergeIn(enriched, XmlHelper.newDocument("<HttpData><Link>" + LINK + "</Link></HttpData>"));

        String result = XmlHelper.prettyPrint(enriched);

        assertTrue(result.contains("<Link>" + LINK + "</Link>"), result);
        assertFalse(result.contains("&amp;"), result);
    }
}
