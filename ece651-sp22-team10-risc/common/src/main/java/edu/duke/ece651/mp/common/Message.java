package edu.duke.ece651.mp.common;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/*
 * Message Class contains the type of the order, owner of request, source
 * destination, destination and the number of units requested. The Message
 * objects are transfered from client to server to realize communication.
 */
public class Message implements Serializable {
  private final String type;
  private final Color owner;
  private final String source;
  private final String dest;
  private final List<Unit> allocatedUnits;
  private final String target;

  public Message() {
    this("");
  }

  // use for sending confirmation
  public Message(String msg) {
    this(msg, null, "", "", new ArrayList<>(), ""); 
  }

  // use for issuing order
  public Message(String type, Color owner, String source, String dest, List<Unit> allocatedUnits) {
    this(type, owner, source, dest, allocatedUnits, "");
  }

  // Use for Unit upgrade
  public Message(String type, Color owner, String source, List<Unit> allocatedUnits, String target) {
    this(type, owner, source, "", allocatedUnits, target);
  }

  // Use for tech level upgrade & research cloaking
  public Message(String type, Color owner) {
    this(type, owner, "", "", new ArrayList<>(), "");
  }

  // Use for territroy cloaking
  public Message(String type, Color owner, String source) {
    this(type, owner, source, "", new ArrayList<>(), "");
  }

  public Message(String type, Color owner, String source, String dest, List<Unit> allocatedUnits, String target) {
    this.type = type;
    this.owner = owner;
    this.source = source;
    this.dest = dest;
    this.allocatedUnits = allocatedUnits;
    this.target = target;
  }

  public String getType() {
    return type;
  }

  public Color getOwner() {
    return owner;
  }

  public String getSource() {
    return source;
  }

  public String getDest() {
    return dest;
  }

  public String getTarget() {
    return target;
  }

  public List<Unit> getAllocatedUnits() {
    return allocatedUnits;
  }

  @Override
  public String toString() {
    String str = "";
    if (owner != null) {
      str  += owner.toString() + ": ";
    }
    str += type + ", " + source + ", " + dest + ", " + allocatedUnits.size() + " units";
    /*
    for (Unit unit : allocatedUnits) {
      str += ", " + unit;
    }
    */
    return str;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass().equals(getClass())) {
      Message msg = (Message) obj;
      if (toString().equals(msg.toString())) {
        return true;
      }
    }
    return false;
  }

}
