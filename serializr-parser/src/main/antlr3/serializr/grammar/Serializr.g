grammar Serializr;

options
{
language=Java;
output=AST;
ASTLabelType=CommonTree;
}

tokens
{
	UNIT;
	PACKAGE;
	
	SEQUENCE;
	ROLE;
	ROLE_REFS;
	ROLE_REF;
	SEQUENCE_BODY;
	TYPES;
	PRIMITIVE_TYPE_REF;
	COMPLEX_TYPE_REF;
	COLLECTION_TYPE_REF;
	FIELD;
	MODIFIER;
	
	QNAME;
}

@header
{
package serializr.grammar;

import java.util.List;

}

@lexer::header
{
package serializr.grammar;
}

@members
{
	private List<RecognitionException> occuredErrors = new ArrayList<RecognitionException>();

	private boolean suppressErrors = false;

	@Override
	public void reportError(RecognitionException e)
	{
		occuredErrors.add(e);
		super.reportError(e);
	}	
	
	// ANTLR must provide this information natively, but how???
	public boolean success()
	{
		return occuredErrors.size() == 0;
	}
	
	public List<RecognitionException> getOccuredErrors()
	{
		return occuredErrors;
	}
	
	public void setSuppressErrors(boolean suppress)
	{
		this.suppressErrors = suppress;
	}
	
	@Override
	public void emitErrorMessage(String errorMsg)
	{
		if( !suppressErrors)
		{
			super.emitErrorMessage(errorMsg);
		}
	}
	
	@Override
	public void beginResync()
	{
	}
	
	@Override
	public void endResync()
	{
	}
		
}

translationUnit
	: 	
	packageDeclaration		
	(seqOrRoleDeclaration)*
	-> ^(UNIT packageDeclaration ^(TYPES seqOrRoleDeclaration+));

packageDeclaration
    :    'package' qualifiedName
         ';'
         -> ^(PACKAGE qualifiedName) 
    ;

qualifiedName 
    :   IDENTIFIER
            ( '.' IDENTIFIER )* -> ^(QNAME IDENTIFIER+)		
    ; 

modifiers 
	:
	( 'optional' )*	
	;
	
seqOrRoleDeclaration
	:	(seqDeclaration) | (roleDeclaration)
	;	
	
seqDeclaration
	:	'seq'  IDENTIFIER ('is' roleRefList)? seqBody	
	-> ^(SEQUENCE IDENTIFIER ^(SEQUENCE_BODY seqBody?) ^(ROLE_REFS roleRefList?) ) 
	;
	
seqBody
	:	
	'{'!
	(seqBodyDeclaration(','! seqBodyDeclaration)*)?
	'}'! ';'!
	;

seqBodyDeclaration
	:
	fieldDeclaration
	;

fieldDeclaration
	:
	modifiers variableDeclaration ':' typeOrCollectionType 
	-> ^(FIELD typeOrCollectionType variableDeclaration ^(MODIFIER modifiers? ) )
	;

variableDeclaration
	:
	IDENTIFIER
	;
	

roleRefList
	:	roleRef (','! roleRef)*	
	;	
	
roleRef
	:	roleOrSeqType '(' NUMBER ')' -> ^(ROLE_REF roleOrSeqType NUMBER)
	;
		
typeOrCollectionType
	:  collectionType | type;
	
type	: 
	roleOrSeqType | primitiveType
	;

primitiveType
	:	primitiveTypeItems -> ^(PRIMITIVE_TYPE_REF primitiveTypeItems)
	;

primitiveTypeItems
	:	'Boolean'
	|	'Char'
	|	primitiveIntegerNumberType
	|	'Float'
	|	'Double'
	;
	
primitiveIntegerNumberType
	:
		'Byte'
	|	'Short'
	|	'Int'
	|	'Long'
	;	
	
collectionType
	: 'List' '[' type ']' -> ^(COLLECTION_TYPE_REF type)
	;	
		
roleOrSeqType
	:
	qualifiedName -> ^(COMPLEX_TYPE_REF qualifiedName)
	;	
	
roleDeclaration
	:
	'role' IDENTIFIER ';' -> ^(ROLE IDENTIFIER)
	;
		
    
IDENTIFIER
    :   IdentifierStart IdentifierPart*
    ;

fragment
IdentifierStart
    	:	'a'..'z'
    	|	'A'..'Z'
    	|	'_'
    	;

fragment 
IdentifierPart
	:	'a'..'z'
    	|	'A'..'Z'	
    	|	'0'..'9'
    	|	'_'
	;
NUMBER
	:	('0'..'9')+;
	
/*	
COLLECTION_NAME
	:	'List'
	;
*/
			
/* everything hidden */
WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}	
    ;
