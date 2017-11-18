package mx.databeetle.core.sanitizer

import groovy.transform.CompileStatic

import java.text.Normalizer

@CompileStatic
class StringSanitizer {

    static String sanitizeContent(String content) {
        String sanitizedString = Normalizer.normalize(content, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        sanitizedString = sanitizedString.replaceAll("\\.+", " ")
        sanitizedString = sanitizedString.replaceAll("[\\\\|/|:|\\*|\\?|\\<|\\>|\\|\\[|\\]|\\{|\\}|@|]", " ")
        sanitizedString = sanitizedString.replaceAll("\\s+", " ")
        return sanitizedString
    }
}
