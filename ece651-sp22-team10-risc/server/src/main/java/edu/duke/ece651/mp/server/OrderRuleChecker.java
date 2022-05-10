package edu.duke.ece651.mp.server;

import java.util.HashMap;
import edu.duke.ece651.mp.common.*;

/*
 * Abstract class OrderRuleChecker: Check if the input Message class corresponds to rules. 
 */
public abstract class OrderRuleChecker {
  private final OrderRuleChecker next;

  public OrderRuleChecker(OrderRuleChecker next) {
    this.next = next;
  }

  /*
   * Allow checkers check on the rule by implementing this method.
   */
  protected abstract String checkMyRule(RiscGame myGame, Message message);

  /*
   * This function allows multiple checkers make a chain.
   */
  public String checkOrder(RiscGame myGame, Message message) {
    String result = checkMyRule(myGame, message);
    // Use recursion to get message.
    
    if (result != null || next == null) {
      return result;
    }
    else {
      return next.checkOrder(myGame, message);
    }
  }
}

