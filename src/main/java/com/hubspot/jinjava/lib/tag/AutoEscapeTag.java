package com.hubspot.jinjava.lib.tag;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.hubspot.jinjava.doc.annotations.JinjavaDoc;
import com.hubspot.jinjava.doc.annotations.JinjavaSnippet;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.tree.Node;
import com.hubspot.jinjava.tree.TagNode;

@JinjavaDoc(
    value = "Autoescape the tag's contents",
    hidden = true,
    snippets = {
        @JinjavaSnippet(
            code = "{% autoescape %}\n" +
                "<div>Code to escape</div>\n" +
                "{% endautoescape %}"
        )
    })
public class AutoEscapeTag implements Tag {
  private static final long serialVersionUID = 786006577642541285L;

  @Override
  public String getName() {
    return "autoescape";
  }

  @Override
  public String getEndTagName() {
    return "endautoescape";
  }

  @Override
  public String interpret(TagNode tagNode, JinjavaInterpreter interpreter) {
    interpreter.enterScope();
    try {
      boolean oldEscapeFlag = BooleanUtils.toBoolean(StringUtils.trim(tagNode.getHelpers()));
      interpreter.getContext().put("autoescape", !oldEscapeFlag);

      StringBuilder result = new StringBuilder();

      for (Node child : tagNode.getChildren()) {
        result.append(child.render(interpreter));
      }

      return result.toString();
    } finally {
      interpreter.leaveScope();
    }
  }

}
