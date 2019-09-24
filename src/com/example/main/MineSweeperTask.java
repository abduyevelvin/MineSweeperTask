package com.example.main;

import com.example.impl.MineSweeperImpl;

public class MineSweeperTask {

	public static void main(String[] args) {
		MineSweeperImpl mine = new MineSweeperImpl();
		
		String mineField = "*...\n..*.\n....";
		mine.setMineField(mineField);
		System.out.println(mine.getHintField());
	}

}
