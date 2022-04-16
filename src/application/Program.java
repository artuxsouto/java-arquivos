package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) throws ParseException {

		Scanner sc = new Scanner(System.in);
		Locale.setDefault(Locale.US);
		
		List<Product> product = new ArrayList<>();
		
		System.out.print("Enter file path: ");
		String strPath = sc.nextLine();
		
		File sourceFile = new File(strPath);
		String sourceStrPath = sourceFile.getParent();
		
		boolean success = new File(sourceStrPath + "\\out").mkdir();
		
		String targetFileStr = sourceStrPath + "\\out\\summary.csv";
		
		try (BufferedReader br = new BufferedReader(new FileReader(strPath))) {
			
			
			String itemCsv = br.readLine();
			
			while(itemCsv != null) {
				String[] fields = itemCsv.split(",");
				String name = fields[0];
				double price = Double.parseDouble(fields[1]);
				int quantity = Integer.parseInt(fields[2]);
				
				product.add(new Product(name, price, quantity));
				
				itemCsv = br.readLine();
				
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(strPath))) {
				for (Product i : product) {
					bw.write(i.getName() + "," + String.format("%.2f", i.total()));
					bw.newLine();
				}
				
				System.out.println(targetFileStr + " CREATED!");
				
			}catch (IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			}
		}
		catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		
		sc.close();
	}
}