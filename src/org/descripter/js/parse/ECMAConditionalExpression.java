/* Generated By:JJTree: Do not edit this line. ECMAConditionalExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=ECMA,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.descripter.js.parse;

public
class ECMAConditionalExpression extends AbstractNode {
  public ECMAConditionalExpression(int id) {
    super(id);
  }

  public ECMAConditionalExpression(JSParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JSParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=14fd937c65f8275c36fafbd5b7d46563 (do not edit this line) */
