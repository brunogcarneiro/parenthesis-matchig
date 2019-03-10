package com.brunocarneiro;

import java.util.Stack;
import java.util.stream.Stream;

public class ParenthesisMatchingEnum {

    Stack<Bracket> stack = new Stack<>();
    
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
        
        if (b.isOpenning()) {
            stack.push(b);
        } else {
            pop(b);
        }
    }
    
    private void pop(Bracket currentBracket){
		 if (stack.size() == 0){ 
		     throw new NotBalancedException(); //try to pop an bracket but there is no more.
		 }
    	 
    	Bracket stackBracket = stack.pop();
        
        if ( ! stackBracket.matchClosing(currentBracket) ) {
            throw new NotBalancedException(); 
        }
    }
    
    private class BracketFactory {
        public Bracket parse(Character c) throws InvalidCharacterException {
        	Bracket bracket = null;
            switch (c) {
                case '{': bracket = Bracket.CURLY_OPENNING; break;
                case '[': bracket = Bracket.BOX_OPENNING;   break;
                case '(': bracket = Bracket.ROUND_OPPENING; break;
                
                case '}': bracket = Bracket.CURLY_CLOSING; break;
                case ']': bracket = Bracket.BOX_CLOSING;   break;
                case ')': bracket = Bracket.ROUND_CLOSING; break;
                
                default:
                    throw new InvalidCharacterException(); 
            }
            
            return bracket;
        }
    }
    
    private enum Bracket {
    	CURLY_OPENNING {
    		public boolean matchClosing(Bracket bracket) { return bracket == Bracket.CURLY_CLOSING;}
    		public boolean isOpenning() {return true;}
    	},
    	BOX_OPENNING {
    		public boolean matchClosing(Bracket bracket) {return bracket == Bracket.BOX_CLOSING;}
    		public boolean isOpenning() {return true;}
    	},
    	ROUND_OPPENING {
    		public boolean matchClosing(Bracket bracket) {return bracket == Bracket.ROUND_CLOSING;}
    		public boolean isOpenning() {return true;}
    	},
    	CURLY_CLOSING, BOX_CLOSING, ROUND_CLOSING;
    	
    	public boolean matchClosing(Bracket bracket) {throw new UnsupportedOperationException();}
    	public boolean isOpenning() {return false;}
    }
    
    /**
     * Related Exceptions
     * 
     * Runtime exception was choosen because checked exceptions don’t bubble up in the pipeline processing of a stream
     */
    private class InvalidCharacterException extends RuntimeException {}
    private class NotBalancedException extends RuntimeException {}
}
