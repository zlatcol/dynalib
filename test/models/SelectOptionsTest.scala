package models

import play.test._
import org.junit._
import org.junit.Assert._
import models.SelectOptions

class SelectOptionsTest {
     
    @Test
    def selectOptionShouldHaveCorrectNumberOfElements() {
        assertEquals(6, SelectOptions.options.size);
    }
    
    @Test
    def someOtherOptionShouldContailValueHejHej() {
    	
    	assertEquals("hejhej",SelectOptions.someOtherOptions(0))
    }
 
}

