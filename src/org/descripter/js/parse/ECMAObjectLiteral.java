/* Generated By:JJTree: Do not edit this line. ECMAObjectLiteral.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=ECMA,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.descripter.js.parse;

public
class ECMAObjectLiteral extends AbstractNode {
  public ECMAObjectLiteral(int id) {
    super(id);
  }

  public ECMAObjectLiteral(JSParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JSParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=591ff8196fc874d420971925b5717b79 (do not edit this line) */
