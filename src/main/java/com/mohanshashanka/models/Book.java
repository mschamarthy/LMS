package com.mohanshashanka.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
	private IntegerProperty bId;
	private StringProperty name;
	private StringProperty author;
	private StringProperty category;
	private DoubleProperty cost;
	private DoubleProperty rentalCost;
	private IntegerProperty quantity;

	public int getBId() {
		return bId.get();
	}

	public void setBId(int bId) {
		this.bId = new SimpleIntegerProperty(bId);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(String author) {
		this.author = new SimpleStringProperty(author);
	}

	public String getCategory() {
		return category.get();
	}

	public void setCategory(String category) {
		this.category = new SimpleStringProperty(category);
	}

	public Double getCost() {
		return cost.get();
	}

	public void setCost(Double cost) {
		this.cost = new SimpleDoubleProperty(cost);
	}

	public Double getRentalCost() {
		return rentalCost.get();
	}

	public void setRentalCost(Double rentalCost) {
		this.rentalCost = new SimpleDoubleProperty(rentalCost);
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	@Override
	public String toString() {
		return "Book id: " + this.bId + ", Name: " + this.name + ", Author: " + this.author;
	}


}
