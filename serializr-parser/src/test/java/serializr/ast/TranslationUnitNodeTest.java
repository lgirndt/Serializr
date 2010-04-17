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

import com.google.common.collect.Lists;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import serializr.typesystem.Role;
import serializr.typesystem.Sequence;
import serializr.typesystem.SerializrPackage;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
*
*/
public class TranslationUnitNodeTest {

    private String[] translationUnit() {
        return new String[]{
                "package foo.bar;",
                "",
                "role Role123;",
                "role Role345;",
                "role AnotherRole;",
                "",
                "seq Seq1 is Role123(12) {",
                "   aField : Long, ",
                "   anotherField : Int",
                "}",
                "",
                "seq Seq2 is Role345(23) {",
                "   optional seq1 : Seq1",
                "}"
        };
    }

    @Test
    public void testGetPackage() throws Exception {
        TranslationUnitNode unit = parse(translationUnit());
        SerializrPackage expected = new SerializrPackage("foo", "bar");
        assertThat(unit.getPackage(), is(expected));
    }

    @Test
    public void testGetRoles() throws Exception {
        TranslationUnitNode unit = parse(translationUnit());
        List<Sequence> seqs = Lists.newArrayList(unit.getSequences());
        assertThat(seqs.size(), is(2));
    }

    @Test
    public void testGetSequences() throws Exception {
        TranslationUnitNode unit = parse(translationUnit());
        List<Role> roles = Lists.newArrayList(unit.getRoles());
        assertThat(roles.size(), is(3));
    }

    private TranslationUnitNode parse(String... lines) throws IOException, RecognitionException {
        return (TranslationUnitNode) GrammarUtil.toParser(lines).translationUnit().getTree();
    }
}
