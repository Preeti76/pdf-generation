package com.example.demo.repository;

import java.awt.color.ColorSpace;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

@Service
@Transactional
public class UserRepositoryImpl  {
	
	
	@Autowired
	UserRepository repo;

	@PersistenceContext
	EntityManager manager;

	public User createUser(User user) {
		
		
		//return repo.save(user);
		manager.persist(user);
		return user;
		
	}
	
	public List<User> getUser() {
		
	
	return manager.createQuery("From User", User.class).getResultList();
	
	 

	
	
		//return repo.findAll();
		
	}

	public User getOneUser(int id) {


		return repo.getById(id);
	}
	
	
	public void generatePdf()  {
		List<User> ls=this.getUser();
		String dest = "C:\\Users\\Rony\\Desktop\\abc.pdf"; 
		PdfWriter writer = null;
		try {
			writer = new PdfWriter(dest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Creating a PdfDocument  
		PdfDocument pdfDoc = new PdfDocument(writer);
		// Creating a Document   
		//Document document = new Document(pdfDoc); 
		
		Document my_pdf_report = new Document(pdfDoc);
        //PdfWriter.getInstance(my_pdf_report, new FileOutputStream("dest"));
		// Creating a table object 
		float [] pointColumnWidths = {150F, 150F, 150F}; 
		Table table = new Table(pointColumnWidths);
		table.addHeaderCell("User_name").setMaxWidth(10);
		table.addHeaderCell("Password");
		table.addHeaderCell("Email");
		table.addHeaderCell("Address");
		table.addHeaderCell("Phone");
		
		
		List<String> stringList = new ArrayList<String>();
		for(User user : ls){
			stringList.add(user.getUsername());
			stringList.add(user.getAddress());
			stringList.add(user.getEmail());
			stringList.add(user.getPassword());
			stringList.add(user.getPhone());
		}
		
		
		Iterator<String> it=stringList.iterator();
		
		while(it.hasNext()) {
			String username=it.next();
			System.out.println(username);
			table.addCell(username);
			
			String pasword=it.next();
			System.out.println(pasword);
			table.addCell(pasword);
			String email=it.next();
			System.out.println(email);
			table.addCell(email);
			String address=it.next();
			System.out.println(address);
			table.addCell(email);
			String phone=it.next();
			System.out.println(phone);
			table.addCell(phone);
			
		}
		
		my_pdf_report.add(table);
		my_pdf_report.close();
	}
}
