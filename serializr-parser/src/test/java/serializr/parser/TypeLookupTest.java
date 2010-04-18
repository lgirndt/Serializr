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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import serializr.typesystem.SerializrPackage;
import serializr.typesystem.Type;
import serializr.typesystem.TypeRef;
import serializr.typesystem.simples.SimpleType;
import serializr.typesystem.simples.SimpleTypeRef;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
*
*/
public class TypeLookupTest {

    private TestReporter errorReporter;
    private TypeLookup lookup;

    @Before
    public void setUp() throws Exception {
        errorReporter = new TestReporter();
        lookup = new TypeLookup(errorReporter);
    }

    @Test
    public void testTypeFound() throws Exception {

        lookup.foundType(new SimpleType("Foo", "bar"));
        List<Type> types = Lists.newArrayList(lookup.getTypes());

        errorReporter.assertNoError();
        assertThat(types.size(), is(1));
    }

    @Test
    public void testTypeFoundTwice() throws Exception {

        lookup.foundType(new SimpleType("Foo", "bar"));
        lookup.foundType(new SimpleType("Foo", "bar"));

        errorReporter.assertError(1);
    }

    @Test
    public void testTypeRefFound() throws Exception {
        lookup.foundTypeRef(new SimpleTypeRef("Foo"));
        lookup.foundTypeRef(new SimpleTypeRef("Foo"));
        List<TypeRef> typeRefs = Lists.newArrayList(lookup.getTypeRefs());
        errorReporter.assertNoError();
        assertThat(typeRefs.size(), is(2));
    }

    @Test
    public void testLookupFullRef() throws Exception {
        Type expectedType = new SimpleType("Foo", "bar");
        lookup.foundType(expectedType);

        Type type = lookup.lookup(new SimpleTypeRef("Foo", "bar"), new SerializrPackage("bar"));

        assertThat(type, is(expectedType));
    }

    @Test
    public void testLookupLocalRef() throws Exception {
        Type expectedType = new SimpleType("Foo", "bar");
        lookup.foundType(expectedType);

        Type type = lookup.lookup(new SimpleTypeRef("Foo"), new SerializrPackage("bar"));

        assertThat(type, is(expectedType));
    }

}
