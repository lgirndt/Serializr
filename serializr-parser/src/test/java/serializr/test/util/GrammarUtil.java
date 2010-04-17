package serializr.test.util;

import serializr.grammar.SerializrParser;
import serializr.parser.ParserFactory;

import java.io.IOException;

/**
 *
 */
public class GrammarUtil {
    public static SerializrParser toParser() throws IOException {
        SerializrParser parser = new ParserFactory().createParser("seq MyName {}");
        return parser;
    }
}
