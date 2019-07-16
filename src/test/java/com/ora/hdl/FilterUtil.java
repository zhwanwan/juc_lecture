package com.ora.hdl;

import java.util.Deque;
import java.util.LinkedList;

/**
 *
 */
public class FilterUtil {

    private static final char LBRACKET = '(';
    private static final char RBRACKET = ')';

    /**
     * Checks if the attribute's value is null.
     * Throws an IllegalArgumentException if the value is null.
     *
     * @param name  attributeName
     * @param value
     */
    private static void validateNotNullArg(String name, Object value) {
        if (value == null) {
            String errorMsg = name + " argument cannot be null.";
            /**
             *       if (AppsLogger.isEnabled(AppsLogger.SEVERE)) {
             *         AppsLogger.write(FilterManager.class, errorMsg, AppsLogger.SEVERE);
             *       }
             */
            throw new IllegalArgumentException(errorMsg);

        }
    }

    /**
     * Checks if the brackets of filterText are matched.
     * Throws an IllegalArgumentException if brackets unmatched.
     * Example:
     * Validated:   (Branch EQUALTO 103 AND Account EQUALTO 0008)
     * Invalidated: (Branch EQUALTO 103 AND Account EQUALTO 0008
     * <p>
     * (A EQ 10 OR (A EQ 11 AND B EQ 10))
     *
     * @param filterText
     */
    private static void validateFilterText(String filterText) {
        //validateNotNullArg("filterText", filterText);
        String errorMsg = "Brackets are not matched: " + filterText;
        Deque<Character> deque = new LinkedList<Character>();
        //filterText = filterText.trim();

        char c;
        boolean isMatched = true;

        for (int i = 0, len = filterText.length(); i < len; i++) {
            c = filterText.charAt(i);
            if (FilterUtil.LBRACKET == c) {
                deque.push(c);
            } else if (FilterUtil.RBRACKET == c) {
                if (deque.isEmpty()) {
                    isMatched = false;
                    break;
                } else {
                    deque.pop();
                }
            }
        }

        if (!deque.isEmpty() || !isMatched) {
            throw new IllegalArgumentException(errorMsg);
        }
    }


    public static void main(String[] args) {
        String filterText = "(Branch Equals to 103)\n" +
                "AND (   Account Equals to 0008\n" +
                "     OR Account Equals to 1120)";
        validateFilterText(filterText);
    }


}
