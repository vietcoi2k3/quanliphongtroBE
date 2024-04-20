package com.apec.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class TableEntity {
	@Id
	private int id;
	private String table_code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTable_code() {
		return table_code;
	}

	public void setTable_code(String table_code) {
		this.table_code = table_code;
	}

}
