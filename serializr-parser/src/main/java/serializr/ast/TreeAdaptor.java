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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.BaseTreeAdaptor;
import serializr.grammar.SerializrParser;

/*
*
*/
public class TreeAdaptor extends BaseTreeAdaptor {


    @Override
    public Token createToken(int tokenType, String text) {
        return new SerializrToken(tokenType, text);
    }

    @Override
    public Token createToken(Token fromToken) {
        return new SerializrToken(fromToken);
    }

    public Object create(Token payload) {

        if (payload == null) {
            return new DefaultNode();
        }

        switch (payload.getType()) {
            case SerializrParser.SEQUENCE:
                return new SequenceNode(payload);
            case SerializrParser.FIELD:
                return new FieldNode(payload);
            case SerializrParser.PRIMITIVE_TYPE_REF:
                return new PrimitiveTypeRefNode(payload);
            case SerializrParser.COMPLEX_TYPE_REF:
                return new CompexTypeRefNode(payload);
            case SerializrParser.QNAME:
                return new QualifiedNameNode(payload);
            case SerializrParser.ROLE_REF:
                return new RoleRefNode(payload);
            case SerializrParser.ROLE:
                return new RoleNode(payload);
            case SerializrParser.MODIFIER:
                return new ModifierNode(payload);
            default:
                return new DefaultNode(payload);
        }
    }

    public Object dupNode(Object treeNode) {
        return new DefaultNode((Node) treeNode);
    }

    public Token getToken(Object t) {
        return asNode(t).getToken();
    }

    public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
        if (t == null) {
            return;
        }
        Node node = (Node) t;
        node.setTokenStartIndex(startToken.getTokenIndex());
        node.setTokenStopIndex(stopToken.getTokenIndex());
    }

    public int getTokenStartIndex(Object t) {
        return asNode(t).getTokenStartIndex();
    }

    public int getTokenStopIndex(Object t) {
        return asNode(t).getTokenStopIndex();
    }


    public Object getParent(Object t) {
        return asNode(t).getParent();
    }

    public void setParent(Object t, Object parent) {
        asNode(t).setParent(asNode(parent));
    }

    public int getChildIndex(Object t) {
        return asNode(t).getChildIndex();
    }

    public void setChildIndex(Object t, int index) {
        asNode(t).setChildIndex(index);
    }

    public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
        asNode(parent).replaceChildren(startChildIndex, stopChildIndex, t);
    }

    private Node asNode(Object o) {
        return (Node) o;
    }

}
