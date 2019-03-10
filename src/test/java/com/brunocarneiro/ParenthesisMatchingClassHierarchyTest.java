package com.brunocarneiro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class ParenthesisMatchingClassHierarchyTest 
{
	ParenthesisMatchingClassHierarchy pm = new ParenthesisMatchingClassHierarchy();
	
    @Test
    public void test1()
    {
        assertTrue(pm.check("(a[0]+b[2c[6]]){24 + 53}"));
    }
    
    @Test
    public void test2()
    {
        assertTrue(pm.check("f(e(d))"));
    }
    
    @Test
    public void test3()
    {
        assertTrue(pm.check("[()]{}([])"));
    }
    
    @Test
    public void test4()
    {
        assertTrue(!pm.check("((b)"));
    }
    
    @Test
    public void test5()
    {
        assertTrue(!pm.check("(c]"));
    }
    
    @Test
    public void test6()
    {
        assertTrue(!pm.check("{(a[]))}"));
    }
    
    @Test
    public void test7()
    {
        assertTrue(!pm.check("([)]"));
    }
    
    @Test
    public void test8()
    {
        assertTrue(!pm.check(")("));
    }
    
    @Test
    public void test9()
    {
        assertTrue(pm.check("empty"));
    }
}
