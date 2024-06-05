package kamel.unoengine.concretion.rule;

import kamel.unoengine.abstraction.ApplyBehavior;
import kamel.unoengine.abstraction.CheckBehavior;

public class Rule {
    private String ruleName;
    private CheckBehavior ruleValidator;
    private ApplyBehavior handler;

    public Rule(String ruleName, CheckBehavior ruleValidator, ApplyBehavior handler) {
        this.ruleName = ruleName;
        this.ruleValidator = ruleValidator;
        this.handler = handler;
    }

    public String getRuleName() {
        return ruleName;
    }

    public CheckBehavior getRuleValidator() {
        return ruleValidator;
    }

    public void setRuleValidator(CheckBehavior ruleValidator) {
        this.ruleValidator = ruleValidator;
    }

    public ApplyBehavior getHandler() {
        return handler;
    }

    public void setHandler(ApplyBehavior handler) {
        this.handler = handler;
    }
}
