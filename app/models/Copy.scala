package models

import java.util.Date

case class Copy (
	var id: Int,
	var bookId: Int,
	var borrowedBy: Option[Int],
	var borrowedAt: Option[Date],
	var backBy: Option[Date]	
)