package serializr.ast;

import com.google.common.base.Joiner;
import serializr.grammar.SerializrParser;
import serializr.parser.ParserFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public class GrammarUtil {

    public static String toStr(String... line) {
        return Joiner.on("\n").join(Arrays.asList(line));
    }

    public static SerializrParser toParser(String... lines) throws IOException {
        return new ParserFactory().createParser(toStr(lines));
    }

}
