package com.mohanshashanka.models;

public class Borrow {
	private int borrowId;
	private int bId;
	private int uId;
	private Status status;
	
	enum Status {
		CHECKED_OUT, RETURNED, COMPLETE; 
	}

	public int getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public int getbId() {
		return bId;
	}
	public void setbId(int bId) {
		this.bId = bId;
	}

	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
