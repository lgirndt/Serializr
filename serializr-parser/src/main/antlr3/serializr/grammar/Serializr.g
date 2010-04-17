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
	ROLE_REFS;
	SEQUENCE_BODY;
	TYPES;
	TYPEREF;
	FIELD;
	
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
            ( '.'! IDENTIFIER )*		
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
	-> ^(SEQUENCE IDENTIFIER ^(ROLE_REFS seqBody?) ^(SEQUENCE_BODY roleRefList?) ) 
	;
	
seqBody
	:	
	'{'!
	(seqBodyDeclaration(',' seqBodyDeclaration)*)?
	'}'! ';'!
	;

seqBodyDeclaration
	:
	fieldDeclaration
	;

fieldDeclaration
	:
	modifiers variableDeclaration ':' type -> ^(FIELD ^(TYPEREF type) variableDeclaration modifiers?)
	;

variableDeclaration
	:
	IDENTIFIER
	;
	

roleRefList
	:	roleRef (','! roleRef)*	
	;	
	
roleRef
	:	IDENTIFIER '<' NUMBER '>'
	;
		
typeOrCollectionType
	: type | collectionType;
	
type	: 
	roleOrSeqType | primitiveType
	;

primitiveType
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
	:	collectionTypeName '['! type ']'!;	
	
collectionTypeName
	:	'List'
	;	
	
roleOrSeqType
	:
	qualifiedName
	;	
	
roleDeclaration
	:
	'role' IDENTIFIER '<'! primitiveIntegerNumberType '>'! ';'!
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
	:	'0'..'9';
			
/* everything hidden */
WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}	
    ;
