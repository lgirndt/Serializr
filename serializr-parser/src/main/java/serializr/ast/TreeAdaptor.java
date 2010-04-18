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
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.BaseTreeAdaptor;
import serializr.grammar.SerializrParser;
import serializr.typesystem.Type;
import serializr.typesystem.TypeRef;

import java.util.List;

/*
*
*/
public class TreeAdaptor extends BaseTreeAdaptor {

    private final List<TypeParsingEventListener> typeParsingListeners = Lists.newArrayList();

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
            case SerializrParser.UNIT:
                return newTranslationUnit(payload);
            case SerializrParser.SEQUENCE:
                return newSequenceNode(payload);
            case SerializrParser.FIELD:
                return newFieldNode(payload);
            case SerializrParser.PRIMITIVE_TYPE_REF:
                return newPrimitiveTypeRefNode(payload);
            case SerializrParser.COMPLEX_TYPE_REF:
                return newComplexTypeRefNode(payload);
            case SerializrParser.COLLECTION_TYPE_REF:
                return newCollectionTypeRefNode(payload);
            case SerializrParser.QNAME:
                return newQualifiedNameNode(payload);
            case SerializrParser.ROLE_REF:
                return newRoleRefNode(payload);
            case SerializrParser.ROLE:
                return newRoleNode(payload);
            case SerializrParser.MODIFIER:
                return newModifierNode(payload);
            default:
                return newDefaultNode(payload);
        }
    }

    private Object newCollectionTypeRefNode(Token payload) {
        return new CollectionTypeRefNode(payload);
    }

    private Object newDefaultNode(Token payload) {
        return new DefaultNode(payload);
    }

    private Object newModifierNode(Token payload) {
        return new ModifierNode(payload);
    }

    private Object newRoleNode(Token payload) {
        RoleNode roleNode = new RoleNode(payload);
        fireFoundType(roleNode);
        return roleNode;
    }

    private Object newRoleRefNode(Token payload) {
        return new RoleRefNode(payload);
    }

    private Object newQualifiedNameNode(Token payload) {
        return new QualifiedNameNode(payload);
    }

    private Object newComplexTypeRefNode(Token payload) {
        CompexTypeRefNode node = new CompexTypeRefNode(payload);
        fireFoundTypeRef(node);
        return node;
    }

    private Object newPrimitiveTypeRefNode(Token payload) {
        PrimitiveTypeRefNode node = new PrimitiveTypeRefNode(payload);
        fireFoundTypeRef(node);
        return node;
    }

    private Object newFieldNode(Token payload) {
        return new FieldNode(payload);
    }

    private Object newSequenceNode(Token payload) {
        SequenceNode node = new SequenceNode(payload);
        fireFoundType(node);
        return node;
    }

    private Object newTranslationUnit(Token payload) {
        return new TranslationUnitNode(payload);
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

    public void addListener(TypeParsingEventListener typeParsingEventListener) {
        typeParsingListeners.add(typeParsingEventListener);
    }

    public void removeListener(TypeParsingEventListener typeParsingEventListener) {
        typeParsingListeners.remove(typeParsingEventListener);
    }

    private void fireFoundTypeRef(TypeRef typeRef) {
        for (TypeParsingEventListener listener : typeParsingListeners) {
            listener.foundTypeRef(typeRef);
        }
    }

    private void fireFoundType(Type type) {
        for (TypeParsingEventListener listener : typeParsingListeners) {
            listener.foundType(type);
        }
    }

}
