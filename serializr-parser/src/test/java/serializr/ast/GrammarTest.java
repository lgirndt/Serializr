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
package serializr.ast;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import serializr.grammar.SerializrParser;

import java.io.IOException;

/*
*
*/
public class GrammarTest {

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
                "seq SeqName is Role1(1) {",
                "   aField : Int",
                "};"
        );
    }

    @Test
    public void testSeqWithMultipleRoles() throws Exception {
        assertValidSeq(
                "seq SeqName is Role1(1), Role2(2) {",
                "   aField : Int",
                "};"
        );
    }

    @Test
    public void testSeqWithComplexField() throws Exception {
        assertValidSeq(
                "seq SeqName { aField : ComplexType };"
        );
    }

    @Test
    public void testSeqEmpty() throws Exception {
        assertValidSeq("seq SeqName {};");
    }

    @Test
    public void testSeqWithLargerRoleToken() throws Exception {
        assertValidSeq("seq SeqName is Role(1234) {};");
    }

    @Test
    public void testOptionalFieldQualifier() throws Exception {
        assertValidSeq("seq SeqName { optional foo : Int };");
    }

    @Test
    public void testRoleDeclaration() throws Exception {
        assertValidRole("role Role1;");
    }

    @Test
    public void testCorrectUnit() throws Exception {
        SerializrParser parser = GrammarUtil.toParser(
                "package foo.bar;",
                "",
                "role MyRole;",
                "",
                "seq MySeq is MyRole(0) {",
                "   id : Int,",
                "   optional aField : Int,",
                "   complexType : ComplexType",
                "};"
        );
        parser.translationUnit();
        GrammarAssert.assertValidParsing(parser);
    }

    private void assertValidSeq(String... lines) throws IOException, RecognitionException {
        SerializrParser parser = GrammarUtil.toParser(lines);
        GrammarUtil.parseSeq(parser);
        GrammarAssert.assertValidParsing(parser);
    }

    private void assertValidRole(String... lines) throws IOException, RecognitionException {
        SerializrParser parser = GrammarUtil.toParser(lines);
        parser.roleDeclaration().getTree();
        GrammarAssert.assertValidParsing(parser);
    }

}
