package edu.duke.ece651.mp.common;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Color class: act as player, owned by Territory class.
 * Holds a field called 'name' and functions to get the color's name
 */
public class Color implements Serializable{
  private String name;
  private int technology_level;
  private int food_resource;
  private int sanity_resource;
  public List<String> visible_territories;
  private boolean cloakStatus;

  public Color(String _name) {
    this(_name, new ArrayList<>());
  }
  public Color(String name, List<String> visible_territories) {
    this.name = name;
    this.visible_territories = visible_territories;
    technology_level = 1;
    food_resource = 100;
    sanity_resource = 50;
    cloakStatus = false;

  }

  

  /* 
   * This get the name of color
   */
  public String getName() {
    return name;
  }

   /**
   * This compares whether two Colors are the same
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Color c = (Color) o;
      return c.getName().equals(name);
    }
    return false;
  }

   /**
   * This transforms Color to string
   */
  @Override
  public String toString() {
    return "(" + name + ")";
  }

  /**
   * This transforms the Color string to hashcode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /*
    Get the technology level of this palyer
   */
  public int getTechLevel() {
    return technology_level;
  }

/*
    Get the amount of food resource of this palyer
   */
  public int getFoodResource() {
    return food_resource;
  }

  /*
    Get the amount of sanity source of this palyer
   */
  public int getSanityResource() {
    return sanity_resource;
  }

  /*
    This upgrade the technology level by 1
   */
  public void upgradeTechLevel() {
    technology_level++;
  }

  /*
    This set the technology level back to 1
   */
  public void destroyTechLevel() {
    technology_level = 1;
  }

  /*
    This update food resource by specific amount
   */
  public void updateFoodResource(int modify) {
    food_resource += modify;
  }

  /*
    This update sanity resource by specific amount
   */
  public void updateSanityResource(int modify) {
    sanity_resource += modify;
  }

  public void emptyVisibility() {
    visible_territories.clear();
  }
  
  public void addVisibleTerritory(String terrName) {
    visible_territories.add(terrName);
  }
  
  /*
    This get the cloaking ability status
  */
  public boolean getCloakStatus() {
    return cloakStatus;
  }

  /*
    This update the cloaking ability status
  */
  public void updateCloakStatus(boolean status) {
    cloakStatus = status;
  }

}
