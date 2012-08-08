package controllers

object Operations {
	
	def doOperation(x: Int, y: Int, operation: String): Any = {
		var result: Any = 0
		var op = operation
		
		if(operation.equalsIgnoreCase("addition")) {
			result = addition(x,y)
		}
		else if (operation.equalsIgnoreCase("subtraction")) {
			result = subtraction(x,y)
			
		} else if (operation.equalsIgnoreCase("multiplication")) {
			result = multiplication(x,y)
		} else if (operation.equalsIgnoreCase("division")) {
			result = division(x,y)
		} else if (operation.equalsIgnoreCase("gcd")) {
			result = gcd(x,y) 
		} else if (operation.equalsIgnoreCase("lcd")) {
			result = lcd(x,y)
		} else {
			result = 0
			op = "error"
		}
		DBHandler.saveResultInDb(result, op)
		return result
	}
	
	def addition(x: Int, y: Int): Int = {
		return x + y
	}
	
	def subtraction(x: Int, y: Int): Int = {
		return x - y
	}
	
	def multiplication(x: Int, y: Int): Int = {
		return x*y
	}
	
	def division(x: Int, y: Int): Float = {
		return x.asInstanceOf[Float]/y.asInstanceOf[Float]
	}
	
	def gcd(x: Int, y: Int): Int = {
		var xtemp = x
		var ytemp = y
		var result = 0
		while(xtemp != ytemp) {
			if (xtemp > ytemp) {
				xtemp = xtemp-ytemp
			}
			if (xtemp < ytemp) {
				ytemp = ytemp-xtemp
			}
		}
		return xtemp
	}
	
	def lcd(x: Int, y: Int): Int = {
		return (multiplication(x,y)/gcd(x,y)).asInstanceOf[Int]
	}

}