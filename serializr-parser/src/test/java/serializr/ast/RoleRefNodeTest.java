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
import serializr.test.util.GrammarUtil;
import serializr.typesystem.TypeMatch;
import serializr.typesystem.TypeRef;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/*
*
*/
public class RoleRefNodeTest {

    @Test
    public void testGetTypeRef() throws Exception {
        RoleRefNode roleRef = parse("MyRole(42)");
        TypeRef typeRef = roleRef.getTypeRef();
        TypeMatch expected = TypeMatch.createFullTypeMatch("MyRole", "some", "pkg");

        assertNotNull(typeRef);
        assertThat(typeRef.getTypeMatch().matches(expected), is(true));
    }

    @Test
    public void testGetRoleToken() throws Exception {
        RoleRefNode roleRef = parse("MyRole(42)");
        assertThat(roleRef.getRoleToken(), is(42));
    }

    private RoleRefNode parse(String line) throws IOException, RecognitionException {
        return (RoleRefNode) GrammarUtil.toParser(line).roleRef().getTree();
    }
}
