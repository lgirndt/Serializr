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
package serializr.typesystem;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/*
*
*/
public class TypeMatchTest {

    @Test
    public void testMatchesFully() {
        TypeMatch m1 = TypeMatch.createFullTypeMatch("A", "b", "c", "d");
        TypeMatch m2 = TypeMatch.createFullTypeMatch("A", "b", "c", "d");

        assertThat(m1.matches(m2), is(true));
    }

    @Test
    public void testMatchesFullyDoesNotMatch() {
        TypeMatch m1 = TypeMatch.createFullTypeMatch("A", "a", "b", "c");
        TypeMatch m2 = TypeMatch.createFullTypeMatch("B", "a", "b", "c");

        assertThat(m1.matches(m2), not(is(true)));
    }

    @Test
    public void testMatchesLocally() {
        TypeMatch testee = TypeMatch.createLocalTypeMatch("A");
        TypeMatch tester = TypeMatch.createFullTypeMatch("A", "a", "b", "c");

        assertThat(testee.matches(tester), is(true));
    }
}
