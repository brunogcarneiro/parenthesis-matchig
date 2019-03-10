package com.brunocarneiro;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.Stack;

public class ParenthesisMatching {
    
    private static ParenthesisMatching pm = new ParenthesisMatching();
    Stack<OpenningBracket> stack = new Stack<>();

    public static void main(String[] args) {
        
        String[] tests = {
            "(a[0]+b[2c[6]]){24 + 53}",
            "f(e(d))",
            "[()]{}([])",
            "((b)",
            "(c]",
            "{(a[]))}",
            "([)]",
            ")(",
            "empty"
        };
        
        Arrays.stream(tests)
            .map(pm::check)
            .forEach(System.out::println);
    }
    
    private Boolean check(String sample) {
        Stream<Character> characterStream = sample
                                             .chars()
                                              .mapToObj(c -> (char) c);
        
        try {
            characterStream
                .forEach ( pm::processCharacter );
            
            if (stack.size() > 0) {
                throw new NotBalancedException(); 
            }
            
            return true;
        } catch (InvalidCharacterException | NotBalancedException e){
            //e.printStackTrace();
            return false;
        }
    }
    
    private void processCharacter(Character c) throws InvalidCharacterException, NotBalancedException {
        CharInput b = new BracketFactory().parse(c);
        
        if (b instanceof OpenningBracket) {
            stack.push((OpenningBracket) b);
        } else if (b instanceof ClosingBracket) {
            if (stack.size() == 0){ 
                throw new NotBalancedException(); //try to pop an bracket but there is no more.
            }
            
            pop((ClosingBracket) b);
        } else {
            //Ignore NonBracket
        }
    }
    
    private void pop(ClosingBracket currentBracket){
        OpenningBracket stackBracket = stack.pop();
        
        if ( ! matchBracketTypes(stackBracket, currentBracket) ) {
            throw new NotBalancedException(); 
        }
    }
    
    private boolean matchBracketTypes(OpenningBracket stackBracket, ClosingBracket currentBracket){
        return (stackBracket instanceof CurlyOpenning ) ? currentBracket instanceof CurlyClosing :
               (stackBracket instanceof BoxOpenning   ) ? currentBracket instanceof BoxClosing   :
               (stackBracket instanceof RoundOpenning ) ? currentBracket instanceof RoundClosing 
                                                        : false;
    }
    
    private class BracketFactory {
        public CharInput parse(Character c) throws InvalidCharacterException {
            CharInput ci = null;
            switch (c) {
                case '{': ci = new CurlyOpenning(); break;
                case '[': ci = new BoxOpenning();   break;
                case '(': ci = new RoundOpenning(); break;
                
                case '}': ci = new CurlyClosing(); break;
                case ']': ci = new BoxClosing();   break;
                case ')': ci = new RoundClosing(); break;
                
                default:
                    ci = new NonBracket(); 
            }
            
            return ci;
        }
    }
    
    /**
     * Related types
     */
    private abstract class CharInput {}
    
    private class NonBracket extends CharInput {}
    
    private abstract class Bracket extends CharInput {}
    
    private abstract class OpenningBracket extends Bracket {}
    private class CurlyOpenning extends OpenningBracket{}
    private class BoxOpenning extends OpenningBracket{}
    private class RoundOpenning extends OpenningBracket{}
    
    private abstract class ClosingBracket extends Bracket {}
    private class CurlyClosing extends ClosingBracket{}
    private class BoxClosing extends ClosingBracket{}
    private class RoundClosing extends ClosingBracket{}
    
    /**
     * Related Exceptions
     * 
     * Runtime exception was choosen because checked exceptions don’t bubble up in the pipeline processing of a stream
     */
    private class InvalidCharacterException extends RuntimeException {}
    private class NotBalancedException extends RuntimeException {}
}
