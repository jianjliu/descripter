/* Generated By:JJTree: Do not edit this line. ECMAFunctionDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=ECMA,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.descripter.js.parse;

public
class ECMAFunctionDeclaration extends AbstractNode {
  public ECMAFunctionDeclaration(int id) {
    super(id);
  }

  public ECMAFunctionDeclaration(JSParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JSParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=953457d6da14e6e48d9e64b1dfa58094 (do not edit this line) */