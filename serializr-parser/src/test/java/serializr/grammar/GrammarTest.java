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
package serializr.grammar;

import com.google.common.base.Joiner;
import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;
import serializr.ast.SequenceNode;
import serializr.parser.ParserFactory;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/*
*
*/
public class GrammarTest {
    private ParserFactory parserFactory;

    @Before
    public void setUp() throws Exception {
        parserFactory = new ParserFactory();
    }

    @Test
    public void testSimpleSeq() throws Exception {
        assertValidSeq(
                "seq SeqName {",
                "   aField : Int,",
                "   anotherField: String",
                "};"
        );
    }

    @Test
    public void testSeqWithSingleRole() throws Exception {
        assertValidSeq(
                "seq SeqName is Role1<1> {",
                "   aField : Int",
                "};"
        );
    }

    @Test
    public void testSeqWithMultipleRoles() throws Exception {
        assertValidSeq(
                "seq SeqName is Role1<1>, Role2<2> {",
                "   aField : Int",
                "};"
        );
    }

    @Test
    public void testSeqEmpty() throws Exception {
        assertValidSeq("seq SeqName {};");
    }

    @Test
    public void testOptionalFieldQualifier() throws Exception {
        assertValidSeq("seq SeqName { optional foo : Int };");
    }

    @Test
    public void testRoleDeclaration() throws Exception {
        assertValidRole("role Role1<Int>;");
    }

    @Test
    public void testInvalidRoleDeclaration() throws Exception {
        SerializrParser parser = createParserOnLines("role Role<Foo>;");
        parser.roleDeclaration();
        assertInvalidParsing(parser);
    }

    @Test
    public void testCorrectUnit() throws Exception {
        SerializrParser parser = createParserOnLines(
                "package foo.bar;",
                "",
                "role MyRole<Long>;",
                "",
                "seq MySeq is MyRole<0> {",
                "   id : Int,",
                "   optional aField : Int",
                "};"
        );
        parser.translationUnit();
        assertValidParsing(parser);
    }

    private void assertValidSeq(String... lines) throws IOException, RecognitionException {
        SerializrParser parser = createParserOnLines(lines);
        parseSeq(parser);
        assertValidParsing(parser);
    }

    private void assertValidRole(String... lines) throws IOException, RecognitionException {
        SerializrParser parser = createParserOnLines(lines);
        parser.roleDeclaration().getTree();
        assertValidParsing(parser);
    }

    private void assertValidParsing(SerializrParser parser) {
        assertEquals(0, parser.getOccuredErrors().size());
    }

    private void assertInvalidParsing(SerializrParser parser) {
        if (parser.getOccuredErrors().size() == 0) {
            fail("Expected parser to fail.");
        }
    }

    private SerializrParser createParserOnLines(String... lines) throws IOException {
        return parserFactory.createParser(toStr(lines));
    }

    private SequenceNode parseSeq(SerializrParser parser) throws RecognitionException {
        return (SequenceNode) parser.seqDeclaration().getTree();
    }

    private static String toStr(String... line) {
        return Joiner.on("\n").join(Arrays.asList(line));
    }
}
