/* Generated By:JJTree: Do not edit this line. ECMAIfStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=ECMA,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.descripter.js.parse;

public
class ECMAIfStatement extends AbstractNode {
  public ECMAIfStatement(int id) {
    super(id);
  }

  public ECMAIfStatement(JSParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JSParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d387ad18204e3c7663bb8e662b0b1014 (do not edit this line) */
