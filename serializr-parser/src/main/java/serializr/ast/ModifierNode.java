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

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;

import java.util.HashSet;
import java.util.Set;

/*
*
*/
public class ModifierNode extends DefaultNode {

    public ModifierNode(Token t) {
        super(t);
    }

    public Set<String> getModifiers() {
        return toStringSet();
    }

    public boolean containsOptional() {
        return getModifiers().contains("optional");
    }

    private HashSet<String> toStringSet() {
        return Sets.newHashSet(
                Iterables.transform(new TreeChildrenIterable(this),
                        new Function<Tree, String>() {

                            @Override
                            public String apply(Tree tree) {
                                return tree.getText();
                            }
                        })
        );
    }
}
