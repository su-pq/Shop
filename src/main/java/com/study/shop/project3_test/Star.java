package com.study.shop.project3_test;

import java.io.IOException;

public class Star {
	public static void main(String[] args) throws IOException {
		int num = 4;
		for(int i = 0; i<num; i++) {
			for(int j = num-i-1; j>0; j--) {
				System.out.print(" ");
				
			}
			for(int j = 1; j<=i*2+1; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
}
