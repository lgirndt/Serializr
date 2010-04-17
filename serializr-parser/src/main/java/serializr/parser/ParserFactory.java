/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package serializr.parser;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.TreeAdaptor;
import serializr.grammar.SerializrLexer;
import serializr.grammar.SerializrParser;

import java.io.File;
import java.io.IOException;

/*
*
*/
public class ParserFactory {

    public Parser createParser(File file) throws IOException {

        CharStream input = new ANTLRFileStream(file.getAbsolutePath());
        SerializrParser parser = createParser(input);

        return parser;
    }

    public Parser createParser(String data) throws IOException {
        CharStream input = new ANTLRStringStream(data);
        return createParser(input);
    }

    private SerializrParser createParser(CharStream input) {
        SerializrLexer lex = new SerializrLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        SerializrParser parser = new SerializrParser(tokens);
        parser.setTreeAdaptor(createTreeAdaptor());
        return parser;
    }

    private TreeAdaptor createTreeAdaptor() {
        return new serializr.ast.TreeAdaptor();
    }
}
