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

import org.junit.Test;
import serializr.ast.GrammarUtil;
import serializr.typesystem.Field;
import serializr.typesystem.Role;
import serializr.typesystem.Sequence;
import serializr.typesystem.TranslationUnit;

import static org.junit.Assert.assertNotNull;

/*
*
*/
public class ModelFactoryTest {

    @Test
    public void testCreateModel() throws Exception {
        ModelFactory modelFactory = new ModelFactory();
        TranslationUnit unit = modelFactory.createModel(GrammarUtil.toStr(
                "package foo.bar;",
                "role Role1;",
                "role Role2;",
                "seq MySeq is Role1(42) { aField : Role1, forWard : AnotherSeq };",
                "seq AnotherSeq is Role2(23) { anInt : Int, aLong : Long };",
                "seq SeqWithList { aPrimitiveList : List[Int], aSeqList : List[MySeq] };"
        ));
        assertNotNull(unit);
        for (Role role : unit.getRoles()) {
            assertNotNull(role);
            assertNotNull(role.getName());
            assertNotNull(role.getPackage());
        }
        for (Sequence seq : unit.getSequences()) {
            assertNotNull(seq);
            for (Field field : seq.getFields()) {
                assertNotNull(field);
                assertNotNull(field.getName());
                assertNotNull(field.getTypeRef());
                assertNotNull(field.getTypeRef().getSerializrType());
            }
        }
    }
}
