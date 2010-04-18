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

import org.antlr.runtime.RecognitionException;
import serializr.grammar.SerializrParser;
import serializr.typesystem.TranslationUnit;

import java.io.IOException;

/*
*
*/
public class ModelFactory {

    private static interface CreateParserCallback {
        SerializrParser createParser(ParserFactory parserFactory) throws IOException;
    }

    public TranslationUnit createModel(final String data) throws ModelCreationException {
        return createModel(new CreateParserCallback() {
            @Override
            public SerializrParser createParser(ParserFactory parserFactory) throws IOException {
                return parserFactory.createParser(data);
            }
        });
    }

    private TranslationUnit createModel(CreateParserCallback callback) throws ModelCreationException {
        DefaultErrorReporter errorReporter = new DefaultErrorReporter();
        try {
            return parseAndResolve(callback, errorReporter);

        } catch (IOException e) {
            throw new ModelCreationException(e.getMessage(), errorReporter.getErrorMessages());
        } catch (RecognitionException e) {
            throw new ModelCreationException(e.getMessage(), errorReporter.getErrorMessages());
        }
    }

    private TranslationUnit parseAndResolve(CreateParserCallback callback, DefaultErrorReporter errorReporter) throws IOException, RecognitionException, ModelCreationException {

        TypeResolver resolver = new TypeResolver(errorReporter);

        ParserFactory parserFactory = new ParserFactory();
        parserFactory.withTypeParsingEventListener(resolver);

        SerializrParser parser = callback.createParser(parserFactory);

        TranslationUnit unit = (TranslationUnit) parser.translationUnit().getTree();
        extractErrors(parser, errorReporter);

        resolver.resolve(unit);

        if (errorReporter.hasErrors()) {
            throw new ModelCreationException(errorReporter.getErrorMessages());
        }

        return unit;
    }

    private void extractErrors(SerializrParser parser, DefaultErrorReporter errorReporter) {
        for (RecognitionException e : parser.getOccuredErrors()) {
            errorReporter.reportError(e.getMessage());
        }
    }

}
