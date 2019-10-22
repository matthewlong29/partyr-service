package com.partygames.partygamesservice.util;

import org.springframework.stereotype.Component;

@Component
public class PartyrLogger {
  public static final String ANSI_RESET = "\u001B[0m"; // reset
  public static final String ANSI_RED = "\u001B[31m"; // error
  public static final String ANSI_GREEN = "\u001B[32m"; // query
  public static final String ANSI_YELLOW = "\u001B[33m"; // warning
  public static final String ANSI_BLUE = "\u001B[34m"; // info
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";

  /**
   * error.
   */
  static public void error(String message) {
    System.out.println("[" + ANSI_RED + "ERROR" + ANSI_RESET + "] " + ANSI_RED + message + ANSI_RESET);
  }

  /**
   * warning.
   */
  static public void warning(String message) {
    System.out.println("[" + ANSI_YELLOW + "WARNING" + ANSI_RESET + "] " + ANSI_YELLOW + message + ANSI_RESET);
  }

  /**
   * info.
   */
  static public void info(String message) {
    System.out.println("[" + ANSI_BLUE + "INFO" + ANSI_RESET + "] " + ANSI_BLUE + message + ANSI_RESET);
  }

  /**
   * query.
   */
  static public void query(String message) {
    System.out.println("[" + ANSI_GREEN + "QUERY" + ANSI_RESET + "] " + ANSI_GREEN + message + ANSI_RESET);
  }
}