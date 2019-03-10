package com.brunocarneiro;

import java.util.Stack;
import java.util.stream.Stream;

public class ParenthesisMatchingClassHierarchy {

    Stack<OpenningBracket> stack = new Stack<>();
    
    public Boolean check(String sample) {
        Stream<Character> characterStream = sample
                                             .chars()
                                              .mapToObj(c -> (char) c)
                                              .filter(ch -> "(){}[]".indexOf(ch) >= 0);
        
        try {
            characterStream
                .forEach ( this::processCharacter );
            
            if (stack.size() > 0) {
                throw new NotBalancedException(); 
            }
            
            return true;
        } catch (InvalidCharacterException | NotBalancedException e){
            //e.printStackTrace();
            return false;
        } finally {
        	stack.clear(); //reset stack for next check
		}
        
    }
    
    private void processCharacter(Character c) throws InvalidCharacterException, NotBalancedException {
        Bracket b = new BracketFactory().parse(c);
        
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
        
        if ( ! stackBracket.matchClosing(currentBracket) ) {
            throw new NotBalancedException(); 
        }
    }
    
    private class BracketFactory {
        public Bracket parse(Character c) throws InvalidCharacterException {
        	Bracket bracket = null;
            switch (c) {
                case '{': bracket = new CurlyOpenning(); break;
                case '[': bracket = new BoxOpenning();   break;
                case '(': bracket = new RoundOpenning(); break;
                
                case '}': bracket = new CurlyClosing(); break;
                case ']': bracket = new BoxClosing();   break;
                case ')': bracket = new RoundClosing(); break;
                
                default:
                    throw new InvalidCharacterException(); 
            }
            
            return bracket;
        }
    }
    
    /**
     * Related types
     */
    private abstract class Bracket {}
    
    private abstract class OpenningBracket extends Bracket {
    	public abstract boolean matchClosing(ClosingBracket cb);
    }
    
    private class CurlyOpenning extends OpenningBracket{
    	@Override
    	public boolean matchClosing(ClosingBracket cb) {
    		return cb instanceof CurlyClosing;
    	}
    }
    private class BoxOpenning extends OpenningBracket{
    	@Override
    	public boolean matchClosing(ClosingBracket cb) {
    		return cb instanceof BoxClosing;
    	}
    }
    private class RoundOpenning extends OpenningBracket{
    	@Override
    	public boolean matchClosing(ClosingBracket cb) {
    		return cb instanceof RoundClosing;
    	}
    }
    
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
