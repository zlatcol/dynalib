package controllers

import play.test._
import org.junit._
import org.junit.Assert._
import controllers.Operations

class OperationsTest {
	
    @Test
    def divisionShouldWork8div4() {
        assertEquals(2, Operations.division(8,4), 0.000001); // A really important thing to test
    }
    
   @Test
    def divisionShouldWork4div8() {
        assertEquals(0.5, Operations.division(4,8), 0.000001); // A really important thing to test
    }
   
    @Test
    def divisionShouldWork5div8() {
        assertEquals(0.625, Operations.division(5,8), 0.000001); // A really important thing to test
    }
    
    @Test
    def multiplicationShouldWorkAsIntended() {
    	assertEquals(1,Operations.multiplication(1,1))
    	assertEquals(2,Operations.multiplication(2,1))
    	assertEquals(2,Operations.multiplication(1,2))
    	assertEquals(9,Operations.multiplication(3,3))
    	assertEquals(0,Operations.multiplication(1,0))
    }
    
    @Test
    def greatestCommonDivisionShouldWorkAsIntended() {
    	assertEquals(1,Operations.gcd(1,1))
    	assertEquals(1,Operations.gcd(2,1))
    	assertEquals(1,Operations.gcd(5,3))
    	assertEquals(1,Operations.gcd(51,11))
    	assertEquals(4,Operations.gcd(8,4))
    	assertEquals(10,Operations.gcd(30,20))
    }
    
    @Test
    def leastCommonDivisorShouldWorkAsIntended() {
    	assertEquals(1,Operations.lcd(1,1))
    	assertEquals(8,Operations.lcd(4,8))
    	assertEquals(12,Operations.lcd(4,6))
    }
}