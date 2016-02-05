package com.mohanshashanka.models;

import java.sql.Date;

public class BorrowDetails {
	private int borrowId;
	private Date borrowDate;
	private Date returnDate;
	private int lateCharge;
	
	enum Status {
		CHECKED_OUT, RETURNED, COMPLETE; 
	}

	public int getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getLateCharge() {
		return lateCharge;
	}

	public void setLateCharge(int lateCharge) {
		this.lateCharge = lateCharge;
	}

}
